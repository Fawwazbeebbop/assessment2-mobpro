package org.d3if3051.assessment2.screen

import androidx.lifecycle.ViewModel
import org.d3if3051.assessment2.model.Finance

class MainViewModel: ViewModel() {
    val data = getDataDummy()

    private fun getDataDummy(): List<Finance>{
        val data = mutableListOf<Finance>()
        for (i in 29 downTo 20){
            data.add(
                Finance(
                    i.toLong(),
                    "Shopping in Alfamart",
                    "Expense Ratio 250.000",
                    "2024-02-$i 11:30:31"
                )
            )
        }
        return data
    }
}