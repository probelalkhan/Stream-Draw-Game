package io.getstream.ui.game

import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GameScreenBottomSheet(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    sheetContent: @Composable () -> Unit,
    mainContent: @Composable () -> Unit
) {
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            sheetContent()
        },
        sheetPeekHeight = 12.dp
    ) {
        mainContent()
    }
}
