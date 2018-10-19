package com.example.rahma.myfootballapi.view.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import com.example.rahma.myfootballapi.*
import com.example.rahma.myfootballapi.view.fragment.MainFavoriteFragment
import com.example.rahma.myfootballapi.view.fragment.MainMatchFragment
import com.example.rahma.myfootballapi.view.fragment.TeamsFragment
import org.jetbrains.anko.ctx

class MainActivity : AppCompatActivity() {
    private var menuItem: Menu? = null
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_match, menu)
        menuItem = menu
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = getMatch()
        openFragment(MainMatchFragment())
        val bottomNavigation: BottomNavigationView = findViewById(R.id.navBottom)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {

            R.id.search -> {
                startActivity(Intent(ctx, SearchMatchMatch::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.match -> {
                supportActionBar?.title = getMatch()
                val mainMatchFragment = MainMatchFragment()
                openFragment(mainMatchFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.teams -> {
                supportActionBar?.title = getTeam()
                val teamsFragment = TeamsFragment()
                openFragment(teamsFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.favorite -> {
                supportActionBar?.title = getFavorite()
                val favoriteFragment = MainFavoriteFragment()
                openFragment(favoriteFragment)
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this).setMessage("EXIT ?").setCancelable(false)
                .setPositiveButton("YES",
                        { dialogInterface, i -> moveTaskToBack(true) })
                .setNegativeButton("NO",
                        { dialogInterface, i -> }).show()
    }


}

