package com.mustafaderinoz.userapp.utils

import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

// Exception sınıfını genişleten özel fonksiyonumuz
fun Exception.toUserFriendlyMessage(): String {
    return when (this) {
        is UnknownHostException, is ConnectException ->
            "İnternet bağlantınız yok gibi görünüyor. Lütfen hücresel veriyi veya Wi-Fi'ı kontrol edin."
        is SocketTimeoutException ->
            "Sunucu yanıt vermedi. Lütfen daha sonra tekrar deneyin."
        is HttpException ->
            "Sunucu tarafında bir sorun oluştu (Hata Kodu: ${this.code()}). Lütfen daha sonra tekrar deneyin."
        is IOException ->
            "Ağ bağlantısında bir sorun oluştu. Lütfen bağlantınızı kontrol edin."
        else ->
            "Beklenmeyen bir hata oluştu. Lütfen tekrar deneyin."
    }
}