package org.d3if3051.assessment2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.d3if3051.assessment2.screen.AddNoteScreen
import org.d3if3051.assessment2.screen.AppScreen
import org.d3if3051.assessment2.screen.FirstScreen
import org.d3if3051.assessment2.screen.KEY_ID_FINANCE

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()){
    NavHost(
        navController = navController,
        startDestination = ScreenApp.Home.route
    ){
        composable(route = ScreenApp.Home.route){
            FirstScreen(navController)
        }
        composable(route = ScreenApp.Content.route){
            AppScreen(navController)
        }
        composable(route = ScreenApp.NewForm.route){
            AddNoteScreen(navController)
        }
        composable(
            route = ScreenApp.ChangeForm.route,
            arguments = listOf(
                navArgument(KEY_ID_FINANCE) { type = NavType.LongType }
            )
        ){navBackStackEntry ->  
            val id = navBackStackEntry.arguments?.getLong(KEY_ID_FINANCE)
            AddNoteScreen(navController, id)
        }
    }
}