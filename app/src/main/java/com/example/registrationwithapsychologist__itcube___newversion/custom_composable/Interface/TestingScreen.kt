package com.example.registrationwithapsychologist__itcube___newversion.custom_composable.Interface

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestingScreen(modifier: Modifier = Modifier) {
    var testBoolean by remember { mutableStateOf(true) }
    ExposedDropdownMenuBox(
        expanded = testBoolean,
        onExpandedChange = { testBoolean = it }
    ) {
        Column {
            Text("Hello")
        }
    }
}