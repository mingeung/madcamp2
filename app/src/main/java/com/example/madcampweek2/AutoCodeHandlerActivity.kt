package com.example.madcampweek2

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class AutoCodeHandlerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initKakaoLogin()
    }

    private fun initKakaoLogin() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            loginWithKakaoTalk()
        } else {
            loginWithKakaoAccount()
        }
    }

    private val loginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "로그인 실패 $error")
            handleError(error)
        } else if (token != null) {
            Log.e(TAG, "로그인 성공 ${token.accessToken}")
            handleSuccess(token)
        }
    }

    private fun loginWithKakaoTalk() {
        UserApiClient.instance.loginWithKakaoTalk(this, callback = loginCallback)
    }

    private fun loginWithKakaoAccount() {
        UserApiClient.instance.loginWithKakaoAccount(this, callback = loginCallback)
    }

    private fun handleSuccess(token: OAuthToken) {
        // Navigate to another activity or update UI
        Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
    }

    private fun handleError(error: Throwable) {
        // Provide more detailed error handling here
        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
            Toast.makeText(this, "로그인 취소", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "로그인 실패: $error", Toast.LENGTH_SHORT).show()
        }
    }
}