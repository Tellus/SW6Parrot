package dk.aau.cs.giraf.oasis.localdb;

import android.net.Uri;
import android.provider.BaseColumns;

public class DepartmentsMetaData {
	
	public static final Uri CONTENT_URI = Uri.parse("content://dk.aau.cs.giraf.oasis.localdb.AutismProvider/departments");
	
	public static final String CONTENT_TYPE_DEPARTMENTS_LIST = "vnd.android.cursor.dir/vnd.dk.departments";
	public static final String CONTENT_TYPE_DEPARTMENT_ONE = "vnd.android.cursor.item/vnd.dk.departments";
	
	public class Table implements BaseColumns {
		public static final String TABLE_NAME = "tbl_departments";
		
		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_NAME = "departments_name";
		public static final String COLUMN_ADDRESS = "departments_address";
		public static final String COLUMN_PHONE = "departments_phone";
		public static final String COLUMN_EMAIL = "departments_email";
	}
}
