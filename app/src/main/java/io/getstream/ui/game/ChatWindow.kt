package io.getstream.ui.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import io.getstream.R
import io.getstream.ui.components.AppTextField
import io.getstream.ui.components.NormalText

@Composable
fun ChatWindow(
    viewModel: GameViewModel
) {
    val isHost by viewModel.isHost.collectAsState()
    val gameChatList by viewModel.gameChatMessages.collectAsState()
    var guess by remember { mutableStateOf("") }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        val (textField, sendButton, chatList) = createRefs()

        LazyColumn(content = {
            items(gameChatList){ item ->
                NormalText(text = "${item.user}: ${item.message}", size = 18.sp)
            }
        })

        if (!isHost) {
            Box(
                modifier = Modifier.constrainAs(textField) {
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(sendButton.start)
                    width = Dimension.fillToConstraints
                }
            ) {
                AppTextField(
                    label = stringResource(id = R.string.enter_guess),
                    value = guess,
                    onValueChange = { guess = it })
            }

            Button(
                modifier = Modifier
                    .constrainAs(sendButton) {
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .width(52.dp)
                    .height(52.dp),
                onClick = {
                    viewModel.sendGuessToChannel(guess)
                    guess = ""
                },
                shape = CircleShape
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_send), contentDescription = "")
            }
        }

    }
}


@Preview
@Composable
fun PreviewChatWindow() {
    ChatWindow(hiltViewModel())
}
