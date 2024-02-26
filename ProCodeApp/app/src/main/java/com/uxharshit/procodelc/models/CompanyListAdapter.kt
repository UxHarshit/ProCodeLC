package com.uxharshit.procodelc.models

import android.content.Context
import android.database.DataSetObserver
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListAdapter
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.uxharshit.procodelc.R
import java.util.ArrayList

class CompanyListAdapter(context: Context?, item: Int, companyList: List<String>) :
    RecyclerView.Adapter<CompanyListAdapter.ViewHolder>(){

        private val companyList: List<String>
        private val context: Context?
        private val item: Int
        init {
            this.companyList = companyList
            this.context = context
            this.item = item
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var textView: TextView
            var cardView: View
            init {
                textView = itemView.findViewById(R.id.textView)
                cardView = itemView.findViewById(R.id.cardView)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, item, null)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return companyList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = companyList[position]
        holder.cardView.setOnClickListener{
            val name = companyList[position]
            val bundle = Bundle()
            bundle.putString("companyName", name)
            Navigation.findNavController(it).navigate(R.id.navigation_questions, bundle)
        }
    }
}
