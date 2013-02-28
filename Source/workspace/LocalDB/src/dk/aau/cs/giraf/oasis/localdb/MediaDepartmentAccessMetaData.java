package dk.aau.cs.giraf.oasis.localdb;

import android.net.Uri;
import android.provider.BaseColumns;

public class MediaDepartmentAccessMetaData {

	public static final Uri CONTENT_URI = Uri.parse("content://dk.aau.cs.giraf.oasis.localdb.AutismProvider/mediadepartmentaccess");

	public static final String CONTENT_TYPE_MEDIADEPARTMENTACCESS_LIST = "vnd.android.cursor.dir/vnd.dk.mediadepartmentaccess";
	public static final String CONTENT_TYPE_MEDIADEPARTMENTACCESS_ONE = "vnd.android.cursor.item/vnd.dk.mediadepartmentaccess";

	public class Table implements BaseColumns {
		public static final String TABLE_NAME = "tbl_mediadepartmentaccess";

		public static final String COLUMN_IDDEPARTMENT = "mediadepartmentaccess_departmentid";
		public static final String COLUMN_IDMEDIA = "mediadepartmentaccess_mediaid";
	}
}
