package com.example.yournursery

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

val requstcode = 0
lateinit var auth:FirebaseAuth
class signup_loginfrag : Fragment() {


    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup_loginfrag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        var email:String? = null
//        var password :String? = null
        val button=view.findViewById<ImageButton>(R.id.imageButton2)
        auth = FirebaseAuth.getInstance()
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        val googlesigninclient = GoogleSignIn.getClient(requireActivity(),options)
        val textview = view.findViewById<TextView>(R.id.textView3)
        if(auth.currentUser != null){
            val bundle = Bundle()
            bundle.putString("email", auth.currentUser!!.email)
            findNavController().navigate(R.id.action_signup_loginfrag_to_checkOut,bundle)
        }else {
            button.setOnClickListener {
                googlesigninclient.signInIntent.also {
                    startActivityForResult(it, requstcode)
                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == requstcode){

                    try {
                       val account =GoogleSignIn.getSignedInAccountFromIntent(data).result
                        account?.let {
                            googleAuthForFirebase(it)
                        }
                    }catch (_:Exception){

                    }
            }
    }

    private fun googleAuthForFirebase(it: GoogleSignInAccount) {
            val credentials = GoogleAuthProvider.getCredential(it.idToken,null)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                    auth.signInWithCredential(credentials).await()
                withContext(Dispatchers.Main){
                    val bundle = Bundle()
                    bundle.putString("email", auth.currentUser!!.email)
                    findNavController().navigate(R.id.action_signup_loginfrag_to_checkOut,bundle)
                }
            }catch (ex:java.lang.Exception){
                withContext(Dispatchers.IO) {
                    Toast.makeText(context, ex.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}