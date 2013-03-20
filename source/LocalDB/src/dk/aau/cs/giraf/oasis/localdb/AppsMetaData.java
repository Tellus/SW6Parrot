package dk.aau.cs.giraf.oasis.localdb;

import android.net.Uri;
import android.provider.BaseColumns;

public class AppsMetaData {
	
	public static final Uri CONTENT_URI = Uri.parse("content://dk.aau.cs.giraf.oasis.localdb.AutismProvider/apps");
	
	public static final String CONTENT_TYPE_APPS_LIST = "vnd.android.cursor.dir/vnd.dk.apps";
	public static final String CONTENT_TYPE_APP_ONE = "vnd.android.cursor.item/vnd.dk.apps";
	
	public class Table implements BaseColumns {
		public static final String TABLE_NAME = "tbl_applications";
		
		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_NAME = "applications_name";
		public static final String COLUMN_VERSION = "applications_version";
		public static final String COLUMN_ICON = "applications_icon";
		public static final String COLUMN_PACKAGE = "applications_package";
		public static final String COLUMN_ACTIVITY = "applications_activity";
	}
}
