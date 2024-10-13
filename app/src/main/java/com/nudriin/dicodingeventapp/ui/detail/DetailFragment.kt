package com.nudriin.dicodingeventapp.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.nudriin.dicodingeventapp.EventListAdapter
import com.nudriin.dicodingeventapp.MainActivity
import com.nudriin.dicodingeventapp.R
import com.nudriin.dicodingeventapp.data.response.Event
import com.nudriin.dicodingeventapp.data.response.ListEventsItem
import com.nudriin.dicodingeventapp.databinding.FragmentDetailBinding
import com.nudriin.dicodingeventapp.databinding.FragmentUpcomingBinding

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val viewModel: DetailViewModel by viewModels<DetailViewModel>()
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

        viewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        viewModel.getEventDetailById(id)
        viewModel.eventDetail.observe(viewLifecycleOwner){ event ->
            if (event != null) {
                setEventDetail(event)
            }
        }
        Log.d("EventId", id)
    }

    private fun setEventDetail(eventDetail: Event){
        Glide.with(this)
            .load(eventDetail.mediaCover)
            .into(binding.eventImg)

        with (binding) {
            tvTitle.text = eventDetail.name
            tvAuthor.text = resources.getString(R.string.author, eventDetail.ownerName)
            tvSummary.text = eventDetail.summary
            tvBeginTime.text = resources.getString(R.string.begin_time, eventDetail.beginTime)
            tvQuota.text = resources.getString(R.string.quota, eventDetail.quota)
            tvDescription.text = HtmlCompat.fromHtml(
                eventDetail.description.toString(),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnRegister.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.btnRegister.visibility = View.VISIBLE
        }
    }
}