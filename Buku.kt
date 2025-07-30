package model

data class Buku(
    var id: String,
    var judul: String,
    var penulis: String,
    var tahunTerbit: Int,
    var status: StatusBuku = StatusBuku.TERSEDIA
)
