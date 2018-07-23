package com.sample.wrapper.realm.pavelfedor.manageconatactssample


import android.app.Activity
import android.content.ContentProviderOperation
import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.content.Intent
import android.content.Intent.ACTION_PICK
import android.database.Cursor
import android.net.Uri
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAdd.setOnClickListener {
            getContactFromAddressBook(0)
        }

        btnGet.setOnClickListener {
            getContactFromAddressBook(1)
        }

        rvContactData.adapter = ListRvAdapter()
        rvContactData.layoutManager = LinearLayoutManager(this)
    }

    private fun getContactFromAddressBook(resultCode: Int) {
        startActivityForResult(Intent(ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), resultCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 0 || requestCode == 1) {
            data?.let {
                (rvContactData.adapter as ListRvAdapter).apply {
                    contact = getContact(it.data ?: return, requestCode)
                    notifyItemRangeChanged(0, contact?.contactsData?.size ?: 0)
                }
            }
        }
    }

    private fun getContact(uri: Uri, requestCode: Int): Contact {
        return contentResolver.run {
            query(uri, null, null, null, null).run {

                var id = ""
                val data = mutableListOf<ContactsData>()

                moveToFirst()

                do {
                    data.add(ContactsData(
                            "",
                            "Name",
                            getString(getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    ))

                    val contactId = getString(getColumnIndex(ContactsContract.Contacts._ID))

                    query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = $contactId",
                            null,
                            null
                    ).apply {
                        moveToFirst()
                        do {
                            data.add(ContactsData(
                                    getString(ContactsContract.CommonDataKinds.Phone._ID),
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
                                    getString(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            ))
                        } while (moveToNext())
                    }.close()


                    if (requestCode == 0) query(ContactsContract.RawContacts.CONTENT_URI,
                            null,
                            "${ContactsContract.RawContacts.CONTACT_ID} = $contactId",
                            null,
                            null
                    ).apply {

                        if (moveToFirst()) {
                            do {
                                linkToTreel(getString(ContactsContract.RawContacts._ID))
                            } while (moveToNext())
                        }
                    }

                    query(ContactsContract.Data.CONTENT_URI,
                            null,
                            "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = $contactId" +
                                    " AND ${ContactsContract.Data.MIMETYPE} = \"${ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE}\"",
                            null,
                            null
                    ).apply {
                        if (moveToFirst()) {
                            do {
                                data.add(ContactsData(
                                        getString(ContactsContract.Data._ID),
                                        ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE,
                                        "${getString(ContactsContract.Data.DATA6)}: ${getString(ContactsContract.Data.DATA1)} "
                                                + "with uid ${getString(ContactsContract.Data.DATA2)}"
                                ))
                            } while (moveToNext())
                        }
                    }

                } while (moveToNext())
                close()
                Contact(id, data)
            }
        }
    }

    private fun linkToTreel(id: String) {
        contentResolver.applyBatch(
                ContactsContract.AUTHORITY,
                ArrayList(listOf(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValues(ContentValues().apply {
                            put(ContactsContract.Data.RAW_CONTACT_ID, id)
                            put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE)
                            put(ContactsContract.CommonDataKinds.Im.DATA, "Linked name")
                            put(ContactsContract.CommonDataKinds.Im.PROTOCOL, ContactsContract.CommonDataKinds.Im.PROTOCOL_CUSTOM)
                            put(ContactsContract.CommonDataKinds.Im.CUSTOM_PROTOCOL, "Linked Contact")
                            put(ContactsContract.CommonDataKinds.Im.DATA2, Random().nextInt().toString())
                        }).build()))
        )
    }
}


fun Cursor.getString(name: String): String {
    return try {
        getString(getColumnIndex(name))
    } catch (e: Throwable) {
        e.printStackTrace()
        ""
    }
}
