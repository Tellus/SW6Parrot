package dk.aau.cs.giraf.oasis.lib.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import dk.aau.cs.giraf.oasis.lib.metadata.AuthUsersMetaData;
import dk.aau.cs.giraf.oasis.lib.models.AuthUser;

/**
 * Auth users controller
 * @author Admin
 *
 */
class AuthUsersController {

	private Context _context; 
	private String[] columns = new String[] { 
			AuthUsersMetaData.Table.COLUMN_ID, 
			AuthUsersMetaData.Table.COLUMN_CERTIFICATE,
			AuthUsersMetaData.Table.COLUMN_ROLE};

	/**
	 * Constructor
	 * @param context Current context
	 */
	public AuthUsersController(Context context) {
		_context = context;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//METHODS TO CALL
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//	/**
//	 * Clear auth users table
//	 * @return Rows
//	 */
//	public int clearAuthUsersTable() {
//		return _context.getContentResolver().delete(AuthUsersMetaData.CONTENT_URI, null, null);
//	}
	
	/**
	 * Remove AuthUser
	 * @param authUser AuthUser to remove
	 * @return Rows affected
	 */
	public int removeAuthUser(AuthUser authUser) {
		if (authUser == null) {
			return -1;
		}
		
		return _context.getContentResolver().delete(AuthUsersMetaData.CONTENT_URI, 
				AuthUsersMetaData.Table.COLUMN_ID + " = '" + authUser.getId() + "'", null);
	}

	/**
	 * Insert auth user
	 * @param role User role
	 * @return Profile id
	 */
	public long insertAuthUser(long role) {
		List<AuthUser> authUsers = getAuthUsers();

		long id;
		if (authUsers.size() != 0) {
			id = authUsers.get(authUsers.size() - 1).getId() + 1;
		} else {
			id = 1;
		}
		
		AuthUser authUser = new AuthUser(id, getNewCertificate(), role);
		ContentValues cv = getContentValues(authUser);
		_context.getContentResolver().insert(AuthUsersMetaData.CONTENT_URI, cv);

		return id;
	}

	/**
	 * Set certificate
	 * @param certificate Certificate
	 * @param id Profile id
	 * @return Rows
	 */
	public int setCertificate(String certificate, long id) {
		if (certificate == null) {
			return -1;
		}
		
		Uri uri = ContentUris.withAppendedId(AuthUsersMetaData.CONTENT_URI, id);

		ContentValues cv = new ContentValues();
		cv.put(AuthUsersMetaData.Table.COLUMN_CERTIFICATE, certificate);
		return _context.getContentResolver().update(uri, cv, null, null);
	}

	/**
	 * Modify auth user
	 * @param authUser Auth user
	 * @return Rows
	 */
	public int modifyAuthUser(AuthUser authUser) {
		if (authUser == null) {
			return -1;
		}
		
		Uri uri = ContentUris.withAppendedId(AuthUsersMetaData.CONTENT_URI, authUser.getId());
		ContentValues cv = getContentValues(authUser);
		return _context.getContentResolver().update(uri, cv, null, null);
	}

	/**
	 * Get auth users
	 * @return List of auth users
	 */
	public List<AuthUser> getAuthUsers() {
		List<AuthUser> authUsers = new ArrayList<AuthUser>();

		Cursor c = _context.getContentResolver().query(AuthUsersMetaData.CONTENT_URI, columns, null, null, null);

		if (c != null) {
			authUsers = cursorToAuthUsers(c);
			c.close();
		}

		return authUsers;
	}

	/**
	 * Get certificates by id
	 * @param id Profile id
	 * @return List of strings
	 */
	public List<String> getCertificatesById(long id) {
		List<String> certificates = new ArrayList<String>();
		Uri uri = ContentUris.withAppendedId(AuthUsersMetaData.CONTENT_URI, id);

		Cursor c = _context.getContentResolver().query(uri, columns, null, null, null);

		if (c != null) {
			if (c.moveToFirst()) {
				while (!c.isAfterLast()) {
					certificates.add(c.getString(c.getColumnIndex(AuthUsersMetaData.Table.COLUMN_CERTIFICATE)));
					c.moveToNext();
				}
			}
			c.close();
		}

		return certificates;
	}

	/**
	 * Get id by certificate
	 * @param certificate Certificate
	 * @return Profile id
	 */
	public long getIdByCertificate(String certificate) {
		if (certificate == null) {
			return -1;
		}
		
		long id = -1;
		Cursor c = _context.getContentResolver().query(AuthUsersMetaData.CONTENT_URI, columns, AuthUsersMetaData.Table.COLUMN_CERTIFICATE + " = '" + certificate + "'", null, null);
		if (c != null) {
			if (c.moveToFirst()) {
				id = c.getLong(c.getColumnIndex(AuthUsersMetaData.Table.COLUMN_ID));
			}
			c.close();	
		}
		return id;
	}
	
	public AuthUser getAuthUserById(long id) {
		AuthUser authUser = null;
		
		Cursor c = _context.getContentResolver().query(AuthUsersMetaData.CONTENT_URI, columns, AuthUsersMetaData.Table.COLUMN_ID + " = '" + id + "'", null, null);
		if (c != null) {
			if (c.moveToFirst()) {
				authUser = cursorToAuthUser(c);
			}
			c.close();
		}

		return authUser;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//PRIVATE METHODS - keep out!
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Cursor to AuthUsers
	 * @param cursor Input cursor
	 * @return Output AuthUsers
	 */
	private List<AuthUser> cursorToAuthUsers(Cursor cursor) {
		List<AuthUser> authUsers = new ArrayList<AuthUser>();

		if(cursor.moveToFirst()) {
			while(!cursor.isAfterLast()) {
				authUsers.add(cursorToAuthUser(cursor));
				cursor.moveToNext();
			}
		}

		return authUsers;
	}

	/**
	 * Cursor to AuthUser
	 * @param cursor Input cursor
	 * @return Output AuthUser
	 */
	private AuthUser cursorToAuthUser(Cursor cursor) {
		AuthUser authUser = new AuthUser();
		authUser.setId(cursor.getLong(cursor.getColumnIndex(AuthUsersMetaData.Table.COLUMN_ID)));
		authUser.setCertificate(cursor.getString(cursor.getColumnIndex(AuthUsersMetaData.Table.COLUMN_CERTIFICATE)));
		authUser.setaRole(cursor.getLong(cursor.getColumnIndex(AuthUsersMetaData.Table.COLUMN_ROLE)));
		return authUser;
	}

	/**
	 * @param authUser the authUser to put in the database
	 * @return the contentValues
	 */
	private ContentValues getContentValues(AuthUser authUser) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(AuthUsersMetaData.Table.COLUMN_ID, authUser.getId());
		contentValues.put(AuthUsersMetaData.Table.COLUMN_CERTIFICATE, authUser.getCertificate());
		contentValues.put(AuthUsersMetaData.Table.COLUMN_ROLE, authUser.getaRole());
		return contentValues;
	}

	/**
	 * @return a certificate
	 */
	private String getNewCertificate() {
		Random rnd = new Random();
		StringBuilder certificate = new StringBuilder();
		for (int i = 0; i < 200; i++)
		{
			certificate.append((char)(rnd.nextInt(26) + 97));
		}
		return certificate.toString();
	}
}
