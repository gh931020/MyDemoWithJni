package com.example.mydemowithjni.backthread

import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executor

sealed class Result<out R>{
    data class Success<out T>(val data: T): Result<T>()

    data class Error(val exception: Exception): Result<Nothing>()

}

class LoginRepository(private val responseParser: LoginResponseParser
,private val executor: Executor){
    private val loginUrl = "https://example.com/login"
    fun makeLoginRequest(jsonBody: String, callback: (Result<LoginResponse>) -> Unit){
        try {
            executor.execute {
                val response = makeSynchronousLoginRequest(jsonBody)
                callback(response)
            }
        }catch (e: Exception){
            val errorResult = Result.Error(e)
            callback(errorResult)
        }

    }

    private fun makeSynchronousLoginRequest(jsonBody: String): Result<LoginResponse>{
        val url = URL(loginUrl)
        (url.openConnection() as? HttpURLConnection)?.run {
            requestMethod = "POST"
            setRequestProperty("Content-Type", "application/json; charset=utf-8")
            setRequestProperty("Accept", "application/json")
            doOutput = true
            outputStream.write(jsonBody.toByteArray())

            return Result.Success(responseParser.parse(inputStream)!!)
        }
        return Result.Error(Exception("cant open HttpURLConnection"))
    }
}