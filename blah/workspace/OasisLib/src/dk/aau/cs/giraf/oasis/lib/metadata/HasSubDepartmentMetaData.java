package dk.aau.cs.giraf.oasis.lib.metadata;

import android.net.Uri;
import android.provider.BaseColumns;

public class HasSubDepartmentMetaData {

	public static final Uri CONTENT_URI = Uri.parse("content://dk.aau.cs.giraf.oasis.localdb.AutismProvider/hassubdepartment");

	public class Table implements BaseColumns {
		public static final String TABLE_NAME = "tbl_hassubdepartment";

		public static final String COLUMN_IDDEPARTMENT = "hassubdepartment_departmentid";
		public static final String COLUMN_IDSUBDEPARTMENT = "hassubdepartment_subdepartmentid";
	}
}