package controller

import model.Buku
import model.StatusBuku
import java.util.Scanner

class ManajemenBuku {
    private val daftarBuku = mutableListOf<Buku>()

    init {
        daftarBuku.add(Buku("B001", "Laskar Pelangi", "Andrea Hirata", 2005))
        daftarBuku.add(Buku("B002", "Bumi Manusia", "Pramoedya Ananta", 1980))
        daftarBuku.add(Buku("B003", "Laut Bercerita", "Leila Chudori", 2017 ))
        daftarBuku.add(Buku("B004", "Matahari", "Tere Liye", 2016))
        daftarBuku.add(Buku("B005", "Cantik itu Luka", "Eka Kurniawan", 2004))
    }

    fun tambahBuku(scanner: Scanner) {
        print("Masukkan ID Buku: ")
        val id = scanner.next()
        if (findBukuById(id) != null) {
            println("❌ ID Buku sudah ada.")
            return
        }
        scanner.nextLine()
        print("Masukkan Judul: ")
        val judul = scanner.nextLine()
        print("Masukkan Penulis: ")
        val penulis = scanner.nextLine()
        print("Masukkan Tahun Terbit: ")
        val tahun = scanner.nextInt()

        daftarBuku.add(Buku(id, judul, penulis, tahun))
        println("✅ Buku berhasil ditambahkan!")
    }

    fun tampilkanSemuaBuku() {
        if (daftarBuku.isEmpty()) {
            println("ℹ️ Belum ada data buku.")
            return
        }
        println("\n--- Daftar Buku ---")
        println("--------------------------------------------------------------------")
        println("%-5s | %-20s | %-20s | %-10s | %-10s".format("ID", "Judul", "Penulis", "Tahun", "Status"))
        println("--------------------------------------------------------------------")
        daftarBuku.forEach {
            println("%-5s | %-20s | %-20s | %-10d | %-10s".format(it.id, it.judul, it.penulis, it.tahunTerbit, it.status))
        }
        println("--------------------------------------------------------------------")
    }

    fun findBukuById(id: String): Buku? = daftarBuku.find { it.id.equals(id, ignoreCase = true) }

    fun editBuku(scanner: Scanner) {
        print("Masukkan ID Buku yang akan diedit: ")
        val id = scanner.next()
        val buku = findBukuById(id)
        if (buku == null) {
            println("❌ Buku dengan ID $id tidak ditemukan.")
            return
        }

        scanner.nextLine()
        print("Masukkan Judul baru (kosongkan jika tidak ingin mengubah): ")
        val judulBaru = scanner.nextLine()
        if (judulBaru.isNotBlank()) buku.judul = judulBaru

        print("Masukkan Penulis baru (kosongkan jika tidak ingin mengubah): ")
        val penulisBaru = scanner.nextLine()
        if (penulisBaru.isNotBlank()) buku.penulis = penulisBaru

        print("Masukkan Tahun Terbit baru (0 jika tidak ingin mengubah): ")
        val tahunBaru = scanner.nextInt()
        if (tahunBaru != 0) buku.tahunTerbit = tahunBaru

        println("✅ Data buku berhasil diperbarui!")
    }

    fun hapusBuku(scanner: Scanner) {
        print("Masukkan ID Buku yang akan dihapus: ")
        val id = scanner.next()
        val buku = findBukuById(id)
        if (buku == null) {
            println("❌ Buku dengan ID $id tidak ditemukan.")
            return
        }

        if(buku.status == StatusBuku.DIPINJAM) {
            println("❌ Buku tidak bisa dihapus karena sedang dipinjam.")
            return
        }

        daftarBuku.remove(buku)
        println("🗑️ Buku berhasil dihapus.")
    }
}
