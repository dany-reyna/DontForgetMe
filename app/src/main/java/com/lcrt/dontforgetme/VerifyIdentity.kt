package com.lcrt.dontforgetme

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_verify_identity.*

private const val CODE_LENGTH = 6

class VerifyIdentity : AppCompatActivity() {

    private lateinit var codeInput: String
    private lateinit var randomCode: String
    private lateinit var usernameInput: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_identity)

        if (intent.hasExtra(EXTRA_USER)
                && intent.hasExtra(EXTRA_CODE)) {
            usernameInput = intent.getStringExtra(EXTRA_USER)
            randomCode = intent.getStringExtra(EXTRA_CODE)
        }

        button_verify_next.setOnClickListener {
            validateInput()
        }
    }

    private fun validateCode(): Boolean {
        text_input_layout_verify_code.editText?.let {
            codeInput = it.text.toString().trim()
        }

        return when {
            codeInput.isEmpty() -> {
                text_input_layout_verify_code.error = getString(R.string.empty_field_error_message)
                false
            }
            codeInput.length != CODE_LENGTH -> {
                text_input_layout_verify_code.error = getString(R.string.random_code_length_error)
                false
            }
            codeInput != randomCode -> {
                text_input_layout_verify_code.error = getString(R.string.random_code_error)
                false
            }
            else -> {
                text_input_layout_verify_code.error = null
                true
            }
        }
    }

    private fun validateInput() {
        if (!validateCode()) {
            return
        }

        val intent = Intent(this, ChangePassword::class.java).apply {
            putExtra(EXTRA_USER, usernameInput)
        }
        startActivity(intent)
    }
}
