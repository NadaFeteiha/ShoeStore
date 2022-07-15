package com.udacity.shoestore.ui

import android.icu.number.IntegerWidth
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoeDetailBinding
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.viewmodel.ShoeViewModel

class ShoeDetailFragment : Fragment() {

    private lateinit var binding: FragmentShoeDetailBinding
    private val shoeViewModel: ShoeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shoe_detail, container, false)

        binding.shoeViewModel = shoeViewModel
        binding.shoe = shoeViewModel.getSelectedShoe()
        binding.lifecycleOwner = this

        setListeners()

        return binding.root
    }

    private fun setListeners() {
        binding.cancelBtn.setOnClickListener { cancelBtn ->
            cancelBtn.findNavController()
                .navigate(ShoeDetailFragmentDirections.actionShoeDetailFragmentToShoeListFragment())
        }

        binding.saveBtn.setOnClickListener { saveBtn ->
            if (validate()) {
                if (binding.shoe == null) {
                    shoeViewModel.addNewShoe(getShoe())
                } else {
                    shoeViewModel.updateSelectedShoe(binding.shoe as Shoe)
                }
                saveBtn.findNavController()
                    .navigate(ShoeDetailFragmentDirections.actionShoeDetailFragmentToShoeListFragment())

            } else {
                Toast.makeText(
                    context,
                    resources.getString(R.string.error_msg_all_mandatory),
                    Toast.LENGTH_LONG
                ).show()
            }
        }


        //Input text listener for handling error.
        binding.shoeName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.nameTf.isErrorEnabled = false
            }
        })

        binding.company.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.companyTf.isErrorEnabled = false
            }
        })

        binding.size.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.shoeSizeTf.isErrorEnabled = false
            }
        })

        binding.description.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.descriptionTf.isErrorEnabled = false
            }
        })
    }

    private fun validate(): Boolean {
        var valid = true
        if (binding.shoeName.text.isNullOrBlank()) {
            binding.nameTf.error = resources.getString(R.string.shoe_name_error)
            valid = false
        }
        if (binding.company.text.isNullOrBlank()) {
            binding.companyTf.error = resources.getString(R.string.company_error)
            valid = false
        }
        if (binding.size.text.isNullOrBlank()) {
            binding.shoeSizeTf.error = resources.getString(R.string.shoe_size_error)
            valid = false
        }
        if (binding.description.text.isNullOrBlank()) {
            binding.descriptionTf.error = resources.getString(R.string.shoe_description_error)
            valid = false
        }
        return valid
    }

    private fun getShoe(): Shoe {
        return Shoe(
            binding.shoeName.text.toString(),
            binding.size.text.toString().toDouble(),
            binding.company.text.toString(),
            binding.description.text.toString(),
            mutableListOf()
        )
    }
}