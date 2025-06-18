package com.projects.android.recipebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.projects.android.recipebook.databinding.FragmentChoiceAuthorizationWayBinding

class ChoiceAuthorizationWayFragment : Fragment() {

    private var _binding: FragmentChoiceAuthorizationWayBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChoiceAuthorizationWayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.choiceAuthorization.setOnClickListener {
            findNavController().navigate(R.id.action_choice_authorization_way_to_authorizationFragment)
        }

        binding.choiceRegistration.setOnClickListener {
            findNavController().navigate(R.id.action_choice_authorization_way_to_registrationFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}