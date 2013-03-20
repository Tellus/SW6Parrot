package dk.aau.cs.giraf.oasis.localdb;

import android.net.Uri;
import android.provider.BaseColumns;

public class AuthUsersMetaData {

	public static final Uri CONTENT_URI = Uri.parse("content://dk.aau.cs.giraf.oasis.localdb.AutismProvider/authusers");

	public static final String CONTENT_TYPE_AUTHUSERS_LIST = "vnd.android.cursor.dir/vnd.dk.authusers";
	public static final String CONTENT_TYPE_AUTHUSER_ONE = "vnd.android.cursor.item/vnd.dk.authusers";

	public class Table implements BaseColumns {
		public static final String TABLE_NAME = "tbl_authusers";

		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_CERTIFICATE = "authusers_certificate";
		public static final String COLUMN_ROLE = "authusers_role";
	}
}
