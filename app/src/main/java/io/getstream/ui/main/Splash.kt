package io.getstream.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import io.getstream.R
import io.getstream.ui.components.PrimaryButton
import io.getstream.ui.components.SecondaryButton
import io.getstream.ui.nav.NAV_CREATE_GROUP
import io.getstream.ui.nav.NAV_JOIN_GROUP

@Composable
fun Splash(navController: NavController) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {

        val column = createRef()


        Column(
            modifier = Modifier.constrainAs(column) {
                bottom.linkTo(parent.bottom, 64.dp)
                start.linkTo(parent.start, 16.dp)
                end.linkTo(parent.end, 16.dp)
                width = Dimension.fillToConstraints
            }
        ) {
            PrimaryButton(
                text = stringResource(id = R.string.create_group),
                marginBottom = 16.dp,
                onClick = {
                    navController.navigate(NAV_CREATE_GROUP)
                }
            )
            SecondaryButton(
                text = stringResource(id = R.string.join_group),
                onClick = {
                    navController.navigate(NAV_JOIN_GROUP)
                }
            )
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    Splash(rememberNavController())
}
