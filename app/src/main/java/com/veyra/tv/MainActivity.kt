package com.veyra.tv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Button
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import androidx.tv.material3.TextField

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

    var loggedInRole by remember {
        mutableStateOf<UserRole?>(null)
    }

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

    var login by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    val loginFocusRequester = remember {
        FocusRequester()
    }

    val passwordFocusRequester = remember {
        FocusRequester()
    }

    val loginButtonFocusRequester = remember {
        FocusRequester()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF07090D),
                        Color(0xFF11151D),
                        Color(0xFF07090D)
                    )
                )
            )
            .focusRequester(loginFocusRequester)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box(
                modifier = Modifier
                    .width(92.dp)
                    .height(92.dp)
                    .background(
                        color = Color(0xFF1B2330),
                        shape = RoundedCornerShape(24.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "V",
                    style = MaterialTheme.typography.displaySmall,
                    color = Color.White
                )
            }

            Spacer(
                modifier = Modifier.height(22.dp)
            )

            Text(
                text = "VEYRA",
                style = MaterialTheme.typography.displayMedium,
                color = Color.White
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Shaxsiy kino tizimi",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF9AA4B2)
            )

            Spacer(
                modifier = Modifier.height(40.dp)
            )

            Column(
                modifier = Modifier.width(460.dp)
            ) {

                Text(
                    text = "Kirish",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                TextField(
                    value = login,
                    onValueChange = {
                        login = it
                        errorMessage = ""
                    },
                    label = {
                        Text("Login")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(loginFocusRequester)
                        .focusProperties {
                            down = passwordFocusRequester
                        },
                    singleLine = true
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                TextField(
                    value = password,
                    onValueChange = {
                        password = it
                        errorMessage = ""
                    },
                    label = {
                        Text("Parol")
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(passwordFocusRequester)
                        .focusProperties {
                            up = loginFocusRequester
                            down = loginButtonFocusRequester
                        },
                    singleLine = true
                )

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                if (errorMessage.isNotEmpty()) {

                    Text(
                        text = errorMessage,
                        color = Color(0xFFFF6B6B),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(
                        modifier = Modifier.height(14.dp)
                    )
                }

                Button(
                    onClick = {

                        when {

                            login == "admin" &&
                                password == "1234" -> {
                                onLoginSuccess(UserRole.ADMIN)
                            }

                            login == "user" &&
                                password == "1234" -> {
                                onLoginSuccess(UserRole.USER)
                            }

                            else -> {
                                errorMessage =
                                    "Login yoki parol noto‘g‘ri"
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(loginButtonFocusRequester)
                        .focusProperties {
                            up = passwordFocusRequester
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
private fun UserHomeScreen(
    onLogout: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF07090D),
                        Color(0xFF11151D)
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
                        // Keyingi bosqich:
                        // filmlarni yangilash
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

            Spacer(
                modifier = Modifier.height(46.dp)
            )

            Text(
                text = "Filmlar",
                style = MaterialTheme.typography.displaySmall,
                color = Color.White
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = "Hozircha filmlar mavjud emas",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF9AA4B2)
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
                    colors = listOf(
                        Color(0xFF07090D),
                        Color(0xFF11151D)
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

            Spacer(
                modifier = Modifier.height(48.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Button(
                    onClick = {
                        // Keyingi bosqich:
                        // Kino qo‘shish
                    }
                ) {
                    Text("Kinolar")
                }

                Spacer(
                    modifier = Modifier.width(20.dp)
                )

                Button(
                    onClick = {
                        // Keyingi bosqich:
                        // Hisoblar
                    }
                ) {
                    Text("Hisoblar")
                }
            }
        }
    }
}
