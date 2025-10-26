package edu.farmingdale.pizzapartybottomnavbar

import kotlin.math.ceil

const val SLICES_PER_PIZZA = 8

// my solution todo6:
enum class HungerLevel(var numSlices: Int) {
    LIGHT(2), MEDIUM(3), HUNGRY(4), VERYHUNGRY(5)
}

class PizzaCalculator(partySize: Int, var hungerLevel: HungerLevel) {
    var partySize = 0
        set(value) {
            field = if (value >= 0) value else 0
        }

    val totalPizzas: Int
        get() {
            return ceil(partySize * hungerLevel.numSlices / SLICES_PER_PIZZA.toDouble()).toInt()
        }

    init {
        this.partySize = partySize
    }
}