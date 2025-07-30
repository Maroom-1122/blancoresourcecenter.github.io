package controller

import model.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Scanner

class ManajemenPeminjaman(
    private val manBuku: ManajemenBuku,
    private val manAnggota: ManajemenAnggota
) {
    private val daftarPeminjaman = mutableListOf<Peminjaman>()
    private var idCounter = 1

    fun pinjamBuku(scanner: Scanner) {
        print("Masukkan ID Anggota: ")
        val idAnggota = scanner.next()
        val anggota = manAnggota.findAnggotaById(idAnggota)
        if (anggota == null) {
            println("❌ Anggota tidak ditemukan.")
            return
        }

        print("Masukkan ID Buku: ")
        val idBuku = scanner.next()
        val buku = manBuku.findBukuById(idBuku)
        if (buku == null) {
            println("❌ Buku tidak ditemukan.")
            return
        }

        if (buku.status == StatusBuku.DIPINJAM) {
            println("❌ Buku sedang dipinjam.")
            return
        }

        buku.status = StatusBuku.DIPINJAM

        val idTransaksi = "T${String.format("%03d", idCounter++)}"
        val peminjaman = Peminjaman(idTransaksi, buku, anggota, LocalDate.now())
        daftarPeminjaman.add(peminjaman)

        println("✅ Buku '${buku.judul}' berhasil dipinjam oleh ${anggota.nama}.")
        println("Batas pengembalian: ${peminjaman.tanggalPinjam.plusDays(7).format(DateTimeFormatter.ISO_LOCAL_DATE)}")
    }

    fun kembalikanBuku(scanner: Scanner) {
        print("Masukkan ID Transaksi Peminjaman: ")
        val idTransaksi = scanner.next()
        val peminjaman = daftarPeminjaman.find { it.idTransaksi.equals(idTransaksi, ignoreCase = true) && it.tanggalKembali == null }

        if (peminjaman == null) {
            println("❌ Transaksi peminjaman aktif tidak ditemukan.")
            return
        }

        peminjaman.buku.status = StatusBuku.TERSEDIA
        peminjaman.tanggalKembali = LocalDate.now()

        println("✅ Buku '${peminjaman.buku.judul}' berhasil dikembalikan.")
        if (peminjaman.denda > 0) {
            println("⚠️ Anda dikenakan denda keterlambatan sebesar: Rp ${peminjaman.denda}")
        }
    }

    fun tampilkanDataPeminjaman() {
        val pinjamanAktif = daftarPeminjaman.filter { it.tanggalKembali == null }
        if (pinjamanAktif.isEmpty()) {
            println("ℹ️ Tidak ada data peminjaman yang sedang aktif.")
            return
        }
        println("\n--- Data Peminjaman Aktif ---")
        println("-----------------------------------------------------------------------------------------")
        println("%-12s | %-20s | %-15s | %-15s | %-15s".format("ID Transaksi", "Judul Buku", "Peminjam", "Tgl Pinjam", "Harus Kembali"))
        println("-----------------------------------------------------------------------------------------")
        pinjamanAktif.forEach {
            println("%-12s | %-20s | %-15s | %-15s | %-15s".format(
                it.idTransaksi,
                it.buku.judul,
                it.anggota.nama,
                it.tanggalPinjam.format(DateTimeFormatter.ISO_LOCAL_DATE),
                it.tanggalPinjam.plusDays(7).format(DateTimeFormatter.ISO_LOCAL_DATE)
            ))
        }
        println("-----------------------------------------------------------------------------------------")
    }

    fun tampilkanDataPengembalian() {
        val pinjamanSelesai = daftarPeminjaman.filter { it.tanggalKembali != null }
        if (pinjamanSelesai.isEmpty()) {
            println("ℹ️ Belum ada data pengembalian buku.")
            return
        }
        println("\n--- Riwayat Pengembalian ---")
        println("----------------------------------------------------------------------------------------------------")
        println("%-12s | %-20s | %-15s | %-15s | %-15s | %-10s".format("ID Transaksi", "Judul Buku", "Peminjam", "Tgl Pinjam", "Tgl Kembali", "Denda (Rp)"))
        println("----------------------------------------------------------------------------------------------------")
        pinjamanSelesai.forEach {
            println("%-12s | %-20s | %-15s | %-15s | %-15s | %-10d".format(
                it.idTransaksi,
                it.buku.judul,
                it.anggota.nama,
                it.tanggalPinjam.format(DateTimeFormatter.ISO_LOCAL_DATE),
                it.tanggalKembali?.format(DateTimeFormatter.ISO_LOCAL_DATE),
                it.denda
            ))
        }
        println("----------------------------------------------------------------------------------------------------")
    }
}
