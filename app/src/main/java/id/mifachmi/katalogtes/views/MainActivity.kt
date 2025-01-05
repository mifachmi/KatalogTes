package id.mifachmi.katalogtes.views

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.mifachmi.katalogtes.R
import id.mifachmi.katalogtes.databinding.ActivityMainBinding
import id.mifachmi.katalogtes.model.Medicine
import id.mifachmi.katalogtes.utils.GridSpacingItemDecoration
import id.mifachmi.katalogtes.viewmodel.Category
import id.mifachmi.katalogtes.viewmodel.MedicineViewModel
import id.mifachmi.katalogtes.views.adapter.MedicineAdapter

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val medicineViewModel: MedicineViewModel by viewModels()
    private val medicineAdapter: MedicineAdapter by lazy {
        MedicineAdapter(
            onFavoriteClick = { medicine ->
                medicineViewModel.toggleFavorite(medicine)
                showToast(medicine)
            }
        )
    }
    private var category: Category = Category.ALL

    override fun onCreate(savedInstanceState: Bundle?) {//
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupListMedicine()
        searchMedicine()
        setupCategory()
    }

    private fun setupListMedicine() {
        binding.apply {
            rvMedicine.layoutManager = GridLayoutManager(this@MainActivity, 2)

            // Add item decoration for spacing
            val spacingInPixels = resources.getDimensionPixelSize(R.dimen.grid_spacing)
            rvMedicine.addItemDecoration(GridSpacingItemDecoration(2, spacingInPixels, false))

            rvMedicine.adapter = medicineAdapter
            showAllMedicines()
        }
    }

    private fun showFavoritesOnly() {
        medicineViewModel.medicines.observe(this@MainActivity) { medicines ->
            val filteredMedicines = medicines.filter { medicine -> medicine.isFavorited }
            medicineAdapter.submitList(filteredMedicines)
            showHideRv(filteredMedicines)
        }
    }

    private fun showAllMedicines() {
        medicineViewModel.medicines.observe(this@MainActivity) { medicines ->
            medicineAdapter.submitList(medicines)
            showHideRv(medicines)
        }
    }

    private fun searchMedicine() {
        binding.etSearchBar.addTextChangedListener { query ->
            medicineViewModel.updateSearchQuery(query.toString())
            if (category == Category.ALL) {
                medicineViewModel.getFilteredMedicines().let {
                    medicineAdapter.submitList(it)
                    showHideRv(it)
                }
            } else {
                medicineViewModel.getFilteredMedicines().let {
                    val filteredMedicines = it.filter { medicine -> medicine.isFavorited }
                    medicineAdapter.submitList(filteredMedicines)
                    showHideRv(filteredMedicines)
                }
            }

        }
    }

    private fun setupCategory() {
        binding.apply {
            tvAll.setOnClickListener {
                category = Category.ALL
                tvFavorite.background = null
                tvFavorite.setTextColor(
                    AppCompatResources.getColorStateList(
                        this@MainActivity,
                        R.color.black
                    )
                )
                tvAll.background = AppCompatResources.getDrawable(
                    this@MainActivity,
                    R.drawable.bg_rounded_text_active
                )
                tvAll.setTextColor(
                    AppCompatResources.getColorStateList(
                        this@MainActivity,
                        R.color.white
                    )
                )
                showAllMedicines()
            }

            tvFavorite.setOnClickListener {
                category = Category.FAVORITES
                tvAll.background = null
                tvAll.setTextColor(
                    AppCompatResources.getColorStateList(
                        this@MainActivity,
                        R.color.black
                    )
                )
                tvFavorite.background = AppCompatResources.getDrawable(
                    this@MainActivity,
                    R.drawable.bg_rounded_text_active
                )
                tvFavorite.setTextColor(
                    AppCompatResources.getColorStateList(
                        this@MainActivity,
                        R.color.white
                    )
                )
                showFavoritesOnly()
            }
        }
    }

    private fun showHideRv(data: List<Medicine>) {
        if (data.isEmpty()) {
            binding.rvMedicine.visibility = View.GONE
            binding.tvEmptyData.visibility = View.VISIBLE
        } else {
            binding.rvMedicine.visibility = View.VISIBLE
            binding.tvEmptyData.visibility = View.GONE
        }
    }

    private fun showToast(medicine: Medicine) {
        val message = if (medicine.isFavorited) {
            "${medicine.name} dihapus dari favorit"
        } else {
            "${medicine.name} ditambahkan ke favorit"
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}