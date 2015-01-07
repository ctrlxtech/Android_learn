package com.example.androidclient;

import java.util.ArrayList;
import java.util.List;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper{
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "ControlDataManager";

	// Contacts table name
	private static final String TABLE_CONTROL = "ControlButton";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_INFO = "controlInfo";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTROL + "("
				+ KEY_ID + " TEXT PRIMARY KEY," + KEY_INFO + " TEXT)";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTROL);

		// Create tables again
		onCreate(db);
	}

	public void deleteTB(){
		SQLiteDatabase db = this.getWritableDatabase();
		onUpgrade(db,1,2);
	}

	public void addInfo(ButtonEntry button){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		boolean update=false;
		try{
			ButtonEntry oldButton = getButton(button.getID());
			updateButton(new ButtonEntry(button.getID(),button.getControl()),oldButton.getID());
			update = true;
		}catch(CursorIndexOutOfBoundsException e){
			values.put(KEY_ID, button.getID());
			values.put(KEY_INFO, button.getControl());
			db.insert(TABLE_CONTROL, null, values);
		}
		System.out.println("Update DB or not? "+update);
		db.close();
	}

	public ButtonEntry getButton(String id) throws CursorIndexOutOfBoundsException{
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_CONTROL, new String[] { KEY_ID,
				KEY_INFO }, KEY_ID + "=?",
				new String[] { id }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		ButtonEntry button = new ButtonEntry(cursor.getString(0),cursor.getString(1));
		return button;
	}

	public List<ButtonEntry> getAllButton() {
		List<ButtonEntry> buttonList = new ArrayList<ButtonEntry>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CONTROL;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				ButtonEntry button = new ButtonEntry(cursor.getString(0),cursor.getString(1));
				// Adding contact to list
				buttonList.add(button);
			} while (cursor.moveToNext());
		}

		// return contact list
		return buttonList;
	}

	public int updateButton(ButtonEntry newButton,String oldID) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, newButton.getID());
		values.put(KEY_INFO, newButton.getControl());

		// updating row
		return db.update(TABLE_CONTROL, values, KEY_ID + " = ?",
				new String[] { oldID });
	}

	public void deleteContact(ButtonEntry button) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CONTROL, KEY_ID + " = ?",
				new String[] { button.getID() });
		db.close();
	}

	public int getButtonsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_CONTROL;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}
}
