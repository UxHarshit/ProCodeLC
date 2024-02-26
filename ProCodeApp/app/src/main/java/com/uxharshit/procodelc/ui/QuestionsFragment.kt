package com.uxharshit.procodelc.ui

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uxharshit.procodelc.R
import com.uxharshit.procodelc.databinding.FragmentQuestionsBinding
import com.uxharshit.procodelc.models.CompanyInfoModelItem
import com.uxharshit.procodelc.models.QuestionAdapter
import com.uxharshit.procodelc.services.CompanyListIn
import com.uxharshit.procodelc.services.LoadingCompany
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuestionsFragment: Fragment() {

    private var _binding: FragmentQuestionsBinding? = null

    private val binding get() = _binding!!
    private var questionList = ArrayList<CompanyInfoModelItem>()

    private val loadingCompany = LoadingCompany.buildService(CompanyListIn::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuestionsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val companyName = arguments?.getString("companyName")


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            Navigation.findNavController(view).navigateUp()
        }

        val recyclerView = binding.recyclerView
        val  layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        // Space between items
        val space = 10
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                outRect.top = space
                outRect.bottom = space
            }
        })
        recyclerView.layoutManager = layoutManager
        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (questionList.isEmpty()){
                    recyclerView.adapter = QuestionAdapter(requireContext(), R.layout.question, questionList)
                } else{
                    val filteredList = ArrayList<CompanyInfoModelItem>()
                    for (item in questionList){
                        if (item.Title.lowercase().contains(newText.toString().lowercase())){
                            filteredList.add(item)
                        }
                    }
                    recyclerView.adapter = QuestionAdapter(requireContext(), R.layout.question, filteredList)
                }

                return false
            }
        })

        if (companyName != null) {
            GlobalScope.launch {
                val jsonObject = com.google.gson.JsonObject()
                jsonObject.addProperty("company", companyName)
                val response = loadingCompany.getCompanyInfo(jsonObject)
                Log.d("response", response.toString())
                withContext(Dispatchers.Main){
                    if (response.isSuccessful) {
                        questionList = response.body() as ArrayList<CompanyInfoModelItem>
                        recyclerView.adapter = QuestionAdapter(requireContext(), R.layout.question, questionList)
                    } else {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}