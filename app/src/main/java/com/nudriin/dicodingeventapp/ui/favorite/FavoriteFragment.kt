package com.nudriin.dicodingeventapp.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.nudriin.dicodingeventapp.FavoriteListAdapter
import com.nudriin.dicodingeventapp.ViewModelFactory
import com.nudriin.dicodingeventapp.data.local.entity.EventEntity
import com.nudriin.dicodingeventapp.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        binding.rvFavorite.layoutManager = layoutManager

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireContext())
        val viewModel: FavoriteViewModel by viewModels {
            factory
        }

        viewModel.getAllFavoriteEvents().observe(viewLifecycleOwner) { eventList ->
            setEventList(eventList)

        }
    }

    private fun moveToDetail(eventId: String) {
        val toDetail = FavoriteFragmentDirections.actionNavigationFavoriteToDetailFragment(eventId)
        Navigation.findNavController(binding.root).navigate(toDetail)
    }

    private fun setEventList(eventList: List<EventEntity>){
        val adapter = FavoriteListAdapter()
        adapter.submitList(eventList)
        binding.rvFavorite.adapter = adapter
        adapter.setOnItemClickCallback(object : FavoriteListAdapter.OnItemClickCallback{
            override fun onItemClicked(eventId: String) {
                moveToDetail(eventId)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}