package io.getstream.ui.main.group

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.getstream.R
import io.getstream.data.GameConnectionState
import io.getstream.ui.components.AppTextField
import io.getstream.ui.components.SecondaryButton
import io.getstream.ui.game.GameActivity
import io.getstream.ui.main.MainViewModel


@Composable
fun JoinGroup(viewModel: MainViewModel) {
    val context = LocalContext.current
    val gameConnection by viewModel.gameConnectionState.collectAsState()

    var displayName by remember { mutableStateOf("") }
    var groupCode by remember { mutableStateOf("") }

    if(gameConnection is GameConnectionState.Success){
        GameActivity.start(context, (gameConnection as GameConnectionState.Success).channel.cid)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 82.dp)
        ) {
            AppTextField(
                label = stringResource(id = R.string.display_name),
                onValueChange = { displayName = it },
                value = displayName,
                marginTop = 16.dp
            )
            AppTextField(
                label = stringResource(id = R.string.group_code),
                onValueChange = { groupCode = it },
                value = groupCode,
                marginTop = 16.dp
            )
            SecondaryButton(
                text = stringResource(id = R.string.join_group),
                onClick = {
                    viewModel.joinGameGroup(displayName, groupCode)
                },
                marginTop = 24.dp
            )
        }
    }
}

@Preview
@Composable
fun PreviewJoinGroup() {
    JoinGroup(hiltViewModel())
}
