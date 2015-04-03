package com.khonsu.enroute.contactsautocomplete;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filterable;

public class ContactsAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
    public ContactsAutoCompleteAdapter(Context context, int resource){
        super(context, resource);
    }

}
