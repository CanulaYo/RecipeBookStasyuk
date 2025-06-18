package com.projects.android.recipebook

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.projects.android.recipebook.databinding.FragmentAuthorizationBinding

class AuthorizationFragment : Fragment() {
    private var _binding: FragmentAuthorizationBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.authButton.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val email = binding.editTextTextEmailAddress.text.toString().trim()
        val password = binding.editTextTextPassword.text.toString().trim()

        binding.editTextTextEmailAddress.error = null
        binding.editTextTextPassword.error = null

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTextTextEmailAddress.error = "Введите корректный email"
            return
        }

        if (password.isEmpty() || password.length < 6) {
            binding.editTextTextPassword.error = "Введите пароль (мин. 6 символов)"
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        "Вход выполнен успешно!",
                        Toast.LENGTH_SHORT
                    ).show()

                    findNavController().navigate(R.id.action_authorizationFragment_to_fragmentListRecipes)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Ошибка входа: ${task.exception?.message}",
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

