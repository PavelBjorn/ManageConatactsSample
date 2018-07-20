package com.sample.wrapper.realm.pavelfedor.manageconatactssample


import android.app.Activity
import android.content.ContentProviderOperation
import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
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
        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            getContactFromAddressBook()
        }

        rvContactData.adapter = ListRvAdapter()
        rvContactData.layoutManager = LinearLayoutManager(this)
    }

    private fun getContactFromAddressBook() {
        startActivityForResult(Intent(ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 0) {
            data?.let {
                (rvContactData.adapter as ListRvAdapter).apply {
                    contact = getContact(it.data ?: return)
                    notifyItemRangeChanged(0, contact?.contactsData?.size ?: 0)
                }
            }
        }
    }

    private fun getContact(uri: Uri): Contact {
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

                    val contactId = getString(getColumnIndex(ContactsContract.Contacts._ID)).apply {
                        linkToTrill(this)
                    }

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

                    query(ContactsContract.Data.CONTENT_URI,
                            null,
                            "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = $contactId" +
                                    " AND ${ContactsContract.Data.MIMETYPE} = \"${ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE}\"",
                            null,
                            null).apply {
                        moveToFirst()
                        do {
                            data.add(ContactsData(
                                    getString(ContactsContract.Data._ID),
                                    ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE,
                                    "${getString(ContactsContract.Data.DATA6)}: ${getString(ContactsContract.Data.DATA1)} " +
                                            "with uid ${getString(ContactsContract.Data.DATA2)}"
                            ))
                        } while (moveToNext())
                    }

                } while (moveToNext())
                close()
                Contact(id, data)
            }
        }
    }

    private fun linkToTrill(id: String) {

        contentResolver.applyBatch(
                ContactsContract.AUTHORITY,
                ArrayList(listOf(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValues(ContentValues().apply {
                            put(ContactsContract.Data.RAW_CONTACT_ID, id)
                            put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE)
                            put(ContactsContract.CommonDataKinds.Im.DATA, "CustomData")
                            put(ContactsContract.CommonDataKinds.Im.PROTOCOL, ContactsContract.CommonDataKinds.Im.PROTOCOL_CUSTOM)
                            put(ContactsContract.CommonDataKinds.Im.CUSTOM_PROTOCOL, "Treel")
                            put(ContactsContract.CommonDataKinds.Im.DATA2, Random().nextInt().toString())
                        }).build()))
        )
    }

    /*fun getContacts() {
        contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null
        ).apply {
            moveToFirst()
            while (moveToNext()) {
                val id = getString(getColumnIndex(ContactsContract.Contacts._ID))
                val name = getString(getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val data = mutableListOf<ContactsData>()

                if (getInt(getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {

                    contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            "${ContactsContract.Data.CONTACT_ID} = ?",
                            arrayOf(id),
                            null,
                            null
                    ).apply {
                        moveToFirst()
                        while (moveToNext()) {
                            data.add(ContactsData(
                                    getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID)),
                                    getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.MIMETYPE)),
                                    listOf(getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)))
                            ))
                        }

                    }.close()

                    contentResolver.query(
                            ContactsContract.Data.CONTENT_URI,
                            null,
                            "${ContactsContract.Data.CONTACT_ID} = ?",
                            arrayOf(id),
                            null,
                            null
                    ).apply {
                        moveToFirst()
                        while (moveToNext()) {

                            data.add(ContactsData(
                                    getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID)),
                                    getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.MIMETYPE)),
                                    listOf(
                                            getString(getColumnIndex(ContactsContract.Data._ID)),
                                            getString(getColumnIndex(ContactsContract.Data.MIMETYPE)),
                                            getString(getColumnIndex(ContactsContract.Data.DATA1)),
                                            getString(getColumnIndex(ContactsContract.Data.DATA6))
                                            *//* getString(getColumnIndex(ContactsContract.Data.DATA3)),
                                             getString(getColumnIndex(ContactsContract.Data.DATA4)),
                                             getString(getColumnIndex(ContactsContract.Data.DATA5)),
                                             getString(getColumnIndex(ContactsContract.Data.DATA6)),
                                             getString(getColumnIndex(ContactsContract.Data.DATA7)),
                                             getString(getColumnIndex(ContactsContract.Data.DATA8)),
                                             getString(getColumnIndex(ContactsContract.Data.DATA9)),
                                             getString(getColumnIndex(ContactsContract.Data.DATA10)),
                                             getString(getColumnIndex(ContactsContract.Data.DATA11)),
                                             getString(getColumnIndex(ContactsContract.Data.DATA12)),
                                             getString(getColumnIndex(ContactsContract.Data.DATA13)),
                                             getString(getColumnIndex(ContactsContract.Data.DATA14)),
                                             getString(getColumnIndex(ContactsContract.Data.DATA15))*//*
                                    )
                            ))
                        }
                    }.close()

                    contacts.add(Contact(id, name, data))
                }
            }
        }.close()

        Toast.makeText(this, "End", Toast.LENGTH_SHORT).show()
    }*/
}


fun Cursor.getString(name: String): String {
    return getString(getColumnIndex(name))
}
