package com.example.androidtasks

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView

class HobbyFragment : Fragment() {
    private lateinit var videoView: VideoView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hobby, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        videoView = view.findViewById(R.id.videoView)
        videoView.setVideoURI(Uri.parse("android.resource://" + context?.packageName + "/" + R.raw.hobby))
        videoView.start()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HobbyFragment().apply { }
    }
}