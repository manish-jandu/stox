package com.example.stox

import com.example.stox.data.NetworkUtils.Resource
import com.example.stox.data.api.ApiService
import com.example.stox.data.model.Holding
import com.example.stox.data.model.HoldingData
import com.example.stox.data.model.UserHoldingData
import com.example.stox.data.repo.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UserRepositoryUnitTest {
    @Test
    fun testGetUserHoldingsSuccess(): Unit = runBlocking {
        val mockApiService = mock(ApiService::class.java)
        val mockResponse = HoldingData(
            data = UserHoldingData(
                userHolding = listOf(
                    Holding("MAHABANK", 990, 38.05, 35.0, 40.0),
                    Holding("ICICI", 100, 118.25, 110.0, 105.0)
                )
            )
        )

        `when`(mockApiService.getUserHoldings()).thenReturn(mockResponse)

        val repository = UserRepository(mockApiService)
        val result = repository.getUserHoldings()

        assertTrue(result is Resource.Success)
        assertEquals(2, (result as Resource.Success).data.size)
    }

}