package controller

import java.util.Scanner

class Menu {
    private val scanner = Scanner(System.`in`)
    private val manajemenBuku = ManajemenBuku()
    private val manajemenAnggota = ManajemenAnggota()
    private val manajemenPeminjaman = ManajemenPeminjaman(manajemenBuku, manajemenAnggota)

    fun showMenu() {
        while (true) {
            println("\n===== Sistem Informasi Perpustakaan =====")
            println("1. Data Buku")
            println("2. Data Anggota")
            println("3. Data Peminjaman")
            println("4. Data Pengembalian")
            println("5. Keluar")
            print("Pilih menu: ")

            when (scanner.nextInt()) {
                1 -> menuData("Buku") { subMenu ->
                    when (subMenu) {
                        1 -> manajemenBuku.tambahBuku(scanner)
                        2 -> manajemenBuku.editBuku(scanner)
                        3 -> manajemenBuku.hapusBuku(scanner)
                        4 -> manajemenBuku.tampilkanSemuaBuku()
                    }
                }
                2 -> menuData("Anggota") { subMenu ->
                    when (subMenu) {
                        1 -> manajemenAnggota.tambahAnggota(scanner)
                        2 -> manajemenAnggota.editAnggota(scanner)
                        3 -> manajemenAnggota.hapusAnggota(scanner)
                        4 -> manajemenAnggota.tampilkanSemuaAnggota()
                    }
                }
                3 -> {
                    println("\n--- Menu Peminjaman ---")
                    println("1. Pinjam Buku")
                    println("2. Tampilkan Data Peminjaman Aktif")
                    println("3. Kembali ke Menu Utama")
                    print("Pilih sub-menu: ")
                    when (scanner.nextInt()) {
                        1 -> manajemenPeminjaman.pinjamBuku(scanner)
                        2 -> manajemenPeminjaman.tampilkanDataPeminjaman()
                        3 -> return@showMenu
                        else -> println("❌ Pilihan tidak valid.")
                    }
                }
                4 -> {
                    println("\n--- Menu Pengembalian ---")
                    println("1. Kembalikan Buku")
                    println("2. Tampilkan Riwayat Pengembalian")
                    println("3. Kembali ke Menu Utama")
                    print("Pilih sub-menu: ")
                    when (scanner.nextInt()) {
                        1 -> manajemenPeminjaman.kembalikanBuku(scanner)
                        2 -> manajemenPeminjaman.tampilkanDataPengembalian()
                        3 -> return@showMenu
                        else -> println("❌ Pilihan tidak valid.")
                    }
                }
                5 -> {
                    println("\n👋 Terima kasih telah mengunjungi Blanko Resource Center.")
                    break
                }
                else -> println("❌ Pilihan tidak valid, silakan coba lagi.")
            }
        }
        scanner.close()
    }

    private fun menuData(namaMenu: String, action: (Int) -> Unit) {
        while (true) {
            println("\n--- Manajemen Data $namaMenu ---")
            println("1. Tambah Data $namaMenu")
            println("2. Edit Data $namaMenu")
            println("3. Hapus Data $namaMenu")
            println("4. Tampilkan Semua Data $namaMenu")
            println("5. Kembali ke Menu Utama")
            print("Pilih sub-menu: ")

            val pilihan = scanner.nextInt()
            if (pilihan == 5) break

            if (pilihan in 1..4) {
                action(pilihan)
            } else {
                println("❌ Pilihan tidak valid.")
            }
        }
    }
}
