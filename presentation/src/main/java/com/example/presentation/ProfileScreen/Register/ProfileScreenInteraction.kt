package com.example.presentation.ProfileScreen.Register

interface ProfileScreenInteraction {

    fun updateUsername(newInput: String)
    fun updateEmail(newInput: String)
    fun updatePassword(newInput: String)
    fun updateConfirmPassword(newInput: String)
    fun onClickRegister()


}