package org.d3if3051.assessment2.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3051.assessment2.R
import org.d3if3051.assessment2.database.FinanceDb
import org.d3if3051.assessment2.ui.theme.Assessment2Theme
import org.d3if3051.assessment2.util.ViewModelFactory

const val KEY_ID_FINANCE = "idFinance"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(navControlller: NavHostController, id: Long? = null){
    val context = LocalContext.current
    val db = FinanceDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: NoteViewModel = viewModel(factory = factory)

    var title by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }

    var showAlert by remember { mutableStateOf(false) }

    LaunchedEffect(true){
        if (id == null) return@LaunchedEffect
        val data = viewModel.getFinance(id) ?: return@LaunchedEffect
        title = data.title
        note = data.note
        category = data.category
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                     IconButton(onClick = { navControlller.popBackStack() }) {
                         Icon(
                             imageVector = Icons.Filled.ArrowBack,
                             contentDescription = stringResource(R.string.back_button),
                             tint = MaterialTheme.colorScheme.primary
                         )
                     }            
                },
                title = {
                    if (id == null)
                        Text(text = stringResource(id = R.string.add_note))
                    else
                        Text(text = stringResource(id = R.string.change_note))
                        },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {
                        if (title == "" || note == "" || category == ""){
                            Toast.makeText(context, R.string.note_invalid, Toast.LENGTH_LONG).show()
                            return@IconButton
                        }
                        if (id == null){
                            viewModel.insert(title, note, category)
                        } else{
                            viewModel.update(id, title, note, category)
                        }
                        navControlller.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.CheckCircle,
                            contentDescription = stringResource(R.string.save_button),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (id != null){
                        DeleteNote { showAlert = true }
                        DisplayAlert(
                            openDialog = showAlert,
                            onDismissReq = { showAlert = false }
                        ) {
                            showAlert = false
                            viewModel.delete(id)
                            navControlller.popBackStack()
                        }
                    }
                }
            )
        }
    ) {padding ->
        FormPencatatan(
            title = title,
            onTitleChange = { title = it },
            note = note,
            onNoteChange = { note = it },
            category = category,
            onCategoryChange = { category = it },
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
fun DeleteNote(delete: () -> Unit){
    var expand by remember { mutableStateOf(false) }

    IconButton(onClick = { expand = true }) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(R.string.delete_button),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expand,
            onDismissRequest = { expand = false }
        ) {
            DropdownMenuItem(
                text = { Text(text = stringResource(id = R.string.delete)) },
                onClick = {
                    expand = false
                    delete()
                }
            )
        }
    }
}

@Composable
fun FormPencatatan(
    title: String, onTitleChange: (String) -> Unit,
    note: String, onNoteChange: (String) -> Unit,
    category: String, onCategoryChange: (String) -> Unit,
    modifier: Modifier
){
    val radioOptions = listOf(
        stringResource(id = R.string.list_category1),
        stringResource(id = R.string.list_category2),
        stringResource(id = R.string.list_category3),
        stringResource(id = R.string.list_category4),
        stringResource(id = R.string.list_category5)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = {onTitleChange(it)},
            label = { Text(text = stringResource(R.string.title)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = note,
            onValueChange = {onNoteChange(it)},
            label = { Text(text = stringResource(R.string.note)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = stringResource(R.string.category)
        )
        Column (
            modifier = Modifier
                .padding(top = 6.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
        ){
            radioOptions.forEach { text ->
                CategoryOption(
                    label = text,
                    isSelected = category == text,
                    modifier = Modifier
                        .selectable(
                            selected = category == text,
                            onClick = {
                                onCategoryChange(text)
                            },
                            role = Role.RadioButton
                        )
                        .padding(10.dp)
                )
            }
        }
    }
}

@Composable
fun CategoryOption(label: String, isSelected: Boolean, modifier: Modifier){
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = null
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}



@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun AddNoteScreenPreview() {
    Assessment2Theme {
        AddNoteScreen(rememberNavController())
    }
}