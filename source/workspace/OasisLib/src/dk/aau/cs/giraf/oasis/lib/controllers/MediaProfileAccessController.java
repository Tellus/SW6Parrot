package dk.aau.cs.giraf.oasis.lib.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import dk.aau.cs.giraf.oasis.lib.metadata.MediaProfileAccessMetaData;
import dk.aau.cs.giraf.oasis.lib.models.MediaProfileAccess;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

/**
 * Media profile access controller
 * @author Admin
 *
 */
class MediaProfileAccessController {

	private Context _context;
	private String[] columns = new String[] { 
			MediaProfileAccessMetaData.Table.COLUMN_IDMEDIA, 
			MediaProfileAccessMetaData.Table.COLUMN_IDPROFILE};

	/**
	 * Constructor
	 * @param context
	 */
	public MediaProfileAccessController(Context context) {
		_context = context;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//METHODS TO CALL
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//	/**
//	 * Clear media profile access
//	 * @return Rows
//	 */
//	public int clearMediaProfileAccessTable() {
//		return _context.getContentResolver().delete(MediaProfileAccessMetaData.CONTENT_URI, null, null);
//	}
	
	/**
	 * Remove mediaProfileAccess by profile id
	 * @param profileId profile id to remove MediaProfileAccess by
	 * @return Rows affected
	 */
	public int removeMediaProfileAccessByProfileId(long profileId) {
		return _context.getContentResolver().delete(MediaProfileAccessMetaData.CONTENT_URI, 
				MediaProfileAccessMetaData.Table.COLUMN_IDPROFILE + " = '" + profileId + "'", null);
	}
	
	/**
	 * Remove mediaProfileAccess by media id
	 * @param mediaId media id to remove MediaProfileAccess by
	 * @return Rows affected
	 */
	public int removeMediaProfileAccessByMediaId(long mediaId) {
		return _context.getContentResolver().delete(MediaProfileAccessMetaData.CONTENT_URI, 
				MediaProfileAccessMetaData.Table.COLUMN_IDMEDIA + " = '" + mediaId + "'", null);
	}
	
	/**
	 * Remove mediaProfileAccess
	 * @param mediaProfileAccess MediaProfileAccess to remove
	 * @return Rows affected
	 */
	public int removeMediaProfileAccess(MediaProfileAccess mediaProfileAccess) {
		if (mediaProfileAccess == null) {
			return -1;
		}
		
		return _context.getContentResolver().delete(MediaProfileAccessMetaData.CONTENT_URI, 
				MediaProfileAccessMetaData.Table.COLUMN_IDMEDIA + " = '" + mediaProfileAccess.getIdMedia() + "' AND " + 
						MediaProfileAccessMetaData.Table.COLUMN_IDPROFILE + " = '" + mediaProfileAccess.getIdProfile() + "'", null);
	}

	/**
	 * Insert media profile access
	 * @param mpa Media profile access
	 * @return 0
	 */
	public int insertMediaProfileAccess(MediaProfileAccess mpa) {
		if (mpa == null) {
			return -1;
		}
		
		ContentValues cv = getContentValues(mpa);
		_context.getContentResolver().insert(MediaProfileAccessMetaData.CONTENT_URI, cv);

		return 0;
	}
	
	/**
	 * Modify media profile access
	 * @param mpa Media profile access
	 * @return Rows
	 */
	public int modifyMediaProfileAccess(MediaProfileAccess mpa) {
		if (mpa == null) {
			return -1;
		}
		
		ContentValues cv = getContentValues(mpa);
		return _context.getContentResolver().update(MediaProfileAccessMetaData.CONTENT_URI, cv, 
				MediaProfileAccessMetaData.Table.COLUMN_IDMEDIA + " = '" + mpa.getIdMedia() + "' AND " +
				MediaProfileAccessMetaData.Table.COLUMN_IDPROFILE + " = '" + mpa.getIdProfile() + "'", null);
	}
	/**
	 * Get media profile accesses
	 * @return List of media profile access
	 */
	public List<MediaProfileAccess> getMediaProfileAccesses() {
		List<MediaProfileAccess> list = new ArrayList<MediaProfileAccess>();

		Cursor c = _context.getContentResolver().query(MediaProfileAccessMetaData.CONTENT_URI, columns, null, null, null);

		if (c != null) {
			list = cursorToMediaProfileAccessList(c);

			c.close();
		}

		return list;
	}

	/**
	 * Get media by profile
	 * @param profile Profile
	 * @return List of media profile access
	 */
	public List<MediaProfileAccess> getMediaProfileAccessByProfile(Profile profile) {
		List<MediaProfileAccess> list = new ArrayList<MediaProfileAccess>();
		
		if (profile == null) {
			return list;
		}

		Cursor c = _context.getContentResolver().query(MediaProfileAccessMetaData.CONTENT_URI, columns, 
				MediaProfileAccessMetaData.Table.COLUMN_IDPROFILE + " = '" + profile.getId() + "'", null, null);

		if (c != null) {
			list = cursorToMediaProfileAccessList(c);

			c.close();
		}

		return list;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//PRIVATE METHODS - keep out!
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Cursor to media profile access list
	 * @param cursor Cursor
	 * @return List of media profile access
	 */
	private List<MediaProfileAccess> cursorToMediaProfileAccessList(Cursor cursor) {
		List<MediaProfileAccess> list = new ArrayList<MediaProfileAccess>();

		if(cursor.moveToFirst()) {
			while(!cursor.isAfterLast()) {
				list.add(cursorToMediaProfileAccess(cursor));
				cursor.moveToNext();
			}
		}

		return  list;
	}

	/**
	 * Cursor to media profile access
	 * @param cursor Cursor
	 * @return Media profile access
	 */
	private MediaProfileAccess cursorToMediaProfileAccess(Cursor cursor) {
		MediaProfileAccess mpa = new MediaProfileAccess();
		mpa.setIdMedia(cursor.getLong(cursor.getColumnIndex(MediaProfileAccessMetaData.Table.COLUMN_IDMEDIA)));
		mpa.setIdProfile(cursor.getLong(cursor.getColumnIndex(MediaProfileAccessMetaData.Table.COLUMN_IDPROFILE)));
		return mpa;
	}

	/**
	 * Content values
	 * @param mpa Media profile access
	 * @return Content values
	 */
	private ContentValues getContentValues(MediaProfileAccess mpa) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(MediaProfileAccessMetaData.Table.COLUMN_IDMEDIA, mpa.getIdMedia());
		contentValues.put(MediaProfileAccessMetaData.Table.COLUMN_IDPROFILE, mpa.getIdProfile());
		return contentValues;
	}
}