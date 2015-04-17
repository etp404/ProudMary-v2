package com.khonsu.enroute.contactsautocomplete;

import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class ContactsAccessor {
	private final ContentResolver contentResolver;
	private List<Contact> contacts;
	private ContactsAccessorListener listener;

	public ContactsAccessor(ContentResolver contentResolver) {
		this.contentResolver = contentResolver;
	}

	public void invalidateCache() {
		contacts = null;
	}

	private void updateCachedContacts() {
		if (listener != null) {
			listener.startingToFetchContacts();
		}
		contacts = new ArrayList<>();
		Cursor contactsCursor =
				contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts.SORT_KEY_PRIMARY);
		ContentProviderClient mCProviderClient = contentResolver.acquireContentProviderClient(ContactsContract.Contacts.CONTENT_URI);

		if (contactsCursor.getCount() > 0) {
			while (contactsCursor.moveToNext()) {
				String contactId = contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.Data._ID));

				int displayNameIx = contactsCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
				String name = contactsCursor.getString(displayNameIx);

				if (contactsCursor.getInt(contactsCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
					try {
						Cursor pCur = mCProviderClient.query(
								ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
								null,
								ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
								null,
								null);
						while (pCur.moveToNext()) {
							int numberIx = pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
							String phoneNo = pCur.getString(numberIx);
							contacts.add(new Contact(name, phoneNo));
						}
						pCur.close();
					} catch (RemoteException e) {
						e.printStackTrace();
					}

				}
			}
		}
		contactsCursor.close();
		if (listener != null) {
			listener.finishedFetchingContacts();
		}
	}

	public List<Contact> getCachedContacts() {
		if (contacts == null) {
			updateCachedContacts();
		}
		return contacts;
	}

	public Contact getContact(Uri uri) {
		Cursor cursor = contentResolver.query(uri, null, null, null, null);
		cursor.moveToFirst();
		String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
		String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
		return new Contact(name, number);
	}

	public void setListener(ContactsAccessorListener listener) {
		this.listener = listener;
	}

	public interface ContactsAccessorListener {
		public void startingToFetchContacts();
		public void finishedFetchingContacts();
	}
}