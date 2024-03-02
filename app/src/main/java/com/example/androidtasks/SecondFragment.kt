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

    private val dataModel: DataViewModel by activityViewModels()
    private lateinit var ivPhoto: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivPhoto = view.findViewById(R.id.iv_photo)

        dataModel.colorMessage.observe(activity as LifecycleOwner) {
            try {
                view.setBackgroundColor(Color.parseColor(it))
            } catch (e: Exception) {
                Toast.makeText(activity, "Некорректный цвет", Toast.LENGTH_SHORT).show()
            }
        }

        dataModel.photoMessage.observe(activity as LifecycleOwner) {
            if (it.isNotEmpty()) {
                ivPhoto.setImageURI(Uri.parse(it))
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SecondFragment()
    }
}