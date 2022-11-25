package com.sathyapriyan.healthcare

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sathyapriyan.healthcare.data.remote.NetworkResult
import com.sathyapriyan.healthcare.databinding.FragmentLoginBinding
import com.sathyapriyan.healthcare.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(layoutInflater)

        binding.btnLogin.setOnClickListener {

            viewModel.authenticatePatient(
                userName = binding.txtInputLayoutUserName.editText?.text.toString(),
                password = binding.txtInputLayoutPassword.editText?.text.toString(),
                requireContext()
            )

        }

        viewModel.authenticationResponse.observe(viewLifecycleOwner){ response ->

            when(response) {

                is NetworkResult.Success -> {

                    binding.progressBarLogin.visibility = View.INVISIBLE
                    binding.btnLogin.visibility = View.VISIBLE

                    Toast.makeText(
                        requireContext(),
                        "Login Successful",
                        Toast.LENGTH_SHORT
                    ).show()

                    val directions = LoginFragmentDirections.actionLoginFragmentToPatientProfileFragment(
                        response.data!!
                    )
                    findNavController().navigate(directions)

                    println("Login Response --> ${response.data}")

                }

                is NetworkResult.Error -> {

                    binding.progressBarLogin.visibility = View.INVISIBLE
                    binding.btnLogin.visibility = View.VISIBLE

                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()

                }

                is NetworkResult.Loading -> {

                    binding.progressBarLogin.visibility = View.VISIBLE
                    binding.btnLogin.visibility = View.INVISIBLE

                }

            }

        }

        return binding.root

    }

}