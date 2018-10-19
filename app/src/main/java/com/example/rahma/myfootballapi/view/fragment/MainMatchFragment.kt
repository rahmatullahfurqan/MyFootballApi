package com.example.rahma.myfootballapi.view.fragment

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toolbar
import com.example.rahma.myfootballapi.R
import com.example.rahma.myfootballapi.adapter.ViewPagerAdapter
import com.example.rahma.myfootballapi.getMatch
import com.example.rahma.myfootballapi.getNextMatch
import org.jetbrains.anko.support.v4.ctx

class MainMatchFragment : Fragment() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(view?.findViewById(R.id.toolbar))
            (activity as AppCompatActivity).supportActionBar?.title = getMatch()
        }

        viewPager = view?.findViewById(R.id.viewpagerMatch) as ViewPager
        setupViewPager(viewPager)
        tabLayout = view?.findViewById(R.id.tabsMatch) as TabLayout
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_match, container, false)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(childFragmentManager)
        setHasOptionsMenu(true)
        adapter.addFragment(MatchFragment(), getMatch())
        adapter.addFragment(MatchNextFragment(), getNextMatch())
        viewPager.adapter = adapter
    }


}
