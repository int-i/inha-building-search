package com.inti.inhabuildingsearch

import android.icu.text.CaseMap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        val idState = remember { mutableStateOf(TextFieldValue()) }
        val passwordState = remember { mutableStateOf(TextFieldValue()) }

        Title()
        Id(idState)
        Password(passwordState)
        LoginButton()

        val stateList = listOf(
            idState,
            passwordState
        )
        // TODO: 서버로 각각의 state에 들어있는 value값을 넘김
    }
}

@Composable
fun Title() {
    Text(
        text = stringResource(R.string.sign_in_welcome_text),
        fontSize = MaterialTheme.typography.h3.fontSize,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
}



@Composable
fun Id(idState: MutableState<TextFieldValue>) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = idState.value,
        onValueChange = { idState.value = it },
        label = { Text(text = stringResource(R.string.id_hint)) },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp),
        //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

    )
}

@Composable
fun Password(passwordState: MutableState<TextFieldValue>) {
    val showPassword = remember { mutableStateOf(false) }
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = passwordState.value,
        onValueChange = { passwordState.value = it },
        label = { Text(text = stringResource(R.string.password_hint)) },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(8.dp),
        visualTransformation = if (showPassword.value) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()//패스워드 가림
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            if (showPassword.value) {
                IconButton(onClick = { showPassword.value = false }) {
                    Icon(
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = stringResource(R.string.hide_password)
                    )
                }
            } else {
                IconButton(onClick = { showPassword.value = true }) {
                    Icon(
                        imageVector = Icons.Filled.VisibilityOff,
                        contentDescription = stringResource(R.string.show_password)
                    )
                }
            }
        }
    )
}

@Composable
fun LoginButton() {
    Button(
        onClick = { },
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(id = R.color.blue_900),
            contentColor = Color.White
        )
    ) {
        Text(
            text = stringResource(R.string.sign_in)
        )
    }
}


@Composable
@Preview(showSystemUi = true)
fun LoginPreview() {
    LoginScreen()
}