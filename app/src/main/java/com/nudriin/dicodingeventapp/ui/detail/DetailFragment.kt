package com.nudriin.dicodingeventapp.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nudriin.dicodingeventapp.R
import com.nudriin.dicodingeventapp.databinding.FragmentDetailBinding
import com.nudriin.dicodingeventapp.databinding.FragmentUpcomingBinding

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = DetailFragmentArgs.fromBundle(arguments as Bundle).eventId

        Log.d("EventId", id)
    }
}