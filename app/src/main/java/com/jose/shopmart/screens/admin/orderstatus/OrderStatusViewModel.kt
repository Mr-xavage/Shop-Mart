package com.jose.shopmart.screens.admin.orderstatus


import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.jose.shopmart.data.DataOrException
import com.jose.shopmart.models.MOrder
import com.jose.shopmart.models.PushNotificationData
import com.jose.shopmart.networks.NotificationApi
import com.jose.shopmart.repository.FireOrderStatusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderStatusViewModel @Inject constructor(private val fireOrderStatusRepository: FireOrderStatusRepository,private val notificationApiInterface: NotificationApi): ViewModel() {

    val fireStatus: MutableState<DataOrException<List<MOrder>,
            Boolean,
            Exception>> = mutableStateOf(DataOrException(listOf(),
        false, Exception("")))

    val db = FirebaseFirestore.getInstance()

    init {
        getOrderStatusFromFB()
    }

    private fun getOrderStatusFromFB(){
        viewModelScope.launch {

            fireStatus.value = fireOrderStatusRepository.getOrderStatusFromFB()
        }
    }

    fun markOnTheWay(userId: String,product_title: String,success:() -> Unit){
        viewModelScope.launch {
            db.collection("Orders").document(userId + product_title).update("delivery_status","On The Way").addOnSuccessListener {
                success()
            }
        }
    }

    fun markDelivered(userId: String,product_title: String,success:() -> Unit){
        viewModelScope.launch {
            db.collection("Orders").document(userId + product_title).update("delivery_status","Delivered").addOnSuccessListener {
                //pop backstack
                success()
            }
        }
    }

    fun sendNotification(notification: PushNotificationData){
        viewModelScope.launch {
            try {
                val response = notificationApiInterface.postNotification(notification)
            }catch (e: Exception){

            }
        }
    }
}