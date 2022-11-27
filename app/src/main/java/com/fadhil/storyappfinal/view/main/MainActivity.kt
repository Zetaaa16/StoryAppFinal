package com.fadhil.storyappfinal.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.fadhil.storyappfinal.R
import com.fadhil.storyappfinal.databinding.ActivityMainBinding
import com.fadhil.storyappfinal.storage.pref.PreferenceDataSource
import com.fadhil.storyappfinal.storage.pref.UserModel
import com.fadhil.storyappfinal.utils.ConstVal.EXTRA_KEY
import com.fadhil.storyappfinal.view.login.LoginActivity
import com.fadhil.storyappfinal.view.maps.MapsActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userModel: UserModel
    private lateinit var pref: PreferenceDataSource

    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentManager = supportFragmentManager
        val listStoryFragment = StoryFragment()
        val fragment = fragmentManager.findFragmentByTag(StoryFragment::class.java.simpleName)

        if (fragment !is StoryFragment) {
            Log.d(
                "MyFlexibleFragment",
                "Fragment Name :" + StoryFragment::class.java.simpleName
            )
            fragmentManager
                .beginTransaction()
                .add(R.id.main, listStoryFragment, StoryFragment::class.java.simpleName)
                .commit()
        }

        pref = PreferenceDataSource(this)
        userModel = pref.getUser()

        token = userModel.token.toString()

        supportActionBar?.setTitle(R.string.app_name)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.mainmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                logout()
                true
            }
            R.id.language -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            R.id.map -> {
                val intent = Intent(this, MapsActivity::class.java)
                intent.putExtra(EXTRA_KEY, token)
                startActivity(intent)
                true
            }
            else -> true
        }
    }

    private fun logout() {
        userModel.token = ""
        pref.setUser(userModel)
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}