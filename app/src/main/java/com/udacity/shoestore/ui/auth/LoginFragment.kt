package com.udacity.shoestore.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentLoginBinding
import com.udacity.shoestore.util.Util

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        setListeners()

        return binding.root
    }

    private fun setListeners() {
        binding.loginBtn.setOnClickListener { view ->
            Util.saveLoginStatus(requireActivity(), true)
            view.findNavController()
                .navigate(LoginFragmentDirections.actionLoginFragmentToWelcomeFragment())
        }
        binding.signupBtn.setOnClickListener { view ->
            Util.saveLoginStatus(requireActivity(), true)
            view.findNavController()
                .navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
        }
    }
}