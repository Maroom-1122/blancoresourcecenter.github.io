package controller

import model.Anggota
import java.util.Scanner

class ManajemenAnggota {
    private val daftarAnggota = mutableListOf<Anggota>()

    init {
        daftarAnggota.add(Anggota("A001", "Budi Santoso", "Jl. Merdeka No. 10"))
        daftarAnggota.add(Anggota("A002", "Citra Lestari", "Jl. Pahlawan No. 25"))
        daftarAnggota.add(Anggota("A003", "Alwi Marom", "Jl. Melati No. 13"))
        daftarAnggota.add(Anggota("A004", "Lulu Novi", "Jl. Sudirman No. 05"))
        daftarAnggota.add(Anggota("A005", "Kencanawungu", "Jl. Ahmad Yani No. 09"))
    }

    fun tambahAnggota(scanner: Scanner) {
        print("Masukkan ID Anggota: ")
        val id = scanner.next()
        if (findAnggotaById(id) != null) {
            println("❌ ID Anggota sudah ada.")
            return
        }
        scanner.nextLine()
        print("Masukkan Nama Anggota: ")
        val nama = scanner.nextLine()
        print("Masukkan Alamat: ")
        val alamat = scanner.nextLine()

        daftarAnggota.add(Anggota(id, nama, alamat))
        println("✅ Anggota berhasil ditambahkan!")
    }

    fun tampilkanSemuaAnggota() {
        if (daftarAnggota.isEmpty()) {
            println("ℹ️ Belum ada data anggota.")
            return
        }
        println("\n--- Daftar Anggota ---")
        println("----------------------------------------------------------")
        println("%-5s | %-20s | %-25s".format("ID", "Nama", "Alamat"))
        println("----------------------------------------------------------")
        daftarAnggota.forEach {
            println("%-5s | %-20s | %-25s".format(it.id, it.nama, it.alamat))
        }
        println("----------------------------------------------------------")
    }

    fun findAnggotaById(id: String): Anggota? = daftarAnggota.find { it.id.equals(id, ignoreCase = true) }

    fun editAnggota(scanner: Scanner) {
        print("Masukkan ID Anggota yang akan diedit: ")
        val id = scanner.next()
        val anggota = findAnggotaById(id)
        if (anggota == null) {
            println("❌ Anggota dengan ID $id tidak ditemukan.")
            return
        }

        scanner.nextLine()
        print("Masukkan Nama baru (kosongkan jika tidak ingin mengubah): ")
        val namaBaru = scanner.nextLine()
        if (namaBaru.isNotBlank()) anggota.nama = namaBaru

        print("Masukkan Alamat baru (kosongkan jika tidak ingin mengubah): ")
        val alamatBaru = scanner.nextLine()
        if (alamatBaru.isNotBlank()) anggota.alamat = alamatBaru

        println("✅ Data anggota berhasil diperbarui!")
    }

    fun hapusAnggota(scanner: Scanner) {
        print("Masukkan ID Anggota yang akan dihapus: ")
        val id = scanner.next()
        val anggota = findAnggotaById(id)
        if (anggota == null) {
            println("❌ Anggota dengan ID $id tidak ditemukan.")
            return
        }

        daftarAnggota.remove(anggota)
        println("🗑️ Anggota berhasil dihapus.")
    }
}
