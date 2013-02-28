package dk.aau.cs.giraf.oasis.lib.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import dk.aau.cs.giraf.oasis.lib.metadata.TagsMetaData;
import dk.aau.cs.giraf.oasis.lib.models.Tag;

/**
 * Tags helper
 * @author Admin
 *
 */
public class TagsHelper {

	private Context _context;
	private HasTagController ht;
	private String[] columns = new String[] { 
			TagsMetaData.Table.COLUMN_ID, 
			TagsMetaData.Table.COLUMN_CAPTION};

	/**
	 * Constructor
	 * @param context
	 */
	public TagsHelper(Context context) {
		_context = context;
		ht = new HasTagController(_context);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//METHODS TO CALL
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//	/**
//	 * Clear tags
//	 * @return Rows
//	 */
//	public int clearTagsTable() {
//		return _context.getContentResolver().delete(TagsMetaData.CONTENT_URI, null, null);
//	}
	
	/**
	 * Remove tag
	 * @param tag Tag to remove
	 * @return Rows affected
	 */
	public int removeTag(Tag tag) {
		if (tag == null) {
			return -1;
		}
		
		int rows = 0;
		
		rows += ht.removeHasTagByTagId(tag.getId());
		rows += _context.getContentResolver().delete(TagsMetaData.CONTENT_URI, 
					TagsMetaData.Table.COLUMN_ID + " = '" + tag.getId() + "'", null);
		
		return rows;
	}
	
	/**
	 * Insert tag
	 * @param tag Tag
	 * @return Tag id
	 */
	public long insertTag(Tag tag) {
		if (tag == null) {
			return -1;
		}
		
		long id = 0;
		
		Cursor c = _context.getContentResolver().query(TagsMetaData.CONTENT_URI, columns, TagsMetaData.Table.COLUMN_CAPTION + " = '" + tag.getCaption() + "'", null, null);
		
		if (c != null) {
			if (c.moveToFirst()) {
				id = c.getLong(c.getColumnIndex(TagsMetaData.Table.COLUMN_ID));
			} else {
				ContentValues cv = getContentValues(tag);
				Uri uri = _context.getContentResolver().insert(TagsMetaData.CONTENT_URI, cv);
				id = Integer.parseInt(uri.getPathSegments().get(1));
			}
			c.close();
		} 
		return id;
	}

	/**
	 * Modify tag
	 * @param tag Tag
	 * @return Rows
	 */
	public int modifyTag(Tag tag) {
		if (tag == null) {
			return -1;
		}
		
		Uri uri = ContentUris.withAppendedId(TagsMetaData.CONTENT_URI, tag.getId());
		ContentValues cv = getContentValues(tag);
		return _context.getContentResolver().update(uri, cv, null, null);
	}

	/**
	 * Get tags
	 * @return List of tags
	 */
	public List<Tag> getTags() {
		List<Tag> tags = new ArrayList<Tag>();

		Cursor c = _context.getContentResolver().query(TagsMetaData.CONTENT_URI, columns, null, null, null);

		if (c != null) {
			tags = cursorToTagList(c);
			c.close();
		}

		return tags;
	}
	
	/**
	 * Get tags by caption
	 * @param name Caption
	 * @return List of tags
	 */
	public List<Tag> getTagsByCaption(String name) {
		List<Tag> tags = new ArrayList<Tag>();
		
		if (name == null) {
			return tags;
		}
		
		Cursor c = _context.getContentResolver().query(TagsMetaData.CONTENT_URI, columns, TagsMetaData.Table.COLUMN_CAPTION + " LIKE '%" + name + "%'", null, null);

		if (c != null) {
			tags = cursorToTagList(c);
			c.close();
		}

		return tags;
	}
	
	/**
	 * Get tag by id
	 * @param id Id
	 * @return Tag
	 */
	public Tag getTagById(long id) {
		Tag tag = null;
		
		if (id <= 0) {
			return null;
		}
		
		Uri uri = ContentUris.withAppendedId(TagsMetaData.CONTENT_URI, id);
		
		Cursor c = _context.getContentResolver().query(uri, columns, null, null, null);

		if (c != null) {
			if (c.moveToFirst()) {
				tag = cursorToTag(c);
			}
			c.close();
		}

		return tag;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//PRIVATE METHODS - keep out!
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Cursor to Tags
	 * @param cursor Input cursor
	 * @return Output List of Tags
	 */
	private List<Tag> cursorToTagList(Cursor cursor) {
		List<Tag> tags = new ArrayList<Tag>();

		if(cursor.moveToFirst()) {
			while(!cursor.isAfterLast()) {
				tags.add(cursorToTag(cursor));
				cursor.moveToNext();
			}
		}


		return tags;
	}

	/**
	 * Cursor to tag
	 * @param cursor Cursor
	 * @return Tag
	 */
	private Tag cursorToTag(Cursor cursor) {
		Tag tag = new Tag();
		tag.setId(cursor.getLong(cursor.getColumnIndex(TagsMetaData.Table.COLUMN_ID)));
		tag.setCaption(cursor.getString(cursor.getColumnIndex(TagsMetaData.Table.COLUMN_CAPTION)));
		return tag;
	}

	/**
	 * Content values
	 * @param tag Tag
	 * @return Content values
	 */
	private ContentValues getContentValues(Tag tag) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(TagsMetaData.Table.COLUMN_CAPTION, tag.getCaption());
		return contentValues;
	}
}
