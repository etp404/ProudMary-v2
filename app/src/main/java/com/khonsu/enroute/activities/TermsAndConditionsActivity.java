package com.khonsu.enroute.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.khonsu.enroute.R;

public class TermsAndConditionsActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.terms_and_conditions);

		TextView googleTermsAndConditionsLink = (TextView) findViewById(R.id.google_terms_and_conditions_link);
		googleTermsAndConditionsLink.setMovementMethod(LinkMovementMethod.getInstance());

		TextView googlePrivacyPolicyLink = (TextView) findViewById(R.id.google_privacy_policy_link);
		googlePrivacyPolicyLink.setMovementMethod(LinkMovementMethod.getInstance());
	}
}
