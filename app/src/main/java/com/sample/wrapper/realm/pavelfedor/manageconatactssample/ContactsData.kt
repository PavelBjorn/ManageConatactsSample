package com.sample.wrapper.realm.pavelfedor.manageconatactssample

import android.provider.ContactsContract

data class ContactsData(
        val dataId: String,
        val memType: String,
        val value: String
) {
    fun getTitle() = if (memType == ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE) "PHONE" else
        memType.takeLastWhile { it != '/' }.toUpperCase()
}
