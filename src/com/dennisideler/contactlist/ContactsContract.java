package com.dennisideler.contactlist;

import android.provider.BaseColumns;

/**
 * A companion or contract class explicitly specifies the layout of your schema
 * in a systematic and self-documenting way. E.g. table and column names.
 * 
 * @author Dennis Ideler
 *
 */
public class ContactsContract {

	// Empty constructor prevents contract class from accidental instantiation.
	private ContactsContract() {}
	
	// Contacts table
	public static abstract class Contacts implements BaseColumns
	{
		public static final String TABLE_NAME = "contacts";
		//public static final String COLUMN_NAME_ID = "id"; // TODO: remove?
		public static final String COLUMN_NAME_FNAME = "firstname";
		public static final String COLUMN_NAME_LNAME = "lastname";
		public static final String COLUMN_NAME_PHONE = "phone";
		public static final String COLUMN_NAME_EMAIL = "email";
		public static final String COLUMN_NAME_BDAY = "birthdate";
		public static final String DEFAULT_SORT_ORDER = "firstname ASC";
	}
	
}