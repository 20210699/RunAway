package com.example.runaway


data class MyJsonItems(
    val address: String, // 주소
    val lon: String, // 경도
    val lat: String, // 위도
    val shel_nm: String, // 대피소명
    val shel_av: String, // 최대 수용 인원
    val tel: String, // 관리기관 전화번호
    val new_address: String // 도로명 주소
)

data class MyJsonBody(val row: MutableList<MyJsonItems>)
data class JsonResponse(val TsunamiShelter: List<MyJsonBody>)

/*{
    "TsunamiShelter":[
    {
        "head":[
        {
            "totalCount":535
        },
        {
            "numOfRows":"5",
            "pageNo":"1",
            "type":"JSON"
        },
        {
            "RESULT":{
            "resultCode":"INFO-0",
            "resultMsg":"NOMAL SERVICE"
        }
        }
        ]
    },
    {
        "row":[
        {
            "id":642,
            "sido_name":"경상북도",
            "sigungu_name":"울릉군",
            "remarks":"현포지구",
            "shel_nm":"현포분교",
            "address":"경상북도 울릉군 북면 현포리 549",
            "lon":130.82201300,
            "lat":37.52445100,
            "shel_av":300,
            "lenth":416,
            "shel_div_type":"학교운동장",
            "seismic":"",
            "height":35,
            "tel":"054-790-6152",
            "new_address":"경상북도 울릉군 북면 현포1길 38",
            "manage_gov_nm":"울릉군청"
        }
        ]
    }
    ]
}
 */