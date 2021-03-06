package com.airanza.mathquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MathQuizOpenHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "MATHQUIZDB";
	private static final int DATABASE_VERSION = 2;
	private static final String RESULTS_TABLE_NAME = "results";
	private static final String RESULTS_TABLE_CREATE = "CREATE TABLE " +
					RESULTS_TABLE_NAME + " (" +
					"TIMESTAMP INTEGER, PROBLEM TEXT, SOLUTION TEXT, CORRECT INTEGER);";

	MathQuizOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(RESULTS_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
