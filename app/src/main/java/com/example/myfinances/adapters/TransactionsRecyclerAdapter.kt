package com.example.myfinances.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfinances.R
import com.example.myfinances.data.transaction
import com.example.myfinances.data.transactionDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionsRecyclerAdapter(val context: Context?, private var transactions: MutableList<transaction>): RecyclerView.Adapter<TransactionsRecyclerAdapter.TransactionsViewHolder>() {

    inner class TransactionsViewHolder(view: View): RecyclerView.ViewHolder(view){

        val srno: TextView = view.findViewById(R.id.sr_no)
        val title: TextView = view.findViewById(R.id.name)
        val cost: TextView = view.findViewById(R.id.cost)
        val btnDelete: ImageView = view.findViewById(R.id.btn_delete)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_row_transaction, parent, false)
        return TransactionsViewHolder(view)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.srno.text = (position + 1).toString()
        holder.title.text = transaction.name
        holder.cost.text = transaction.value + "Rs"


        holder.btnDelete.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                transactionDatabase.getDatabase(context!!).dao().deleteTransaction(transaction)
            }
            transactions.remove(transaction)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return transactions.size
    }
}