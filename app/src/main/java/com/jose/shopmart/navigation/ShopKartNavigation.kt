package com.jose.shopmart.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jose.shopmart.screens.ForgotPasswordScreen
import com.jose.shopmart.screens.SplashScreen
import com.jose.shopmart.screens.login.LoginScreen2
import com.jose.shopmart.screens.mainscreenholder.MainScreenHolder
import com.jose.shopmart.screens.register.RegisterScreen


@Composable
fun ShopKartNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavScreens.SplashScreen.name){
        composable(NavScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }

        composable(NavScreens.LoginScreen.name){
            LoginScreen2(navController = navController)
        }

        composable(NavScreens.RegisterScreen.name){
            RegisterScreen(navController = navController)
        }

        composable(NavScreens.MainScreenHolder.name){
            MainScreenHolder(navController = navController)
        }

        composable(NavScreens.ForgotPasswordScreen.name) {
            ForgotPasswordScreen(navHostController = navController)
        }

    }

}