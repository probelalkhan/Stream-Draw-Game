package io.getstream.utils

fun generateUserId(): String{
    return generateUniqueId(8, ('A'..'Z') + ('a'..'z') + ('0'..'9'))
}

fun generateGroupId(): String {
    return generateUniqueId(6, ('A'..'Z') + ('0'..'9'))
}


private fun generateUniqueId(length: Int, allowedChars: List<Char>): String{
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}
