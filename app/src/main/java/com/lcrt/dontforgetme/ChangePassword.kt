package com.lcrt.dontforgetme

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePassword : AppCompatActivity() {

    private lateinit var passwordInput: String
    private lateinit var confirmPasswordInput: String
    private lateinit var usernameInput: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        if (intent.hasExtra(EXTRA_USER)) {
            usernameInput = intent.getStringExtra(EXTRA_USER)
        }
        button_change_password.setOnClickListener {
            validateInput()
        }
    }

    private fun validatePassword(): Boolean {
        text_input_layout_change_password.editText?.let {
            passwordInput = it.text.toString().trim()
        }

        return when {
            passwordInput.isEmpty() -> {
                text_input_layout_change_password.error = getString(R.string.empty_field_error_message)
                false
            }
            passwordInput.length < PASSWORD_MIN_LENGTH -> {
                text_input_layout_change_password.error = getString(R.string.short_password_error_message)
                false
            }
            passwordInput.length > PASSWORD_MAX_LENGTH -> {
                text_input_layout_change_password.error = getString(R.string.long_password_error_message)
                false
            }
            else -> {
                text_input_layout_change_password.error = null
                true
            }
        }
    }

    private fun validateConfirmPassword(): Boolean {
        text_input_layout_change_confirm_password.editText?.let {
            confirmPasswordInput = it.text.toString().trim()
        }

        return when {
            confirmPasswordInput.isEmpty() -> {
                text_input_layout_change_confirm_password.error = getString(R.string.empty_field_error_message)
                false
            }
            confirmPasswordInput != passwordInput -> {
                text_input_layout_change_confirm_password.error = getString(R.string.confirm_password_error)
                false
            }
            else -> {
                text_input_layout_change_confirm_password.error = null
                true
            }
        }
    }

    private fun validateInput() {
        if (!validatePassword() or !validateConfirmPassword()) {
            return
        }

        //ToDo: update password on DB

        val intent = Intent(this, Dashboard::class.java).apply {
            putExtra(EXTRA_USER, usernameInput)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }
}
