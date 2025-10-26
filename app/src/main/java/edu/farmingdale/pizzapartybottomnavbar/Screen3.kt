package edu.farmingdale.pizzapartybottomnavbar

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ToDo 2: the slider should be able to change the text value of the screen

// ToDo 3: Make the UI look better by adding a gradient background (vertical) and padding

@Composable
fun Screen3() {
    var sliderValue by remember { mutableStateOf(20f) } // base font size
    var chkd by remember { mutableStateOf(true) }
    val context = LocalContext.current
    Column(
        // my solution todo3:
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xFFEFF3FF),
                        Color(0xFFDCE6FF)
                    )
                )
            )
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Slider(
            value = sliderValue,
            onValueChange = { sliderValue = it },
            valueRange = 10f..50f,
            modifier = Modifier.fillMaxWidth(),
            enabled = chkd
        )
        // my solution todo2:
        Text(fontSize = sliderValue.sp, text = "Second Screen")

        Button(onClick = {
            val newInt = Intent(Intent.ACTION_VIEW)
            newInt.setData(Uri.parse("tel:6314202000"))
            context.startActivity(newInt)
        }) {
            Text(fontSize = 20.sp, text = "Call me")
        }
        Checkbox(checked = chkd, onCheckedChange = { chkd = it }, modifier = Modifier.padding(10.dp))
    }
}