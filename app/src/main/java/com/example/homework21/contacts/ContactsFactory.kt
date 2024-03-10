package com.example.homework21.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ContactsFactory(
    private val repository: ContactsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactViewModel::class.java))
            return ContactViewModel(repository) as T
        throw IllegalArgumentException("Unknown model")
    }
}