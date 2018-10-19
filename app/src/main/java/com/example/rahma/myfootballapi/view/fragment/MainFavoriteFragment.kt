package com.example.rahma.myfootballapi.view.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rahma.myfootballapi.*
import com.example.rahma.myfootballapi.adapter.ViewPagerAdapter


class MainFavoriteFragment : Fragment() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(view?.findViewById(R.id.toolbar))
            (activity as AppCompatActivity).supportActionBar?.title = getFavorite()
        }
        viewPager = view?.findViewById(R.id.viewpagerFavorite) as ViewPager
        setupViewPager(viewPager)
        tabLayout = view?.findViewById(R.id.tabsFavorite) as TabLayout
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_main_favorite, container, false)

    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(FavoriteMatchFragment(), getFavoriteMatch())
        adapter.addFragment(FavoriteTeamFragment(), getFavoriteTeam())
        viewPager.adapter = adapter
    }
}
