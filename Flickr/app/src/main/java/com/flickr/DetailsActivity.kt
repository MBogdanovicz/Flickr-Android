package com.flickr

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.flickr.com.flickr.model.BaseInfo
import com.flickr.com.flickr.model.BaseSize
import com.flickr.com.flickr.model.Photo
import com.flickr.com.flickr.model.PhotoInfo
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsActivity : AppCompatActivity() {

    private val service = RetrofitInitializer.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        setContentView(R.layout.activity_details)

        val bundle: Bundle ?= intent.extras
        val photo = bundle!!.get("PHOTO") as Photo

        getInfo(photo.id)
    }

    private fun getInfo(id: String) {
        val call = service.getInfo(id)

        call.enqueue(object: Callback<BaseInfo> {

            override fun onResponse(call: Call<BaseInfo>, response: Response<BaseInfo>) {
                val body = response.body()!!

                if (response.code() == 200 && body.stat == "ok") {
                    val photo = body.photo!!
                    showDetails(photo)
                    getSizes(photo.id!!)
                } else if (body.stat == "fail"){
                    Toast.makeText(this@DetailsActivity, body.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<BaseInfo>, t: Throwable) {
                Toast.makeText(this@DetailsActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showDetails(info: PhotoInfo) {
        tv_title.text = info.title!!._content
        tv_description.text = info.description!!._content
        tv_date.text = info.dates!!.taken
    }

    private fun getSizes(id: String) {
        val call = service.getSizes(id)

        call.enqueue(object: Callback<BaseSize> {

            override fun onResponse(call: Call<BaseSize>, response: Response<BaseSize>) {
                val body = response.body()!!

                if (response.code() == 200 && body.stat == "ok") {
                    val sizes = body.sizes!!.size!!
                    val source = sizes.last().source!!
                    Picasso.get().load(source).into(iv_photo)
                } else if (body.stat == "fail"){
                    Toast.makeText(this@DetailsActivity, body.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<BaseSize>, t: Throwable) {
                Toast.makeText(this@DetailsActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}