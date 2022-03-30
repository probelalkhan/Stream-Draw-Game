package io.getstream.ui.main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.getstream.ui.main.group.CreateGroup
import io.getstream.ui.main.group.JoinGroup
import io.getstream.ui.nav.NAV_CREATE_GROUP
import io.getstream.ui.nav.NAV_JOIN_GROUP
import io.getstream.ui.nav.NAV_SPLASH

@Composable
fun MainScreen(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NAV_SPLASH){
        composable(NAV_SPLASH){ Splash(navController) }
        composable(NAV_CREATE_GROUP){ CreateGroup(hiltViewModel()) }
        composable(NAV_JOIN_GROUP){ JoinGroup(hiltViewModel()) }
    }

}
