package dk.aau.cs.giraf.oasis.localdb;

import android.net.Uri;
import android.provider.BaseColumns;

public class ListOfAppsMetaData {

	public static final Uri CONTENT_URI = Uri.parse("content://dk.aau.cs.giraf.oasis.localdb.AutismProvider/listofapps");

	public static final String CONTENT_TYPE_LISTOFAPPS_LIST = "vnd.android.cursor.dir/vnd.dk.listofapps";
	public static final String CONTENT_TYPE_LISTOFAPP_ONE = "vnd.android.cursor.item/vnd.dk.listofapps";

	public class Table implements BaseColumns {
		public static final String TABLE_NAME = "tbl_listofapps";

		public static final String COLUMN_IDAPP = "listofapps_appid";
		public static final String COLUMN_IDPROFILE = "listofapps_profileid";
		public static final String COLUMN_SETTINGS = "listofapps_settings";
		public static final String COLUMN_STATS = "listofapps_stats";
	}
}
