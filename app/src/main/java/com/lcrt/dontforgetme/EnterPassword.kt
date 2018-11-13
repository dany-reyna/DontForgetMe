package com.lcrt.dontforgetme

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_enter_password.*

class EnterPassword : AppCompatActivity() {

    private lateinit var passwordInput: String
    private lateinit var usernameInput: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_password)

        if (intent.hasExtra(EXTRA_USER)) {
            usernameInput = intent.getStringExtra(EXTRA_USER)
        }

        button_signin.setOnClickListener {
            validateInput()
        }

        text_view_forgotten_password_link.setOnClickListener {
            val intent = Intent(this, RecoverAccount::class.java).apply {
                putExtra(EXTRA_USER, usernameInput)
            }
            startActivity(intent)
        }
    }

    private fun validatePassword(): Boolean {
        text_input_layout_signin_password.editText?.let {
            passwordInput = it.text.toString().trim()
        }
        return when {
            passwordInput.isEmpty() -> {
                text_input_layout_signin_password.error = getString(R.string.empty_field_error_message)
                false
            }
            //ToDo: Check on DB if password is wrong, assign text_input_layout_signin_password.error & return false
            else -> {
                text_input_layout_signin_password.error = null
                true
            }
        }
    }

    private fun validateInput() {
        if (!validatePassword()) {
            return
        }

        val intent = Intent(this, Dashboard::class.java).apply {
            putExtra(EXTRA_USER, usernameInput)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }
}
