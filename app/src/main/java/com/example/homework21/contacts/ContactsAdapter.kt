package com.example.homework21.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework21.databinding.ItemContactsBinding

class ContactsAdapter :
    ListAdapter<ContactModel, ContactsAdapter.ContactViewHolder>(ContactsDiffUtilCallBack()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactViewHolder {

        val binding =
            ItemContactsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {

        val contact = getItem(position)
        holder.bind(contact)

    }

    class ContactViewHolder(private val binding: ItemContactsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: ContactModel) {

            binding.apply {
                textViewContactName.text = contact.name
                textViewContactPhoneNumber.text = contact.number
            }

        }

    }

    class ContactsDiffUtilCallBack : DiffUtil.ItemCallback<ContactModel>() {

        override fun areContentsTheSame(oldItem: ContactModel, newItem: ContactModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldItem: ContactModel, newItem: ContactModel): Boolean {
            return oldItem == newItem
        }

    }
}
