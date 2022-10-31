package com.example.demo.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.demo.ui.man1.FragmentMan1
import com.example.demo.ui.man2.FragmentMan2
import com.example.demo.ui.man3.FragmentMan3
import com.example.demo.R
import com.example.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object{
        fun getIntent(context: Context, page: Int): Intent {
           return Intent(context, MainActivity::class.java).apply {
                this.putExtra("PAGE_INDEX", page)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        replaceFragment(FragmentMan1())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.man1 ->replaceFragment(FragmentMan1())
                R.id.man2 ->replaceFragment(FragmentMan2())
                R.id.man3 ->replaceFragment(FragmentMan3())
            }
            true
        }

//        startActivity(Companion.getIntent(this, 1))
    }

    private fun replaceFragment(fragment : Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransition = fragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.FrameLayout, fragment)
        fragmentTransition.commit()
    }
}