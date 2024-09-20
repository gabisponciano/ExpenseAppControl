package com.gabrielaponciano.expenseapp.network

import com.gabrielaponciano.expenseapp.model.BodyResponse
import com.gabrielaponciano.expenseapp.model.Group
import com.gabrielaponciano.expenseapp.model.GroupCreate
import com.gabrielaponciano.expenseapp.model.GroupJoin
import com.gabrielaponciano.expenseapp.model.GroupLeave
import com.gabrielaponciano.expenseapp.model.LoginRequest
import com.gabrielaponciano.expenseapp.model.LoginResponse
import com.gabrielaponciano.expenseapp.model.Spending
import com.gabrielaponciano.expenseapp.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

private const val envVar: String = ""

interface RoutesApiService{
    // USER
    @POST("/user")
    fun createUser(
        @Body user: User
    ): Call<BodyResponse<User>>

    @POST("/login")
    fun loginUser(
        @Body loginRequest: LoginRequest
    ): Call<BodyResponse<LoginResponse>>

    // SPENDING
    @GET("/spending/{userId}")
    fun getSpendingsByUserId(
        @Path("userId") userId: Int,
        @HeaderMap headers: Map<String, String>
    ) : Call<BodyResponse<List<Spending>>>

    @POST("/spending")
    fun createSpendingUser(
        @Body spending: Spending,
        @HeaderMap headers: Map<String, String>
    ) : Call<BodyResponse<Map<String, String>>>

    @PUT("/spending/{userId}")
    fun updateSpendingUser(
        @Path("userId") userId: Int,
        @Body spending: Spending,
        @HeaderMap headers: Map<String, String>
    ) : Call<BodyResponse<Map<String, String>>?>

    @DELETE("/spending/{spendingId}")
    fun deleteSpendingUser(
        @Path("spendingId") spendingId: Int,
        @HeaderMap headers: Map<String, String>
    ) : Call<BodyResponse<Map<String, String>>>

    // GROUP
    @GET("/group")
    fun getAllGroups(
        @HeaderMap headers: Map<String, String>
    ) : Call<BodyResponse<List<Group>>>

    @GET("/group/{groupId}")
    fun getGroupById(
        @Path("groupId") groupId: Int,
        @HeaderMap headers: Map<String, String>
    ) : Call<BodyResponse<Group>>

    @POST("/group")
    fun createGroup(
        @Body groupCreate: GroupCreate,
        @HeaderMap headers: Map<String, String>
    ): Call<BodyResponse<Map<String, String>>>

    @POST("/group/join")
    fun joinGroup(
        @Body groupJoin: GroupJoin,
        @HeaderMap headers: Map<String, String>
    ): Call<BodyResponse<Map<String, String>>>

    @POST("/group/leave")
    fun leaveGroup(
        @Body groupLeave: GroupLeave,
        @HeaderMap headers: Map<String, String>
    ): Call<BodyResponse<Map<String, String>>>
}
