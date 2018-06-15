package com.dantatartes.simpleplayer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class SongsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_songs, container, false)
        val textView = view.findViewById<TextView>(R.id.txtMain)
        textView.setText(R.string.songs_fragment)

        return view
    }
}
