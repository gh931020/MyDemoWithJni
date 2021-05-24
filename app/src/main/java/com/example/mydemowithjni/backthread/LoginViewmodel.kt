package com.example.mydemowithjni.backthread

class LoginViewmodel(private val loginRepository: LoginRepository) {

    fun makeLoginRequest(userName: String, token: String){
        val jsonBody = "{ username: \\\"$userName\\\", token: \\\"$token\\\"}"
        loginRepository.makeLoginRequest(jsonBody){ result ->
            when(result) {
                is Result.Success<LoginResponse> -> {}
                // Happy path
                else -> {}// Show error in UI
            }
        }
    }
}