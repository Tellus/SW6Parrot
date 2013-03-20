package dk.aau.cs.giraf.oasis.localdb;

import android.database.sqlite.SQLiteDatabase;

public class HasSubDepartmentTable {

	private static final String TABLE_CREATE = "CREATE TABLE "
			+ HasSubDepartmentMetaData.Table.TABLE_NAME
			+ "("
			+ HasSubDepartmentMetaData.Table.COLUMN_IDDEPARTMENT + " INTEGER NOT NULL, "
			+ HasSubDepartmentMetaData.Table.COLUMN_IDSUBDEPARTMENT + " INTEGER NOT NULL, "
			+ "PRIMARY KEY(" + HasSubDepartmentMetaData.Table.COLUMN_IDDEPARTMENT + ", " + HasSubDepartmentMetaData.Table.COLUMN_IDSUBDEPARTMENT + ")"
			+ ");";

	private static final String TABLE_DROP= "DROP TABLE IF EXISTS " + HasSubDepartmentMetaData.Table.TABLE_NAME + ";";

	/**
	 * Executes sql string for creating table
	 * @param db this is an instance of a sqlite database
	 */
	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE);
	}

	/**
	 * executes a sql string which drops the old table and then the method call the oncreate, which creates a new table
	 * @param db this is an instance of a sqlite database
	 * @param oldVersion integer referring to the old version number
	 * @param newVersion integer referring to the new version number
	 */
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(TABLE_DROP);
		onCreate(db);
	}
}