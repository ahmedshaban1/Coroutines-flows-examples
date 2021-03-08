package com.coroutines.examples.models


import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("address")
    var address: Address?,
    @SerializedName("company")
    var company: Company?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("phone")
    var phone: String?,
    @SerializedName("username")
    var username: String?,
    @SerializedName("website")
    var website: String?
)


data class Address(
    @SerializedName("city")
    var city: String?,
    @SerializedName("geo")
    var geo: Geo?,
    @SerializedName("street")
    var street: String?,
    @SerializedName("suite")
    var suite: String?,
    @SerializedName("zipcode")
    var zipcode: String?
)


data class Geo(
    @SerializedName("lat")
    var lat: String?,
    @SerializedName("lng")
    var lng: String?
)

data class Company(
    @SerializedName("bs")
    var bs: String?,
    @SerializedName("catchPhrase")
    var catchPhrase: String?,
    @SerializedName("name")
    var name: String?
)