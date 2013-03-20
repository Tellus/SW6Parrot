package dk.aau.cs.giraf.oasis.lib.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import dk.aau.cs.giraf.oasis.lib.metadata.HasDepartmentMetaData;
import dk.aau.cs.giraf.oasis.lib.models.Department;
import dk.aau.cs.giraf.oasis.lib.models.HasDepartment;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

/**
 * Has department controller
 * @author Admin
 *
 */
class HasDepartmentController {

	private Context _context;
	private String[] columns = new String[] { 
			HasDepartmentMetaData.Table.COLUMN_IDPROFILE, 
			HasDepartmentMetaData.Table.COLUMN_IDDEPARTMENT};

	/**
	 * Constructor
	 * @param context
	 */
	public HasDepartmentController(Context context) {
		_context = context;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//METHODS TO CALL
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//	/**
//	 * Clear has department
//	 * @return Rows
//	 */
//	public int clearHasDepartmentTable() {
//		return _context.getContentResolver().delete(HasDepartmentMetaData.CONTENT_URI, null, null);
//	}
	
	/**
	 * Remove HasDepartment by department id
	 * @param departmentId department id to remove hasDepartment by
	 * @return Rows affected
	 */
	public int removeHasDepartmentByDepartmentId(long departmentId) {
		return _context.getContentResolver().delete(HasDepartmentMetaData.CONTENT_URI, 
				HasDepartmentMetaData.Table.COLUMN_IDDEPARTMENT + " = '" + departmentId + "'", null);
	}
	
	/**
	 * Remove HasDepartment by profile id
	 * @param profileId profile id to remove hasDepartment by
	 * @return Rows affected
	 */
	public int removeHasDepartmentByProfileId(long profileId) {
		return _context.getContentResolver().delete(HasDepartmentMetaData.CONTENT_URI, 
				HasDepartmentMetaData.Table.COLUMN_IDPROFILE + " = '" + profileId + "'", null);
	}
	
	/**
	 * Remove HasDepartment
	 * @param hasDepartment HasDepartment to remove
	 * @return Rows affected
	 */
	public int removeHasDepartment(HasDepartment hasDepartment) {
		if (hasDepartment == null) {
			return -1;
		}
		
		return _context.getContentResolver().delete(HasDepartmentMetaData.CONTENT_URI, 
				HasDepartmentMetaData.Table.COLUMN_IDPROFILE + " = '" + hasDepartment.getIdProfile() + "' AND " +
						HasDepartmentMetaData.Table.COLUMN_IDDEPARTMENT + " = '" + hasDepartment.getIdDepartment() + "'", null);
	}

	/**
	 * Insert has department
	 * @param hd department to insert
	 * @return 0
	 */
	public int insertHasDepartment(HasDepartment hd) {
		if (hd == null) {
			return -1;
		}
		
		ContentValues cv = getContentValues(hd);
		_context.getContentResolver().insert(HasDepartmentMetaData.CONTENT_URI, cv);

		return 0;
	}
	
	/**
	 * Modify has department
	 * @param hd Has department
	 * @return Rows
	 */
	public int modifyHasDepartment(HasDepartment hd) {
		if (hd == null) {
			return -1;
		}
		
		ContentValues cv = getContentValues(hd);
		return _context.getContentResolver().update(HasDepartmentMetaData.CONTENT_URI, cv, 
				HasDepartmentMetaData.Table.COLUMN_IDDEPARTMENT + " = '" + hd.getIdDepartment() + "' AND " +
				HasDepartmentMetaData.Table.COLUMN_IDPROFILE + " = '" + hd.getIdProfile() + "'", null);
	}

	/**
	 * Get had department
	 * @return List of Has departments
	 */
	public List<HasDepartment> getHasDepartments() {
		List<HasDepartment> list = new ArrayList<HasDepartment>();

		Cursor c = _context.getContentResolver().query(HasDepartmentMetaData.CONTENT_URI, columns, null, null, null);

		if (c != null) {
			list = cursorToHasDepartmentList(c);

			c.close();
		}

		return list;
	}

	/**
	 * Get profiles by department
	 * @param dep Department
	 * @return List of Has departments
	 */
	public List<HasDepartment> getProfilesByDepartment(Department dep) {
		List<HasDepartment> list = new ArrayList<HasDepartment>();
		
		if (dep == null) {
			return list;
		}
		
		Cursor c = _context.getContentResolver().query(HasDepartmentMetaData.CONTENT_URI, columns, 
				HasDepartmentMetaData.Table.COLUMN_IDDEPARTMENT + " = '" + dep.getId() + "'", null, null);

		if (c != null) {
			list = cursorToHasDepartmentList(c);
			c.close();
		}

		return list;
	}
	
	/**
	 * Get departments by profile
	 * @param profile Profile
	 * @return List of Has departments
	 */
	public List<HasDepartment> getHasDepartmentsByProfile(Profile profile) {
		List<HasDepartment> list = new ArrayList<HasDepartment>();
		
		if (profile == null) {
			return list;
		}

		Cursor c = _context.getContentResolver().query(HasDepartmentMetaData.CONTENT_URI, columns, 
				HasDepartmentMetaData.Table.COLUMN_IDPROFILE + " = '" + profile.getId() + "'", null, null);

		if (c != null) {
			list = cursorToHasDepartmentList(c);

			c.close();
		}

		return list;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//PRIVATE METHODS - keep out!
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Cyrsor to has department list
	 * @param cursor Cursor
	 * @return List of has departments
	 */
	private List<HasDepartment> cursorToHasDepartmentList(Cursor cursor) {
		List<HasDepartment> list = new ArrayList<HasDepartment>();

		if(cursor.moveToFirst()) {
			while(!cursor.isAfterLast()) {
				list.add(cursorToHasDepartment(cursor));
				cursor.moveToNext();
			}
		}

		return  list;
	}

	/**
	 * Cursor to has department
	 * @param cursor Cursor
	 * @return Has department
	 */
	private HasDepartment cursorToHasDepartment(Cursor cursor) {
		HasDepartment hd = new HasDepartment();
		hd.setIdDepartment(cursor.getLong(cursor.getColumnIndex(HasDepartmentMetaData.Table.COLUMN_IDDEPARTMENT)));
		hd.setIdProfile(cursor.getLong(cursor.getColumnIndex(HasDepartmentMetaData.Table.COLUMN_IDPROFILE)));
		return hd;
	}

	/**
	 * Content values
	 * @param hd Has department
	 * @return Content values
	 */
	private ContentValues getContentValues(HasDepartment hd) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(HasDepartmentMetaData.Table.COLUMN_IDDEPARTMENT, hd.getIdDepartment());
		contentValues.put(HasDepartmentMetaData.Table.COLUMN_IDPROFILE, hd.getIdProfile());
		return contentValues;
	}
}