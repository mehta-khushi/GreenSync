package com.example.greensync.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greensync.adapters.ApplianceAdapter
import com.example.greensync.databinding.FragmentHomeBinding
import com.example.greensync.dialogs.AddApplianceDialog
import com.example.greensync.viewmodels.ApplianceViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ApplianceViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupFab()
        observeData()
    }

    private fun setupRecyclerView() {
        val adapter = ApplianceAdapter(onDeleteClick = {
            viewModel.deleteAppliance(it)
        })
        binding.rvAppliances.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAppliances.adapter = adapter
    }

    private fun setupFab() {
        binding.fabAddAppliance.setOnClickListener {
            AddApplianceDialog().show(childFragmentManager, "AddApplianceDialog")
        }
    }

    private fun observeData() {
        viewModel.appliances.observe(viewLifecycleOwner) { appliances ->
            (binding.rvAppliances.adapter as? ApplianceAdapter)?.submitList(appliances)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
