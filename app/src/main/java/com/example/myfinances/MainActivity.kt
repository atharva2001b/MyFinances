package com.example.myfinances

import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.myfinances.Constants.sharedPreferencesName
import com.example.myfinances.data.transaction
import com.example.myfinances.data.transactionDatabase
import com.example.myfinances.fragments.Charts
import com.example.myfinances.fragments.Home
import com.example.myfinances.fragments.Transactions
import com.google.android.material.bottomnavigation.BottomNavigationView
import ir.mahozad.android.PieChart
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

class MainActivity : AppCompatActivity() {


    lateinit var listTransactions: MutableList<transaction>

    lateinit var chartsFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)



        val homeFragment = Home()
        val transactionsFragment = Transactions()

        var preparedList =  mutableListOf<PieChart.Slice>()





        setCurrentFragment(homeFragment)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_nav)


        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    setCurrentFragment(homeFragment)
                }

                R.id.transactions -> {
                    setCurrentFragment(transactionsFragment)
                }

                R.id.chart -> {

                    CoroutineScope(Dispatchers.IO).launch {
                        listTransactions = getList()
                        preparedList.clear()

                        withContext(Dispatchers.Main){
                            var total = 0f
                            for(item in listTransactions){
                                total += item.value.toFloat()
                            }

                            for(trans in listTransactions){
                                var lable = trans.name + " - "+ String.format("%.0f", ((trans.value.toInt()/total)*100)) + "%"
                                var slice = PieChart.Slice(fraction = trans.value.toInt()/total, Color.argb(225, rand(0,255), rand(0,255), rand(0,255)), label = lable)
                                preparedList.add(slice)
                            }

                            chartsFragment = Charts(preparedList)

                        }
                    }

                    Handler().postDelayed({
                        setCurrentFragment(chartsFragment)
                    },500)

                }
            }
            true
        }


    }

    fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame, fragment)
            detach(fragment)
            attach(fragment)
            commit()
        }
    }

    fun getList(): MutableList<transaction>{
        return transactionDatabase.getDatabase(this).dao().getTransactions()
    }

    fun rand(start: Int, end: Int): Int {
        require(start <= end) { "Illegal Argument" }
        return (Math.random() * (end - start + 1)).toInt() + start
    }

}