package dk.aau.cs.giraf.oasis.localdb;

import android.net.Uri;
import android.provider.BaseColumns;

public class HasTagMetaData {

	public static final Uri CONTENT_URI = Uri.parse("content://dk.aau.cs.giraf.oasis.localdb.AutismProvider/hastag");

	public static final String CONTENT_TYPE_HASTAGS_LIST = "vnd.android.cursor.dir/vnd.dk.hastag";
	public static final String CONTENT_TYPE_HASTAG_ONE = "vnd.android.cursor.item/vnd.dk.hastag";

	public class Table implements BaseColumns {
		public static final String TABLE_NAME = "tbl_hastag";

		public static final String COLUMN_IDMEDIA = "hastag_mediaid";
		public static final String COLUMN_IDTAG = "hastag_tagid";
	}
}
