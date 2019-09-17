package com.flickr

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager.LayoutParams
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.flickr.com.flickr.model.BasePhoto
import com.flickr.com.flickr.model.BaseUser
import com.flickr.com.flickr.model.Photo
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val service = RetrofitInitializer.create()
    private var page = 1
    private var totalPages = 0
    private var adapter: PicturesAdapter? = null
    private var isLoading = false
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(LayoutParams.FLAG_SECURE, LayoutParams.FLAG_SECURE)
        setContentView(R.layout.activity_main)

        findByUsername()
        setGridViewListener()
    }

    private fun findByUsername() {
        val call = service.findByUsername("eyetwist")

        call.enqueue(object: Callback<BaseUser> {

            override fun onResponse(call: Call<BaseUser>, response: Response<BaseUser>) {
                val body = response.body()!!

                if (response.code() == 200 && body.stat == "ok") {
                    userId = body.user.id
                    getPublicPhotos()
                } else if (body.stat == "fail"){
                    Toast.makeText(this@MainActivity, body.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<BaseUser>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getPublicPhotos() {
        if (userId == null) {
            return
        }

        isLoading = true
        val call = service.getPublicPhotos(userId!!, page)

        call.enqueue(object: Callback<BasePhoto> {

            override fun onResponse(call: Call<BasePhoto>, response: Response<BasePhoto>) {
                isLoading = false
                progressBar.visibility = View.GONE

                val body = response.body()!!
                if (response.code() == 200 && body.stat == "ok") {
                    val photo = body.photos!!
                    page = photo.page
                    totalPages = photo.pages

                    if (page == 1) {
                        adapter = PicturesAdapter(photo.photo)
                        gvPhotos.adapter = adapter
                    } else {
                        adapter!!.addPhotos(photo.photo)
                    }
                } else if (body.stat == "fail"){
                    Toast.makeText(this@MainActivity, body.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<BasePhoto>, t: Throwable) {
                isLoading = false
                progressBar.visibility = View.GONE

                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setGridViewListener() {

        gvPhotos.setOnScrollListener(object : AbsListView.OnScrollListener {

            override fun onScrollStateChanged(absListView: AbsListView, i: Int) {
            }

            override fun onScroll(absListView: AbsListView, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {

                if (totalItemCount > 0) {
                    val lastVisibleItem = firstVisibleItem + visibleItemCount
                    if (!isLoading && page < totalPages && lastVisibleItem == totalItemCount) {
                        page += 1
                        getPublicPhotos()
                    }
                }
            }
        })

        gvPhotos.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->

            val photo = adapter!!.getItem(position)
            showDetails(photo)
        }
    }

    private fun showDetails(photo: Photo) {
        val intent = Intent(this, DetailsActivity::class.java)

        intent.putExtra("PHOTO", photo)
        startActivity(intent)
    }
}
