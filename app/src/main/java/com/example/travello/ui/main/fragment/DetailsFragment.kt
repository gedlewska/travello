package com.example.travello.ui.main.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.travello.R
import com.example.travello.databinding.FragmentDetailsBinding
import com.example.travello.ui.main.model.PointOfInterest
import com.example.travello.ui.main.viewmodel.PoiViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DetailsFragment : Fragment() {

    private val viewModel: PoiViewModel by sharedViewModel()

    private var binding: FragmentDetailsBinding? = null
    private var poi: PointOfInterest? = null

    companion object {
        const val TAG = "Details Fragment"

        fun newInstance(): DetailsFragment {
            return DetailsFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setPoiImage()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_details, container, false
        )

        binding!!.poiBackBtn.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStackImmediate()
        }

        viewModel.selectedPoi.observe(requireActivity(), {
            poi = it
            binding!!.poi = poi
            setPoiImage()
        })

        return binding!!.root
    }

    private fun setPoiImage() {
        if (activity != null && binding != null) {
            Glide.with(requireActivity())
                .load(poi?.photo)
                .placeholder(R.drawable.ic_photo)
                .error(R.drawable.ic_photo)
                .override(200, 100)
                .centerCrop()
                .into(binding!!.poiImg)
        }
    }
}