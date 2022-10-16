package com.example.swapnil.ndkjsonarraysort

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableField
import org.json.JSONArray
import org.json.JSONObject

class SharedPersonViewModel : ViewModel() {

    val personList: MutableLiveData<MutableList<Person>> = MutableLiveData()

    fun addPerson(person: Person) {
        val mutableList = personList.value
        mutableList?.add(person)
        personList.postValue(mutableList)
    }

    init {
        personList.value = mutableListOf()
    }

    fun populate(jsonArrayString: String) {
        personList.value?.clear()
        val personJsonArray = JSONArray(jsonArrayString)
        for (i in (0 until personJsonArray.length())) {
            val jsonPersonObject = personJsonArray.getJSONObject(i)
            val person = Person()
            person.name = ObservableField("${jsonPersonObject?.getString("name")}")
            person.dob = ObservableField("${jsonPersonObject?.getString("dob")}")
            personList.value?.add(person)
        }
    }

    fun getJson(): String {
        val jsonArray = JSONArray()
        personList.value?.forEach {
            val jsonObject = JSONObject()
            jsonObject.put("name", "${it.name.get()}")
            jsonObject.put("dob", "${it.dob.get()}")
            jsonArray.put(jsonObject)
        }
        return jsonArray.toString()
    }

}