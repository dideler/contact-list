package com.dennisideler.contactlist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This helper subclass helps to maintain the database and tables.
 * It contains typical statements that create and delete a table, and more.
 * 
 * @author Dennis Ideler
 *
 */
public class ContactsDbHelper extends SQLiteOpenHelper {
	private static final String TAG = "ContactsDbHelper";  // Logger tag.
	
	// If you change the database schema, you must increment the database version.
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "ContactList.db";
	
	private static final String SQL_CREATE_ENTRIES =
	    "CREATE TABLE " + ContactsContract.Contacts.TABLE_NAME + " ("
	    + ContactsContract.Contacts._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
	    //+ ContactsContract.Contacts.COLUMN_NAME_ID + " TEXT,"
	    + ContactsContract.Contacts.COLUMN_NAME_FNAME + " TEXT,"
	    + ContactsContract.Contacts.COLUMN_NAME_LNAME + " TEXT,"
	    + ContactsContract.Contacts.COLUMN_NAME_PHONE + " TEXT," // NOT NULL?
	    + ContactsContract.Contacts.COLUMN_NAME_EMAIL + " TEXT,"
	    + ContactsContract.Contacts.COLUMN_NAME_BDAY + " TEXT"
	    + ");";

	private static final String SQL_DELETE_ENTRIES =
	    "DROP TABLE IF EXISTS " + ContactsContract.Contacts.TABLE_NAME;
	
	public ContactsDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	//@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
	}
	
	/**
	 * Here you define the upgrade policy for the database.
	 * Implement how to "move" your application data during an upgrade of
	 * schema versions. There is no ALTER TABLE command in SQLite, so this
	 * generally involves CREATING a new table, moving data if possible,
	 * or deleting the old data and starting fresh. Your call.
	 */
	//@Override	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Not a real application, so delete the old data and start fresh.
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
		db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
	}
	
	
	//@Override
	//public void onOpen(SQLiteDatabase db) {
	//	super.onOpen(db);
	//}
}