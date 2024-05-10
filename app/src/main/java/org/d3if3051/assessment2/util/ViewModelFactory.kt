package org.d3if3051.assessment2.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3051.assessment2.database.FinanceDao
import org.d3if3051.assessment2.screen.MainViewModel
import org.d3if3051.assessment2.screen.NoteViewModel

class ViewModelFactory(
private val dao: FinanceDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(dao) as T
        } else if (modelClass.isAssignableFrom(NoteViewModel::class.java)){
            return NoteViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}