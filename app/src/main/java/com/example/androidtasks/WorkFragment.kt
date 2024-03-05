package com.example.androidtasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class WorkFragment : Fragment() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_work, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = view.findViewById(R.id.view_pager)
        tabLayout = view.findViewById(R.id.tab_layout)

        val images = listOf(
            R.drawable.work,
            R.drawable.work2,
            R.drawable.work3,
            R.drawable.work4
        )
        val adapter = ViewPagerAdapter(images)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (images[position]) {
                R.drawable.work -> tab.text = "Волк"
                R.drawable.work2 -> tab.text = "Деньги"
                R.drawable.work3 -> tab.text = "Дом"
                R.drawable.work4 -> tab.text = "Опыт"
            }
        }.attach()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val toastMessage = when (tab?.text) {
                    "Волк" -> "Работа не волк"
                    "Деньги" -> "Значит надо ходить"
                    "Дом" -> "Лучше не работать"
                    "Опыт" -> "Опыт без опыта"
                    else -> ""
                }
                Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {
                onTabSelected(tab)
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WorkFragment().apply { }
    }
}