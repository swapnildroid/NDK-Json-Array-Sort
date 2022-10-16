package com.example.swapnil.ndkjsonarraysort

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import com.example.swapnil.ndkjsonarraysort.databinding.ItemPersonBinding

class PersonRecyclerAdapter(
    private val layoutInflater: LayoutInflater,
    private val personList: List<Person>
) : RecyclerView.Adapter<PersonRecyclerAdapter.PersonViewHolder>() {

    class PersonViewHolder(val itemPersonBinding: ItemPersonBinding) : RecyclerView.ViewHolder(itemPersonBinding.root)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PersonViewHolder {
        return PersonViewHolder(
            ItemPersonBinding.inflate(
                layoutInflater
            )
        )
    }

    override fun getItemCount(): Int {
        return personList.size
    }

    override fun onBindViewHolder(personViewHolder: PersonViewHolder, position: Int) {
        personViewHolder.itemPersonBinding.person = personList[position]
    }


}