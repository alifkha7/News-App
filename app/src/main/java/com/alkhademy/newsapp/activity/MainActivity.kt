package com.alkhademy.newsapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alkhademy.newsapp.adapter.PagerAdapter
import com.alkhademy.newsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.apply {
            viewPager.adapter = PagerAdapter(supportFragmentManager)
            tabLayout.setupWithViewPager(viewPager)
        }
    }
}