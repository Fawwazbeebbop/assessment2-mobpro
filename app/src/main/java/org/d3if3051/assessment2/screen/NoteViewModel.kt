package org.d3if3051.assessment2.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3051.assessment2.database.FinanceDao
import org.d3if3051.assessment2.model.Finance
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteViewModel(private val dao: FinanceDao) : ViewModel() {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    fun insert(title: String, note: String, category: String){
        val finance = Finance(
            date = dateFormat.format(Date()),
            title = title,
            note = note,
            category = category
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(finance)
        }
    }

    suspend fun getFinance(id: Long): Finance?{
        return dao.getFinanceById(id)
    }

    fun update(id: Long, title: String, note: String, category: String){
        val finance = Finance(
            id = id,
            date = dateFormat.format(Date()),
            title = title,
            note = note,
            category = category
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(finance)
        }
    }

    fun delete(id: Long){
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
}