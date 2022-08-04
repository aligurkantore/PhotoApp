package com.codingurkan.photoapp.ui.fragments.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.codingurkan.photoapp.R
import com.codingurkan.photoapp.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterFragment : Fragment() {

    private var binding : FragmentRegisterBinding? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        auth = FirebaseAuth.getInstance()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickImage()
        registerAction()
    }
    private fun onClickImage(){
        val tvClickMe = binding?.imageBack

        tvClickMe?.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }
    private fun registerAction(){
        binding?.registerBtn?.setOnClickListener {
            val userName = binding?.nameRegister?.text.toString()
            val email = binding?.emailRegister?.text.toString()
            val password = binding?.passwordRegister?.text.toString()
            val passwordAgain = binding?.passwordAgainRegister?.text.toString()

            if (userName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && passwordAgain.isNotEmpty()){
                if (password == passwordAgain){
                    auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                        findNavController().navigate(R.id.action_registerFragment_to_photoListFragment)
                    }.addOnFailureListener { _exception ->
                        Toast.makeText(requireContext(), _exception.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(requireContext(), "Password are not the same", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(), "You entered incomplete information", Toast.LENGTH_SHORT).show()
            }
        }
    }
}