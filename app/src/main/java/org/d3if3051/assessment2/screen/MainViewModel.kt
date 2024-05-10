package org.d3if3051.assessment2.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.d3if3051.assessment2.database.FinanceDao
import org.d3if3051.assessment2.model.Finance

class MainViewModel(dao: FinanceDao) : ViewModel() {

    val data: StateFlow<List<Finance>> = dao.getFinance().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

}
