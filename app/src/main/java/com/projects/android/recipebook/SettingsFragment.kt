package com.projects.android.recipebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.projects.android.recipebook.databinding.FragmentSettingsBinding
import com.projects.android.recipebook.view.SettingsViewModel


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val settingsViewModel: SettingsViewModel by viewModels()
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        settingsViewModel.isDarkTheme.observe(viewLifecycleOwner) { isDark ->
            binding.switchTheme.isChecked = isDark
        }


        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.toggleTheme(isChecked)
        }


        binding.exitButton.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.action_settingsFragment_to_choice_authorization_way)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}