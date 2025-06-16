package com.jose.shopmart.repository


import com.google.firebase.FirebaseException
import com.google.firebase.firestore.Query
import com.jose.shopmart.data.DataOrException
import com.jose.shopmart.models.MOrder
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireOrderRepository @Inject constructor(private val queryOrder: Query) {

    suspend fun getOrdersFromFirebase(): DataOrException<List<MOrder>, Boolean, Exception>{

        val dataOrException = DataOrException<List<MOrder>, Boolean, Exception>()

        try {

            dataOrException.data = queryOrder.get().await().documents.map { orders ->

                orders.toObject(MOrder::class.java)!!

            }

        }catch (ex: FirebaseException){
            dataOrException.e = ex
        }
        return dataOrException
    }

}