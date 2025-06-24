package com.jose.shopmart.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jose.shopmart.navigation.BottomNavScreens.AddEmployee
import com.jose.shopmart.navigation.BottomNavScreens.AddProductSliderAdmin
import com.jose.shopmart.navigation.BottomNavScreens.AddProductSliderEmpl
import com.jose.shopmart.navigation.BottomNavScreens.AddRemoveBrandAdmin
import com.jose.shopmart.navigation.BottomNavScreens.AddRemoveBrandEmpl
import com.jose.shopmart.navigation.BottomNavScreens.AddressScreen
import com.jose.shopmart.navigation.BottomNavScreens.AdminScreen
import com.jose.shopmart.navigation.BottomNavScreens.DeliveredItems
import com.jose.shopmart.navigation.BottomNavScreens.DeliveredItemsEmp
import com.jose.shopmart.navigation.BottomNavScreens.EditAddressScreen
import com.jose.shopmart.navigation.BottomNavScreens.EmployeeAttendance
import com.jose.shopmart.navigation.BottomNavScreens.EmployeeScreen
import com.jose.shopmart.navigation.BottomNavScreens.OnTheWayItems
import com.jose.shopmart.navigation.BottomNavScreens.OnTheWayItemsEmp
import com.jose.shopmart.navigation.BottomNavScreens.OrderSuccessScreen
import com.jose.shopmart.navigation.BottomNavScreens.OrderSummaryScreen
import com.jose.shopmart.navigation.BottomNavScreens.OrderedItems
import com.jose.shopmart.navigation.BottomNavScreens.OrderedItemsEmp
import com.jose.shopmart.navigation.BottomNavScreens.PaymentScreen
import com.jose.shopmart.navigation.BottomNavScreens.SearchScreen
import com.jose.shopmart.screens.AboutScreen
import com.jose.shopmart.screens.admin.AddEmployee
import com.jose.shopmart.screens.admin.AddProductSliderAdmin
import com.jose.shopmart.screens.admin.AddRemoveBrandAdmin
import com.jose.shopmart.screens.admin.AdminScreen
import com.jose.shopmart.screens.admin.AdminScreenViewModel
import com.jose.shopmart.screens.admin.EmployeeAttendance
import com.jose.shopmart.screens.admin.orderstatus.DeliveredItems
import com.jose.shopmart.screens.admin.orderstatus.OnTheWayItems
import com.jose.shopmart.screens.admin.orderstatus.OrderedItems
import com.jose.shopmart.screens.cart.CartScreen
import com.jose.shopmart.screens.cart.CartScreenViewModel
import com.jose.shopmart.screens.checkout.OrderSuccessScreen
import com.jose.shopmart.screens.checkout.address.AddressScreen
import com.jose.shopmart.screens.checkout.address.EditAddressScreen
import com.jose.shopmart.screens.checkout.ordersummary.OrderSummaryScreen
import com.jose.shopmart.screens.checkout.payment.PaymentScreen
import com.jose.shopmart.screens.details.DetailsScreen
import com.jose.shopmart.screens.details.DetailsScreenViewModel
import com.jose.shopmart.screens.employee.AddProductSliderEmpl
import com.jose.shopmart.screens.employee.AddRemoveBrandEmpl
import com.jose.shopmart.screens.employee.EmployeeScreen
import com.jose.shopmart.screens.employee.orderstatus.DeliveredItemsEmp
import com.jose.shopmart.screens.employee.orderstatus.OnTheWayItemsEmp
import com.jose.shopmart.screens.employee.orderstatus.OrderedItemsEmp
import com.jose.shopmart.screens.home.HomeScreen
import com.jose.shopmart.screens.home.HomeViewModel
import com.jose.shopmart.screens.myorderdetails.MyOrderDetailsScreen
import com.jose.shopmart.screens.myprofile.MyProfileScreen
import com.jose.shopmart.screens.orders.OrdersScreen
import com.jose.shopmart.screens.profile.ProfileScreen
import com.jose.shopmart.screens.search.SearchScreen


//BottomNavScreens.Home.route
@Composable
fun BottomNavigation(
    navController: NavHostController,
    email: String,
    signOut: () -> Unit,
) {
    NavHost(navController = navController, startDestination = BottomNavScreens.Home.route) {
        composable(BottomNavScreens.Home.route) {
            val viewModel = hiltViewModel<HomeViewModel>()
            val viewModelDetails = hiltViewModel<DetailsScreenViewModel>()

            val context = LocalContext.current
            val haptic = LocalHapticFeedback.current

            HomeScreen(navController = navController, viewModel) { product ->
                //Uploading Item to Firebase Cart
                viewModelDetails.uploadCartToFirebase(
                    url = product.product_url,
                    title = product.product_title,
                    description = product.product_description,
                    price = product.product_price,
                    stock = product.stock,
                    category = product.category,
                    productId = product.product_id
                )

                //Haptic Feedback
                haptic.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
                Toast.makeText(context, "Item added to cart", Toast.LENGTH_SHORT).show()
            }
        }

        composable(BottomNavScreens.Orders.route) {
            OrdersScreen(navController = navController)
        }

        composable(BottomNavScreens.Cart.route) {
            val viewModel = hiltViewModel<CartScreenViewModel>()
            CartScreen(navController = navController, viewModel)
        }

        composable(BottomNavScreens.Profile.route) {
            ProfileScreen(
                navController = navController,
                email = email,
            ) {
                signOut()
            }
        }

        composable(BottomNavScreens.MyProfile.route) {
            MyProfileScreen(navController = navController)
        }

        composable(BottomNavScreens.About.route) {
            AboutScreen(navController = navController)
        }

        val detailsScreen = BottomNavScreens.Details.route
        composable("$detailsScreen/{imageUrl}/{productTitle}/{productDescription}/{productPrice}/{stock}/{category}/{productId}",
            arguments = listOf(
                navArgument("imageUrl") {
                    type = NavType.StringType
                },
                navArgument("productTitle") {
                    type = NavType.StringType
                },
                navArgument("productDescription") {
                    type = NavType.StringType
                },

                navArgument("productPrice") {
                    type = NavType.IntType
                },

                navArgument("stock") {
                    type = NavType.IntType
                },

                navArgument("category") {
                    type = NavType.StringType
                },

                navArgument("productId") {
                    type = NavType.StringType
                }
            )) { backstack ->
            val imageUrl = backstack.arguments?.getString("imageUrl")
            val productTitle = backstack.arguments?.getString("productTitle")
            val productDescription = backstack.arguments?.getString("productDescription")
            val productPrice = backstack.arguments?.getInt("productPrice")
            val stock = backstack.arguments?.getInt("stock")
            val category = backstack.arguments?.getString("category")
            val productId = backstack.arguments?.getString("productId")
            DetailsScreen(
                navController = navController,
                imageUrl = imageUrl.toString(),
                productTitle = productTitle.toString(),
                productDescription = productDescription.toString(),
                productPrice = productPrice!!,
                stock = stock!!,
                category = category!!,
                productId = productId!!,
            )
        }

        val myOrderDetails = BottomNavScreens.MyOrderDetails.route
        composable(
            "$myOrderDetails/{status}/{product_title}/{product_url}/{product_price}/{quantity}/{payment_method}/{order_id}/{order_date}",
            arguments = listOf(
                navArgument("status") {
                    type = NavType.StringType
                },

                navArgument("product_title") {
                    type = NavType.StringType
                },

                navArgument("product_url") {
                    type = NavType.StringType
                },

                navArgument("product_price") {
                    type = NavType.IntType
                },

                navArgument("quantity") {
                    type = NavType.IntType
                },

                navArgument("payment_method") {
                    type = NavType.StringType
                },

                navArgument("order_id") {
                    type = NavType.StringType
                },

                navArgument("order_date") {
                    type = NavType.StringType
                },
            )
        ) { bacStack ->
            val status = bacStack.arguments?.getString("status")
            val productTitle = bacStack.arguments?.getString("product_title")
            val productUrl = bacStack.arguments?.getString("product_url")
            val productPrice = bacStack.arguments?.getInt("product_price")
            val quantity = bacStack.arguments?.getInt("quantity")
            val paymentMethod = bacStack.arguments?.getString("payment_method")
            val orderId = bacStack.arguments?.getString("order_id")
            val orderDate = bacStack.arguments?.getString("order_date")
            MyOrderDetailsScreen(
                navController = navController,
                status = status!!,
                product_title = productTitle!!,
                product_url = productUrl!!,
                product_price = productPrice!!,
                quantity = quantity!!,
                payment_method = paymentMethod!!,
                order_id = orderId!!,
                order_date = orderDate!!
            )
        }


        composable(BottomNavScreens.AddressScreen.route) {
            AddressScreen(navController = navController)
        }

        composable(BottomNavScreens.EditAddressScreen.route) {
            EditAddressScreen(navController = navController)
        }

        composable(BottomNavScreens.OrderSummaryScreen.route) {
            OrderSummaryScreen(navController = navController)
        }

        val paymentScreen = BottomNavScreens.PaymentScreen.route
        composable("$paymentScreen/{totalAmount}", arguments = listOf(navArgument("totalAmount") {
            type = NavType.IntType
        })) { backStack ->
            backStack.arguments?.getInt("totalAmount")
                .let { PaymentScreen(navController = navController, totalAmount = it!!) }

        }

        composable(BottomNavScreens.OrderSuccessScreen.route) {
            OrderSuccessScreen(navController = navController)
        }

        composable(BottomNavScreens.SearchScreen.route) {
            SearchScreen(navController = navController)
        }

        composable(BottomNavScreens.AdminScreen.route) {
            AdminScreen(navController = navController)
        }

        composable(BottomNavScreens.EmployeeScreen.route) {
            EmployeeScreen(navController = navController)
        }

        composable(BottomNavScreens.AddRemoveBrandAdmin.route) {
            AddRemoveBrandAdmin(navHostController = navController)
        }

        composable(BottomNavScreens.AddProductSliderAdmin.route) {
            AddProductSliderAdmin(navHostController = navController)
        }

        composable(BottomNavScreens.EmployeeAttendance.route) {
            val viewModel = hiltViewModel<AdminScreenViewModel>()
            EmployeeAttendance(navController = navController, viewModel)
        }

        composable(BottomNavScreens.AddRemoveBrandEmpl.route) {
            AddRemoveBrandEmpl(navHostController = navController)
        }

        composable(BottomNavScreens.AddProductSliderEmpl.route) {
            AddProductSliderEmpl(navHostController = navController)
        }

        composable(BottomNavScreens.AddEmployee.route) {
            AddEmployee(navHostController = navController)
        }

        composable(BottomNavScreens.OrderedItems.route) {
            OrderedItems(navHostController = navController)
        }

        composable(BottomNavScreens.OnTheWayItems.route) {
            OnTheWayItems(navHostController = navController)
        }

        composable(BottomNavScreens.DeliveredItems.route) {
            DeliveredItems(navHostController = navController)
        }

        composable(BottomNavScreens.OrderedItemsEmp.route) {
            OrderedItemsEmp(navHostController = navController)
        }

        composable(BottomNavScreens.OnTheWayItemsEmp.route) {
            OnTheWayItemsEmp(navHostController = navController)
        }

        composable(BottomNavScreens.DeliveredItemsEmp.route) {
            DeliveredItemsEmp(navHostController = navController)
        }
    }
}