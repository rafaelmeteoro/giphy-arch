package com.meteoro.giphy.giflist

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.meteoro.giphy.R

class GifListHostActivity : AppCompatActivity(R.layout.activity_gif_list_host) {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_gif_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_favorite -> {
            val action = GifListFragmentDirections.actionToFavorite()
            findNavController(R.id.main_content).navigate(action)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
}
