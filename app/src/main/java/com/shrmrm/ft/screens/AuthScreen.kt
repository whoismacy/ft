package com.shrmrm.ft.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.shrmrm.ft.data.events.EventManager

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun AuthScreen() {
    val context = LocalContext.current
    val biometricManager = BiometricManager.from(context)
    var supportsBiometric by remember { mutableStateOf(false) }

    supportsBiometric =
        when (
            biometricManager
                .canAuthenticate(
                    BiometricManager
                        .Authenticators.BIOMETRIC_STRONG,
                )
        ) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                true
            }

            else -> {
                EventManager
                    .triggerEvent(
                        EventManager
                            .AppEvent
                            .ShowSnackbar("Biometric Authentication Unavailable"),
                    )
                false
            }
        }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        BiometricButton(
            state = supportsBiometric,
            onClick = {
                authenticateUser(context as FragmentActivity)
            },
            text = "Authenticate",
        )
    }
}

@Composable
fun BiometricButton(
    state: Boolean,
    onClick: () -> Unit,
    text: String,
) {
    Button(
        enabled = state,
        onClick = onClick,
        modifier = Modifier.padding(8.dp),
    ) {
        Text(text)
    }
}

@RequiresApi(Build.VERSION_CODES.P)
fun authenticateUser(context: FragmentActivity) {
    val executor = context.mainExecutor
    val biometricPrompt =
        BiometricPrompt(
            context,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    EventManager
                        .triggerEvent(
                            EventManager
                                .AppEvent
                                .ShowSnackbar("Authentication Successful"),
                        )
                }

                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence,
                ) {
                    EventManager
                        .triggerEvent(
                            EventManager
                                .AppEvent
                                .ShowSnackbar("Authentication unSuccessful: $errString"),
                        )
                }

                override fun onAuthenticationFailed() {
                    EventManager
                        .triggerEvent(
                            EventManager
                                .AppEvent
                                .ShowSnackbar("Authentication Failed"),
                        )
                }
            },
        )

    val promptInfo =
        BiometricPrompt.PromptInfo
            .Builder()
            .setTitle(
                "Biometric Authentication",
            ).setDescription(
                "Use the fingerprint sensor or camera to authenticate",
            ).setNegativeButtonText("Cancel")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
            .build()

    biometricPrompt.authenticate(promptInfo)
}
