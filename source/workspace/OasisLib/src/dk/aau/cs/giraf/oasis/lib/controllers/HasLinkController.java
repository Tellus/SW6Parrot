package dk.aau.cs.giraf.oasis.lib.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import dk.aau.cs.giraf.oasis.lib.metadata.HasLinkMetaData;
import dk.aau.cs.giraf.oasis.lib.models.HasLink;
import dk.aau.cs.giraf.oasis.lib.models.Media;

/**
 * Has link controller
 * @author Admin
 *
 */
class HasLinkController {

	private Context _context;
	private String[] columns = new String[] { 
			HasLinkMetaData.Table.COLUMN_IDMEDIA, 
			HasLinkMetaData.Table.COLUMN_IDSUBMEDIA};

	/**
	 * Constructor
	 * @param context
	 */
	public HasLinkController(Context context) {
		_context = context;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//METHODS TO CALL
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//	/**
//	 * Clear has link
//	 * @return Rows
//	 */
//	public int clearHasLinkTable() {
//		return _context.getContentResolver().delete(HasLinkMetaData.CONTENT_URI, null, null);
//	}

	/**
	 * Remove has link by media id
	 * @param idMedia the media id to remove haslink by
	 * @return Rows affected
	 */
	public int removeHasLinkByMediaId(long idMedia) {
		return _context.getContentResolver().delete(HasLinkMetaData.CONTENT_URI, 
					HasLinkMetaData.Table.COLUMN_IDMEDIA + " = '" + idMedia + "'", null);
	}
	/**
	 * Remove has link by submedia id
	 * @param idSubMedia the submedia id to remove haslink by
	 * @return Rows affected
	 */
	public int removeHasLinkBySubMediaId(long idSubMedia) {
		return _context.getContentResolver().delete(HasLinkMetaData.CONTENT_URI, 
					HasLinkMetaData.Table.COLUMN_IDSUBMEDIA + " = '" + idSubMedia + "'", null);
	}

	/**
	 * Remove has link
	 * @param hasLink HasLink to remove
	 * @return Rows affected
	 */
	public int removeHasLink(HasLink hasLink) {
		if (hasLink == null) {
			return -1;
		}
		
		return _context.getContentResolver().delete(HasLinkMetaData.CONTENT_URI, 
				HasLinkMetaData.Table.COLUMN_IDMEDIA + " = '" + hasLink.getIdMedia() + "' AND " +
						HasLinkMetaData.Table.COLUMN_IDSUBMEDIA + " = '" + hasLink.getIdSubMedia() + "'", null);
	}

	/**
	 * Insert has link
	 * @param hl Has link
	 * @return 0 for success or -1 for error
	 */
	public int insertHasLink(HasLink hl) {
		if (hl == null) {
			return -1;
		}
		
		ContentValues cv = getContentValues(hl);
		_context.getContentResolver().insert(HasLinkMetaData.CONTENT_URI, cv);

		return 0;
	}
	
	/**
	 * Modify has link
	 * @param hl Has link
	 * @return Rows
	 */
	public int modifyHasLink(HasLink hl) {
		if (hl == null) {
			return -1;
		}
		
		ContentValues cv = getContentValues(hl);
		return _context.getContentResolver().update(HasLinkMetaData.CONTENT_URI, cv, 
				HasLinkMetaData.Table.COLUMN_IDMEDIA + " = '" + hl.getIdMedia() + "' AND " +
						HasLinkMetaData.Table.COLUMN_IDSUBMEDIA + " = '" + hl.getIdSubMedia() + "'", null);
	}

	/**
	 * Get has links
	 * @return List of has links
	 */
	public List<HasLink> getHasLinks() {
		List<HasLink> list = new ArrayList<HasLink>();

		Cursor c = _context.getContentResolver().query(HasLinkMetaData.CONTENT_URI, columns, null, null, null);

		if (c != null) {
			list = cursorToHasLinkList(c);

			c.close();
		}

		return list;
	}

	/**
	 * Get sub media by media
	 * @param media Media
	 * @return List of has links
	 */
	public List<HasLink> getSubMediaByMedia(Media media) {
		List<HasLink> list = new ArrayList<HasLink>();
		
		if (media == null) {
			return list;
		}

		Cursor c = _context.getContentResolver().query(HasLinkMetaData.CONTENT_URI, columns, 
				HasLinkMetaData.Table.COLUMN_IDMEDIA + " = '" + media.getId() + "'", null, null);

		if (c != null) {
			list = cursorToHasLinkList(c);
			c.close();
		}

		return list;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//PRIVATE METHODS - keep out!
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Cursor to has link list
	 * @param cursor Cursor
	 * @return List of has links
	 */
	private List<HasLink> cursorToHasLinkList(Cursor cursor) {
		List<HasLink> list = new ArrayList<HasLink>();

		if(cursor.moveToFirst()) {
			while(!cursor.isAfterLast()) {
				list.add(cursorToHasLink(cursor));
				cursor.moveToNext();
			}
		}

		return  list;
	}

	/**
	 * Cursor
	 * @param cursor Cursor
	 * @return Has link
	 */
	private HasLink cursorToHasLink(Cursor cursor) {
		HasLink hl = new HasLink();
		hl.setIdMedia(cursor.getLong(cursor.getColumnIndex(HasLinkMetaData.Table.COLUMN_IDMEDIA)));
		hl.setIdSubMedia(cursor.getLong(cursor.getColumnIndex(HasLinkMetaData.Table.COLUMN_IDSUBMEDIA)));
		return hl;
	}

	/**
	 * Content values
	 * @param hl Has link
	 * @return Content values
	 */
	private ContentValues getContentValues(HasLink hl) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(HasLinkMetaData.Table.COLUMN_IDMEDIA, hl.getIdMedia());
		contentValues.put(HasLinkMetaData.Table.COLUMN_IDSUBMEDIA, hl.getIdSubMedia());
		return contentValues;
	}
}