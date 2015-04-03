package com.khonsu.enroute.contactsautocomplete;

import com.khonsu.enroute.PhoneNumberValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactsFormatter {

    private final static Pattern contactPattern = Pattern.compile(".*<(.*)>");
    private final static String FORMAT = "%s <%s>";
    private final PhoneNumberValidator phoneNumberValidator;

    public ContactsFormatter(PhoneNumberValidator phoneNumberValidator) {
        this.phoneNumberValidator = phoneNumberValidator;
    }

    public static String format(String name, String number) {
        return String.format(FORMAT, name, number);
    }

    public String getNumberFromContact(String contactString) {
        if (phoneNumberValidator.validate(contactString)) {
            return contactString;
        }
        Matcher matcher = contactPattern.matcher(contactString);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return null;
    }
}
