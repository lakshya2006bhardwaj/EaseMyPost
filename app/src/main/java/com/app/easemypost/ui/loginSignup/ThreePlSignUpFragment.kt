package com.app.easemypost.ui.loginSignup

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.app.easemypost.databinding.FragmentThreePlSignUpBinding

class ThreePlSignUpFragment : Fragment() {
    private var _binding: FragmentThreePlSignUpBinding? = null
    private val binding get() = _binding!!

    private lateinit var filePickerLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThreePlSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the file picker launcher
        filePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) { // Use Activity.RESULT_OK
                val uri = result.data?.data
                if (uri != null) {
                    val fileName = getFileName(uri)
                    binding.tvTpFile.text = fileName
                } else {
                    Toast.makeText(requireContext(), "File selection failed", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "No file selected", Toast.LENGTH_SHORT).show()
            }
        }

        init()
    }

    private fun init() {
        initSetViews()
        initClickListener()
    }

    private fun initSetViews() {
        // Set any default values for views here
    }

    private fun initClickListener() {
        binding.btnTpUploadFile.setOnClickListener {
            openFilePicker()
        }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*" // Restrict to specific types, e.g., "image/*", "application/pdf" if needed
        filePickerLauncher.launch(intent)
    }

    private fun getFileName(uri: Uri): String {
        var fileName = "Unknown File"
        val cursor = requireContext().contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (columnIndex >= 0) { // Check for valid index
                    fileName = it.getString(columnIndex) ?: fileName
                }
            }
        }
        return fileName
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
