package com.jose.shopmart.repository


import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.jose.shopmart.data.DataOrException
import com.jose.shopmart.models.MAttendance
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireAttendanceRepository @Inject constructor(private val queryAttendance: Query) {

    suspend fun getEmployeeAttendanceFromFB(): DataOrException<List<MAttendance>, Boolean, Exception>{

        val dataOrException = DataOrException<List<MAttendance>, Boolean, Exception>()

        try {
            dataOrException.loading = true
            dataOrException.data = queryAttendance.get().await().documents.map { queryDocumentSnapshot ->

                queryDocumentSnapshot.toObject(MAttendance::class.java)!!

            }

            if (dataOrException.data.isNullOrEmpty()) dataOrException.loading = false

        }catch (e: FirebaseFirestoreException){
            dataOrException.e = e
        }

        return dataOrException

    }
}