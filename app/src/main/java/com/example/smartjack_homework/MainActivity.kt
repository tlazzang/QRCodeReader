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

    val NAME_OF_APPLICANT: String = "심재영"
    val PHONE_OF_APPLICANT: String = "010-2901-5566"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openScanner()
    }

    private fun openScanner() {
        val intentIntegrator = IntentIntegrator(this)
        intentIntegrator.setBeepEnabled(false)
        intentIntegrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            if (result.contents == null) {
                Toast.makeText(applicationContext, "스캔 취소", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, "스캔 결과: " + result.contents, Toast.LENGTH_LONG).show()
                sendTextToServer(result.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun sendTextToServer(value: String) {
        val map: HashMap<String, Any> = HashMap()
        map.put("value", value)
        map.put("name", NAME_OF_APPLICANT)
        map.put("phone", PHONE_OF_APPLICANT)

        RetrofitInstance.getInstance().create(RetrofitService::class.java).postText(map)
            .enqueue(object : retrofit2.Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(applicationContext, "네트워크 에러", Toast.LENGTH_LONG).show()
                    Log.d("MainActivity", "네트워크 에러")
                }

                override fun onResponse(call: Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        Toast.makeText(applicationContext, "POST 성공", Toast.LENGTH_LONG).show()
                        Log.d("MainActivity", "POST 성공")
                    } else {
                        Toast.makeText(applicationContext, "POST 실패 errorcode = " + response.code().toString(), Toast.LENGTH_LONG).show()
                        Log.d("MainActivity", "POST 실패 errorcode = " + response.code().toString())
                    }
                }
            })
    }
}
