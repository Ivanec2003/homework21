package com.example.homework21.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ContactViewModel(
    private val repository: ContactsRepository,
) : ViewModel() {
    private val _contacts: MutableLiveData<List<ContactModel>> = MutableLiveData()
    val contacts: LiveData<List<ContactModel>>
        get() = _contacts

    fun fetchListContacts(): Boolean {
        _contacts.value = repository.getContacts()
        return contacts.value?.isEmpty() != false
    }

    fun addContact(contact: ContactModel) {
        repository.saveContact(contact)
        val currentContacts = _contacts.value.orEmpty().toMutableList()
        currentContacts.add(contact)
        _contacts.value = currentContacts
    }
}