package com.ysn.examplesquarecamera

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraUtils
import kotlinx.android.synthetic.main.activity_take_picture.*
import java.io.File
import java.io.FileOutputStream

class TakePictureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_picture)
        val appName = getString(R.string.app_name)
        val directory = Environment.getExternalStorageDirectory().path + "/" + appName
        camera_view_activity_take_picture.addCameraListener(object : CameraListener() {
            override fun onPictureTaken(jpeg: ByteArray?) {
                val fileName = "${System.currentTimeMillis()}_image.jpg"
                CameraUtils.decodeBitmap(jpeg) {
                    val photo = File(directory, fileName)
                    val fileOutputStream = FileOutputStream(photo)
                    it.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
                    finish()
                }
            }
        })
        relative_layout_container_activity_take_picture.setOnClickListener {
            showProgressDialog()
            camera_view_activity_take_picture.capturePicture()
        }
    }

    private fun showProgressDialog() {
        relative_layout_container_progress_dialog_activity_take_picture.visibility = View.VISIBLE
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    override fun onResume() {
        super.onResume()
        camera_view_activity_take_picture.start()
    }

    override fun onPause() {
        super.onPause()
        camera_view_activity_take_picture.stop()
    }
}
