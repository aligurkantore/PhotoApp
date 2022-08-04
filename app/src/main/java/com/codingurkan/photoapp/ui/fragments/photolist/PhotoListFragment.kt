package com.codingurkan.photoapp.ui.fragments.photolist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.codingurkan.photoapp.R
import com.codingurkan.photoapp.adapter.PhotoListAdapter
import com.codingurkan.photoapp.base.BaseFragment
import com.codingurkan.photoapp.databinding.FragmentPhotoListBinding
import com.codingurkan.photoapp.model.Hit
import com.codingurkan.photoapp.utils.FIRST_PAGE
import com.codingurkan.photoapp.utils.PER_PAGE
import com.codingurkan.photoapp.utils.PHOTO_DATA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoListFragment : BaseFragment<FragmentPhotoListBinding,PhotoListViewModel>(
    FragmentPhotoListBinding::inflate
) {
    override val viewModel by viewModels<PhotoListViewModel>()
    private lateinit var adapter: PhotoListAdapter

    override fun onCreateFinished() {
        viewModel.downloadPhotos(FIRST_PAGE, PER_PAGE)
    }
    override fun initializeListeners() {
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
            photoList.observe(viewLifecycleOwner) { _data ->
                if (pageNumber.value == _data.totalHits/PER_PAGE){
                    binding.btnNext.visibility = View.INVISIBLE
                }
                else{
                    binding.btnNext.visibility = View.VISIBLE
                }
               _data?.let {
                   initAdapters(_data.hits)
               }
            }
            pageNumber.observe(viewLifecycleOwner) { _pageNumber ->
                if (_pageNumber == 1) {
                    binding.apply {
                        btnBack.visibility = View.INVISIBLE
                        tvPage.text = _pageNumber.toString()
                        viewModel.downloadPhotos(_pageNumber, PER_PAGE)
                    }
                }else{
                    binding.apply {
                        btnBack.visibility = View.VISIBLE
                        tvPage.text = _pageNumber.toString()
                        viewModel.downloadPhotos(_pageNumber, PER_PAGE)
                    }
                }
            }
        }
    }
    override fun initAdapters(data: List<Hit>) {
        adapter = PhotoListAdapter(data,object : PhotoListAdapter.ItemClickListener{
            override fun onClick(data: Hit) {
                val bundle = Bundle()
                bundle.putSerializable(PHOTO_DATA,data)
                findNavController().navigate(R.id.action_photoListFragment_to_photoDetailsFragment,bundle)
            }
        })
        binding.recyclerView.adapter = adapter
    }
}