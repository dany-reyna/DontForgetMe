package com.lcrt.dontforgetme

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.Patterns
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_recover_account.*
import kotlin.random.Random

internal const val EXTRA_CODE = "com.lcrt.dontforgetme.CODE"
private const val EMAIL_LENGTH = 100
private const val TAG = "Mail"

class RecoverAccount : AppCompatActivity() {
    private lateinit var emailInput: String
    private lateinit var usernameInput: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover_account)

        if (intent.hasExtra(EXTRA_USER)) {
            usernameInput = intent.getStringExtra(EXTRA_USER)
        }

        button_recover_next.setOnClickListener {
            validateInput()
        }
    }

    private fun validateEmail(): Boolean {
        text_input_layout_recover_email.editText?.let {
            emailInput = it.text.toString().trim()
        }
        return when {
            emailInput.isEmpty() -> {
                text_input_layout_recover_email.error = getString(R.string.empty_field_error_message)
                false
            }
            emailInput.length > EMAIL_LENGTH -> {
                text_input_layout_recover_email.error = getString(R.string.long_email_error_message)
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(emailInput).matches() -> {
                text_input_layout_recover_email.error = getString(R.string.email_format_error)
                false
            }
            else -> {
                text_input_layout_recover_email.error = null
                true
            }
        }
    }

    private fun validateInput() {
        if (!validateEmail()) {
            return
        }

        val randomCode = Random.nextInt(100000, 1000000)
        sendMail(randomCode.toString())
    }

    private fun sendMail(randomCode: String) {
        val key = ""

        val to = emailInput
        val from = ""
        val fromName = getString(R.string.from_name)
        val subject = getString(R.string.mail_subject_verification_code)
        val message = "Saludos. Tu código de verificación es $randomCode"

        val content = """
            {
                "personalizations": [
                    {
                        "to": [
                            {
                                "email": "$to"
                            }
                        ]
                    }
                ],
                "from": {
                    "email": "$from",
                    "name": "$fromName"
                },
                "subject": "$subject",
                "content": [
                    {
                        "type": "text/plain",
                        "value": "$message"
                    },
                    {
                        "type": "text/html",
                        "value": "<html><body>$message</body></html>"
                    }
                ]
        }
        """.trimIndent()

        FuelManager.instance.baseHeaders = mapOf("Content-Type" to "application/json", "Authorization" to "Bearer $key")
        "https://api.sendgrid.com/v3/mail/send".httpPost().body((content)).response { request, response, result ->
            when (result) {
                is Result.Failure -> {
                    Log.e(TAG, "API call failed")
                }
                is Result.Success -> {
                    when (response.statusCode) {
                        202 -> Log.d(TAG, "Mail sent successfully")
                        400, 401, 413 -> Log.d(TAG, "SendGrid returned an error code of 400 series")
                        else -> Log.d(TAG, "An unexpected code came back")
                    }
                }
            }
            Log.d(TAG, request.toString())
            Log.d(TAG, response.toString())
            Log.d(TAG, result.toString())

            val intent = Intent(this, VerifyIdentity::class.java).apply {
                putExtra(EXTRA_USER, usernameInput)
                putExtra(EXTRA_CODE, randomCode)
            }
            startActivity(intent)
        }
    }
}
