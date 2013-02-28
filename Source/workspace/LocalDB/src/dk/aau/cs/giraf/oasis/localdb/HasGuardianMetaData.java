package dk.aau.cs.giraf.oasis.localdb;

import android.net.Uri;
import android.provider.BaseColumns;

public class HasGuardianMetaData {

	public static final Uri CONTENT_URI = Uri.parse("content://dk.aau.cs.giraf.oasis.localdb.AutismProvider/hasguardian");

	public static final String CONTENT_TYPE_HASGUARDIANS_LIST = "vnd.android.cursor.dir/vnd.dk.hasguardian";
	public static final String CONTENT_TYPE_HASGUARDIAN_ONE = "vnd.android.cursor.item/vnd.dk.hasguardian";

	public class Table implements BaseColumns {
		public static final String TABLE_NAME = "tbl_hasguardian";

		public static final String COLUMN_IDGUARDIAN = "hasguardian_guardianid";
		public static final String COLUMN_IDCHILD = "hasguardian_childid";
	}
}