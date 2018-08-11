package com.ysn.examplesquarecamera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.ysn.examplesquarecamera.adapter.AdapterPhotos
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var photos: MutableList<File>
    private lateinit var adapterPhotos: AdapterPhotos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permissions, 100)
            }
        }
        floating_action_button_take_picture_activity_main.setOnClickListener {
            startActivity(Intent(this@MainActivity, TakePictureActivity::class.java))
        }
        photos = mutableListOf()
        adapterPhotos = AdapterPhotos(photos = photos)
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recycler_view_photos_activity_main.layoutManager = LinearLayoutManager(this)
        recycler_view_photos_activity_main.addItemDecoration(dividerItemDecoration)
        recycler_view_photos_activity_main.adapter = adapterPhotos
    }

    override fun onResume() {
        super.onResume()
        val appName = getString(R.string.app_name)
        val directory = File(Environment.getExternalStorageDirectory().path + "/" + appName)
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val files = directory.listFiles()
        photos.clear()
        if (files != null) {
            photos.addAll(files)
        }
        adapterPhotos.refresh(photos = photos)
        if (photos.size == 0) {
            Snackbar.make(findViewById(android.R.id.content), "No photos available", Snackbar.LENGTH_LONG)
                    .show()
        }
    }

}
