package id.mifachmi.katalogtes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.mifachmi.katalogtes.model.Medicine
import id.mifachmi.katalogtes.repository.MedicineRepository

class MedicineViewModel: ViewModel() {
    private val medicineRepository = MedicineRepository()

    private val _medicines = MutableLiveData<List<Medicine>>()
    val medicines: LiveData<List<Medicine>> = _medicines

    private val _searchQuery = MutableLiveData("")
    val searchQuery: LiveData<String> = _searchQuery

    private val _showFavoritesOnly = MutableLiveData(false)
    val showFavoritesOnly: LiveData<Boolean> = _showFavoritesOnly

    init {
        loadMedicines()
    }

    private fun loadMedicines() {
        _medicines.value = medicineRepository.getMedicineList()
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleFavoritesOnly() {
        _showFavoritesOnly.value = !_showFavoritesOnly.value!!
    }

    fun toggleFavorite(medicine: Medicine) {
        _medicines.value = _medicines.value?.map {
            if (it.id == medicine.id) it.copy(isFavorited = !it.isFavorited) else it
        }
    }

    fun getFilteredMedicines(): List<Medicine> {
        val query = _searchQuery.value?.lowercase() ?: ""
        val showFavorites = _showFavoritesOnly.value ?: false

        return _medicines.value?.filter { medicine ->
            (!showFavorites || medicine.isFavorited) &&
                    (query.isEmpty() || medicine.name.lowercase().contains(query))
        } ?: emptyList()
    }
}