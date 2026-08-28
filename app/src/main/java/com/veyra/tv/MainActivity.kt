package com.veyra.tv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Button
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                VeyraApp()
            }
        }
    }
}

private enum class UserRole {
    USER,
    ADMIN
}

@Composable
private fun VeyraApp() {

    var loggedInRole by remember { mutableStateOf<UserRole?>(null) }

    when (loggedInRole) {
        null -> LoginScreen(
            onLoginSuccess = { role ->
                loggedInRole = role
            }
        )

        UserRole.USER -> UserHomeScreen(
            onLogout = {
                loggedInRole = null
            }
        )

        UserRole.ADMIN -> AdminHomeScreen(
            onLogout = {
                loggedInRole = null
            }
        )
    }
}

@Composable
private fun LoginScreen(
    onLoginSuccess: (UserRole) -> Unit
) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "VEYRA",
            style = MaterialTheme.typography.displaySmall
        )

        Spacer(modifier = Modifier.height(36.dp))

        Text(
            text = "Kirish",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(24.dp))

        VeyraTextField(
            value = login,
            onValueChange = {
                login = it
                errorMessage = ""
            },
            placeholder = "Login",
            password = false
        )

        Spacer(modifier = Modifier.height(16.dp))

        VeyraTextField(
            value = password,
            onValueChange = {
                password = it
                errorMessage = ""
            },
            placeholder = "Parol",
            password = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        Button(
            onClick = {
                when {
                    login == "admin" && password == "1234" -> {
                        onLoginSuccess(UserRole.ADMIN)
                    }

                    login == "user" && password == "1234" -> {
                        onLoginSuccess(UserRole.USER)
                    }

                    else -> {
                        errorMessage = "Login yoki parol noto‘g‘ri"
                    }
                }
            }
        ) {
            Text("Kirish")
        }
    }
}

@Composable
private fun VeyraTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    password: Boolean
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        visualTransformation = if (password) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        modifier = Modifier
            .fillMaxWidth(0.55f)
            .background(
                color = Color(0xFF202124),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp
            ),
        decorationBox = { innerTextField ->
            if (value.isEmpty()) {
                Text(
                    text = placeholder,
                    color = Color.LightGray
                )
            }

            innerTextField()
        }
    )
}

@Composable
private fun UserHomeScreen(
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(48.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = {
                    // Keyingi bosqichda filmlar yangilanadi
                }
            ) {
                Text("Yangilash")
            }

            Button(
                onClick = onLogout
            ) {
                Text("Chiqish")
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "FILMLAR",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Hozircha filmlar mavjud emas.",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun AdminHomeScreen(
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(48.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "ADMIN PANEL",
                style = MaterialTheme.typography.headlineLarge
            )

            Button(
                onClick = onLogout
            ) {
                Text("Chiqish")
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = {
                // Keyingi bosqich: Kino qo‘shish
            }
        ) {
            Text("Kinolar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Keyingi bosqich: Hisoblar
            }
        ) {
            Text("Hisoblar")
        }
    }
}
