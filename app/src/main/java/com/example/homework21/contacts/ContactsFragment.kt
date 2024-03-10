package com.example.homework21.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework21.databinding.FragmentContactsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ContactViewModel

    private lateinit var adapter: ContactsAdapter

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        requestPermission.launch(
            arrayOf(
                "android.permission.READ_CONTACTS",
                "android.permission.WRITE_CONTACTS"
            )
        )

        viewModel = ViewModelProvider(
            this,
            ContactsFactory(ContactsRepository(requireContext()))
        )[ContactViewModel::class.java]

        adapter = ContactsAdapter()
        binding.recyclerViewContacts.adapter = adapter
        binding.recyclerViewContacts.layoutManager = LinearLayoutManager(requireContext())

        initObserve()

        clickButtonAddContact()
    }

    private fun initObserve() {
        // Налаштування спостерігача для оновлення списку контактів
        viewModel.contacts.observe(viewLifecycleOwner) { contacts ->
            adapter.submitList(contacts)
        }
    }

    private fun clickButtonAddContact(){

        binding.floatingActionButtonAddContact.setOnClickListener{
            MaterialAlertDialogBuilder(requireContext())

                .setTitle("Add contact")
                .setMessage("How would you like to add contacts??")
                .setPositiveButton("Device") { _, _ ->

                    viewModel.fetchListContacts()

                    binding.textViewInscriptionAddContact.visibility = View.INVISIBLE
                    binding.floatingActionButtonAddContact.visibility = View.INVISIBLE

                }
                .setNegativeButton("Create") { _, _ ->

                    val index = viewModel.contacts.value?.size ?: 0
                    viewModel.addContact(ContactModel(index,"Vlad228", "432449234782"))

                }
                .show()

        }

    }


}