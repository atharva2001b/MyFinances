package com.example.myfinances.fragments

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myfinances.Constants
import com.example.myfinances.R
import com.google.android.material.textfield.TextInputEditText

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {

    lateinit var inpCredit: TextInputEditText
    lateinit var btnSave: Button
    lateinit var txtStatus: TextView

    lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)



        btnSave = view.findViewById(R.id.btn_mth_cred_save)
        inpCredit = view.findViewById(R.id.inp_txt)
        txtStatus = view.findViewById(R.id.txtStatus)


        sharedPreferences = requireActivity().getSharedPreferences(
            Constants.sharedPreferencesName,
            AppCompatActivity.MODE_PRIVATE
        )

        txtStatus.setText("Current credit: ${sharedPreferences.getInt("monthlyCredit", 0)} Rs")



        btnSave.setOnClickListener {
            if(inpCredit.text?.length == null){
                Toast.makeText(activity, "Please Enter data", Toast.LENGTH_SHORT).show()

            }else if(inpCredit.text!!.length < 1){
                Toast.makeText(activity, "Please Enter valid data", Toast.LENGTH_SHORT).show()
            }else{
                sharedPreferences.edit().putInt("monthlyCredit", inpCredit.text.toString().toInt()).apply()
            }

            txtStatus.setText("Current credit: ${sharedPreferences.getInt("monthlyCredit", 0)} Rs")

        }

        return view
    }

}