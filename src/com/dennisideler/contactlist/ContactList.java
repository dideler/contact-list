package com.dennisideler.contactlist;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.Menu;

public class ContactList extends Activity {
	
	private static final String TAG = "ContactList";  // Logger tag.
	//protected ContactsDbHelper mDbHelper = null; 
	//protected Cursor mCursor = null;
	//protected SQLiteDatabase mDB = null;
	// See PT2 MainActivity for example of how the protected members are used

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        
        // FIXME: just testing the db, do it properly later
        
        // INSERT
        ContactsDbHelper mDbHelper = new ContactsDbHelper(this.getApplicationContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase(); // Write mode
        
        // Create a new map of values, where column names are the keys
        String fname = "Dennis";
        String lname = "Ideler";
        String number = PhoneNumberUtils.formatNumber("2896971010");
        String email = "ideler.dennis@gmail.com";
        String date = "today";
        
        ContentValues values = new ContentValues();
        values.put(ContactsContract.Contacts.COLUMN_NAME_FNAME, fname);
        values.put(ContactsContract.Contacts.COLUMN_NAME_LNAME, lname);
        values.put(ContactsContract.Contacts.COLUMN_NAME_PHONE, number);
        values.put(ContactsContract.Contacts.COLUMN_NAME_EMAIL, email);
        values.put(ContactsContract.Contacts.COLUMN_NAME_BDAY, date);
        
        // Insert the new row, returning primary key value of the new row.
        long rowId = db.insert(
        		ContactsContract.Contacts.TABLE_NAME,
        		null,
        		values);
        
        // READ
        /*SQLiteDatabase*/ db = mDbHelper.getReadableDatabase();
        
        // Define a projection (i.e. columns to be used after query)
        String[] projection = {
        		ContactsContract.Contacts._ID,
        		ContactsContract.Contacts.COLUMN_NAME_FNAME,
        		ContactsContract.Contacts.COLUMN_NAME_LNAME,
        		// other columns you want...,
        };
        
        // How to sort results (default is set to fname ASC)
        String sortOrder = ContactsContract.Contacts.DEFAULT_SORT_ORDER;
        
        // Query the db
        Cursor c = db.query(
        		ContactsContract.Contacts.TABLE_NAME, // table to query
        		projection, // columns to return
        		null, // columns for the WHERE clause
        		null, // values for the WHERE clause
        		null, // GROUP BY clause
        		null, // HAVING clause
        		sortOrder // sort order
        		);
        
        // Get the data from the cursor and display it.
        c.moveToFirst();  // Places read position on the first entry in the results
        Long id = c.getLong(c.getColumnIndex(ContactsContract.Contacts._ID));
        String first_name = c.getString(c.getColumnIndex(ContactsContract.Contacts.COLUMN_NAME_FNAME));
        String last_name = c.getString(c.getColumnIndex(ContactsContract.Contacts.COLUMN_NAME_LNAME));
        // and so forth for each row...
        //   do while c.moveToNext() is true
        //   or for-loop with c.getCount()
        Log.i(TAG, "id = " + id + ", fname = " + first_name + ", lname = " + last_name);
        
        // UPDATE
        db = mDbHelper.getReadableDatabase();
        ContentValues valuesToUpdate = new ContentValues(); // New value for one column
        valuesToUpdate.put(ContactsContract.Contacts.COLUMN_NAME_LNAME, "Badass");
        
        String whereClause = ContactsContract.Contacts.COLUMN_NAME_LNAME + " LIKE ?";
        String[] whereArgs = { "Ideler" }; 
        int numRowsAffected = db.update(
        		ContactsContract.Contacts.TABLE_NAME,
        		valuesToUpdate,
        		whereClause,
        		whereArgs
        		);
        
        // Query the db again to see how it's changed
        c = db.query(
        		ContactsContract.Contacts.TABLE_NAME, // table to query
        		projection, // columns to return
        		null, // columns for the WHERE clause
        		null, // values for the WHERE clause
        		null, // GROUP BY clause
        		null, // HAVING clause
        		sortOrder // sort order
        		);
        
        // Get the data from the cursor and display it.
        c.moveToFirst();  // Places read position on the first entry in the results
        id = c.getLong(c.getColumnIndex(ContactsContract.Contacts._ID));
        first_name = c.getString(c.getColumnIndex(ContactsContract.Contacts.COLUMN_NAME_FNAME));
        last_name = c.getString(c.getColumnIndex(ContactsContract.Contacts.COLUMN_NAME_LNAME));
        Log.i(TAG, "id = " + id + ", fname = " + first_name + ", lname = " + last_name);
        
        
        // DELETE
        // Need to provide selection criteria that identifies the rows
        String selection = ContactsContract.Contacts.COLUMN_NAME_FNAME + "=\"Dennis\""; // Define WHERE part. Use " LIKE ?" when unsure.
        String[] selectionArgs = { String.valueOf(rowId) }; // Specify args in placeholder order
        db.delete(ContactsContract.Contacts.TABLE_NAME, selection, null /*selectionArgs*/);
        // Something like this should also work
        // String[] selectionArgs = { id.toString() };
        // db.delete(TABLE_NAME, ContactsContract.Contacts._ID + "=?", selectionArgs);
        
        Log.i(TAG, "# records before query = " + c.getCount());
        
        // Need to update the cursor with a fresh query!
        c = db.query(
        		ContactsContract.Contacts.TABLE_NAME, // table to query
        		projection, // columns to return
        		null, // columns for the WHERE clause
        		null, // values for the WHERE clause
        		null, // GROUP BY clause
        		null, // HAVING clause
        		sortOrder // sort order
        		);
        
        Log.i(TAG, "# records after query = " + c.getCount());
        
        c.close(); // When cursor no longer needed.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_contact_list, menu);
        return true;
    }
}
