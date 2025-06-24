package com.jose.shopmart.screens.register

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jose.shopmart.R
import com.jose.shopmart.ShopKartUtils
import com.jose.shopmart.component.BackButton
import com.jose.shopmart.component.PasswordTextBox
import com.jose.shopmart.component.PillButton
import com.jose.shopmart.component.TextBox
import com.jose.shopmart.screens.login.Quotes
import com.jose.shopmart.ui.theme.Orange900

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val nameState = rememberSaveable { mutableStateOf("") }
    val emailState = rememberSaveable { mutableStateOf("") }
    val passwordState = rememberSaveable { mutableStateOf("") }
    val phoneState = rememberSaveable { mutableStateOf("") }
    val addressState = rememberSaveable { mutableStateOf("") }
    val errorState = rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = ShopKartUtils.Aqua
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            BackButton(navController = navController)

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Welcome to ShopKart",
                style = TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.SansSerif
                ),
                color = Color(0xFF333333),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Quotes(title = "Biggest discounts are on your way!")
            Quotes(title = "Stay Home, Shop Online.")

            Spacer(modifier = Modifier.height(20.dp))

            TextBox(
                value = nameState.value, labelId = "Full Name",
                onChange = nameState,
                keyBoardType = KeyboardType.Text,
                leadingIcon = R.drawable.profile
            )

            TextBox(
                value = emailState.value, labelId = "Email Address",
                onChange = emailState,
                keyBoardType = KeyboardType.Email,
                leadingIcon = R.drawable.email
            )

            PasswordTextBox(
                value = passwordState.value,
                onChange = passwordState
            )

            TextBox(
                value = phoneState.value, labelId = "Phone Number",
                onChange = phoneState,
                keyBoardType = KeyboardType.Phone,
                leadingIcon = R.drawable.call
            )

            TextBox(
                value = addressState.value, labelId = "Delivery Address",
                onChange = addressState,
                keyBoardType = KeyboardType.Text,
                leadingIcon = R.drawable.address,
                isSingleLine = false,
                imeAction = ImeAction.Done
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (errorState.value.isNotEmpty()) {
                Text(
                    text = errorState.value,
                    color = Color.Red.copy(alpha = 0.8f),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            PillButton(
                title = "Register",
                color = Orange900,
                onClick = {
                    if (nameState.value.isNotBlank() &&
                        emailState.value.isNotBlank() &&
                        passwordState.value.isNotBlank() &&
                        phoneState.value.isNotBlank() &&
                        addressState.value.isNotBlank()
                    ) {
                        when {
                            emailState.value.contains("admin.") -> {
                                errorState.value = "Email cannot have 'admin'."
                            }
                            emailState.value.contains("employee.") -> {
                                errorState.value = "Employee account cannot be created here."
                            }
                            phoneState.value.length > 10 -> {
                                errorState.value = "Enter a valid phone number."
                            }
                            else -> {
                                viewModel.createUser(
                                    email = emailState.value,
                                    password = passwordState.value,
                                    nav = {
                                        viewModel.addUserToDB(
                                            uName = nameState.value,
                                            uEmail = emailState.value.trim(),
                                            uPassword = passwordState.value.trim(),
                                            uPhone = phoneState.value.trim(),
                                            uAddress = addressState.value.trim()
                                        )
                                        navController.popBackStack()
                                        Toast.makeText(context, "Account Created", Toast.LENGTH_SHORT).show()
                                    },
                                    regExcept = {
                                        errorState.value = it
                                    }
                                )
                            }
                        }
                    } else {
                        errorState.value = "All fields are required."
                    }
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Already a member? ",
                    style = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    ),
                    color = Color.Gray
                )
                Text(
                    text = "Login",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.clickable {
                        navController.popBackStack()
                    },
                    color = Color(0xFF1E88E5)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
