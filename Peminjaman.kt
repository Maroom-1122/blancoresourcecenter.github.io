package model

import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class Peminjaman(
    val idTransaksi: String,
    val buku: Buku,
    val anggota: Anggota,
    val tanggalPinjam: LocalDate,
    var tanggalKembali: LocalDate? = null
) {
    val denda: Long
        get() {
            if (tanggalKembali != null) {
                val batasPengembalian = tanggalPinjam.plusDays(7)
                val keterlambatan = ChronoUnit.DAYS.between(batasPengembalian, tanggalKembali)
                return if (keterlambatan > 0) keterlambatan * 1000 else 0
            }
            return 0
        }
}
