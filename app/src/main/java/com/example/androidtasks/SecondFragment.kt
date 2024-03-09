package com.example.androidtasks

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import java.lang.Exception

class SecondFragment : Fragment() {
    private var photoUriPath: String? = null

    private val dataModel: DataViewModel by activityViewModels()
    private lateinit var ivPhoto: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            photoUriPath = it.getString(PHOTO_URI_PATH)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivPhoto = view.findViewById(R.id.iv_photo)

        if (!photoUriPath.isNullOrEmpty())
            ivPhoto.setImageURI(Uri.parse(photoUriPath))

        dataModel.color.observe(viewLifecycleOwner) {
            try {
                view.setBackgroundColor(Color.parseColor(it))
            } catch (e: Exception) {
                Toast.makeText(activity, "Некорректный цвет", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(photoUriPath: String) =
            SecondFragment().apply {
                arguments = Bundle().apply {
                    putString(PHOTO_URI_PATH, photoUriPath)
                }
            }
    }
}