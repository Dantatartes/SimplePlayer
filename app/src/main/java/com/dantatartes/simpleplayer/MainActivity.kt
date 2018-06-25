package com.dantatartes.simpleplayer

import android.Manifest
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var audioList: ArrayList<Audio>
    private var cursor: Cursor? = null
    private lateinit var uri: Uri
    internal lateinit var contentResolver: ContentResolver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        androidRuntimePermission()
        audioList = ArrayList()
        getAllAudio()

        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.app_name)
        if (viewPager != null) {
            val adapter = ViewPagerAdapter(supportFragmentManager)
            viewPager.adapter = adapter
        }
    }


    private fun getAllAudio() {

        contentResolver = baseContext.contentResolver
        uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        cursor = contentResolver.query(
                uri, null, null, null, null
        )// Uri

        if (cursor == null) {
            Toast.makeText(applicationContext, "Something Went Wrong.", Toast.LENGTH_LONG).show()
        } else if (!cursor!!.moveToFirst()) {
            Toast.makeText(applicationContext, "No Music Found on SD Card.", Toast.LENGTH_LONG).show()
        } else {

            val title = cursor!!.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val artist = cursor!!.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val id = cursor!!.getColumnIndex(MediaStore.Audio.Media._ID)
            val duration = cursor!!.getColumnIndex(MediaStore.Audio.Media.DURATION)

            do {

                val songID = cursor!!.getInt(id)
                val finalSuccessfulUri = Uri.withAppendedPath(uri, "" + songID)

                val songTitle = cursor!!.getString(title)
                val songArtist = cursor!!.getString(artist)
                val songDuration = cursor!!.getInt(duration)

                audioList.add(Audio(songTitle, songArtist, finalSuccessfulUri, songDuration))

            } while (cursor!!.moveToNext())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        MenuInflater(application).inflate(R.menu.settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            (R.id.action_settings) -> {
                startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun androidRuntimePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    val alertBuilder = AlertDialog.Builder(this@MainActivity)
                    alertBuilder.setMessage("External Storage Permission is Required.")
                    alertBuilder.setTitle("Please Grant Permission.")
                    alertBuilder.setPositiveButton("OK") { _, _ ->
                        ActivityCompat.requestPermissions(
                                this@MainActivity,
                                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                                RUNTIME_PERMISSION_CODE
                        )
                    }

                    alertBuilder.setNeutralButton("Cancel", null)
                    val dialog = alertBuilder.create()
                    dialog.show()

                } else {
                    ActivityCompat.requestPermissions(
                            this@MainActivity,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            RUNTIME_PERMISSION_CODE
                    )
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            RUNTIME_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }
            }
        }
    }

    companion object {
        const val RUNTIME_PERMISSION_CODE = 7
    }
}

