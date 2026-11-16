package com.example.storeapp

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.storeapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val prefs by lazy {
        requireContext().getSharedPreferences(
            "profile_settings",
            android.content.Context.MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(
            inflater, container, false
        )

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        loadProfile()
        setupAccountMenu()
        setupStoreMenu()
        setupAppearanceMenu()
        setupSecurityMenu()
    }

    // MEMUAT DATA PROFIL
    private fun loadProfile() {

        val nama = prefs.getString("nama", "Nama Pengguna") ?: ""
        val email = prefs.getString("email", "email@example.com") ?: ""
        val telepon = prefs.getString("telepon", "08xxxxxxxxxx") ?: ""
        val namaToko = prefs.getString("nama_toko", "Toko Saya") ?: ""
        val alamat = prefs.getString("alamat_toko", "Belum diatur") ?: ""

        binding.txtNamaProfil.text = nama
        binding.txtNamaLengkap.text = nama
        binding.txtEmail.text = email
        binding.txtTelepon.text = telepon
        binding.txtNamaToko.text = namaToko
        binding.txtAlamatToko.text = alamat

        binding.txtModeTema.text =
            prefs.getString("tema", "Terang")

        binding.txtUkuranTeks.text =
            prefs.getString("ukuran_teks", "Normal")

        binding.txtBahasa.text =
            prefs.getString("bahasa", "Indonesia")
    }

    // INFORMASI AKUN
    private fun setupAccountMenu() {

        binding.btnNamaLengkap.setOnClickListener {
            showEditDialog(
                "Ubah Nama Lengkap",
                "nama",
                binding.txtNamaLengkap.text.toString(),
                "Nama Lengkap"
            ) {
                binding.txtNamaLengkap.text = it
                binding.txtNamaProfil.text = it
            }
        }

        binding.btnEmail.setOnClickListener {
            showEditDialog(
                "Ubah Email",
                "email",
                binding.txtEmail.text.toString(),
                "Email"
            ) {
                binding.txtEmail.text = it
            }
        }

        binding.btnTelepon.setOnClickListener {
            showEditDialog(
                "Ubah Nomor Telepon",
                "telepon",
                binding.txtTelepon.text.toString(),
                "Nomor Telepon",
                InputType.TYPE_CLASS_PHONE
            ) {
                binding.txtTelepon.text = it
            }
        }
    }

    // PENGATURAN TOKO
    private fun setupStoreMenu() {

        binding.btnNamaToko.setOnClickListener {
            showEditDialog(
                "Ubah Nama Toko",
                "nama_toko",
                binding.txtNamaToko.text.toString(),
                "Nama Toko"
            ) {
                binding.txtNamaToko.text = it
            }
        }

        binding.btnAlamatToko.setOnClickListener {
            showEditDialog(
                "Ubah Alamat Toko",
                "alamat_toko",
                binding.txtAlamatToko.text.toString(),
                "Alamat Toko"
            ) {
                binding.txtAlamatToko.text = it
            }
        }

        binding.btnLogoToko.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Fitur pemilihan logo toko akan segera dibuat",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // PENGATURAN TAMPILAN
    private fun setupAppearanceMenu() {

        binding.btnModeTema.setOnClickListener {
            showChoiceDialog(
                "Mode Tampilan",
                arrayOf("Terang", "Gelap")
            ) {
                prefs.edit().putString("tema", it).apply()
                binding.txtModeTema.text = it
            }
        }

        binding.btnUkuranTeks.setOnClickListener {
            showChoiceDialog(
                "Ukuran Teks",
                arrayOf("Kecil", "Normal", "Besar")
            ) {
                prefs.edit().putString("ukuran_teks", it).apply()
                binding.txtUkuranTeks.text = it
            }
        }

        binding.btnBahasa.setOnClickListener {
            showChoiceDialog(
                "Bahasa Aplikasi",
                arrayOf("Indonesia", "English")
            ) {
                prefs.edit().putString("bahasa", it).apply()
                binding.txtBahasa.text = it
            }
        }
    }

    // KEAMANAN AKUN
    private fun setupSecurityMenu() {

        binding.btnUbahPassword.setOnClickListener {
            showPasswordDialog()
        }

        binding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }
    }

    // DIALOG EDIT DATA
    private fun showEditDialog(
        title: String,
        key: String,
        currentValue: String,
        hint: String,
        inputType: Int = InputType.TYPE_CLASS_TEXT,
        onSaved: (String) -> Unit
    ) {

        val input = EditText(requireContext()).apply {
            this.hint = hint
            setText(currentValue)
            this.inputType = inputType
            setSingleLine(inputType != InputType.TYPE_CLASS_TEXT or
                    InputType.TYPE_TEXT_FLAG_MULTI_LINE)
            setPadding(40, 30, 40, 30)
        }

        android.app.AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setView(input)
            .setNegativeButton("Batal", null)
            .setPositiveButton("Simpan") { _, _ ->

                val value = input.text.toString().trim()

                if (value.isNotEmpty()) {
                    prefs.edit().putString(key, value).apply()
                    onSaved(value)

                    Toast.makeText(
                        requireContext(),
                        "Data berhasil disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Data tidak boleh kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .show()
    }

    // DIALOG PILIHAN
    private fun showChoiceDialog(
        title: String,
        options: Array<String>,
        onSelected: (String) -> Unit
    ) {

        android.app.AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setItems(options) { _, which ->
                onSelected(options[which])
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    // DIALOG UBAH PASSWORD
    private fun showPasswordDialog() {

        val input = EditText(requireContext()).apply {
            hint = "Masukkan password baru"
            inputType =
                InputType.TYPE_CLASS_TEXT or
                        InputType.TYPE_TEXT_VARIATION_PASSWORD
            setPadding(40, 30, 40, 30)
        }

        android.app.AlertDialog.Builder(requireContext())
            .setTitle("Ubah Password")
            .setMessage("Masukkan password baru Anda.")
            .setView(input)
            .setNegativeButton("Batal", null)
            .setPositiveButton("Simpan") { _, _ ->

                val password = input.text.toString()

                if (password.length >= 6) {
                    Toast.makeText(
                        requireContext(),
                        "Hubungkan fitur ini dengan sistem autentikasi untuk menyimpan password.",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Password minimal 6 karakter",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .show()
    }

    // DIALOG LOGOUT
    private fun showLogoutDialog() {

        android.app.AlertDialog.Builder(requireContext())
            .setTitle("Keluar Akun")
            .setMessage("Apakah Anda yakin ingin keluar dari akun?")
            .setNegativeButton("Batal", null)
            .setPositiveButton("Logout") { _, _ ->

                Toast.makeText(
                    requireContext(),
                    "Hubungkan logout dengan sistem autentikasi aplikasi.",
                    Toast.LENGTH_LONG
                ).show()

            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
