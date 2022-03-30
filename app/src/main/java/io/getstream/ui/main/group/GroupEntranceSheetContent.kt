package io.getstream.ui.main.group

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.getstream.R
import io.getstream.chat.android.client.models.Channel
import io.getstream.ui.components.NormalText
import io.getstream.ui.components.PrimaryButton
import io.getstream.ui.components.SubtitleText
import io.getstream.ui.components.TitleText
import io.getstream.ui.game.GameActivity
import io.getstream.ui.theme.LightColor
import io.getstream.ui.theme.PrimaryColor
import io.getstream.utils.groupId


@Composable
fun GroupEntranceSheetContent(
    channel: Channel?
) {
    val context = LocalContext.current
    Box(
        Modifier
            .padding(16.dp)
            .heightIn(max = 400.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleText(
                text = stringResource(id = R.string.group_entrance),
                marginTop = 12.dp,
                marginBottom = 12.dp
            )
            SubtitleText(
                text = stringResource(id = R.string.group_created_desc),
                marginTop = 8.dp,
                marginBottom = 18.dp
            )
            Box(
                modifier = Modifier
                    .background(PrimaryColor, shape = RoundedCornerShape(8.dp))
                    .wrapContentSize()
                    .padding(12.dp)
            ) {
                NormalText(
                    text = channel?.groupId ?: "",
                    size = 32.sp,
                    weight = FontWeight.Bold,
                    align = TextAlign.Center,
                    color = LightColor,
                    onClick = { }
                )
            }
            NormalText(
                text = stringResource(id = R.string.invite_people),
                size = 16.sp,
                weight = FontWeight.Normal,
                align = TextAlign.Center,
                marginTop = 16.dp,
                onClick = { }
            )
            PrimaryButton(
                text = stringResource(id = R.string.continue_to_game),
                marginTop = 24.dp,
                marginBottom = 24.dp,
                onClick = {
                    channel?.let { GameActivity.start(context, it.cid) }
                }
            )
        }
    }
}

@Preview
@Composable
fun DefaultPreview1(){
    GroupEntranceSheetContent(null)
}
