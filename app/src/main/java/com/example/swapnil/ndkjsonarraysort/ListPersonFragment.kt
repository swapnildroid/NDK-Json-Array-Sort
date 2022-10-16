package com.example.swapnil.ndkjsonarraysort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.swapnil.ndkjsonarraysort.databinding.FragmentListPersonBinding

class ListPersonFragment : Fragment() {

    lateinit var fragmentListPersonBinding: FragmentListPersonBinding
    private lateinit var sharedPersonViewModel: SharedPersonViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentListPersonBinding = FragmentListPersonBinding.inflate(inflater)
        return fragmentListPersonBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.run {
            sharedPersonViewModel = ViewModelProviders.of(this)[SharedPersonViewModel::class.java]
        }
        fragmentListPersonBinding.personListRecyclerView.run {
            layoutManager = LinearLayoutManager(context)
        }
        updatePersonList()
        fragmentListPersonBinding.addPerson.setOnClickListener {
            (activity as? MainActivity)?.addFragment(AddPersonFragment())
        }
        fragmentListPersonBinding.actionSort.setOnClickListener {
            (activity as? MainActivity)?.sort()
            updatePersonList()
        }
        parentFragmentManager.setFragmentResultListener(EXTRA_ADD_PERSON, this) { key, bundle ->
            if (key == EXTRA_ADD_PERSON) {
                updatePersonList()
            }
        }
    }

    private fun updatePersonList() {
        fragmentListPersonBinding.personListRecyclerView.adapter =
            PersonRecyclerAdapter(layoutInflater, sharedPersonViewModel.personList.value!!.toList())
    }

}