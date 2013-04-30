package dk.aau.cs.giraf.oasis.lib.metadata;

import android.net.Uri;
import android.provider.BaseColumns;

public class HasLinkMetaData {

	public static final Uri CONTENT_URI = Uri.parse("content://dk.aau.cs.giraf.oasis.localdb.AutismProvider/haslink");

	public class Table implements BaseColumns {
		public static final String TABLE_NAME = "tbl_apps";

		public static final String COLUMN_IDMEDIA = "haslink_mediaid";
		public static final String COLUMN_IDSUBMEDIA = "haslink_submediaid";
	}
}
