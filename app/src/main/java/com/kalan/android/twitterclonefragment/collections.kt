package com.kalan.android.twitterclonefragment

//collections
fun main() {
    //immutable list
    val numbers = listOf(7,5,2,3,9,4,6,1,8)
    println(numbers)
    println("indexOf ${numbers.indexOf(7)}")

    val numbersMutable = mutableListOf(5)
    println(numbersMutable)
    numbersMutable.add(3)
    numbersMutable.add(6)
    println("After adding: $numbersMutable")
    numbersMutable.remove(5)
    println("After removing: $numbersMutable")
}