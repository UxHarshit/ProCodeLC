package com.uxharshit.procodelc.ui.dashboard

import android.app.ProgressDialog
import android.graphics.Rect
import android.os.Bundle
import android.provider.Settings.Global
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.uxharshit.procodelc.R
import com.uxharshit.procodelc.databinding.FragmentDashboardBinding
import com.uxharshit.procodelc.models.CompanyInfoModel
import com.uxharshit.procodelc.models.CompanyListAdapter
import com.uxharshit.procodelc.services.CompanyListIn
import com.uxharshit.procodelc.services.LoadingCompany
import com.uxharshit.procodelc.ui.QuestionsFragment
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import java.util.Locale
import javax.security.auth.callback.Callback

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val loadingCompany = LoadingCompany.buildService(CompanyListIn::class.java)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dashboardViewModel =
            ViewModelProvider(this)[DashboardViewModel::class.java]
        val searchView = binding.searchView
        val gridView = binding.gridView
        val gridLayoutManager = GridLayoutManager(context, 2)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL
        // spacing
        gridView.addItemDecoration(GridSpacingItemDecoration(2, 10, true, 0))
        gridView.layoutManager = gridLayoutManager


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    gridView.adapter =
                        CompanyListAdapter(context, R.layout.item, dashboardViewModel.companyList)
                } else {
                    val filteredList: ArrayList<String> = ArrayList()
                    for (company in dashboardViewModel.companyList) {
                        if (company.lowercase().contains(newText.lowercase()))
                            filteredList.add(company)
                    }
                    gridView.adapter = CompanyListAdapter(context, R.layout.item, filteredList)
                }
                return true
            }
        })
        if (dashboardViewModel.companyList.isEmpty()) {

            val dialog = ProgressDialog(context)
            dialog.setMessage("Loading data...")
            dialog.isIndeterminate = true
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            dialog.setCancelable(false)
            dialog.show()

            GlobalScope.launch {
                val response = loadingCompany.getCompanyList()
                withContext(Dispatchers.Main) {
                    dashboardViewModel.companyList = response.body() as ArrayList<String>
                    gridView.adapter =
                        CompanyListAdapter(context, R.layout.item, dashboardViewModel.companyList)
                    dialog.dismiss()
                }
            }

        }


    }

    class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int, private val includeEdge: Boolean, private val headerNum: Int) :
        RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view)
            val column = position % 2
            outRect.left = column * spacing
            outRect.right = spacing - (column + 1) * spacing
            if (position >= 2) {
                outRect.top = spacing
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}