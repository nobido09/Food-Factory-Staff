package com.example.foodfactorystaff.model


data class Cash(
    var orderId: String = "",
    var uid: String = "",
    var amt: Int = 0,
    var payMethod: String = "",
    var date: String = "",
    var status: Boolean = false
)