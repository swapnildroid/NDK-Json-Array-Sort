package com.example.swapnil.ndkjsonarraysort

import androidx.databinding.ObservableField
import kotlin.random.Random

data class Person(
    var name: ObservableField<String> = ObservableField("Test#" + Random.nextInt(1, 100)),
    var dob: ObservableField<String> = ObservableField("")
)