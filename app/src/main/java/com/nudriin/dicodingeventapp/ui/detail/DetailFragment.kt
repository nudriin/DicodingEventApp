package com.nudriin.dicodingeventapp.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.nudriin.dicodingeventapp.R
import com.nudriin.dicodingeventapp.ViewModelFactory
import com.nudriin.dicodingeventapp.data.response.Event
import com.nudriin.dicodingeventapp.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id: String = DetailFragmentArgs.fromBundle(arguments as Bundle).eventId

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireContext())
        val viewModel: DetailViewModel by viewModels {
            factory
        }

        viewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        viewModel.getEventDetailById(id)
        viewModel.eventDetail.observe(viewLifecycleOwner){ event ->
            if (event != null) {
                setEventDetail(event)
                binding.fabAdd.setOnClickListener {
                    viewModel.setFavoriteEvent()
                }
            }
        }

        viewModel.getEventById(id.toInt()).observe(viewLifecycleOwner) {eventEntity ->
            if(eventEntity != null){
                binding.fabAdd.setImageResource(R.drawable.ic_star)
            } else {
                binding.fabAdd.setImageResource(R.drawable.ic_star_border)
            }
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

        binding.btnRegister.setOnClickListener {
            val url = viewModel.eventDetail.value?.link
            if (!url.isNullOrEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            } else {
                Toast.makeText(context, "Link tidak tersedia!", Toast.LENGTH_SHORT).show()
            }
        }
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
            var quotaRemainder = 0
            if(eventDetail.quota != null && eventDetail.registrants != null){
                quotaRemainder = eventDetail.quota - eventDetail.registrants
            }
            tvQuota.text = resources.getString(R.string.quota_remainder, quotaRemainder)
            tvDescription.text = HtmlCompat.fromHtml(
                eventDetail.description.toString(),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            with(binding) {
                progressBar.visibility = View.VISIBLE
                btnRegister.visibility = View.GONE
                tvBeginTime.visibility= View.GONE
                tvQuota.visibility= View.GONE
            }

        } else {
            with(binding) {
                progressBar.visibility = View.GONE
                btnRegister.visibility = View.VISIBLE
                tvBeginTime.visibility= View.VISIBLE
                tvQuota.visibility= View.VISIBLE
            }
        }
    }
}