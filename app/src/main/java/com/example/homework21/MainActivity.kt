package com.example.homework21

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homework21.contacts.ContactsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment()
    }

    private fun loadFragment(){
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, ContactsFragment())
            .commit()
    }
}