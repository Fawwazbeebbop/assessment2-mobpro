package org.d3if3051.assessment2.screen

import android.content.res.Configuration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.d3if3051.assessment2.R
import org.d3if3051.assessment2.ui.theme.Assessment2Theme

@Composable
fun DisplayAlert(
    openDialog: Boolean,
    onDismissReq: () -> Unit,
    onConfirm: () -> Unit
){
    if (openDialog){
        AlertDialog(
            text = { Text(text = stringResource(R.string.delete_message)) },
            onDismissRequest = { onDismissReq() },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text(text = stringResource(R.string.button_del))
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismissReq() }) {
                    Text(text = stringResource(R.string.button_no))
                }
            }
        )
    }
}



@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun AlertDialogPreview() {
    Assessment2Theme {
        DisplayAlert(
            openDialog = true,
            onDismissReq = {},
            onConfirm = {}
        )
    }
}