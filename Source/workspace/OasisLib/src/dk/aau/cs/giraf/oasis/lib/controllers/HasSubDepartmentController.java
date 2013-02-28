package dk.aau.cs.giraf.oasis.lib.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import dk.aau.cs.giraf.oasis.lib.metadata.HasSubDepartmentMetaData;
import dk.aau.cs.giraf.oasis.lib.models.Department;
import dk.aau.cs.giraf.oasis.lib.models.HasSubDepartment;

/**
 * Has sub department controller
 * @author Admin
 *
 */
class HasSubDepartmentController {

	private Context _context;
	private String[] columns = new String[] { 
			HasSubDepartmentMetaData.Table.COLUMN_IDDEPARTMENT, 
			HasSubDepartmentMetaData.Table.COLUMN_IDSUBDEPARTMENT};

	/**
	 * Constructor
	 * @param context
	 */
	public HasSubDepartmentController(Context context) {
		_context = context;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//METHODS TO CALL
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//	/**
//	 * Clear has sub department
//	 * @return Rows
//	 */
//	public int clearHasSubDepartmentTable() {
//		return _context.getContentResolver().delete(HasSubDepartmentMetaData.CONTENT_URI, null, null);
//	}

	/**
	 * Remove HasSubDeparments by subDepartment id
	 * @param idSubDepartment the subdepartment id to remove hasSubDepartments by
	 * @return Rows affected
	 */
	public int removeHasSubDepartmentsBySubDepartmentId(long idSubDepartment) {
		return _context.getContentResolver().delete(HasSubDepartmentMetaData.CONTENT_URI, 
					HasSubDepartmentMetaData.Table.COLUMN_IDSUBDEPARTMENT + " = '" + idSubDepartment + "'", null);
	}
	/**
	 * Remove HasSubDeparments by department id
	 * @param idDepartment the department id to remove hasSubDepartments by
	 * @return Rows affected
	 */
	public int removeHasSubDepartmentsByDepartmentId(long idDepartment) {
		return _context.getContentResolver().delete(HasSubDepartmentMetaData.CONTENT_URI, 
					HasSubDepartmentMetaData.Table.COLUMN_IDDEPARTMENT + " = '" + idDepartment + "'", null);
	}
	/**
	 * Remove HasSubDeparment
	 * @param hasSubDepartment HasSubDepartment
	 * @return Rows affected
	 */
	public int removeHasSubDepartment(HasSubDepartment hasSubDepartment) {
		if (hasSubDepartment == null) {
			return -1;
		}
		
		return _context.getContentResolver().delete(HasSubDepartmentMetaData.CONTENT_URI, 
				HasSubDepartmentMetaData.Table.COLUMN_IDSUBDEPARTMENT + " = '" + hasSubDepartment.getIdSubDepartment() + "' AND " +
						HasSubDepartmentMetaData.Table.COLUMN_IDDEPARTMENT + " = '" + hasSubDepartment.getIdDepartment() + "'", null);
	}

	/**
	 * Insert has sub department
	 * @param hsd Has sub department
	 * @return 0
	 */
	public int insertHasSubDepartment(HasSubDepartment hsd) {
		if (hsd == null) {
			return -1;
		}
		
		ContentValues cv = getContentValues(hsd);
		_context.getContentResolver().insert(HasSubDepartmentMetaData.CONTENT_URI, cv);

		return 0;
	}
	
	/**
	 * Moodify has sub department
	 * @param hsd Has sub department
	 * @return rows
	 */
	public int modifyHasSubDepartment(HasSubDepartment hsd) {
		if (hsd == null) {
			return -1;
		}
		
		ContentValues cv = getContentValues(hsd);
		return _context.getContentResolver().update(HasSubDepartmentMetaData.CONTENT_URI, cv, 
				HasSubDepartmentMetaData.Table.COLUMN_IDDEPARTMENT + " = '" + hsd.getIdDepartment() + "' AND " +
				HasSubDepartmentMetaData.Table.COLUMN_IDSUBDEPARTMENT + " = '" + hsd.getIdSubDepartment() + "'", null);
	}
	
	/**
	 * Get has sub departments
	 * @return List of has sub departments
	 */
	public List<HasSubDepartment> getHasSubDepartments() {
		List<HasSubDepartment> hsdList = new ArrayList<HasSubDepartment>();

		Cursor c = _context.getContentResolver().query(HasSubDepartmentMetaData.CONTENT_URI, columns, null, null, null);

		if (c != null) {
			hsdList = cursorToHasSubDepartmentList(c);

			c.close();
		}

		return hsdList;
	}

	/**
	 * Get sub departments by department
	 * @param dep Department
	 * @return List of has sub departments
	 */
	public List<HasSubDepartment> getSubDepartmentsByDepartment(Department dep) {
		List<HasSubDepartment> hsdList= new ArrayList<HasSubDepartment>();

		if (dep == null) {
			return hsdList;
		}
		
		Cursor c = _context.getContentResolver().query(HasSubDepartmentMetaData.CONTENT_URI, columns, 
				HasSubDepartmentMetaData.Table.COLUMN_IDDEPARTMENT + " = '" + dep.getId() + "'", null, null);

		if (c != null) {
			hsdList = cursorToHasSubDepartmentList(c);

			c.close();
		}

		return hsdList;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//PRIVATE METHODS - keep out!
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Cursor to has sub department list
	 * @param cursor Cursor
	 * @return List of has sub departments
	 */
	private List<HasSubDepartment> cursorToHasSubDepartmentList(Cursor cursor) {
		List<HasSubDepartment> list = new ArrayList<HasSubDepartment>();

		if(cursor.moveToFirst()) {
			while(!cursor.isAfterLast()) {
				list.add(cursorToHasSubDepartment(cursor));
				cursor.moveToNext();
			}
		}

		return  list;
	}

	/**
	 * Cursor to has sub departments
	 * @param cursor Cursor
	 * @return Has sub department
	 */
	private HasSubDepartment cursorToHasSubDepartment(Cursor cursor) {
		HasSubDepartment hsd = new HasSubDepartment();
		hsd.setIdDepartment(cursor.getLong(cursor.getColumnIndex(HasSubDepartmentMetaData.Table.COLUMN_IDDEPARTMENT)));
		hsd.setIdSubDepartment(cursor.getLong(cursor.getColumnIndex(HasSubDepartmentMetaData.Table.COLUMN_IDSUBDEPARTMENT)));
		return hsd;
	}

	/**
	 * Content values
	 * @param hsd Has sub department
	 * @return Content values
	 */
	private ContentValues getContentValues(HasSubDepartment hsd) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(HasSubDepartmentMetaData.Table.COLUMN_IDDEPARTMENT, hsd.getIdDepartment());
		contentValues.put(HasSubDepartmentMetaData.Table.COLUMN_IDSUBDEPARTMENT, hsd.getIdSubDepartment());
		return contentValues;
	}
}