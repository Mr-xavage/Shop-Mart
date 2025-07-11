package com.jose.shopmart.screens.employee


import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jose.shopmart.component.BackButton
import com.jose.shopmart.component.GalleryLaunchComp
import com.jose.shopmart.component.PillButton
import com.jose.shopmart.component.SelectedImageItem
import com.jose.shopmart.component.TextBox2

@Composable
fun AddRemoveBrandEmpl(navHostController: NavHostController,viewModel: EmployeeScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()){

    val selectedBrandImageUri = remember { mutableStateOf<Uri?>(null) }

    val launchGalleryBrand =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri -> selectedBrandImageUri.value = uri })

    val brandName = remember { mutableStateOf("") }

    val context = LocalContext.current

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            BackButton(navController = navHostController,
                topBarTitle = "Add/Remove Brand", spacing = 30.dp)
        },
        backgroundColor = com.jose.shopmart.ShopKartUtils.Gray900) { innerPadding ->

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {

            //Add Brand
            GalleryLaunchComp(
                title = "Select Logo",
                modifier = Modifier.size(200.dp),
                color = Color.Black.copy(alpha = 0.1f)
            ) {
                launchGalleryBrand.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }

            if (selectedBrandImageUri.value != null) SelectedImageItem(uris = selectedBrandImageUri.value)

            TextBox2(value = brandName.value,
                onChange = brandName,
                placeHolder = "Brand Name")

            PillButton(
                title = "Add Brand",
                color = com.jose.shopmart.ShopKartUtils.Red600,
                modifier = Modifier.padding(bottom = 20.dp, top = 10.dp)
            ) {
                if (selectedBrandImageUri.value == null) {
                    Toast.makeText(context, "Select a LOGO", Toast.LENGTH_SHORT).show()
                } else if (brandName.value == "") {
                    Toast.makeText(context, "Add Brand Name", Toast.LENGTH_SHORT).show()
                } else {
                    navHostController.popBackStack()
                    viewModel.uploadBrand(
                        selectedImageUri = selectedBrandImageUri.value,
                        brandName = brandName.value.trim()
                    ) { selectedBrandImageUri.value = null }
                    Toast.makeText(context, "Brand Added", Toast.LENGTH_SHORT).show()
                }
            }

            Divider(modifier = Modifier.padding(bottom = 10.dp))

            //Remove brand
            TextBox2(value = brandName.value, onChange = brandName, placeHolder = "Brand Name")

            PillButton(title = "Remove Brand", color = com.jose.shopmart.ShopKartUtils.BabyBlue,){
                if (brandName.value.isNotEmpty()){
                    viewModel.removeBrand(brandName = brandName.value)
                    navHostController.popBackStack()
                    Toast.makeText(context,"Brand ${brandName.value} removed",Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(context,"Brand Name cannot be empty",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}