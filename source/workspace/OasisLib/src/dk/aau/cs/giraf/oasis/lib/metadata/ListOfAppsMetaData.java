package dk.aau.cs.giraf.oasis.lib.metadata;

import android.net.Uri;
import android.provider.BaseColumns;

public class ListOfAppsMetaData {

	public static final Uri CONTENT_URI = Uri.parse("content://dk.aau.cs.giraf.oasis.localdb.AutismProvider/listofapps");

	public class Table implements BaseColumns {
		public static final String TABLE_NAME = "tbl_listofapps";

		public static final String COLUMN_IDAPP = "listofapps_appid";
		public static final String COLUMN_IDPROFILE = "listofapps_profileid";
		public static final String COLUMN_SETTINGS = "listofapps_settings";
		public static final String COLUMN_STATS = "listofapps_stats";
	}
}
