
package com.example.storeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.storeapp.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(
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

        // MENU KASIR
        binding.btnMenuKasir.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, KasirFragment())
                .addToBackStack(null)
                .commit()
        }

        // MENU PRODUK
        binding.btnMenuProduk.setOnClickListener {
            (requireActivity() as MainActivity)
                .findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(
                    R.id.bottomNavigationView
                )
                .selectedItemId = R.id.produk
        }

        // MENU RIWAYAT
        binding.btnMenuRiwayat.setOnClickListener {
            (requireActivity() as MainActivity)
                .findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(
                    R.id.bottomNavigationView
                )
                .selectedItemId = R.id.riwayat
        }

        // LIHAT SEMUA PRODUK
        binding.btnLihatSemua.setOnClickListener {
            (requireActivity() as MainActivity)
                .findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(
                    R.id.bottomNavigationView
                )
                .selectedItemId = R.id.produk
        }

        // TOMBOL RESTOK
//        binding.btnRestok.setOnClickListener {
//            (requireActivity() as MainActivity)
//                .findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(
//                    R.id.bottomNavigationView
//                )
//                .selectedItemId = R.id.produk
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
