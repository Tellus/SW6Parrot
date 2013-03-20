package dk.aau.cs.giraf.oasis.lib.metadata;

import android.net.Uri;
import android.provider.BaseColumns;

public class MediaDepartmentAccessMetaData {

	public static final Uri CONTENT_URI = Uri.parse("content://dk.aau.cs.giraf.oasis.localdb.AutismProvider/mediadepartmentaccess");

	public class Table implements BaseColumns {
		public static final String TABLE_NAME = "tbl_mediadepartmentaccess";

		public static final String COLUMN_IDDEPARTMENT = "mediadepartmentaccess_departmentid";
		public static final String COLUMN_IDMEDIA = "mediadepartmentaccess_mediaid";
	}
}
