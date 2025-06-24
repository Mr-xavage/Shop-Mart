package com.jose.shopmart.screens.profile

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.jose.shopmart.R
import com.jose.shopmart.ShopKartUtils
import com.jose.shopmart.component.ProfileCards
import com.jose.shopmart.navigation.BottomNavScreens
import com.jose.shopmart.screens.myorderdetails.ShopKartDialog
import com.jose.shopmart.ui.theme.*

@Composable
fun ProfileScreen(
    navController: NavController,
    email: String,
    signOut: () -> Unit
) {
    val context = LocalContext.current

    val hasNotificationPermission = remember {
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            } else true
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            hasNotificationPermission.value = true
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    val isButtonEnabled = remember { mutableStateOf(!hasNotificationPermission.value) }
    val checkAdmin = email.contains("admin.")
    val checkEmployee = email.contains("employee.")
    val surfaceHeight = if (checkAdmin) 252.dp else if (checkEmployee) 192.dp else 250.dp
    val openDialog = remember { mutableStateOf(false) }

    Scaffold(backgroundColor = OffWhite) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 20.dp, end = 20.dp, top = 50.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(surfaceHeight)
                    .padding(top = 10.dp),
                shape = RoundedCornerShape(24.dp),
                color = LightPink.copy(alpha = 0.2f)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    if (checkAdmin) {
                        ProfileCards(
                            title = "Admin",
                            leadingIcon = R.drawable.ic_admin,
                            tint = Orange500,
                            space = 200.dp
                        ) {
                            navController.navigate(BottomNavScreens.AdminScreen.route)
                        }
                        Divider(color = Gray300)
                    }

                    if (checkEmployee) {
                        ProfileCards(
                            title = "Employee",
                            leadingIcon = R.drawable.ic_admin,
                            tint = Purple500,
                            space = 175.dp
                        ) {
                            navController.navigate(BottomNavScreens.EmployeeScreen.route)
                        }
                        Divider(color = Gray300)
                    }

                    if (!checkEmployee) {
                        ProfileCards(
                            title = "My Profile",
                            leadingIcon = R.drawable.ic_profile,
                            tint = Cyan,
                            space = 180.dp
                        ) {
                            navController.navigate(BottomNavScreens.MyProfile.route)
                        }
                        Divider(color = Gray300)
                    }

                    ProfileCards(
                        title = "Log Out",
                        leadingIcon = R.drawable.ic_logout,
                        tint = Red300,
                        space = 190.dp
                    ) {
                        openDialog.value = true
                    }

                    Divider(color = Gray300)

                    ProfileCards(
                        title = "About",
                        leadingIcon = R.drawable.ic_info,
                        tint = SkyBlue,
                        space = 205.dp
                    ) {
                        navController.navigate(BottomNavScreens.About.route)
                    }

                    Divider(color = Gray300)

                    ProfileCards(
                        title = "Notification",
                        leadingIcon = R.drawable.notification,
                        tint = Lemon,
                        space = 122.dp,
                        isChecked = hasNotificationPermission,
                        showButton = true,
                        isButtonEnabled = isButtonEnabled.value
                    ) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !hasNotificationPermission.value) {
                            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                    }
                }
            }
        }
    }

    ShopKartDialog(
        openDialog = openDialog,
        onTap = signOut,
        context = context,
        navController = navController,
        title = "Log Out",
        subTitle = "Are you sure you want to log out?",
        button1 = "Log Out",
        button2 = "Cancel",
        toast = "Logged Out"
    )
}
