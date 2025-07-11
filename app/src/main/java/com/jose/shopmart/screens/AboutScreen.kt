package com.jose.shopmart.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jose.shopmart.R
import com.jose.shopmart.ShopKartUtils
import com.jose.shopmart.component.BackButton


@Composable
fun AboutScreen(navController: NavController){

    val uriHandler = LocalUriHandler.current
    Scaffold(topBar = { BackButton(navController = navController,
        topBarTitle = "About")}, backgroundColor = ShopKartUtils.Primary50,
        modifier = Modifier
        .fillMaxSize()) { innerPadding ->

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {

            Box(modifier = Modifier
                .height(200.dp)
                .width(150.dp)
                .fillMaxSize()){
                Image(painter = painterResource(id = R.drawable.rengoku), contentDescription = "About Image")
            }

            Text(text = "ShopMart is a Online Shopping App developed using Jetpack Compose and Firebase for backend," +
                    " this app follows the Clean Architecture and has a clean UI.",
                style = TextStyle(fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif),
                modifier = Modifier.padding(top = 10.dp,
                    bottom = 10.dp),
                textAlign = TextAlign.Center)

//            Row(modifier = Modifier
//                .fillMaxWidth()
//                .height(100.dp)) {
            Text(text = buildAnnotatedString {
                Text(text = " Developer:",
                    style = TextStyle(fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif),
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp))
                Text(text = "Joseph \t\t\t\t Ahadi",
                    style = TextStyle(fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily.Serif))
            }, textAlign = TextAlign.Center)

            Row(modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly) {
                Box(modifier = Modifier
                    .size(100.dp)
                    .fillMaxSize()
                    .clickable { uriHandler.openUri(uri = "https://github.com/Mr-xavage/Shop-Mart") }) {
                    Icon(
                        painter = painterResource(id = R.drawable.github),
                        contentDescription = "Github Logo"
                    )
                }

                Box(modifier = Modifier
                    .size(100.dp)
                    .fillMaxSize()
                    .clickable { uriHandler.openUri("https://github.com/Mr-xavage/Shop-Mart") }) {
                    Icon(
                        painter = painterResource(id = R.drawable.github),
                        contentDescription = "Github Logo"
                    )
                }
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceEvenly) {
                Box(modifier = Modifier
                    .size(50.dp)
                    .fillMaxSize()
                    .padding(top = 8.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.android),
                        contentDescription = "Android Logo"
                    )
                }

                Divider(modifier = Modifier.height(50.dp).width(1.dp),
                    color = Color.Black.copy(alpha = 0.4f))

                Box(modifier = Modifier
                    .size(50.dp)
                    .fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.jetpack_compose),
                        contentDescription = "Jetpack Compose Logo"
                    )
                }

                Divider(modifier = Modifier.height(50.dp).width(1.dp),
                    color = Color.Black.copy(alpha = 0.4f))

                Box(modifier = Modifier
                    .size(50.dp)
                    .fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.firebase),
                        contentDescription = "Firebase Logo"
                    )
                }

                Divider(modifier = Modifier.height(50.dp).width(1.dp), color = Color.Black.copy(alpha = 0.4f))

                Box(modifier = Modifier
                    .size(50.dp)
                    .fillMaxSize()
                    .padding(top = 8.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.kotlin),
                        contentDescription = "Kotlin Logo"
                    )
                }
            }

//            }
        }
    }
}

@Preview
@Composable
fun Prev(){
    AboutScreen(navController = rememberNavController())
}