package com.example.runaway

data class ShelterData(
    var docId: String? = null,
    var email: String? = null,
    var latitude: String? = null, // 위도
    var longitude: String? =null, // 경도
    var paddress: String? = null, // 도로명 주소
    var pname: String? = null, // 장소 이름
)
