package com.uxharshit.procodelc.models

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannedString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.uxharshit.procodelc.R

class QuestionAdapter(context: Context?, item: Int, companyInfoModel: ArrayList<CompanyInfoModelItem>) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    private val companyInfoModel : ArrayList<CompanyInfoModelItem>
    private val context: Context?
    private val item: Int
    init {
        this.companyInfoModel = companyInfoModel
        this.context = context
        this.item = item
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title : TextView
        var difficulty : TextView
        var acceptance : TextView
        var gotoQuestion : CardView
        var share : LinearLayout
        init {
            title = itemView.findViewById(R.id.title)
            difficulty = itemView.findViewById(R.id.difficulty)
            acceptance = itemView.findViewById(R.id.acceptance)
            gotoQuestion = itemView.findViewById(R.id.gotoquestion)
            share = itemView.findViewById(R.id.share)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = View.inflate(parent.context, item, null)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return companyInfoModel.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val companyInfoModelItem = companyInfoModel[position]
        holder.title.text = companyInfoModelItem.Title
        when (val difficulty = companyInfoModelItem.Difficulty) {
            "Easy" -> {
                // span color green
                holder.difficulty.setTextColor(Color.GREEN)
                holder.difficulty.text = difficulty
            }
            "Medium" -> {
                // span color yellow
                holder.difficulty.setTextColor(Color.YELLOW)
                holder.difficulty.text = difficulty
            }
            else -> {
                // span color red
                holder.difficulty.setTextColor(Color.RED)
                holder.difficulty.text = difficulty
            }
        }
        holder.acceptance.text = "A/R : ${companyInfoModelItem.Acceptance}%"

        holder.gotoQuestion.setOnClickListener{
            // opens web browser with a link
            var url = companyInfoModelItem.Leetcode_Question_Link
            url = url.trim()
            if (!url.startsWith("http://") && !url.startsWith("https://"))
                url = "http://$url"
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context?.startActivity(Intent.createChooser(browserIntent, "Open with"))
        }
        holder.share.setOnClickListener{
            // share the question link
            val customMessage = "Download ProCodeLC to get Leetcode premium questions for free \n\n${companyInfoModelItem.Leetcode_Question_Link}"
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, customMessage)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            context?.startActivity(shareIntent)
        }

    }
}