package com.example.myfinances.fragments

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myfinances.R
import com.example.myfinances.data.transaction
import com.example.myfinances.data.transactionDatabase
import ir.mahozad.android.PieChart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Charts.newInstance] factory method to
 * create an instance of this fragment.
 */
class Charts(var slices1: List<PieChart.Slice>) : Fragment() {

    lateinit var pieChart: PieChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_charts, container, false)

        pieChart = view.findViewById(R.id.pieChart)

        pieChart.slices = slices1

        return view
    }


    fun getList(): MutableList<transaction>{
        return transactionDatabase.getDatabase(this.requireContext()).dao().getTransactions()
    }







}