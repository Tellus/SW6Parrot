package dk.aau.cs.giraf.oasis.lib.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import dk.aau.cs.giraf.oasis.lib.metadata.ProfilesMetaData;
import dk.aau.cs.giraf.oasis.lib.models.AuthUser;
import dk.aau.cs.giraf.oasis.lib.models.Department;
import dk.aau.cs.giraf.oasis.lib.models.HasDepartment;
import dk.aau.cs.giraf.oasis.lib.models.HasGuardian;
import dk.aau.cs.giraf.oasis.lib.models.HasSubDepartment;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.oasis.lib.models.Profile.pRoles;
import dk.aau.cs.giraf.oasis.lib.models.Setting;

/**
 * Helper class for Profiles 
 * @author Admin
 *
 */
public class ProfilesHelper {

	private static Context _context;
	private AuthUsersController au;
	private HasGuardianController hg;
	private HasDepartmentController hd;
	private HasSubDepartmentController hsd;
	private MediaProfileAccessController mpa;
	private ListOfAppsController loa;
	private String[] columns = new String[] { 
			ProfilesMetaData.Table.COLUMN_ID, 
			ProfilesMetaData.Table.COLUMN_FIRST_NAME,
			ProfilesMetaData.Table.COLUMN_SUR_NAME,
			ProfilesMetaData.Table.COLUMN_MIDDLE_NAME,
			ProfilesMetaData.Table.COLUMN_ROLE,
			ProfilesMetaData.Table.COLUMN_PHONE,
			ProfilesMetaData.Table.COLUMN_PICTURE,
			ProfilesMetaData.Table.COLUMN_SETTINGS};

	/**
	 * Constructor
	 * @param context Current context
	 */
	public ProfilesHelper(Context context){
		_context = context;
		au = new AuthUsersController(_context);
		hg = new HasGuardianController(_context);
		hd = new HasDepartmentController(_context);
		hsd = new HasSubDepartmentController(_context);
		mpa = new MediaProfileAccessController(_context);
		loa = new ListOfAppsController(_context);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//METHODS TO CALL
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//	/**
	//	 * Clear profiles table
	//	 */
	//	public void clearProfilesTable() {
	//		_context.getContentResolver().delete(ProfilesMetaData.CONTENT_URI, null, null);
	//	}

	/**
	 * Cascade remove profile
	 * @param profile Profile to remove
	 * @return Rows affected
	 */
	public int removeProfile(Profile profile) {
		if (profile == null) {
			return -1;
		}

		int rows = 0;

		AuthUser authUser = au.getAuthUserById(profile.getId());

		rows += au.removeAuthUser(authUser);
		rows += loa.removeListOfAppsByProfileId(profile.getId());
		rows += hg.removeHasGuardiansByProfile(profile);
		rows += hd.removeHasDepartmentByProfileId(profile.getId());
		rows += mpa.removeMediaProfileAccessByProfileId(profile.getId());
		rows += _context.getContentResolver().delete(ProfilesMetaData.CONTENT_URI, 
				ProfilesMetaData.Table.COLUMN_ID + " = '" + profile.getId() + "'", null);

		return rows;
	}

	/**
	 * Remove child attachment to guardian
	 * @param child Child profile
	 * @param guardian Guardian profile
	 * @return Rows affected
	 */
	public int removeChildAttachmentToGuardian(Profile child, Profile guardian) {
		if (child == null || guardian == null) {
			return -1;
		}

		int rows = -4;
		if (child.getPRole() == pRoles.CHILD.ordinal() && 
				(guardian.getPRole() == pRoles.GUARDIAN.ordinal() || 
				guardian.getPRole() == pRoles.PARENT.ordinal())) {
			rows = hg.removeHasGuardian(new HasGuardian(guardian.getId(), child.getId()));
		} else {
			rows = -2;
		}

		return rows;
	}

	/**
	 * Insert profile
	 * @param profile Profile containing data
	 * @return profile id
	 */
	public long insertProfile(Profile profile) {
		if (profile == null) {
			return -1;
		}

		long id = au.insertAuthUser(AuthUser.aRole.PROFILE.ordinal());

		ContentValues profileContentValues = getContentValues(profile);
		profileContentValues.put(ProfilesMetaData.Table.COLUMN_ID, id);
		_context.getContentResolver().insert(ProfilesMetaData.CONTENT_URI, profileContentValues);

		return id;
	}

	/**
	 * Attach child to guardian
	 * @param child Child profile
	 * @param guardian Guardian profile
	 * @return Rows affected
	 */
	public int attachChildToGuardian(Profile child, Profile guardian) {
		if (child == null || guardian == null) {
			return -1;
		}

		int result = -2; 

		if (child.getPRole() == pRoles.CHILD.ordinal() && 
				(guardian.getPRole() == pRoles.GUARDIAN.ordinal() || 
				guardian.getPRole() == pRoles.PARENT.ordinal())) {

			result = hg.insertHasGuardian(new HasGuardian(guardian.getId(), child.getId()));
		}

		return result;
	}

	/**
	 * Modify profile
	 * @param profile Profile containing data to modify
	 * @return Rows affected
	 */
	public int modifyProfile(Profile profile) {
		if (profile == null) {
			return -1;
		}

		Uri uri = ContentUris.withAppendedId(ProfilesMetaData.CONTENT_URI, profile.getId());
		ContentValues cv = getContentValues(profile);
		return _context.getContentResolver().update(uri, cv, null, null);
	}

	/**
	 * Authenticates the profile
	 * @param certificate the certificate that authenticates the profile
	 * @return Authenticated profile
	 */
	public Profile authenticateProfile(String certificate) {
		if (certificate == null || !certificate.matches("[a-z]{200}")) {
			return null;
		}

		Profile profile = null;
		long id = au.getIdByCertificate(certificate);

		if (id != -1) {
			profile = getProfileById(id);
		}

		return profile;
	}

	/**
	 * Set a new certificate
	 * @param certificate the certificate to set
	 * @param profile the profile whom the certificate is updated for
	 * @return Rows affected
	 */
	public int setCertificate(String certificate, Profile profile) {
		if (certificate == null || !certificate.matches("[a-z]{200}") || profile == null) {
			return -1;
		}
		
		return au.setCertificate(certificate, profile.getId());
	}

	/**
	 * Get all profiles
	 * @return List of profile, containing all profiles
	 */
	public List<Profile> getProfiles() {
		List<Profile> profiles = new ArrayList<Profile>();
		Cursor c = _context.getContentResolver().query(ProfilesMetaData.CONTENT_URI, columns, null, null, null);

		if (c != null) {
			profiles = cursorToProfiles(c);
			c.close();
		}

		return profiles;
	}

	/**
	 * Get all guardians
	 * @return List of guardians
	 */
	public List<Profile> getGuardians() {
		List<Profile> guardians = new ArrayList<Profile>();

		Cursor c = _context.getContentResolver().query(ProfilesMetaData.CONTENT_URI, columns, 
				ProfilesMetaData.Table.COLUMN_ROLE + " = '" + Profile.pRoles.GUARDIAN.ordinal() + "'", null, null);

		if (c != null) {
			guardians = cursorToProfiles(c);
			c.close();
		}

		return guardians;
	}

	/**
	 * Get all children
	 * @return List of children
	 */
	public List<Profile> getChildren() {
		List<Profile> children = new ArrayList<Profile>();

		Cursor c = _context.getContentResolver().query(ProfilesMetaData.CONTENT_URI, columns, 
				ProfilesMetaData.Table.COLUMN_ROLE + " = '" + Profile.pRoles.CHILD.ordinal() + "'", null, null);

		if (c != null) {
			children = cursorToProfiles(c);
			c.close();
		}

		return children;
	}

	/**
	 * Retrieve the certificates for a profile
	 * @param profile
	 * @return List of strings
	 */
	public List<String> getCertificatesByProfile(Profile profile) {
		List<String> certificates = new ArrayList<String>();

		if (profile == null) {
			return certificates;
		}

		certificates = au.getCertificatesById(profile.getId());
		return certificates;
	}

	/**
	 * Get children by department
	 * @param department Department
	 * @return List of profiles
	 */
	public List<Profile> getChildrenByDepartment(Department department) {
		List<Profile> children = new ArrayList<Profile>();

		if (department == null) {
			return children;
		}

		List<HasDepartment> list = hd.getProfilesByDepartment(department);

		for (HasDepartment hdModel : list) {
			Profile profile = getProfileById(hdModel.getIdProfile());
			if (profile.getPRole() == pRoles.CHILD.ordinal()) {
				children.add(profile);
			}
		}

		return children;
	}

	/**
	 * Get children by department and sub departments
	 * @param department Department
	 * @return List of profiles
	 */
	public List<Profile> getChildrenByDepartmentAndSubDepartments(Department department) {
		List<Profile> children = new ArrayList<Profile>();

		if (department == null) {
			return children;
		}

		children.addAll(getChildrenByDepartment(department));

		List<HasSubDepartment> list = hsd.getSubDepartmentsByDepartment(department);

		for (HasSubDepartment hsdModel : list) {
			Department _department = new Department();
			department.setId(hsdModel.getIdSubDepartment());
			children.addAll(getChildrenByDepartmentAndSubDepartments(_department));
		}

		return children;
	}

	/**
	 * Get children by guardian
	 * @param guardian Guardian profile
	 * @return List of profiles
	 */
	public List<Profile> getChildrenByGuardian(Profile guardian) {
		List<Profile> children = new ArrayList<Profile>();

		if (guardian == null) {
			return children;
		}

		if (guardian.getPRole() == pRoles.GUARDIAN.ordinal() || 
				guardian.getPRole() == pRoles.PARENT.ordinal()) {

			List<HasGuardian> list = hg.getChildrenByGuardian(guardian);

			for (HasGuardian hgModel : list) {
				Profile child = getProfileById(hgModel.getIdChild());
				if (child.getPRole() == pRoles.CHILD.ordinal()) {
					children.add(child);
				}
			}
		}

		return children;
	}

	/**
	 * get children with no department
	 * @return list of children with no department
	 */
	public List<Profile> getChildrenWithNoDepartment() {
		List<Profile> allChildren = getChildren();

		List<HasDepartment> hasDepartments = hd.getHasDepartments();
		for (HasDepartment hasDepartment : hasDepartments) {
			Profile profile = getProfileById(hasDepartment.getIdProfile());
			if (profile.getPRole() == Profile.pRoles.CHILD.ordinal()) {
				allChildren.remove(profile);
			}
		}

		return allChildren;
	}

	/**
	 * Get profiles by department
	 * @param department Department
	 * @return List of profiles
	 */
	public List<Profile> getProfilesByDepartment(Department department) {
		List<Profile> profiles = new ArrayList<Profile>();

		if (department == null) {
			return profiles;
		}

		List<HasDepartment> list = hd.getProfilesByDepartment(department);

		for (HasDepartment hd : list) {
			profiles.add(getProfileById(hd.getIdProfile()));
		}

		return profiles;
	}

	/**
	 * get guardians with no department
	 * @return list of guardians with no department
	 */
	public List<Profile> getGuardiansWithNoDepartment() {
		List<Profile> allGuardians = getGuardians();

		List<HasDepartment> hasDepartments = hd.getHasDepartments();
		for (HasDepartment hasDepartment : hasDepartments) {
			Profile profile = getProfileById(hasDepartment.getIdProfile());
			if (profile.getPRole() == Profile.pRoles.GUARDIAN.ordinal()) {
				allGuardians.remove(profile);
			}
		}

		return allGuardians;
	}

	/**
	 * Get guardian by department
	 * @param department Department
	 * @return List of profiles
	 */
	public List<Profile> getGuardiansByDepartment(Department department) {
		List<Profile> guardians = new ArrayList<Profile>();

		if (department == null) {
			return guardians;
		}

		List<HasDepartment> list = hd.getProfilesByDepartment(department);

		for (HasDepartment hd : list) {
			Profile guardian = getProfileById(hd.getIdProfile());
			if (guardian.getPRole() == pRoles.GUARDIAN.ordinal()) {
				guardians.add(guardian);
			}
		}

		return guardians;
	}

	/**
	 * Get guardians by child
	 * @param child Child profile to get by
	 * @return List of guardians
	 */
	public List<Profile> getGuardiansByChild(Profile child) {
		List<Profile> guardians = new ArrayList<Profile>();

		if (child == null) {
			return guardians;
		}

		if (child.getPRole() == pRoles.CHILD.ordinal()) {
			List<HasGuardian> list = hg.getGuardiansByChild(child);

			for (HasGuardian hgModel : list) {
				Profile guardian = getProfileById(hgModel.getIdGuardian());
				if (guardian.getPRole() == pRoles.GUARDIAN.ordinal()) {
					guardians.add(guardian);
				}
			}
		}

		return guardians;
	}

	/**
	 * Get a profile by its Id
	 * @param id the id of the profile
	 * @return profile
	 */
	public Profile getProfileById(long id) {
		Profile profile = null;
		
		if (id <= 0) {
			return null;
		}
		
		Uri uri = ContentUris.withAppendedId(ProfilesMetaData.CONTENT_URI, id);
		Cursor c = _context.getContentResolver().query(uri, columns, null, null, null);

		if (c != null) {
			if (c.moveToFirst()) {
				profile = cursorToProfile(c);
			}
			c.close();
		}

		return profile;
	}

	/**
	 * Get profiles by name
	 * @param name Profile name
	 * @return List of profiles
	 */
	public List<Profile> getProfilesByName(String name) {
		List<Profile> profiles = new ArrayList<Profile>();

		if (name == null) {
			return profiles;
		}

		Cursor c = _context.getContentResolver().query(ProfilesMetaData.CONTENT_URI, columns, 
				ProfilesMetaData.Table.COLUMN_FIRST_NAME + " LIKE '%" + name + "%' OR " +
						ProfilesMetaData.Table.COLUMN_MIDDLE_NAME + " LIKE '%" + name + "%' OR " +
						ProfilesMetaData.Table.COLUMN_SUR_NAME +  " LIKE '%" + name + "%'", null, null);

		if (c != null) {
			profiles = cursorToProfiles(c);
			c.close();
		}

		return profiles;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//PRIVATE METHODS - Keep out!
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Cursor to profiles
	 * @param cursor Input cursor
	 * @return Output Profiles
	 */
	private List<Profile> cursorToProfiles(Cursor cursor) {
		List<Profile> profiles = new ArrayList<Profile>();

		if (cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				profiles.add(cursorToProfile(cursor));
				cursor.moveToNext();
			}
		}

		return profiles;
	}

	/**
	 * Cursor to profile
	 * @param cursor Input cursor
	 * @return Output Profile
	 */
	private Profile cursorToProfile(Cursor cursor) {
		Profile profile = new Profile();
		profile.setId(cursor.getLong(cursor.getColumnIndex(ProfilesMetaData.Table.COLUMN_ID)));
		profile.setFirstname(cursor.getString(cursor.getColumnIndex(ProfilesMetaData.Table.COLUMN_FIRST_NAME)));
		profile.setMiddlename(cursor.getString(cursor.getColumnIndex(ProfilesMetaData.Table.COLUMN_MIDDLE_NAME)));
		profile.setSurname(cursor.getString(cursor.getColumnIndex(ProfilesMetaData.Table.COLUMN_SUR_NAME)));
		profile.setPicture(cursor.getString(cursor.getColumnIndex(ProfilesMetaData.Table.COLUMN_PICTURE)));
		profile.setPhone(cursor.getLong(cursor.getColumnIndex(ProfilesMetaData.Table.COLUMN_PHONE)));
		profile.setPRole(cursor.getLong(cursor.getColumnIndex(ProfilesMetaData.Table.COLUMN_ROLE)));
		profile.setSettings(Setting.toSetting(cursor.getString(cursor.getColumnIndex(ProfilesMetaData.Table.COLUMN_SETTINGS))));
		return profile;
	}

	/**
	 * @param profile the profile to put in the database
	 * @return the contentValues
	 */
	private ContentValues getContentValues(Profile profile) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(ProfilesMetaData.Table.COLUMN_FIRST_NAME, profile.getFirstname());
		contentValues.put(ProfilesMetaData.Table.COLUMN_SUR_NAME, profile.getSurname());
		contentValues.put(ProfilesMetaData.Table.COLUMN_MIDDLE_NAME, profile.getMiddlename());
		contentValues.put(ProfilesMetaData.Table.COLUMN_ROLE, profile.getPRole());
		contentValues.put(ProfilesMetaData.Table.COLUMN_PHONE, profile.getPhone());
		contentValues.put(ProfilesMetaData.Table.COLUMN_PICTURE, profile.getPicture());
		contentValues.put(ProfilesMetaData.Table.COLUMN_SETTINGS, Setting.toStringSetting(profile.getSettings()));

		return contentValues;
	}
}
