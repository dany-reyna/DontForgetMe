package com.lcrt.dontforgetme

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_enter_username.*

internal const val EXTRA_USER = "com.lcrt.dontforgetme.USER"

class EnterUsername : AppCompatActivity() {
    private lateinit var usernameInput: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_username)

        button_signin_next.setOnClickListener {
            validateInput()
        }

        text_view_create_account_link.setOnClickListener {
            val intent = Intent(this, CreateAccount::class.java)
            startActivity(intent)
        }
    }

    private fun validateUser(): Boolean {
        text_input_layout_signin_user.editText?.let {
            usernameInput = it.text.toString().trim()
        }
        return when {
            usernameInput.isEmpty() -> {
                text_input_layout_signin_user.error = getString(R.string.empty_field_error_message)
                false
            }
            usernameInput.length > USERNAME_LENGTH -> {
                text_input_layout_signin_user.error = getString(R.string.long_username_error_message)
                false
            }
            //ToDo: check on DB if username does not exist, assign text_input_layout_signin_user.error & return false
            else -> {
                text_input_layout_signin_user.error = null
                true
            }
        }
    }

    private fun validateInput() {
        if (!validateUser()) {
            return
        }

        val intent = Intent(this, EnterPassword::class.java).apply {
            putExtra(EXTRA_USER, usernameInput)
        }
        startActivity(intent)
    }
}
