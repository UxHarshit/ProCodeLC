package com.uxharshit.procodelc.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uxharshit.procodelc.R

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    var companyList :  ArrayList<String> = ArrayList()

    val text: LiveData<String> = _text
    var urlBase = R.string.baseUrl.toString()
    var urlCompanyTc = urlBase + "tc/"
    var urlCompanyList = urlBase + "getCompany"
    var urlCompanyDetails = urlBase + "getCompanyInfo"
}