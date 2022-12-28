package com.bcaf.finance

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main_portofolio.*
import android.Manifest
import android.graphics.Bitmap
import android.os.Environment
import android.provider.MediaStore
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class MainPortofolio : AppCompatActivity() {

    companion object {
        private val REQUEST_CODE_PERMISSION = 999
        private val CAMERA_REQUEST_CAPTURE = 100
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_portofolio)

        btnImgCall.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel: 089505340300")
            }
            startActivity(intent)
        })

        btnImgEmail.setOnClickListener {
            val recipient = "agus@gmail.com"
            val subject = "Subject nya apa"
            val message = "Selamat pagi dunia"
            sendEmail(recipient, subject, message)
        }

        btnImgMaps.setOnClickListener(){
            val intent = Intent(Intent.ACTION_VIEW,Uri.parse("geo:0,0?q=hutan+kota"))
            intent.setPackage("com.google.android.apps.maps")
            startActivity(intent)
        }

        imgCamera.setOnClickListener(View.OnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    val permission = arrayOf(Manifest.permission.CAMERA)
                    requestPermissions(permission, REQUEST_CODE_PERMISSION)
                }else{
                    captureCamera()
                }
            }
        })

        mainCalculator.setOnClickListener(){
            val intent = Intent(this,Calculator::class.java)
            startActivity(intent)
        }

        mainUmur.setOnClickListener(){
            val intent = Intent(this,MenuActivity::class.java)
            startActivity(intent)
        }
    }

    fun captureCamera(){

        val takeCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        startActivityForResult(takeCamera, CAMERA_REQUEST_CAPTURE)
    }

    private fun sendEmail(recipient: String, subject: String, message: String) {
        val mIntent = Intent(Intent.ACTION_SEND)
        mIntent.data = Uri.parse("mailto:")
        mIntent.type = "text/plain"
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        mIntent.putExtra(Intent.EXTRA_TEXT, message)


        try {
            startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
        }
        catch (e: Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }

//    fun showMap(geoLocation: Uri) {
//        val intent = Intent(Intent.ACTION_VIEW).apply {
//            data = geoLocation
//        }
//        if (intent.resolveActivity(packageManager) != null) {
//            startActivity(intent)
//        }
//    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CODE_PERMISSION -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    captureCamera()
                }else{
                    Toast.makeText(this, "Maaf Permission Denied", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun saveImage(bitmap: Bitmap){
        try {
            val tanggal = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())

            val extraStorage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString()

            val namaFile = extraStorage + "/BCAF_" + tanggal + ".png"
            var file :File? = null

            file = File(namaFile)
            file.createNewFile()

            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0,bos)
            val bitmapData = bos.toByteArray()

            val fos = FileOutputStream(file)

            fos.write(bitmapData)
            fos.flush()
            fos.close()
        } catch (e:java.lang.Exception){
            e.printStackTrace()
        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CAMERA_REQUEST_CAPTURE && resultCode == RESULT_OK){
            val bitmapImage = data?.extras?.get("data") as Bitmap
            imgCamera.setImageBitmap(bitmapImage)
            saveImage(bitmapImage)
        }
    }
}