package dk.aau.cs.giraf.oasis.lib.metadata;

import android.net.Uri;
import android.provider.BaseColumns;

public class MediaProfileAccessMetaData {

	public static final Uri CONTENT_URI = Uri.parse("content://dk.aau.cs.giraf.oasis.localdb.AutismProvider/mediaprofileaccess");

	public class Table implements BaseColumns {
		public static final String TABLE_NAME = "tbl_mediaprofileaccess";

		public static final String COLUMN_IDPROFILE = "mediaprofileaccess_profileid";
		public static final String COLUMN_IDMEDIA = "mediaprofileaccess_mediaid";
	}
}
