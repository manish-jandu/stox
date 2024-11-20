package com.example.stox.data.repo

import com.example.stox.data.api.ApiService
import com.example.stox.data.model.Holding
import com.example.stox.data.network_utils.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getUserHoldings(): Resource<List<Holding>> {
        return try {
            val response = apiService.getUserHoldings()
            Resource.Success(response.data.userHolding)
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    Resource.Error("Server error: ${e.message()}")
                }
                is IOException -> {
                    Resource.Error("Network error: ${e.message}")
                }
                else -> {
                    Resource.Error("Unexpected error: ${e.localizedMessage}")
                }
            }
        }
    }


}
