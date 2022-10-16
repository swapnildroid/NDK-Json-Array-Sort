package com.example.swapnil.ndkjsonarraysort

import android.content.res.AssetManager
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.swapnil.ndkjsonarraysort.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private val gson = Gson()
    lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var sharedPersonViewModel: SharedPersonViewModel

    private fun readMockData(): String {
        val inputStream = assets.open("MOCK_DATA.json")
        val text: String
        inputStream.bufferedReader().use {
            text = it.readText()
        }
        inputStream.close()
        return text
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        sharedPersonViewModel = ViewModelProviders.of(this).get(SharedPersonViewModel::class.java)
        val readMockData = readMockData()
        sharedPersonViewModel.populate(readMockData)
        // Example of a call to a native method
        title = stringFromJNI()
        addFragment(ListPersonFragment())
        supportFragmentManager.run {
            addOnBackStackChangedListener {
                if (backStackEntryCount == 0) finish()
            }
        }
    }


    fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment)
            .addToBackStack(fragment.toString())
            .commit()
    }

    fun sort() {
        val toJson = sharedPersonViewModel.getJson()
        val example = example(toJson)
        sharedPersonViewModel.populate(example)
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    external fun example(string: String): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }

}