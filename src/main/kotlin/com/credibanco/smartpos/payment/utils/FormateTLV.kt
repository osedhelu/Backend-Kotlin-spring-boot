package com.credibanco.smartpos.payment.utils

data class TLV(val tag: String, val length: String, val value: String)

fun getLength(classValue: String): Int {
    return when (classValue) {
        "83" -> 8
        else -> 6
    }
}


fun getTlV(data: String, len: Int, lenCode: Int): TLV {
    val TagName = data.substring(0, len)
    val lenHex = TagName.substring(TagName.length - lenCode)
    println("TagNama ===== $TagName")
    println("lenHex ===== $lenHex")

    val value = data.substring(TagName.length, TagName.length + (lenHex.toInt(16) * 2))

    return TLV(TagName.dropLast(2), lenHex, value)
}

fun getTLVClass(data: String, lenCode: String, len: Int): TLV {
    val initTag = data.substring(0, 2) + data.substring(2, len)
    val allTagAndValue = initTag + lenCode
    val value =
        data.substring(allTagAndValue.length, allTagAndValue.length + (lenCode.toInt(16) * 2))
    return TLV(initTag, lenCode, value)
}

fun decodeTLVList(data: String): List<TLV> {
    val tlvs = mutableListOf<TLV>()
    var i = 0
    var tmpIndex = 0

    while (i < data.length) {
        tmpIndex += 1
        val label = data.substring(i, i + 2)
        val tagState: TLV = when (label) {
            "C0" -> {
                val newData = data.substring(i, data.length)
                val lenCode = newData.substring(2, 4)
                getTLVClass(newData, lenCode, 2)
            }

            "C1" -> {
                val newData = data.substring(i, data.length)
                val lenCode = newData.substring(2, 4)
                getTLVClass(newData, lenCode, 2)
            }

            "C2" -> {
                val newData = data.substring(i, data.length)
                val lenCode = newData.substring(4, 8)
                getTLVClass(newData, lenCode, 4)
            }

            "C4" -> {
                val newData = data.substring(i, data.length)
                val lenCode = newData.substring(2, 4)
                getTLVClass(newData, lenCode, 2)
            }

            "9F" -> {
                val newData = data.substring(i, data.length)
                val lenCode = newData.substring(2, 4)
                val a = getLength(lenCode)
                getTlV(newData, a, 2)
            }

            "DF" -> {
                val newData = data.substring(i, data.length)
                val lenCode = newData.substring(2, 4)
                val a = getLength(lenCode)
                getTlV(newData, a, 2)
            }

            else -> {
                val newData = data.substring(i, data.length)
                val lenCode = newData.substring(2, 4)
                val a = getLength(lenCode)
                getTlV(data, a, 2)
            }
        }
        tlvs.add(tagState)
        val dataState = "${tagState.tag}${tagState.length}${tagState.value}"
        println("xxxxxxxx $dataState")
        i += dataState.length


    }

    return tlvs
}

fun test() {
    val hexData =
        "DF8330020200C28201A0F7CC36152E50AE1A67BC7C5A60F751CB1FC1453F67232CB5D78B466770D687A206E22717BBBF6F4C918885FEE5D022B901393878FB8AA94ADBA2D6BF532CFABA8C6AB7838EFF0C7135FA6169021EE1948E072431A1005E554BB1BA71A00ED9124C5EE1AE264ACF4C56531FB64CD5CDC69764B670C66D5024147640AC31D5D202D43458D25B63A9F58BA3F4F00D7192C5CC5FCFD9DF41BAA698E52D1826AFCB4562DDDAA170FCD0AE5B3B139AA97784FE0B4B17A5505EAC8E414CAB3D66718CD56C8991D103E1C11A955B11DDE1C5648177C0A4944B710C9170271C9F190C7234A20DA95651460204AAF4F7B03B1DA9FC5B3E845A64241E544A3F2B838BF3D0025C69487E3D98C7E25B859FD95807AA596C3F68B972BBAA397DEF01920E447129D7A281525BCF55FAB77939E65DD81CE67852C56F80C10AA7611F11C2F634A150739B4ED1B3439B98F05FBF9E871EC5B7ADEBF6BBAB07EE7F364AFCD9B80E224DAB03422A882ED73286C6D8B75A1A9722BC5E3A6B11CF6A3386DD101EC67EB16CB70E2A8BDD75F3E60E8FD0D88E38863F5619FBDE749A325988B890F5BEE57C93C00AFFFF9876543210E00004C4075327FFFFFF77399F530152DF834F0D50424A37323241583331343836DF83440A56313333383439393130DF836B050000258963DF834C053030303035DF834B143030303030303030303030303030303030303034"
    val tlvs = decodeTLVList(hexData)
    tlvs.forEach { println(it) }
}
