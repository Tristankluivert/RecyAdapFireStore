package com.hybrid.recyadapfirestore

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import es.dmoral.toasty.Toasty

class Register : AppCompatActivity() {


    lateinit var signupbutton: RelativeLayout
    lateinit var mAuth: FirebaseAuth
    lateinit var db: FirebaseFirestore
    lateinit var collectRef: CollectionReference
    lateinit var edfirstname: EditText
    lateinit var edlastname : EditText
    lateinit var edemail : EditText
    lateinit var edpassword : EditText
    lateinit var progressDial : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        FirebaseApp.initializeApp(this)
        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        progressDial = ProgressDialog(this)
        collectRef = db.collection(mAuth.currentUser.toString())

        signupbutton = findViewById(R.id.signupbutton)
        edemail = findViewById(R.id.edemail)
        edpassword = findViewById(R.id.edpassword)




        val email: String = edemail.text.toString().trim()
        val password: String = edpassword.text.toString().trim()

        signupbutton.setOnClickListener {


 if (TextUtils.isEmpty(edemail.text.toString())) {
                edemail.error = "email is requires"
                edemail.requestFocus()
                return@setOnClickListener

            } else if (TextUtils.isEmpty(edpassword.text.toString())) {
                edpassword.error = "Password is required"
                edpassword.requestFocus()
                return@setOnClickListener

            } else if(edpassword.text.toString().length <= 5) {
                edpassword.error = "Password is too short"
                edpassword.requestFocus()
                return@setOnClickListener

            }else{

                progressDial.setTitle("Creating an account")
                progressDial.setMessage("This would take a few seconds")
                progressDial.setCancelable(false)
                progressDial.show()

                mAuth!!.createUserWithEmailAndPassword(
                    edemail.text.toString().trim(),
                    edpassword.text.toString().trim()
                )
                    .addOnCompleteListener(this) { task ->

                        if (task.isSuccessful) {

                            var users = HashMap<String, Any>()
                            users.put("Email", edemail.text.toString().trim())
                            users.put("Uid", mAuth.currentUser!!.uid)
                            db.collection("Users").document(mAuth.currentUser!!.uid).set(users)
                                .addOnSuccessListener {
                                    Toasty.success(
                                        applicationContext,
                                        "Success",
                                        Toast.LENGTH_SHORT,
                                        true
                                    ).show()
                                    progressDial.dismiss()
                                    sendUserIn()
                                }
                                .addOnFailureListener {
                                    progressDial.dismiss()
                                    Toasty.error(
                                        applicationContext,
                                        "Error occured, Please check your internet",
                                        Toast.LENGTH_SHORT,
                                        true
                                    ).show()
                                }

                        } else {
                            progressDial.dismiss()
                            val errorMsg = task.exception.toString()
                            Toasty.error(
                                applicationContext,
                                "Error : $errorMsg",
                                Toast.LENGTH_SHORT,
                                true
                            ).show()
                        }

                    }

            }
        }


    }

    public override fun onStart() {
        super.onStart()
        val currentuser = mAuth.getCurrentUser()
        if (currentuser != null) {
            sendUserIn()
        }
    }

    private fun sendUserIn() {
        val wel = Intent(applicationContext, MainActivity::class.java)
        wel.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(wel)
        Animatoo.animateSlideLeft(this)
       finish()

    }






}
