package dk.aau.cs.giraf.oasis.localdb;

import android.net.Uri;
import android.provider.BaseColumns;

public class HasDepartmentMetaData {

	public static final Uri CONTENT_URI = Uri.parse("content://dk.aau.cs.giraf.oasis.localdb.AutismProvider/hasdepartment");

	public static final String CONTENT_TYPE_HASDEPARTMENTS_LIST = "vnd.android.cursor.dir/vnd.dk.hasdepartment";
	public static final String CONTENT_TYPE_HASDEPARTMENT_ONE = "vnd.android.cursor.item/vnd.dk.hasdepartment";

	public class Table implements BaseColumns {
		public static final String TABLE_NAME = "tbl_hasdepartment";

		public static final String COLUMN_IDPROFILE = "hasdepartment_profileid";
		public static final String COLUMN_IDDEPARTMENT = "hasdepartment_departmentid";
	}
}
