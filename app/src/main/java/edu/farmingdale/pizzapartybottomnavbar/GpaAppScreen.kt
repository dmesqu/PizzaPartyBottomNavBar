package edu.farmingdale.pizzapartybottomnavbar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

// ToDo 4: Match the UI as in drawable gpa_design.png. Use the following hints:
// - The background color should be Color.Cyan
// - Fix padding, alignment, and keypad type

// ToDo 5:  Add the GpaAppScreen composable button that clears the input fields when clicked

@Composable
fun GpaAppScreen() {

    var grade1 by remember { mutableStateOf("") }
    var grade2 by remember { mutableStateOf("") }
    var grade3 by remember { mutableStateOf("") }

    // Declare variables for GPA result and background color
    var gpa by remember { mutableStateOf("") }
    var backColor by remember { mutableStateOf(Color.White) }
    var btnLabel by remember { mutableStateOf("Calulate GPA") }
    Column(
        // my solution todo4:
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = grade1,
            onValueChange = { grade1 = it },
            // my solution todo4:
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            label = { Text("Course 1 Grade") },
            // my solution todo4:
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        TextField(
            value = grade2,
            onValueChange = { grade2 = it },
            // my solution todo4:
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            label = { Text("Course 2 Grade") },
            // my solution todo4:
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        TextField(
            value = grade3,
            onValueChange = { grade3 = it },
            // my solution todo4:
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            label = { Text("Course 3 Grade") },
            // my solution todo4:
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Button(onClick = {
            if (btnLabel == "Compute GPA") {

                val gpaVal = calGPA(grade1, grade2, grade3)
                if (gpaVal != null) {
                    gpa = gpaVal.toString()

                    // Change background color based on GPA
                    backColor = when {
                        gpaVal < 60 -> Color.Red
                        gpaVal in 60.0..79.0 -> Color.Yellow
                        else -> Color.Green
                    }
                    btnLabel = "Clear"
                } else {
                    gpa = "Invalid input"
                }
            } else {
                // Reset all value to none
                grade1 = ""
                grade2 = ""
                grade3 = ""
                gpa = ""
                backColor = Color.White
                btnLabel = "Compute GPA"
            }
        }, modifier = Modifier.padding(top = 56.dp)) {
            Text(btnLabel)
        }
        // my solution todo5:
        Button(
            onClick = {
                grade1 = ""
                grade2 = ""
                grade3 = ""
                gpa = ""
                backColor = Color.White
                btnLabel = "Compute GPA"
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Clear Fields")
        }
        if (gpa.isNotEmpty()) {
            Text(text = "GPA: $gpa")
        }
    }
}

fun calGPA(grade1: String, grade2: String, grade3: String): Double {
    val grades = listOf(grade1.toDouble(), grade2.toDouble(), grade3.toDouble())
    return grades.average()
}
