package edu.farmingdale.pizzapartybottomnavbar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class PizzaPartyViewModel : ViewModel() {
    var numPeopleInput by mutableStateOf("")

    var hungerLevel by mutableStateOf(HungerLevel.MEDIUM)

    var totalPizzas by mutableIntStateOf(0)
        private set

    fun calculateNumPizzas() {
        val calc = PizzaCalculator(numPeopleInput.toIntOrNull() ?: 0, hungerLevel)
        totalPizzas = calc.totalPizzas
    }

    // my solution todo7:
    fun clear() {
        numPeopleInput = ""
        hungerLevel = HungerLevel.MEDIUM
        totalPizzas = 0
    }
}