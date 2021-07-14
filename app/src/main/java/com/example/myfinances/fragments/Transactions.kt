package com.example.myfinances.fragments

import android.content.SharedPreferences
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfinances.Constants
import com.example.myfinances.R
import com.example.myfinances.adapters.TransactionsRecyclerAdapter
import com.example.myfinances.data.transaction
import com.example.myfinances.data.transactionDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Transactions.newInstance] factory method to
 * create an instance of this fragment.
 */

class Transactions : Fragment() {

    lateinit var rv: RecyclerView
    lateinit var rvAdapter: TransactionsRecyclerAdapter
    lateinit var rvLayoutManager: RecyclerView.LayoutManager

    lateinit var addShade: LinearLayout
    lateinit var addDialogue: LinearLayout
    lateinit var addFab: FloatingActionButton

    lateinit var btnAddTransaction: Button
    lateinit var btnCancelTransaction: Button
    lateinit var inpTitle: TextInputEditText
    lateinit var inpCost: TextInputEditText


    lateinit var txtInBudget : TextView
    lateinit var sharedPreferences: SharedPreferences

    lateinit var transactions :MutableList<transaction>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infnlate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_transactions, container, false)

        sharedPreferences = requireActivity().getSharedPreferences(
            Constants.sharedPreferencesName,
            AppCompatActivity.MODE_PRIVATE
        )

        rv = view.findViewById(R.id.rv_ll)
        rvLayoutManager = LinearLayoutManager(activity)

        CoroutineScope(Dispatchers.IO).launch{
            transactions = getList()
            withContext(Dispatchers.Main){
                rvAdapter = TransactionsRecyclerAdapter(activity, transactions)
                rv.adapter = rvAdapter
                rv.layoutManager = rvLayoutManager
            }
        }

        calculateStatus()


        addShade = view.findViewById(R.id.add_shade)
        addDialogue = view.findViewById(R.id.add_layout)
        addFab = view.findViewById(R.id.btn_fab)

        btnAddTransaction = view.findViewById(R.id.btn_save)
        btnCancelTransaction = view.findViewById(R.id.btn_cancel)
        inpCost = view.findViewById(R.id.inp_cost)
        inpTitle = view.findViewById(R.id.inp_title)

        txtInBudget = view.findViewById(R.id.status_inbudget)


        addShade.visibility = View.INVISIBLE
        addDialogue.visibility = View.INVISIBLE

        addFab.setOnClickListener {

            addShade.visibility = View.VISIBLE
            addDialogue.visibility = View.VISIBLE
            addFab.visibility = View.INVISIBLE

        }

        btnCancelTransaction.setOnClickListener {


            inpTitle.setText("")
            inpCost.setText("")

            addShade.visibility = View.INVISIBLE
            addDialogue.visibility = View.INVISIBLE
            addFab.visibility = View.VISIBLE
        }

        btnAddTransaction.setOnClickListener {
            if (inpTitle.length() < 3 || inpCost.length() < 1){
                Toast.makeText(activity, " Please enter Valid data", Toast.LENGTH_SHORT).show()
            }else{
                val trans = transaction(inpTitle.text.toString(), inpCost.text.toString())
                CoroutineScope(Dispatchers.IO).launch{
                    addTransaction(trans)
                    transactions = getList()
                    withContext(Dispatchers.Main){
                        rvAdapter = TransactionsRecyclerAdapter(activity, transactions)
                        rv.adapter = rvAdapter
                        rv.layoutManager = rvLayoutManager
                    }
                }

                inpTitle.setText("")
                inpCost.setText("")

                addShade.visibility = View.INVISIBLE
                addDialogue.visibility = View.INVISIBLE
                addFab.visibility = View.VISIBLE

                calculateStatus()

            }
        }


        return view
    }

    fun getList(): MutableList<transaction>{
        return transactionDatabase.getDatabase(this.requireContext()).dao().getTransactions()
    }

    fun addTransaction(trans: transaction){
        transactionDatabase.getDatabase(this.requireContext()).dao().insertTransaction(trans)
    }

    fun calculateStatus(){

        CoroutineScope(Dispatchers.IO).launch {
            var transa =  transactionDatabase.getDatabase(requireActivity().applicationContext).dao().getTransactions()
            var total = 0
            for(item in transa){
                total = total + item.value.toInt()
            }
            var monthlyCredit = sharedPreferences.getInt("monthlyCredit", 0)
            if(total > monthlyCredit){
                withContext(Dispatchers.Main){
                    txtInBudget.setText("Over Budget by ${total - monthlyCredit} Rs")
                    txtInBudget.setTextColor(ContextCompat.getColor(requireActivity().applicationContext,R.color.red))
                }
            }else{
                withContext(Dispatchers.Main){
                    txtInBudget.setText("Currently in Budget")
                    txtInBudget.setTextColor(ContextCompat.getColor(requireActivity().applicationContext,R.color.green))
                }
            }
        }
    }


}