package dk.aau.cs.giraf.oasis.localdb;

import android.database.sqlite.SQLiteDatabase;

public class AuthUsersTable {

	private static final String TABLE_CREATE = "CREATE TABLE "
			+ AuthUsersMetaData.Table.TABLE_NAME
			+ "("
			+ AuthUsersMetaData.Table.COLUMN_ID + " INTEGER NOT NULL, "
			+ AuthUsersMetaData.Table.COLUMN_CERTIFICATE + " TEXT NOT NULL, "
			+ AuthUsersMetaData.Table.COLUMN_ROLE + " INTEGER NOT NULL, "
			+ "PRIMARY KEY (" + AuthUsersMetaData.Table.COLUMN_ID + ", " + AuthUsersMetaData.Table.COLUMN_ID + ")"
			+ ");";

	private static final String TABLE_DROP= "DROP TABLE IF EXISTS " + AuthUsersMetaData.Table.TABLE_NAME + ";";

	/**
	 * Executes sql string for creating certificate table
	 * @param db this is an instance of a sqlite database
	 */
	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	/**
	 * executes a sql string which drops the old table and then the method calls oncreate, which create a new certificate table
	 * @param db this is a instance of a sqlite database
	 * @param oldVersion integer referring to the old version number
	 * @param newVersion integer referring to the new version number
	 */
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(TABLE_DROP);
		onCreate(db);
	}
}
