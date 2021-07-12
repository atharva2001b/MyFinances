package com.example.myfinances.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.myfinances.R
import com.example.myfinances.data.transaction
import com.example.myfinances.data.transactionDatabase

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

    lateinit var rv: LinearLayout
    //////////////

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infnlate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_transactions, container, false)




        return view
    }

    suspend fun getList(): List<transaction>{
        return transactionDatabase.getDatabase(this.requireContext()).dao().getTransactions()
    }


}