package com.nudriin.dicodingeventapp.ui.upcoming

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.nudriin.dicodingeventapp.EventListAdapter
import com.nudriin.dicodingeventapp.SearchBarListener
import com.nudriin.dicodingeventapp.data.response.ListEventsItem
import com.nudriin.dicodingeventapp.databinding.FragmentUpcomingBinding
import com.nudriin.dicodingeventapp.ui.detail.DetailFragment
import com.nudriin.dicodingeventapp.ui.detail.DetailFragmentArgs

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: UpcomingViewModel
    private var searchViewListener: SearchBarListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SearchBarListener) {
            searchViewListener = context
        } else {
            throw RuntimeException("$context must implement SearchViewListener")
        }
    }

    override fun onResume() {
        super.onResume()
        searchViewListener?.showSearchView()
    }

    override fun onPause() {
        super.onPause()
        searchViewListener?.hideSearchView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        binding.rvUpcoming.layoutManager = layoutManager

        viewModel = ViewModelProvider(this)[UpcomingViewModel::class.java]

        viewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        viewModel.eventList.observe(viewLifecycleOwner){eventList ->
            setEventList(eventList)
            searchEvent()
        }

        viewModel.toastText.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { toastText ->
                Toast.makeText(
                    context,
                    toastText,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun setEventList(eventList: List<ListEventsItem>){
        val adapter = EventListAdapter()
        adapter.submitList(eventList)
        binding.rvUpcoming.adapter = adapter

        adapter.setOnItemClickCallback(object : EventListAdapter.OnItemClickCallback{
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

    private fun moveToDetail(eventId: String) {
        val toDetail = UpcomingFragmentDirections.actionNavigationUpcomingToDetailFragment(eventId)
        Navigation.findNavController(binding.root).navigate(toDetail)
    }

    private fun searchEvent() {
        val searchView = searchViewListener?.getSearchView()
        val searchBar = searchViewListener?.getSearchBar()
        searchView?.editText?.setOnEditorActionListener { v, actionId, event ->
            searchBar?.setText(searchView.text)
            searchView.hide()
            viewModel.searchUpcomingEvent(searchView.text.toString())
            false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}