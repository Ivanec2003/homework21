package com.example.homework21.contacts

import android.content.ContentProviderOperation
import android.content.ContentResolver
import android.content.Context
import android.provider.ContactsContract
import java.lang.Exception

class ContactsRepository(private val context: Context) {
    fun getContacts(): List<ContactModel>{
        val contentResolver: ContentResolver = context.contentResolver
        val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI

        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_ALTERNATIVE,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )

        val cursor = contentResolver.query(uri, projection, null, null, null)
        cursor?.use {

            if(it.moveToFirst()){

                val listContacts: MutableList<ContactModel> = mutableListOf()

                do {
                    val contact = ContactModel()
                    contact.id = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)

                    val nameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_ALTERNATIVE)
                    val numberIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

                    if(nameIndex >= 0){
                        contact.name = it.getString(nameIndex)
                    }
                    if(nameIndex >= 0){
                        contact.number = it.getString(numberIndex)
                    }
                    listContacts.add(contact)
                }while (it.moveToNext())

                return listContacts
            }
        }
        return emptyList()
    }

    fun saveContact(contact: ContactModel){
        val contentResolver: ContentResolver = context.contentResolver
        val operations = ArrayList<ContentProviderOperation>()

        operations.add(
            ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build()
        )

        operations.add(
            ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, contact.name)
                .build()
        )

        operations.add(
            ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.number)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .build()
        )
        try {
            contentResolver.applyBatch(ContactsContract.AUTHORITY, operations)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}