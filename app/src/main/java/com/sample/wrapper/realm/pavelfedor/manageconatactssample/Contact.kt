package com.sample.wrapper.realm.pavelfedor.manageconatactssample

data class Contact(
        val contactId: String,
        val contactsData: List<ContactsData> = listOf()
)
