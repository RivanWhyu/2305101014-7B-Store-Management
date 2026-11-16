package com.example.storeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

data class ProdukPreview(
    val nama: String,
    val harga: Int,
    val stok: Int,
    val gambar: Int
)

class ProdukFragment : Fragment() {

    private lateinit var containerProduk: LinearLayout
    private lateinit var edtCariProduk: EditText

    private val daftarProduk = mutableListOf<ProdukPreview>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(
            R.layout.fragment_produk,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        containerProduk = view.findViewById(R.id.containerProduk)
        edtCariProduk = view.findViewById(R.id.edtCariProduk)

        // DATA CONTOH UNTUK PREVIEW TAMPILAN
        daftarProduk.clear()

        daftarProduk.add(
            ProdukPreview(
                "Air Mineral 600ml",
                3000,
                120,
                R.drawable.outline_deployed_code_24
            )
        )

        daftarProduk.add(
            ProdukPreview(
                "Keripik Singkong",
                2000,
                80,
                R.drawable.outline_deployed_code_24
            )
        )

        daftarProduk.add(
            ProdukPreview(
                "Indomie Goreng",
                3500,
                150,
                R.drawable.outline_deployed_code_24
            )
        )

        daftarProduk.add(
            ProdukPreview(
                "Teh Botol Sosro",
                4000,
                100,
                R.drawable.outline_deployed_code_24
            )
        )

        daftarProduk.add(
            ProdukPreview(
                "Biskuit Roma",
                2500,
                70,
                R.drawable.outline_deployed_code_24
            )
        )

        tampilkanProduk(daftarProduk)

        // PENCARIAN PRODUK
        edtCariProduk.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                val keyword = s.toString().trim()

                val hasilPencarian = daftarProduk.filter {
                    it.nama.contains(
                        keyword,
                        ignoreCase = true
                    )
                }

                tampilkanProduk(hasilPencarian)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // TOMBOL TAMBAH
        view.findViewById<TextView>(R.id.btnTambahProduk)
            .setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    "Fitur tambah produk akan segera dibuat",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun tampilkanProduk(data: List<ProdukPreview>) {

        containerProduk.removeAllViews()

        if (data.isEmpty()) {
            val emptyView = TextView(requireContext()).apply {
                text = "Produk tidak ditemukan"
                textSize = 14f
                setTextColor(
                    resources.getColor(
                        R.color.dashboard_gray,
                        null
                    )
                )
                gravity = android.view.Gravity.CENTER
                setPadding(10, 40, 10, 40)
            }

            containerProduk.addView(emptyView)
            return
        }

        data.forEach { produk ->

            val itemView = layoutInflater.inflate(
                R.layout.item_produk,
                containerProduk,
                false
            )

            val imgProduk =
                itemView.findViewById<ImageView>(R.id.imgProduk)

            val txtNama =
                itemView.findViewById<TextView>(R.id.txtNamaProduk)

            val txtHarga =
                itemView.findViewById<TextView>(R.id.txtHargaProduk)

            val txtStok =
                itemView.findViewById<TextView>(R.id.txtStokProduk)

            val btnEdit =
                itemView.findViewById<TextView>(R.id.btnEditProduk)

            val btnHapus =
                itemView.findViewById<TextView>(R.id.btnHapusProduk)

            imgProduk.setImageResource(produk.gambar)

            txtNama.text = produk.nama

            txtHarga.text = "Rp ${
                String.format("%,d", produk.harga)
                    .replace(',', '.')
            }"

            txtStok.text = "Stok: ${produk.stok} pcs"

            btnEdit.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    "Edit: ${produk.nama}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            btnHapus.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    "Hapus: ${produk.nama}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            containerProduk.addView(itemView)
        }
    }
}