package id.mifachmi.katalogtes.model

data class Medicine(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val isFavorited: Boolean = false
)
