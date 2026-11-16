package com.example.storeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.storeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            replaceFragment(DashboardFragment())
        }

        binding.bottomNavigationView.background = null

        // Navigasi Bottom Navigation
        binding.bottomNavigationView.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.dashboard ->
                    replaceFragment(DashboardFragment())

                R.id.produk ->
                    replaceFragment(ProdukFragment())

                R.id.riwayat ->
                    replaceFragment(RiwayatFragment())

                R.id.profile ->
                    replaceFragment(ProfileFragment())
            }

            true
        }

        // Tombol FAB di tengah navigasi
        binding.fabButton.setOnClickListener {
            openKasir()
        }
    }

    // Fungsi membuka halaman Kasir
    fun openKasir() {
        replaceFragment(KasirFragment())
    }

    // Fungsi navigasi dari Dashboard
    fun navigateTo(itemId: Int) {
        binding.bottomNavigationView.selectedItemId = itemId
    }

    // Fungsi mengganti Fragment
    private fun replaceFragment(fragment: Fragment) {

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }
}
