package com.codingurkan.photoapp.ui.fragments.photodetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codingurkan.photoapp.databinding.FragmentPhotoDetailsBinding
import com.codingurkan.photoapp.model.Hit
import com.codingurkan.photoapp.utils.PHOTO_DATA
import com.codingurkan.photoapp.utils.loadImage


class PhotoDetailsFragment : Fragment() {

    private var binding : FragmentPhotoDetailsBinding? = null
    private var data : Hit? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentPhotoDetailsBinding.inflate(layoutInflater)
        return binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data = arguments?.getSerializable(PHOTO_DATA) as Hit
        initListeners()
    }
    private fun initListeners(){
        binding?.imageDetails?.loadImage(data?.previewURL.toString())
        binding?.userImage?.loadImage(data?.userImageURL.toString())
    }


}