package com.hybrid.recyadapfirestore

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import es.dmoral.toasty.Toasty

class MainActivity : AppCompatActivity() {


    var db = FirebaseFirestore.getInstance()

    lateinit var recyclerView: RecyclerView
    var dealsAdapter:DealsAdapter? = null
    var TAG: String? = null
    private val mUserList: ArrayList<DealModel> = ArrayList<DealModel>()
    lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseAuth = FirebaseAuth.getInstance()
        recyclerView = findViewById(R.id.recydeals)
        retrievepatientdetails()
        checkinternet()

    }

    fun retrievepatientdetails() {

        db.collection("Drugs").document("Deals").collection("Most bought")
            .addSnapshotListener(EventListener { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@EventListener
                }

                for (doc in value!!) {
                    val dealModel = DealModel()
                    dealModel.setName(doc.getString("name"))
                    dealModel.setPrice(doc.getString("price"))
                    dealModel.setImage(doc.getString("image"))


                    recyclerView.layoutManager = LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL,false)
                    recyclerView.itemAnimator = DefaultItemAnimator()
                    mUserList.add(dealModel)
                    dealsAdapter = DealsAdapter(applicationContext, mUserList)
                    recyclerView.adapter = dealsAdapter


                }

            })
    }

    override fun onStart() {
        super.onStart()

        var currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser !=null){
            Toasty.success(applicationContext,"Great", Toast.LENGTH_SHORT).show()
        }

    }


    fun checkinternet() {
        var connected = false
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).state == NetworkInfo.State.CONNECTED ||
            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).state == NetworkInfo.State.CONNECTED
        ) { //we are connected to a network
            connected = true
        } else {
            connected = false
            Toast.makeText(
                applicationContext,
                getString(R.string.nonetwork),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
