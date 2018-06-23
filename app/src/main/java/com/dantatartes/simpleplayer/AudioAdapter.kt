package com.dantatartes.simpleplayer

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import java.util.*

class AudioAdapter(context: Activity, androidSongs: ArrayList<Audio>) : ArrayAdapter<Audio>(context, 0, androidSongs) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(
                    R.layout.list_song, parent, false)
        }
        val currentSong = getItem(position)
        val songTextView = listItemView!!.findViewById<TextView>(R.id.song)
        songTextView.text = currentSong!!.title
        val artistTextView = listItemView.findViewById<TextView>(R.id.artist)
        artistTextView.text = currentSong.artist
        val durationTextView = listItemView.findViewById<TextView>(R.id.duration)
        durationTextView.text = currentSong.duration.toString()
        return listItemView
    }


}