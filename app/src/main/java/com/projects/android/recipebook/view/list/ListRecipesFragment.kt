package com.projects.android.recipebook.view.list

import android.graphics.drawable.Icon
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.projects.android.recipebook.R
import com.projects.android.recipebook.databinding.FragmentListRecipesBinding
import com.projects.android.recipebook.view.single.SingleRecipeFragmentDirections
import kotlinx.coroutines.launch

class ListRecipesFragment : Fragment() {


    private val listRecipesViewModel: ListRecipesViewModel by viewModels()


    private var _binding: FragmentListRecipesBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Не возможно получить биндинг"
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        // RECYCLER VIEW
        _binding = FragmentListRecipesBinding.inflate(inflater, container, false)
        binding.recipesRecyclerViewList.layoutManager = LinearLayoutManager(context)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            expandFiltersList.setOnClickListener {
                if (filtersLayoutList.visibility == VISIBLE) {
                    filtersLayoutList.visibility = GONE
                    expandFiltersList.setImageIcon(
                        Icon.createWithResource(
                            context,
                            R.drawable.ic_baseline_keyboard_arrow_down_24
                        )
                    )
                } else {
                    filtersLayoutList.visibility = VISIBLE
                    expandFiltersList.setImageIcon(
                        Icon.createWithResource(
                            context,
                            R.drawable.ic_baseline_keyboard_arrow_up_24
                        )
                    )
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                listRecipesViewModel.recipes.collect { recipes ->
                    binding.recipesRecyclerViewList.adapter =
                        ListRecipesAdapter(recipes, requireContext()) { recipeID ->
                            findNavController().navigate(
                                ListRecipesFragmentDirections.fromListRecipesFragmentToSingleRecipeFragment(
                                    recipeID
                                )
                            )
                        }

                    binding.recipesRecyclerViewList.addItemDecoration(
                        DividerItemDecoration(
                            context,
                            VERTICAL
                        )
                    )
                }
            }
        }

        binding.addRecipeFABList.setOnClickListener {
            findNavController().navigate(
                ListRecipesFragmentDirections.fromListRecipesFragmentToAddRecipeFragment(
                    -2
                )
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_menu_settings, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_recipe -> {
                AlertDialog.Builder(requireContext())
                    .setTitle("Уверены, что хотите удалить?")
                    .setIcon(R.drawable.ic_baseline_dangerous_24)
                    .setPositiveButton(android.R.string.ok) { _, _ ->
                        viewLifecycleOwner.lifecycleScope.launch {

                        }
                        findNavController().navigateUp()
                    }
                    .setNegativeButton(android.R.string.cancel, null)
                    .show()
                true
            }

            R.id.action_settings -> {
                findNavController().navigate(R.id.action_fragmentListRecipes_to_settingsFragment)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}