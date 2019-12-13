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
import com.google.firebase.auth.FirebaseAuth
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    lateinit var fromright: Animation
    lateinit var mAuth: FirebaseAuth
    lateinit var emaillogin : EditText
    lateinit var passlogin : EditText
    lateinit var progressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
       emaillogin = findViewById(R.id.emaillogin)
        passlogin = findViewById(R.id.passlogin)




        buttonlogin.setOnClickListener{

        if (TextUtils.isEmpty(emaillogin.text.toString())) {
           emaillogin.error = "Password is required"
            emaillogin.requestFocus()
            return@setOnClickListener

        } else if(TextUtils.isEmpty(passlogin.text.toString())) {
            passlogin.error = "Password is too short"
            passlogin.requestFocus()
            return@setOnClickListener

        }else {
             progressDialog.setTitle("Please wait!!!")
             progressDialog.setMessage("This would take a few seconds")
             progressDialog.setCancelable(false)
             progressDialog.show()

            mAuth.signInWithEmailAndPassword(emaillogin.text.toString().trim(), passlogin.text.toString().trim()).addOnCompleteListener(this){task ->

                if (task.isSuccessful){
                   progressDialog.dismiss()
                    Toasty.success(applicationContext,"Success",Toast.LENGTH_SHORT,true).show()
                    sendUserIn()

                }else{
                   progressDialog.dismiss()
                    val errorMsg = task.exception.toString()
                    Toasty.error(applicationContext,"$errorMsg",Toast.LENGTH_SHORT,true).show()

                }

            }

        }


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
