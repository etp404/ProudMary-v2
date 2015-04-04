package com.khonsu.enroute.customviewcomponents;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

public class AutoCompleteLoading extends AutoCompleteTextView {

	private ProgressBar mLoadingIndicator;

	public AutoCompleteLoading(Context context) {
		super(context);
	}

	public AutoCompleteLoading(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AutoCompleteLoading(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setLoadingIndicator(ProgressBar view) {
		mLoadingIndicator = view;
	}

	@Override
	protected void performFiltering(CharSequence text, int keyCode) {
		mLoadingIndicator.setVisibility(View.VISIBLE);
		super.performFiltering(text, keyCode);
	}

	@Override
	public void onFilterComplete(int count) {
		mLoadingIndicator.setVisibility(View.GONE);
		super.onFilterComplete(count);
	}

}