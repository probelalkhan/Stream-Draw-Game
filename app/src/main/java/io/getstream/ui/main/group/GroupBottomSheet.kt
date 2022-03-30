package io.getstream.ui.main.group

import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp
import kotlin.math.exp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GroupBottomSheet(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    expand: Boolean,
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

    if(expand && bottomSheetScaffoldState.bottomSheetState.isCollapsed){
        LaunchedEffect(bottomSheetScaffoldState.bottomSheetState){
            bottomSheetScaffoldState.bottomSheetState.expand()
        }
    }
}
