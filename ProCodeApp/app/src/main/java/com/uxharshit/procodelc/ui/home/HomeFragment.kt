package com.uxharshit.procodelc.ui.home

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.uxharshit.procodelc.R
import com.uxharshit.procodelc.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val dev_click = binding.abcv
        dev_click.setOnClickListener {
            val dialog = Dialog(it.context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dev_dialog)
            val linkedin = dialog.findViewById<CardView>(R.id.linkedin)

            val youtube = dialog.findViewById<CardView>(R.id.youtube)
            val github = dialog.findViewById<CardView>(R.id.github)
            val telegram = dialog.findViewById<CardView>(R.id.telegram)
            linkedin.setOnClickListener{
                val i = Intent(Intent.ACTION_VIEW)
                i.setData(Uri.parse("https://www.linkedin.com/in/harshit-katheria"))
                startActivity(i)
            }
            youtube.setOnClickListener{
                val i = Intent(Intent.ACTION_VIEW)
                i.setData(Uri.parse("https://www.youtube.com/@teamuxh6855"))
                startActivity(i)
            }
            github.setOnClickListener{
                val i = Intent(Intent.ACTION_VIEW)
                i.setData(Uri.parse("https://github.com/UxHarshit"))
                startActivity(i)
            }
            telegram.setOnClickListener{

                val i = Intent(Intent.ACTION_VIEW)
                i.setData(Uri.parse("https://t.me/uxhurricane"))
                startActivity(i)
            }
            dialog.show()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}