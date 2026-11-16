package com.example.storeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.storeapp.databinding.FragmentKasirBinding

class KasirFragment : Fragment() {

    private var _binding: FragmentKasirBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ProdukKasirAdapter

    private var kategoriAktif = "Semua"
    private var kataKunci = ""

    // DATA CONTOH SEMENTARA
    private val semuaProduk = listOf(
        ProdukKasir("Air Mineral 600ml", 3000, "Minuman", "🧴"),
        ProdukKasir("Indomie Goreng", 3500, "Makanan", "🍜"),
        ProdukKasir("Teh Botol Sosro", 5000, "Minuman", "🧃"),
        ProdukKasir("Ultra Milk 250ml", 6000, "Minuman", "🥛"),
        ProdukKasir("Susu Cokelat 250ml", 6500, "Minuman", "🥤"),
        ProdukKasir("Lays Original", 8000, "Makanan", "🍟"),
        ProdukKasir("Pop Mie Ayam", 8500, "Makanan", "🍜"),
        ProdukKasir("SilverQueen Cokelat", 12000, "Makanan", "🍫")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKasirBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearch()
        setupKategori()
        tampilkanProduk()
    }

    private fun setupRecyclerView() {
        adapter = ProdukKasirAdapter { produk ->
            Toast.makeText(
                requireContext(),
                "${produk.nama} dipilih. Fitur keranjang akan dibuat nanti.",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.rvProdukKasir.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = this@KasirFragment.adapter
            setHasFixedSize(true)
        }
    }

    private fun setupSearch() {
        binding.edtCariProdukKasir.addTextChangedListener { text ->
            kataKunci = text.toString().trim()
            tampilkanProduk()
        }
    }

    private fun setupKategori() {

        val daftarKategori = listOf(
            binding.btnKategoriSemua to "Semua",
            binding.btnKategoriElektronik to "Elektronik",
            binding.btnKategoriMakanan to "Makanan",
            binding.btnKategoriMinuman to "Minuman",
            binding.btnKategoriLainnya to "Lainnya"
        )

        daftarKategori.forEach { (tombol, kategori) ->

            tombol.setOnClickListener {
                kategoriAktif = kategori

                daftarKategori.forEach { (item, nama) ->
                    item.setBackgroundResource(
                        if (nama == kategoriAktif) {
                            R.drawable.bg_kasir_chip_active
                        } else {
                            R.drawable.bg_kasir_chip
                        }
                    )

                    item.setTextColor(
                        resources.getColor(
                            if (nama == kategoriAktif) {
                                android.R.color.white
                            } else {
                                R.color.dashboard_text
                            },
                            null
                        )
                    )
                }

                tampilkanProduk()
            }
        }
    }

    private fun tampilkanProduk() {

        val hasilFilter = semuaProduk.filter { produk ->

            val cocokNama = produk.nama.contains(
                kataKunci,
                ignoreCase = true
            )

            val cocokKategori =
                kategoriAktif == "Semua" ||
                        produk.kategori == kategoriAktif

            cocokNama && cocokKategori
        }

        adapter.submitList(hasilFilter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
