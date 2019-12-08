package com.example.smartjack_homework

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.zxing.integration.android.IntentIntegrator
import android.widget.Toast
import com.example.idus_homework.Network.RetrofitInstance
import com.example.idus_homework.Network.RetrofitService
import okhttp3.ResponseBody
import retrofit2.Call

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openScanner()

    }
    fun openScanner(){
        val intentIntegrator = IntentIntegrator(this)
        intentIntegrator.setBeepEnabled(false)
        intentIntegrator.initiateScan()
    }

    fun sendTextToServer(value: String, name: String, phone: String){
        val map: HashMap<String, Any> = HashMap()
        map.put("value", value)
        map.put("name", name)
        map.put("phone", phone) //validation check 필요
        RetrofitInstance.retrofitInstance.create(RetrofitService::class.java).postText(map).enqueue(object:
            retrofit2.Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("MainActivity", "네트워크 에러")
            }

            override fun onResponse(call: Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
                if(response.isSuccessful){
                    Log.d("MainActivity", "POST 성공")
                }
                else{
                    Log.d("MainActivity", "POST 실패" + response.code().toString())
                }
            }

        })
    }

    fun isValidPhoneNumber(phone: String) : Boolean{
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                sendTextToServer("test", "test", "010-1234-5678")
            } else {
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                sendTextToServer("test", "test", "010-1234-5678")
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
