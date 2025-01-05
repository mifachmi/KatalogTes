package id.mifachmi.katalogtes.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.mifachmi.katalogtes.R
import id.mifachmi.katalogtes.databinding.ItemListMedicineBinding
import id.mifachmi.katalogtes.model.Medicine

class MedicineAdapter(
    private val onFavoriteClick: (Medicine) -> Unit
) : ListAdapter<Medicine, MedicineAdapter.MedicineViewHolder>(MedicineDiffCallback()) {

    class MedicineDiffCallback : DiffUtil.ItemCallback<Medicine>() {
        override fun areItemsTheSame(oldItem: Medicine, newItem: Medicine): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Medicine, newItem: Medicine): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val binding = ItemListMedicineBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MedicineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        holder.bind(getItem(position), onFavoriteClick)
    }

    inner class MedicineViewHolder(private val binding: ItemListMedicineBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(medicine: Medicine, onFavoriteClick: (Medicine) -> Unit) {
            val resourceId = itemView.context.resources.getIdentifier(
                medicine.image.removeSuffix(".png"),
                "drawable",
                itemView.context.packageName
            )
            binding.apply {
                tvMedicineName.text = medicine.name
                tvMedicinePrice.text = "Rp ${medicine.price}"
                Glide.with(itemView.context)
                    .load(resourceId)
                    .into(ivMedicine)
                ivFavorite.setImageResource(
                    if (medicine.isFavorited) {
                        R.drawable.ic_star_active
                    } else {
                        R.drawable.ic_star_inactive
                    }
                )
                ivFavorite.setOnClickListener {
                    onFavoriteClick(medicine)
                }
            }
        }

    }
}