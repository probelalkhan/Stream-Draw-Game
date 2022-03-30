package io.getstream.ui.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import io.getstream.sketchbook.rememberSketchbookController
import io.getstream.utils.toBitmap

/*
* Host -> should see the word selection dialog , should be able to draw
* Normal Participant -> Cannot draw and should not see the word selection dialog
*
* Chat Window -> Host cannot send the message
* */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GameScreen(viewModel: GameViewModel) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    GameScreenBottomSheet(
        bottomSheetScaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            ChatWindow(viewModel)
        }
    ) {
      GameDrawing(viewModel = viewModel)
    }
}

@Composable
fun GameDrawing(viewModel: GameViewModel) {
    val isHost by viewModel.isHost.collectAsState()
    if (isHost) {
        GameDrawingHost(viewModel)
    } else {
        GameDrawingNormal(viewModel)
    }
}

@Composable
fun GameDrawingHost(viewModel: GameViewModel) {
    val randomWords by viewModel.randomWords.collectAsState()
    val selectedWord by viewModel.selectedWord.collectAsState()
    if (randomWords != null && selectedWord == null) {
        WordSelectionDialog(randomWords!!) { word ->
            viewModel.setSelectedWord(word)
        }
    } else {
        val sketchbookController = rememberSketchbookController()
        sketchbookController.setPaintStrokeWidth(10f)
        sketchbookController.setPaintColor(Color.Black)
        SketchbookScreen(sketchbookController) { viewModel.broadcastBitmap(it) }
    }
}

@Composable
fun GameDrawingNormal(viewModel: GameViewModel) {
    val drawingImage = viewModel.newDrawingImage.value
    Box(modifier = Modifier.fillMaxSize()) {
        drawingImage?.toBitmap()?.asImageBitmap()?.let {
            Image(bitmap = it, contentDescription = "")
        }
    }
}
