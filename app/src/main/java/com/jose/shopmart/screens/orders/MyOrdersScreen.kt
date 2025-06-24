package com.jose.shopmart.screens.orders

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.jose.shopmart.R
import com.jose.shopmart.ShopKartUtils
import com.jose.shopmart.component.LoadingComp2
import com.jose.shopmart.component.OrdersCard
import com.jose.shopmart.models.MOrder

@Composable
fun OrdersScreen(navController: NavController, viewModel: MyOrderViewModel = hiltViewModel()) {
    var orderList = emptyList<MOrder>()
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    if (!viewModel.fireOrder.value.data.isNullOrEmpty()) {
        orderList = viewModel.fireOrder.value.data!!.filter { it.user_id == userId }
    }

    Scaffold(
        topBar = { OrdersAppBar() },
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Color(0xFFFFF8F4) // Soft warm pastel background
    ) { innerPadding ->
        if (!viewModel.fireOrder.value.loading!!) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(Color(0xFFFFF8F4)) // Matching background
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                OrdersCard(
                    cardList = orderList,
                    navController = navController,
                    viewModel = viewModel
                )

                Spacer(modifier = Modifier.height(120.dp))
            }

            if (orderList.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 80.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(Color(0xFFFFCDD2), Color(0xFFF8BBD0))
                                )
                            )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.empty_cart),
                            contentScale = ContentScale.Crop,
                            contentDescription = "No Orders",
                            modifier = Modifier
                                .size(300.dp)
                                .padding(10.dp)
                        )
                    }

                    Text(
                        text = "No Orders\n  Order Something!",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Default,
                            color = Color(0xFFE91E63),
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
            }
        } else {
            LoadingComp2()
        }
    }
}

@Composable
fun OrdersAppBar() {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .background(
                Brush.horizontalGradient(
                    colors = listOf(Color(0xFFFFC1E3), Color(0xFFFFF59D))
                )
            )
    ) {
        Text(
            text = "My Orders",
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Default,
                color = Color(0xFF5D1049)
            )
        )

        Divider(
            modifier = Modifier
                .height(2.dp)
                .width(320.dp),
            color = Color(0xFFE91E63)
        )
    }
}
