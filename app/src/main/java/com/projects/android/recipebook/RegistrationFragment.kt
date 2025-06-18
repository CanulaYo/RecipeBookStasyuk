package com.projects.android.recipebook

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.projects.android.recipebook.databinding.FragmentRegistrationBinding


class RegistrationFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        binding.registerButton.setOnClickListener {
            validateAndRegister()
        }
    }

    private fun validateAndRegister() {

        val email = binding.editTextTextEmailAddress.text.toString().trim()
        val password = binding.editTextTextPassword.text.toString().trim()

        binding.editTextTextEmailAddress.error = null
        binding.editTextTextPassword.error = null

        var hasErrors = false

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTextTextEmailAddress.error = "Введите корректную почту"
            hasErrors = true
        }

        if (password.length < 6) {
            binding.editTextTextPassword.error = "Пароль должен содежрать более 6 символов."
            hasErrors = true
        }

        if (!hasErrors) {
            registerUser(email, password)
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        "Регистрация успешна!",
                        Toast.LENGTH_SHORT
                    ).show()

                    findNavController().navigate(R.id.action_registrationFragment_to_fragmentListRecipes)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Ошибка регистрации: ${task.exception?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}