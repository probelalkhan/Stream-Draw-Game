package io.getstream.ui.main.group

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import io.getstream.R
import io.getstream.ui.components.AppTextField
import io.getstream.ui.components.PrimaryButton
import io.getstream.ui.main.MainViewModel

@Composable
fun CreateGroupForm(viewModel: MainViewModel){
    var displayName by remember { mutableStateOf("") }
    var limitUser by remember { mutableStateOf(5) }
    var limitTime by remember { mutableStateOf(60) }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val column = createRef()

        Column(
            modifier = Modifier.constrainAs(column){
                top.linkTo(parent.top, 12.dp)
                bottom.linkTo(parent.bottom, 12.dp)
                start.linkTo(parent.start, 12.dp)
                end.linkTo(parent.end, 12.dp)
                width = Dimension.fillToConstraints
            }
        ) {
            AppTextField(
                label = stringResource(id = R.string.display_name),
                marginTop = 16.dp,
                onValueChange = { displayName = it },
                value = displayName
            )

            AppTextField(
                label = stringResource(id = R.string.limit_user),
                keyboardType = KeyboardType.Number,
                marginTop = 16.dp,
                onValueChange = { limitUser = it.toInt() },
                value = limitUser.toString()
            )

            AppTextField(
                label = stringResource(id = R.string.limit_time),
                keyboardType = KeyboardType.Number,
                marginTop = 16.dp,
                onValueChange = { limitTime = it.toInt() },
                value = limitTime.toString()
            )

            PrimaryButton(
                onClick = {
                    viewModel.createGameGroup(displayName, limitUser, limitTime)
                },
                text = stringResource(id = R.string.create_group),
                marginTop = 24.dp
            )
        }
    }
}
