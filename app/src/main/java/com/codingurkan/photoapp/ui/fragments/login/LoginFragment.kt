package com.codingurkan.photoapp.ui.fragments.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.codingurkan.photoapp.R
import com.codingurkan.photoapp.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    private var binding : FragmentLoginBinding? = null
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
       binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginAction()
        onClickText()
    }
    private fun loginAction(){
        binding?.loginBtn?.setOnClickListener {
            val email = binding?.emailLogin?.text.toString()
            val password = binding?.passwordLogin?.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()){
                auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                    findNavController().navigate(R.id.action_loginFragment_to_photoListFragment)
                }.addOnFailureListener { _exception ->
                    Toast.makeText(requireContext(), _exception.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(), "You entered incomplete information", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun onClickText(){
        val tvClickMe = binding?.tvRegister

        tvClickMe?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
}