package dk.aau.cs.giraf.oasis.lib.metadata;

import android.net.Uri;
import android.provider.BaseColumns;

public class HasDepartmentMetaData {

	public static final Uri CONTENT_URI = Uri.parse("content://dk.aau.cs.giraf.oasis.localdb.AutismProvider/hasdepartment");

	public class Table implements BaseColumns {
		public static final String TABLE_NAME = "tbl_hasdepartment";

		public static final String COLUMN_IDPROFILE = "hasdepartment_profileid";
		public static final String COLUMN_IDDEPARTMENT = "hasdepartment_departmentid";
	}
}
