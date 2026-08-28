package com.veyra.tv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
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
        null -> {
            LoginScreen(
                onLoginSuccess = { role ->
                    loggedInRole = role
                }
            )
        }

        UserRole.USER -> {
            UserHomeScreen(
                onLogout = {
                    loggedInRole = null
                }
            )
        }

        UserRole.ADMIN -> {
            AdminHomeScreen(
                onLogout = {
                    loggedInRole = null
                }
            )
        }
    }
}

@Composable
private fun LoginScreen(
    onLoginSuccess: (UserRole) -> Unit
) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val loginFocus = remember { FocusRequester() }
    val passwordFocus = remember { FocusRequester() }
    val buttonFocus = remember { FocusRequester() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF05070A),
                        Color(0xFF0D121A),
                        Color(0xFF05070A)
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "VEYRA",
                style = MaterialTheme.typography.displayLarge,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Shaxsiy kino tizimi",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF9CA7B5)
            )

            Spacer(modifier = Modifier.height(42.dp))

            Column(
                modifier = Modifier.width(500.dp)
            ) {

                Text(
                    text = "Tizimga kirish",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(20.dp))

                VeyraInput(
                    value = login,
                    onValueChange = {
                        login = it
                        errorMessage = ""
                    },
                    placeholder = "Login",
                    password = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(loginFocus)
                        .focusProperties {
                            down = passwordFocus
                        }
                )

                Spacer(modifier = Modifier.height(16.dp))

                VeyraInput(
                    value = password,
                    onValueChange = {
                        password = it
                        errorMessage = ""
                    },
                    placeholder = "Parol",
                    password = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(passwordFocus)
                        .focusProperties {
                            up = loginFocus
                            down = buttonFocus
                        }
                )

                Spacer(modifier = Modifier.height(14.dp))

                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFFFF6B6B)
                    )

                    Spacer(modifier = Modifier.height(14.dp))
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
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(buttonFocus)
                        .focusProperties {
                            up = passwordFocus
                        }
                ) {
                    Text(
                        text = "Kirish",
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun VeyraInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    password: Boolean,
    modifier: Modifier = Modifier
) {
    var focused by remember { mutableStateOf(false) }

    val borderColor = if (focused) {
        Color(0xFF7AA2FF)
    } else {
        Color(0xFF29313D)
    }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        visualTransformation = if (password) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        modifier = modifier
            .onFocusChanged {
                focused = it.isFocused
            }
            .focusable()
            .background(
                color = Color(0xFF121821),
                shape = RoundedCornerShape(14.dp)
            )
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(14.dp)
            )
            .padding(
                horizontal = 22.dp,
                vertical = 19.dp
            ),
        decorationBox = { innerTextField ->

            if (value.isEmpty()) {
                Text(
                    text = placeholder,
                    color = Color(0xFF7F8A99),
                    style = MaterialTheme.typography.bodyLarge
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF05070A),
                        Color(0xFF10151D)
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(48.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Button(
                    onClick = {
                        // Keyingi bosqichda yangilash ishlaydi
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
                text = "Filmlar",
                style = MaterialTheme.typography.displaySmall,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Hozircha filmlar mavjud emas.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF9CA7B5)
            )
        }
    }
}

@Composable
private fun AdminHomeScreen(
    onLogout: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF05070A),
                        Color(0xFF10151D)
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(48.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Admin panel",
                    style = MaterialTheme.typography.displaySmall,
                    color = Color.White
                )

                Button(
                    onClick = onLogout
                ) {
                    Text("Chiqish")
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Button(
                    onClick = {
                        // Keyingi bosqich: Kinolar
                    }
                ) {
                    Text("Kinolar")
                }

                Spacer(modifier = Modifier.width(20.dp))

                Button(
                    onClick = {
                        // Keyingi bosqich: Hisoblar
                    }
                ) {
                    Text("Hisoblar")
                }
            }
        }
    }
}
