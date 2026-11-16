package com.example.storeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.storeapp.databinding.ItemKasirProdukBinding
import java.text.NumberFormat
import java.util.Locale

class ProdukKasirAdapter(
    private val onAddClick: (ProdukKasir) -> Unit
) : RecyclerView.Adapter<ProdukKasirAdapter.ProdukViewHolder>() {

    private val daftarProduk = mutableListOf<ProdukKasir>()

    inner class ProdukViewHolder(
        val binding: ItemKasirProdukBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProdukViewHolder {
        val binding = ItemKasirProdukBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProdukViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProdukViewHolder,
        position: Int
    ) {
        val produk = daftarProduk[position]
        val binding = holder.binding

        binding.txtGambarProduk.text = produk.gambar
        binding.txtNamaProduk.text = produk.nama

        val formatRupiah = NumberFormat.getCurrencyInstance(
            Locale("id", "ID")
        )

        binding.txtHargaProduk.text =
            formatRupiah.format(produk.harga)

        binding.btnTambahProduk.setOnClickListener {
            onAddClick(produk)
        }
    }

    override fun getItemCount(): Int = daftarProduk.size

    fun submitList(data: List<ProdukKasir>) {
        daftarProduk.clear()
        daftarProduk.addAll(data)
        notifyDataSetChanged()
    }
}
