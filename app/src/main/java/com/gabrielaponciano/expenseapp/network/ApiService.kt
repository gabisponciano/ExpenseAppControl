package com.gabrielaponciano.expenseapp.network

import com.gabrielaponciano.expenseapp.BuildConfig
import com.gabrielaponciano.expenseapp.model.BodyResponse
import com.gabrielaponciano.expenseapp.model.Group
import com.gabrielaponciano.expenseapp.model.GroupCreate
import com.gabrielaponciano.expenseapp.model.GroupJoin
import com.gabrielaponciano.expenseapp.model.GroupLeave
import com.gabrielaponciano.expenseapp.model.LoginRequest
import com.gabrielaponciano.expenseapp.model.LoginResponse
import com.gabrielaponciano.expenseapp.model.Spending
import com.gabrielaponciano.expenseapp.model.User
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)  // Tempo para estabelecer conexão
    .readTimeout(30, TimeUnit.SECONDS)     // Tempo de leitura
    .writeTimeout(30, TimeUnit.SECONDS)    // Tempo de escrita
    .build()

object ExpenseControllerApi{
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.BACKEND_URL)
        .build()

    private val request = retrofit.create(RoutesApiService::class.java)

    fun getHeaderRequest(token: String) : Map<String, String> {
        return mapOf("Authorization" to "Bearer ${token}")
    }

    // USER
    suspend fun createUser(user: User) : BodyResponse<User> {
        return request.createUser(user).await()
    }

    suspend fun loginUser(logiRequest: LoginRequest) : BodyResponse<LoginResponse> {
        return request.loginUser(logiRequest).await()
    }

    // SPENDING
    suspend fun getSpendingsByUserId(userId: Int, token: String) : BodyResponse<List<Spending>> {
        return request.getSpendingsByUserId(userId, getHeaderRequest(token)).await()
    }

    suspend fun createSpendingUser(spending: Spending, token: String) : BodyResponse<Map<String, String>> {
        return request.createSpendingUser(spending, getHeaderRequest(token)).await()
    }

    suspend fun updateSpendingUser(userId: Int, spending: Spending, token: String) : BodyResponse<Map<String, String>>? {
        return request.updateSpendingUser(userId, spending, getHeaderRequest(token)).await()
    }

    suspend fun deleteSpendingUser(spendingId: Int, token: String) : BodyResponse<Map<String, String>> {
        return request.deleteSpendingUser(spendingId, getHeaderRequest(token)).await()
    }

    // GROUP
    suspend fun getAllGroups(token: String): BodyResponse<List<Group>> {
        return request.getAllGroups(getHeaderRequest(token)).await()
    }

    suspend fun getGroupById(groupId: Int, token: String): BodyResponse<Group> {
        return request.getGroupById(groupId, getHeaderRequest(token)).await()
    }

    suspend fun createGroup(groupCreate: GroupCreate, token: String): BodyResponse<Map<String, String>> {
        return request.createGroup(groupCreate, getHeaderRequest(token)).await()
    }

    suspend fun joinGroup(groupjoin: GroupJoin, token: String): BodyResponse<Map<String, String>> {
        return request.joinGroup(groupjoin, getHeaderRequest(token)).await()
    }

    suspend fun leaveGroup(groupLeave: GroupLeave, token: String): BodyResponse<Map<String, String>> {
        return request.leaveGroup(groupLeave, getHeaderRequest(token)).await()
    }


}