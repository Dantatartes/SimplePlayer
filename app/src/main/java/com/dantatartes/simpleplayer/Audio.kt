package com.dantatartes.simpleplayer

import android.net.Uri
import java.io.Serializable

class Audio(val title: String, val artist: String, val uri: Uri, val duration: Int) : Serializable
