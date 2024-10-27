package com.nudriin.dicodingeventapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.nudriin.dicodingeventapp.HomeFinishedListAdapter
import com.nudriin.dicodingeventapp.HomeListAdapter
import com.nudriin.dicodingeventapp.ViewModelFactory
import com.nudriin.dicodingeventapp.data.Result
import com.nudriin.dicodingeventapp.data.response.ListEventsItem
import com.nudriin.dicodingeventapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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
        val horizontalLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvUpcoming.layoutManager = horizontalLayoutManager
        val verticalLayoutManager = LinearLayoutManager(context)
        binding.rvFinished.layoutManager = verticalLayoutManager

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireContext())
        val viewModel: HomeViewModel by viewModels {
            factory
        }

        viewModel.getUpcomingEvent().observe(viewLifecycleOwner) { result ->
            if(result != null){
                when(result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }

                    is Result.Success -> {
                        showLoading(false)
                        setUpcomingEventList(result.data)
                    }

                    is Result.Error -> {
                        showLoading(false)
                        result.error.getContentIfNotHandled().let {toastText ->
                            Toast.makeText(
                                context,
                                toastText,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        viewModel.getFinishedEvent().observe(viewLifecycleOwner) { result ->
            if(result != null){
                when(result) {
                    is Result.Loading -> {
                        showLoading(true)
                    }

                    is Result.Success -> {
                        showLoading(false)
                        setFinishedEventList(result.data)
                    }

                    is Result.Error -> {
                        showLoading(false)
                        result.error.getContentIfNotHandled().let {toastText ->
                            Toast.makeText(
                                context,
                                toastText,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        val url = "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/event-ui-logo.png"
        Glide.with(binding.root.context)
            .load(url)
            .into(binding.dicodingImg)
    }

    private fun moveToDetail(eventId: String) {
        val toDetail = HomeFragmentDirections.actionNavigationHomeToDetailFragment(eventId)
        Navigation.findNavController(binding.root).navigate(toDetail)
    }

    private fun setUpcomingEventList(eventList: List<ListEventsItem>){
        val adapter = HomeListAdapter()
        val max5 = eventList.take(5)
        adapter.submitList(max5)
        binding.rvUpcoming.adapter = adapter
        adapter.setOnItemClickCallback(object : HomeListAdapter.OnItemClickCallback{
            override fun onItemClicked(eventId: String) {
                moveToDetail(eventId)
            }
        })
    }
    private fun setFinishedEventList(eventList: List<ListEventsItem>){
        val adapter = HomeFinishedListAdapter()
        val max5 = eventList.take(5)
        adapter.submitList(max5)
        binding.rvFinished.adapter = adapter
        adapter.setOnItemClickCallback(object : HomeFinishedListAdapter.OnItemClickCallback{
            override fun onItemClicked(eventId: String) {
                moveToDetail(eventId)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}