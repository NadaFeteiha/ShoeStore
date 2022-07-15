package com.udacity.shoestore.ui

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoeListBinding
import com.udacity.shoestore.databinding.ShoeItemBinding
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.util.Util
import com.udacity.shoestore.viewmodel.ShoeViewModel


class ShoeListFragment : Fragment() {

    private lateinit var binding: FragmentShoeListBinding
    private lateinit var shoeViewModel: ShoeViewModel
    private lateinit var menuHost: MenuHost

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shoe_list, container, false)
        setMenu()
        setData()
        setListeners()
        return binding.root
    }

    private fun setMenu() {
        menuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.logout_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                Util.saveLoginStatus(requireContext(), false)
                findNavController()
                    .navigate(ShoeListFragmentDirections.actionShoeListFragmentToLoginFragment())
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setData() {
        shoeViewModel = ViewModelProvider(requireActivity()).get(ShoeViewModel::class.java)

        shoeViewModel.shoeList.observe(viewLifecycleOwner) { shoeItems ->
            if (shoeItems.isEmpty()) {
                binding.shoeLinearList.visibility = View.GONE
                binding.emptyTxt.visibility = View.VISIBLE
            } else {
                binding.emptyTxt.visibility = View.GONE
                binding.shoeLinearList.visibility = View.VISIBLE
                binding.shoeLinearList.removeAllViews()
                shoeItems.forEachIndexed { index, shoe ->
                    setDataDynamicView(index, shoe)
                }
            }
        }
    }

    private fun buildView(shoe: Shoe): View {
        val shoeItemBinding: ShoeItemBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.shoe_item,
            binding.shoeLinearList,
            false
        )
        shoeItemBinding.shoeName.text = shoe.name
        shoeItemBinding.company.text = shoe.company
        shoeItemBinding.size.text = shoe.size.toString()
        shoeItemBinding.description.text = shoe.description
        return shoeItemBinding.root
    }

    private fun setDataDynamicView(index: Int, shoe: Shoe) {
        val view = buildView(shoe)
        view.setOnClickListener { view ->
            shoeViewModel.setSelectedShoe(index)
            view.findNavController()
                .navigate(ShoeListFragmentDirections.actionShoeListFragmentToShoeDetailFragment())
        }
        binding.shoeLinearList.addView(view)
    }

    private fun setListeners() {
        binding.addShoeBtn.setOnClickListener { addBtn ->
            shoeViewModel.setSelectedShoe(-1)
            addBtn.findNavController()
                .navigate(ShoeListFragmentDirections.actionShoeListFragmentToShoeDetailFragment())
        }
    }
}