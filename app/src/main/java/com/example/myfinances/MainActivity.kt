package com.example.myfinances

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myfinances.data.transaction
import com.example.myfinances.data.transactionDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var trans = transaction("1", "cred", "car", "500rs")

        CoroutineScope(Dispatchers.IO).launch{
            var dao = transactionDatabase.getDatabase(applicationContext).dao()

            dao.insertTransaction(trans)
            dao.insertTransaction(trans)
            dao.insertTransaction(trans)

        }


    }
}