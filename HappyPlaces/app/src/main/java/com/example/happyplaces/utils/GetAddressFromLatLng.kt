package com.example.happyplaces.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.AsyncTask
import java.util.*

// We don't want to block the main thread so we will use AsyncTask
class GetAddressFromLatLng(context: Context, private val latitude: Double, private val longitude : Double) :
    AsyncTask<Void, String, String>() {

    // Geocoder is a class that translates latitude and longitude to readable location address.
    private val geocoder : Geocoder = Geocoder(context, Locale.getDefault())
    private lateinit var mAddressListener: AddressListener

    override fun doInBackground(vararg params: Void?): String {

        try{
            val addressList: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
            if(addressList != null && addressList.isNotEmpty()) {
                val address: Address = addressList[0]
                val sb = StringBuilder()
                // in this address.java class there are postcode, countryname, countrycode etc and we want to take all data with loop and return a string.
                for (i in 0..address.maxAddressLineIndex) {
                    sb.append(address.getAddressLine(i)).append(" ")
                }
                sb.deleteCharAt(sb.length - 1)      // The last char will be an empty space because of for loop.
                return sb.toString()
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
        return ""       // If address if null or there is an error we will return empty string
    }

    override fun onPostExecute(resultString: String?) {

        if(resultString == null){
            mAddressListener.onError()
        }else{
            mAddressListener.onAddressFound(resultString)
        }

        super.onPostExecute(resultString)
    }

    fun setAddressListener(addressListener: AddressListener){
        mAddressListener = addressListener
    }

    fun getAddress(){
        execute()       // it executes AsyncTask. Actually everything in here.
    }

    interface AddressListener{
        fun onAddressFound(address: String?)
        fun onError()
    }
}