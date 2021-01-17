package com.example.cocktailcatalog.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.cocktailcatalog.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class SearchFragment : Fragment() {

    private lateinit var collectionAdapter: CollectionAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        collectionAdapter = CollectionAdapter(this)
        viewPager = view.findViewById(R.id.viewPager)
        viewPager.adapter = collectionAdapter

        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            if (position == 0)
                tab.text = "BY NAME"
            else if (position ==1 )
                tab.text = "BY INGREDIENT"
        }.attach()
    }

    companion object {

        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}

class CollectionAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()

        if(position == 0)
            fragment = SearchByNameFragment.newInstance()
        else
            fragment = SearchByIngredientFragment.newInstance()

        return fragment
    }
}