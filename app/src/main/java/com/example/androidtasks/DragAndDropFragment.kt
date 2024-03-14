package com.example.androidtasks

import android.content.ClipData
import android.content.ClipDescription
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

class DragAndDropFragment : Fragment() {
    private var dragView: View? = null
    private var llUpperFragment: LinearLayout? = null
    private var llLowerFragment: LinearLayout? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_drag_and_drop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nestedUpperFragment = childFragmentManager.findFragmentById(R.id.upper_fragment)
        val nestedUpperFragmentView = nestedUpperFragment?.view
        val nestedLowerFragment = childFragmentManager.findFragmentById(R.id.lower_fragment)
        val nestedLowerFragmentView = nestedLowerFragment?.view

        dragView = nestedUpperFragmentView?.findViewById(R.id.drag_view)
        llUpperFragment =
            nestedUpperFragmentView?.findViewById(R.id.ll_upper_fragment) as LinearLayout
        llLowerFragment =
            nestedLowerFragmentView?.findViewById(R.id.ll_lower_fragment) as LinearLayout

        llUpperFragment?.setOnDragListener(dragListener)
        llLowerFragment?.setOnDragListener(dragListener)

        dragView?.setOnLongClickListener {
            val clipData = "Some text"
            val item = ClipData.Item(clipData)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipData, mimeTypes, item)

            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)

            it.visibility = View.INVISIBLE
            true
        }
    }

    private val dragListener = View.OnDragListener { view, event ->
        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
            }

            DragEvent.ACTION_DRAG_ENTERED -> {
                view.invalidate()
                true
            }

            DragEvent.ACTION_DRAG_LOCATION -> true
            DragEvent.ACTION_DRAG_EXITED -> {
                view.invalidate()
                true
            }

            DragEvent.ACTION_DROP -> {
                val item = event.clipData.getItemAt(0)
                val dragData = item.text
                Log.d("MyApp", dragData.toString())

                view.invalidate()

                val v = event.localState as View
                val owner = v.parent as ViewGroup
                owner.removeView(v)
                val destination = view as LinearLayout
                destination.addView(v)
                v.visibility = View.VISIBLE
                true
            }

            DragEvent.ACTION_DRAG_ENDED -> {
                view.invalidate()
                true
            }

            else -> false
        }
    }
}