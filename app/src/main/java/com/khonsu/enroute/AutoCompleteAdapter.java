package com.khonsu.enroute;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.List;

public class AutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
	private List<String> resultList;
	private AutocompleteSuggestor suggestor;

	public AutoCompleteAdapter(Context context, int textViewResourceId, AutocompleteSuggestor suggestor) {
		super(context, textViewResourceId);
		this.suggestor = suggestor;
	}

	@Override
	public int getCount() {
		return resultList.size();
	}

	@Override
	public String getItem(int index) {
		return resultList.get(index);
	}

	@Override
	public Filter getFilter() {
		return new Filter() {
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults filterResults = new FilterResults();
				if (constraint != null) {
					// Retrieve the autocomplete results.
					resultList =  suggestor.getSuggestions(constraint.toString());

					// Assign the data to the FilterResults
					filterResults.values = resultList;
					filterResults.count = resultList.size();
				}
				return filterResults;
			}

			@Override
			protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
				if (results != null && results.count > 0) {
					notifyDataSetChanged();
				}
				else {
					notifyDataSetInvalidated();
				}
			}};
	}
}