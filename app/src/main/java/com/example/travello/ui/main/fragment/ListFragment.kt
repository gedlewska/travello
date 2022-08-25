package com.example.travello.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.travello.R
import com.example.travello.databinding.FragmentListBinding
import com.example.travello.ui.main.adapter.PoiAdapter
import com.example.travello.ui.main.model.PointOfInterest
import com.example.travello.ui.main.util.OnPoiSelectedListener
import com.example.travello.ui.main.viewmodel.PoiViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ListFragment : Fragment(), OnPoiSelectedListener {

    //private val mFilterDialog: FilterDialogFragment? = null

    private val viewModel: PoiViewModel by sharedViewModel()

    private lateinit var adapter: PoiAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyTv: TextView

    companion object {
        const val TAG = "List Fragment"

        fun newInstance(): ListFragment {
            return ListFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentListBinding>(
            inflater,
            R.layout.fragment_list, container, false
        )

        recyclerView = binding.recyclerView
        emptyTv = binding.emptyTv

        adapter = PoiAdapter(requireActivity(), this)

        viewModel.pois.observe(requireActivity(), {
            setViews(it == null)
            adapter.submitList(it as MutableList<PointOfInterest>)
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    override fun onPoiSelected(poi: PointOfInterest?) {
        viewModel.selectPoi(poi!!)
        requireActivity().supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.container, DetailsFragment.newInstance(), DetailsFragment.TAG)
            addToBackStack(DetailsFragment.TAG)
        }
    }

    private fun setViews(isListEmpty: Boolean) {
        if (isListEmpty) {
            recyclerView.visibility = View.GONE
            emptyTv.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyTv.visibility = View.GONE
        }
    }

}