package io.getstream.ui.game

import android.graphics.Bitmap
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.unit.dp
import io.getstream.sketchbook.Sketchbook
import io.getstream.sketchbook.SketchbookController

@Composable
fun SketchbookScreen(
    controller: SketchbookController,
    bitmapListener: (bitmap: Bitmap) -> Unit
) {

    Box(modifier = Modifier
        .fillMaxSize()
        .border(2.dp, Color.Black)
        .padding(12.dp)) {

        Sketchbook(
            modifier = Modifier.fillMaxSize(),
            controller = controller,
            backgroundColor = Color.White,
            onPathListener = {
                val bitmap = controller.getSketchbookBitmap().asAndroidBitmap()
                bitmapListener.invoke(bitmap)
            }
        )
    }

}
