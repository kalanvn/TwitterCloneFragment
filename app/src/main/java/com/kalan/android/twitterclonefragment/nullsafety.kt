package com.kalan.android.twitterclonefragment

fun main() {
    var a: String = "abc" //a is a non-null variable
    println(a)
    //a = null   Can't assign null to non-null variable

    var b: String? = "bb" //b is a nullable variable
    println(b?.length)
    b = null
    println(b?.length)

}