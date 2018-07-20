/*package com.sample.wrapper.realm.pavelfedor.manageconatactssample

private void getContactList() {
    ContentResolver cr = getContentResolver();
    Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
    null, null, null, null);

    if ((cur != null ? cur.getCount() : 0) > 0) {
        while (cur != null && cur.moveToNext()) {
            String id = cur.getString(
                    cur.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cur.getString(cur.getColumnIndex(
                    ContactsContract.Contacts.DISPLAY_NAME));

            if (cur.getInt(cur.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                Cursor pCur = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                new String[]{id}, null);
                while (pCur.moveToNext()) {
                    String phoneNo = pCur.getString(pCur.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Log.i(TAG, "Name: " + name);
                    Log.i(TAG, "Phone Number: " + phoneNo);
                }
                pCur.close();
            }
        }
    }
    if(cur!=null){
        cur.close();
    }


    Intent intent = new Intent(Intent.ACTION_INSERT_OR_EDIT);
intent.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);
intent.putExtra(ContactsContract.Intents.Insert.NAME, user.getName());

ArrayList<ContentValues> data = new ArrayList<ContentValues>();
ContentValues values = new ContentValues();
values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
values.put(ContactsContract.CommonDataKinds.Im.DATA, user.getID());
values.put(ContactsContract.CommonDataKinds.Im.PROTOCOL, ContactsContract.CommonDataKinds.Im.PROTOCOL_CUSTOM);
values.put(ContactsContract.CommonDataKinds.Im.CUSTOM_PROTOCOL, "your_protocol");
data.add(values);
intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, data);

startActivityForResult(intent, 0);
}*/



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
