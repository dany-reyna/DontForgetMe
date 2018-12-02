package com.lcrt.dontforgetme

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create_account.*
import com.lcrt.dontforgetme.DataBaseHelperUsers

internal const val USERNAME_LENGTH = 15
internal const val PASSWORD_MIN_LENGTH = 6
internal const val PASSWORD_MAX_LENGTH = 100

class CreateAccount : AppCompatActivity() {

    private lateinit var UsersDB: DataBaseHelperUsers
    private lateinit var usernameInput: String
    private lateinit var passwordInput: String
    private lateinit var confirmPasswordInput: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        UsersDB = DataBaseHelperUsers(this)
        button_signup.setOnClickListener {
            validateInput()
        }
    }
    private fun validateUser(): Boolean {
        text_input_layout_signup_user.editText?.let {
            usernameInput = it.text.toString().trim()
        }
        return when {
            usernameInput.isEmpty() -> {
                text_input_layout_signup_user.error = getString(R.string.empty_field_error_message)
                false
            }
            usernameInput.length > USERNAME_LENGTH -> {
                text_input_layout_signup_user.error = getString(R.string.long_username_error_message)
                false
            }
            UsersDB.checkUser(usernameInput) -> {
                //ToDo: check if username already exists, assign text_input_layout_signup_user.error and return false
                text_input_layout_signup_user.error = "El Usuario que desea ingresar ya existe"
                false
            }
            else -> {
                text_input_layout_signup_user.error = null
                true
            }
        }
    }

    private fun validatePassword(): Boolean {
        text_input_layout_signup_password.editText?.let {
            passwordInput = it.text.toString().trim()
        }
        return when {
            passwordInput.isEmpty() -> {
                text_input_layout_signup_password.error = getString(R.string.empty_field_error_message)
                false
            }
            passwordInput.length < PASSWORD_MIN_LENGTH -> {
                text_input_layout_signup_password.error = getString(R.string.short_password_error_message)
                false
            }
            passwordInput.length > PASSWORD_MAX_LENGTH -> {
                text_input_layout_signup_password.error = getString(R.string.long_password_error_message)
                false
            }
            else -> {
                text_input_layout_signup_password.error = null
                true
            }
        }
    }

    private fun validateConfirmPassword(): Boolean {
        text_input_layout_signup_confirm_password.editText?.let {
            confirmPasswordInput = it.text.toString().trim()
        }

        return when {
            confirmPasswordInput.isEmpty() -> {
                text_input_layout_signup_confirm_password.error = getString(R.string.empty_field_error_message)
                false
            }
            confirmPasswordInput != passwordInput -> {
                text_input_layout_signup_confirm_password.error = getString(R.string.confirm_password_error)
                false
            }
            else -> {
                text_input_layout_signup_confirm_password.error = null
                true
            }
        }
    }

    private fun validateInput() {
        if (!validateUser() or !validatePassword() or !validateConfirmPassword()) {
            return
        } else {
            //ToDo: insert user account on DB
            if(UsersDB.addUser(usernameInput,passwordInput)){
                Toast.makeText(applicationContext, "Usuario agregado", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(applicationContext, "Usuario no agregado", Toast.LENGTH_SHORT).show()
            }
        }

        val intent = Intent(this, Dashboard::class.java).apply {
            putExtra(EXTRA_USER, usernameInput)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }
}
