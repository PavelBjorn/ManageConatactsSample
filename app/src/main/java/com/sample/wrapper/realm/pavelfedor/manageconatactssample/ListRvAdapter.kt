package com.sample.wrapper.realm.pavelfedor.manageconatactssample

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item.view.*

class ListRvAdapter : RecyclerView.Adapter<ListRvAdapter.ContactsViewHolder>() {

    var contact: Contact? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ContactsViewHolder {
        return ContactsViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item, p0, false))
    }

    override fun getItemCount(): Int {
        return contact?.contactsData?.size ?: 0
    }

    override fun onBindViewHolder(p0: ContactsViewHolder, p1: Int) {
        p0.bind(contact?.contactsData?.get(p1) ?: return)
    }


    inner class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(contactData: ContactsData) {
            itemView.title.text = contactData.getTitle()
            itemView.value.text = contactData.value
        }
    }
}
