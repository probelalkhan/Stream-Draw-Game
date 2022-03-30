package io.getstream.ui.game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.getstream.R
import io.getstream.ui.components.SubtitleText
import io.getstream.ui.components.TitleText
import io.getstream.ui.theme.PrimaryColor

@Composable
fun WordSelectionDialog(
    words: List<String>,
    wordSelected: (selection: String) -> Unit
){
    Dialog(onDismissRequest = {}) {
        WordSelectionDialogView(words = words, wordSelected = wordSelected)
    }
}

@Composable
private fun WordSelectionDialogView(
    words: List<String>,
    wordSelected: (selection: String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            TitleText(text = stringResource(id = R.string.select_a_word))
            Column(modifier = Modifier.padding(top = 18.dp)) {
                words.forEach {
                    SubtitleText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        text = it,
                        onClick = { wordSelected.invoke(it) }
                    )
                    Divider(color = PrimaryColor, thickness = 1.dp)
                }
            }
        }
    }
}

@Preview
@Composable
fun WordSelectionDialogViewPreview(){
    WordSelectionDialogView(listOf("mango", "banana", "apple")) {}
}
