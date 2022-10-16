package com.example.swapnil.ndkjsonarraysort

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.text.TextUtils
import android.view.*
import android.widget.DatePicker
import android.widget.Toast
import com.example.swapnil.ndkjsonarraysort.databinding.FragmentAddPersonBinding


class AddPersonFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var fragmentAddPersonBinding: FragmentAddPersonBinding
    private lateinit var sharedPersonViewModel: SharedPersonViewModel
    private val datePickerFragment: DatePickerFragment = DatePickerFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.run {
            sharedPersonViewModel = ViewModelProviders.of(this).get(SharedPersonViewModel::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentAddPersonBinding = FragmentAddPersonBinding.inflate(layoutInflater)
        datePickerFragment.onDateSetListenerProxy = this
        return fragmentAddPersonBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentAddPersonBinding.person = Person()
        fragmentAddPersonBinding.personDob.setOnClickListener {
            datePickerFragment.show(childFragmentManager, datePickerFragment.toString())
        }
        fragmentAddPersonBinding.addPerson.setOnClickListener {
            fragmentAddPersonBinding.person?.let {
                if (TextUtils.isEmpty(it.name.get())) {
                    Toast.makeText(context, "Please enter valid name.", Toast.LENGTH_LONG).show()
                    return@let
                }
                if (TextUtils.isEmpty(it.dob.get())) {
                    Toast.makeText(context, "Please enter valid dob.", Toast.LENGTH_LONG).show()
                    return@let
                }
                sharedPersonViewModel.addPerson(it)
                parentFragmentManager.setFragmentResult(EXTRA_ADD_PERSON, Bundle())
                (activity)?.supportFragmentManager?.popBackStack()
            }
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        fragmentAddPersonBinding.personDob.setText("$year-${month + 1}-$dayOfMonth")
    }

}