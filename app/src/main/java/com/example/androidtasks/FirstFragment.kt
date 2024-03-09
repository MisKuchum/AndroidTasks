package com.example.androidtasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels

class FirstFragment : Fragment() {
    private val dataModel: DataViewModel by activityViewModels()
    private lateinit var btnEditColor: Button
    private lateinit var etColor: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnEditColor = view.findViewById(R.id.btn_edit_color)
        etColor = view.findViewById(R.id.et_color)

        btnEditColor.setOnClickListener {
            dataModel.setColor(etColor.text.toString())
        }
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance() = FirstFragment()
    }
}