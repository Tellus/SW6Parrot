package dk.aau.cs.giraf.oasis.lib.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import dk.aau.cs.giraf.oasis.lib.metadata.HasGuardianMetaData;
import dk.aau.cs.giraf.oasis.lib.models.HasGuardian;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

/**
 * Has guardian controller
 * @author Admin
 *
 */
class HasGuardianController {

	private Context _context;
	private String[] columns = new String[] { 
			HasGuardianMetaData.Table.COLUMN_IDGUARDIAN, 
			HasGuardianMetaData.Table.COLUMN_IDCHILD};

	/**
	 * Constructor
	 * @param context
	 */
	public HasGuardianController(Context context) {
		_context = context;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//METHODS TO CALL
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//	/**
//	 * Clear has guardian
//	 * @return Rows
//	 */
//	public int clearHasGuardianTable() {
//		return _context.getContentResolver().delete(HasGuardianMetaData.CONTENT_URI, null, null);
//	}
	
	/**
	 * Remove HasGuardians by profile
	 * @param profile Profile to remove hasGuardians by
	 * @return Rows affected
	 */
	public int removeHasGuardiansByProfile(Profile profile) {
		if (profile == null) {
			return -1;
		}
		
		int rows = 0;
		String table = "";
		switch ((int)profile.getPRole()) {
		case 2: 
			table = HasGuardianMetaData.Table.COLUMN_IDGUARDIAN;
			break;
		case 3:
			table = HasGuardianMetaData.Table.COLUMN_IDCHILD;
		default: 
			table = "other";
			break;
		}
		
		if (!table.equals("other")) {
			rows += _context.getContentResolver().delete(HasGuardianMetaData.CONTENT_URI, 
					table + " = '" + profile.getId() + "'", null);
		}
		
		return rows;
	}
	
	/**
	 * Remove HasGuardian
	 * @param hasGuardian HasGuardian to remove
	 * @return Rows affected
	 */
	public int removeHasGuardian(HasGuardian hasGuardian) {
		if (hasGuardian == null) {
			return -1;
		}
		
		return _context.getContentResolver().delete(HasGuardianMetaData.CONTENT_URI,
				HasGuardianMetaData.Table.COLUMN_IDGUARDIAN + " = '" + hasGuardian.getIdGuardian() + "' AND " +
						HasGuardianMetaData.Table.COLUMN_IDCHILD + " = '" + hasGuardian.getIdChild() + "'", null);						
	}

	/**
	 * Insert has guardian
	 * @param hg Has guardian
	 * @return 0
	 */
	public int insertHasGuardian(HasGuardian hg) {
		if (hg == null) {
			return -1;
		}
		
		ContentValues cv = getContentValues(hg);
		_context.getContentResolver().insert(HasGuardianMetaData.CONTENT_URI, cv);

		return 0;
	}

	/**
	 * Modify has guardians
	 * @param hg Has guardian
	 * @return Rows affected
	 */
	public int modifyHasGuardian(HasGuardian hg) {
		if (hg == null) {
			return -1;
		}
		
		ContentValues cv = getContentValues(hg);
		return _context.getContentResolver().update(HasGuardianMetaData.CONTENT_URI, cv, 
				HasGuardianMetaData.Table.COLUMN_IDGUARDIAN + " = '" + hg.getIdGuardian() + "' AND " +
						HasGuardianMetaData.Table.COLUMN_IDCHILD + " = '" + hg.getIdChild() + "'", null);
	}
	
	/**
	 * Get has guardian
	 * @return List of has guardians
	 */
	public List<HasGuardian> getHasGuardians() {
		List<HasGuardian> hgList = new ArrayList<HasGuardian>();
		
		Cursor c = _context.getContentResolver().query(HasGuardianMetaData.CONTENT_URI, columns, null, null, null);

		if (c != null) {
			hgList = cursorToHasGuardianList(c);
			
			c.close();
		}

		return hgList;
	}
	
	/**
	 * Get children by guardian
	 * @param guardian Guardian profile
	 * @return List of has guardians
	 */
	public List<HasGuardian> getChildrenByGuardian(Profile guardian) {
		List<HasGuardian> hgList = new ArrayList<HasGuardian>();

		if (guardian == null) {
			return hgList;
		}
		
		Cursor c = _context.getContentResolver().query(HasGuardianMetaData.CONTENT_URI, columns, 
				HasGuardianMetaData.Table.COLUMN_IDGUARDIAN + " = '" + guardian.getId() + "'", null, null);

		if (c != null) {
			hgList = cursorToHasGuardianList(c);
			c.close();
		}

		return hgList;
	}
	/**
	 * Get guardians by child
	 * @param child Child profile
	 * @return List of has guardians
	 */
	public List<HasGuardian> getGuardiansByChild(Profile child) {
		List<HasGuardian> hgList = new ArrayList<HasGuardian>();
		
		if (child == null) {
			return hgList;
		}
		
		Cursor c = _context.getContentResolver().query(HasGuardianMetaData.CONTENT_URI, columns, 
				HasGuardianMetaData.Table.COLUMN_IDCHILD + " = '" + child.getId() + "'", null, null);

		if (c != null) {
			hgList = cursorToHasGuardianList(c);
			c.close();
		}

		return hgList;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//PRIVATE METHODS - keep out!
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Cursor to has guardian list
	 * @param cursor Cursor
	 * @return List of has guardians
	 */
	private List<HasGuardian> cursorToHasGuardianList(Cursor cursor) {
		List<HasGuardian> hgList = new ArrayList<HasGuardian>();
		
		if(cursor.moveToFirst()) {
			while(!cursor.isAfterLast()) {
				hgList.add(cursorToHasGuardian(cursor));
				cursor.moveToNext();
			}
		}
		
		return  hgList;
	}

	/**
	 * Cursor to has guardian
	 * @param cursor Cursor
	 * @return Has guardian
	 */
	private HasGuardian cursorToHasGuardian(Cursor cursor) {
		HasGuardian hg = new HasGuardian();
		hg.setIdGuardian(cursor.getLong(cursor.getColumnIndex(HasGuardianMetaData.Table.COLUMN_IDGUARDIAN)));
		hg.setIdChild(cursor.getLong(cursor.getColumnIndex(HasGuardianMetaData.Table.COLUMN_IDCHILD)));
		return hg;
	}

	/**
	 * Content values
	 * @param hg Has guardian
	 * @return Content values
	 */
	private ContentValues getContentValues(HasGuardian hg) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(HasGuardianMetaData.Table.COLUMN_IDGUARDIAN, hg.getIdGuardian());
		contentValues.put(HasGuardianMetaData.Table.COLUMN_IDCHILD, hg.getIdChild());
		return contentValues;
	}
}