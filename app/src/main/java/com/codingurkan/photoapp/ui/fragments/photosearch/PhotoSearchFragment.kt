package com.codingurkan.photoapp.ui.fragments.photosearch


import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.codingurkan.photoapp.R
import com.codingurkan.photoapp.adapter.PhotoListAdapter
import com.codingurkan.photoapp.base.BaseFragment
import com.codingurkan.photoapp.databinding.FragmentPhotoSearchBinding
import com.codingurkan.photoapp.model.Hit
import com.codingurkan.photoapp.utils.FIRST_PAGE
import com.codingurkan.photoapp.utils.PER_PAGE
import com.codingurkan.photoapp.utils.PHOTO_DATA
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class PhotoSearchFragment : BaseFragment<FragmentPhotoSearchBinding, PhotoSearchViewModel>(
    FragmentPhotoSearchBinding::inflate
) {
    override val viewModel by viewModels<PhotoSearchViewModel>()
    private lateinit var adapter: PhotoListAdapter

    override fun onCreateFinished() {
        Toast.makeText(requireContext(), "Welcome to Search Page", Toast.LENGTH_SHORT).show()
        binding.btnNext.visibility = View.INVISIBLE
        binding.tvPage.visibility = View.INVISIBLE
    }
    override fun initializeListeners() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null) {
                    lifecycleScope.launchWhenCreated {
                        delay(500)
                        viewModel.query.postValue(p0)
                    }
                }
                return true
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })
        viewModel.apply {
            binding.apply {
                btnBack.setOnClickListener {
                    pageNumber.value = pageNumber.value?.plus(-1)
                }
                btnNext.setOnClickListener {
                    pageNumber.value = pageNumber.value?.plus(1)
                }
            }
        }
    }
    override fun observeEvents() {
        viewModel.apply {
            searchPhotoList.observe(viewLifecycleOwner) { _data ->
                if (pageNumber.value == _data.totalHits / PER_PAGE) {
                    binding.btnNext.visibility = View.INVISIBLE
                } else {
                    binding.btnNext.visibility = View.VISIBLE
                }
                if (_data.hitsIsEmpty()) {
                    initAdapters(_data.hits)
                    binding.recyclerView.visibility = View.VISIBLE
                } else {
                    binding.apply {
                        btnNext.visibility = View.INVISIBLE
                        tvPage.visibility = View.INVISIBLE
                        recyclerView.visibility = View.INVISIBLE
                    }
                }
            }
            query.observe(viewLifecycleOwner) { _query ->
                _query?.let {
                    pageNumber.value?.let { _pageNumber ->
                        viewModel.downloadSearchPhotos(_query, _pageNumber, PER_PAGE)
                        binding.btnNext.visibility = View.VISIBLE
                        binding.tvPage.visibility = View.VISIBLE
                    }
                    pageNumber.value = FIRST_PAGE
                }
            }
            pageNumber.observe(viewLifecycleOwner) { _pageNumber ->
                if (_pageNumber == 1) {
                    query.value?.let { _query ->
                        downloadSearchPhotos(_query, _pageNumber, PER_PAGE)
                    }
                    binding.apply {
                        btnBack.visibility = View.INVISIBLE
                        tvPage.text = _pageNumber.toString()
                    }
                } else {
                    query.value?.let { _query ->
                        downloadSearchPhotos(_query, _pageNumber, PER_PAGE)
                    }
                    binding.apply {
                        btnBack.visibility = View.VISIBLE
                        tvPage.text = _pageNumber.toString()
                    }
                }
            }
        }
    }
    override fun initAdapters(data: List<Hit>) {
        adapter = PhotoListAdapter(data, object : PhotoListAdapter.ItemClickListener {
            override fun onClick(data: Hit) {
                val bundle = Bundle()
                bundle.putSerializable(PHOTO_DATA, data)
                findNavController().navigate(R.id.action_photoSearchFragment_to_photoDetailsFragment,
                    bundle)
            }
        })
        binding.recyclerView.adapter = adapter
    }
}