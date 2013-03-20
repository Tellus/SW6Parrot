package dk.aau.cs.giraf.oasis.localdb;

import android.net.Uri;
import android.provider.BaseColumns;

public class TagsMetaData {

	public static final Uri CONTENT_URI = Uri.parse("content://dk.aau.cs.giraf.oasis.localdb.AutismProvider/tags");
	
	public static final String CONTENT_TYPE_TAGS_LIST = "vnd.android.cursor.dir/vnd.dk.tags";
	public static final String CONTENT_TYPE_TAG_ONE = "vnd.android.cursor.item/vnd.dk.tags";
	
	public class Table implements BaseColumns {
		public static final String TABLE_NAME = "tbl_tags";
		
		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_CAPTION = "tags_caption";
	}
}

