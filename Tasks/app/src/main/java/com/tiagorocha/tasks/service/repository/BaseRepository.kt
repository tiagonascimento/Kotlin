package com.tiagorocha.tasks.service.repository
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.google.gson.Gson
import com.tiagorocha.tasks.R
import com.tiagorocha.tasks.service.constants.TaskConstants
import com.tiagorocha.tasks.service.listener.IApiListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class BaseRepository( val context: Context) {

    fun <T> CallApi(call: Call<T>,listener: IApiListener<T>) {
        // verifica conexão com a internet

        if(!isConnectionAvailable(context)){
            listener.onFailure(context.getString(R.string.ERROR_INTERNET_CONNECTION))
            return
        }

        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {

                if (response.code() != TaskConstants.HTTP.SUCCESS) {
                    val valitation =Gson().fromJson(response.errorBody()!!.string(), String::class.java)
                    listener.onFailure(valitation)
                } else {
                    response.body()?.let { listener.onSuccess(it) }
                }
            }
            override fun onFailure(call: Call<T>, t: Throwable) {
            //    listener.onFailure(context.getString(R.string.ERROR_UNEXPECTED))
                listener.onFailure(t.message.toString())
            }

        })
    }

    /**
     * Verifica se existe
     * conexão com internet
     */
    fun isConnectionAvailable(context: Context): Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = cm.activeNetwork ?: return false
            val actNw = cm.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            cm.run {
                cm.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }
}