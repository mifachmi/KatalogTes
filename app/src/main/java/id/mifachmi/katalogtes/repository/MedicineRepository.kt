package id.mifachmi.katalogtes.repository

import id.mifachmi.katalogtes.model.Medicine

class MedicineRepository {
    private val medicineList = listOf(
        Medicine(
            id = "1",
            name = "Paracetamol",
            description = "Obat ini digunakan untuk meredakan nyeri dan menurunkan demam.",
            price = 5000.0,
            image = "paracetamol.png"
        ),
        Medicine(
            id = "2",
            name = "Amoxicillin",
            description = "Obat ini digunakan untuk mengobati infeksi bakteri.",
            price = 10000.0,
            image = "amoxicillin.png"
        ),
        Medicine(
            id = "3",
            name = "Betadine",
            description = "Obat ini digunakan untuk mengobati luka.",
            price = 7000.0,
            image = "betadine.png"
        ),
        Medicine(
            id = "4",
            name = "Promag",
            description = "Obat ini digunakan untuk meredakan sakit maag.",
            price = 8000.0,
            image = "promag.png"
        ),
        Medicine(
            id = "5",
            name = "Bodrex",
            description = "Obat ini digunakan untuk meredakan sakit kepala.",
            price = 6000.0,
            image = "bodrex.png"
        ),
        Medicine(
            id = "6",
            name = "Antangin",
            description = "Obat ini digunakan untuk meredakan flu.",
            price = 9000.0,
            image = "antangin.png"
        ),
        Medicine(
            id = "7",
            name = "Diapet",
            description = "Obat ini digunakan untuk meredakan sakit perut.",
            price = 7500.0,
            image = "diapet.png"
        ),
        Medicine(
            id = "8",
            name = "Neozep",
            description = "Obat ini digunakan untuk meredakan pilek.",
            price = 8500.0,
            image = "neozep.png"
        ),
        Medicine(
            id = "9",
            name = "Caviplex",
            description = "Obat ini digunakan untuk meningkatkan daya tahan tubuh.",
            price = 12000.0,
            image = "caviplex.png"
        ),
        Medicine(
            id = "10",
            name = "Vitacimin",
            description = "Obat ini digunakan untuk meningkatkan daya tahan tubuh.",
            price = 11000.0,
            image = "vitacimin.png"
        ),
        Medicine(
            id = "11",
            name = "Panadol",
            description = "Obat ini digunakan untuk meredakan nyeri dan menurunkan demam.",
            price = 5500.0,
            image = "panadol.png"
        ),
    )

    fun getMedicineList(): List<Medicine> = medicineList
}