package dk.aau.cs.giraf.oasis.lib.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import dk.aau.cs.giraf.oasis.lib.metadata.MediaMetaData;
import dk.aau.cs.giraf.oasis.lib.models.Department;
import dk.aau.cs.giraf.oasis.lib.models.HasLink;
import dk.aau.cs.giraf.oasis.lib.models.HasTag;
import dk.aau.cs.giraf.oasis.lib.models.Media;
import dk.aau.cs.giraf.oasis.lib.models.MediaDepartmentAccess;
import dk.aau.cs.giraf.oasis.lib.models.MediaProfileAccess;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.oasis.lib.models.Tag;

/**
 * Helper class for Media
 * @author Admin
 *
 */
public class MediaHelper {

	private static Context _context;
	private DepartmentsHelper dh;
	private HasLinkController hl;
	private MediaDepartmentAccessController mda;
	private MediaProfileAccessController mpa;
	private HasTagController ht;
	private String[] columns = new String[] { 
			MediaMetaData.Table.COLUMN_ID, 
			MediaMetaData.Table.COLUMN_PATH,
			MediaMetaData.Table.COLUMN_NAME,
			MediaMetaData.Table.COLUMN_PUBLIC,
			MediaMetaData.Table.COLUMN_TYPE,
			MediaMetaData.Table.COLUMN_OWNERID};

	/**
	 * Constructor
	 * @param context Current context
	 */
	public MediaHelper(Context context){
		_context = context;
		dh = new DepartmentsHelper(_context);
		hl = new HasLinkController(_context);
		mda = new MediaDepartmentAccessController(_context);
		mpa = new MediaProfileAccessController(_context);
		ht = new HasTagController(_context);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//METHODS TO CALL
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//	/**
	//	 * Clear media table
	//	 */
	//	public void clearMediaTable() {
	//		_context.getContentResolver().delete(MediaMetaData.CONTENT_URI, null, null);
	//	}
	
	/**
	 * Remove media
	 */
	public int removeMedia(Media media) {
		if (media == null) {
			return -1;
		}
		
		int rows = 0;
		
		rows += mpa.removeMediaProfileAccessByMediaId(media.getId());
		rows += mda.removeMediaDepartmentAccessByMediaId(media.getId());
		rows += ht.removeHasTagByMediaId(media.getId());
		rows += _context.getContentResolver().delete(MediaMetaData.CONTENT_URI, 
				MediaMetaData.Table.COLUMN_ID + " = '" + media.getId() + "'", null);
		
		return rows;
	}

	/**
	 * Remove media attachment to profile
	 * @param media Media to remove
	 * @param profile Profile
	 * @return Rows
	 */
	public int removeMediaAttachmentToProfile(Media media, Profile profile) {
		if (media == null || profile == null) {
			return -1;
		}
		
		return mpa.removeMediaProfileAccess(new MediaProfileAccess(profile.getId(), media.getId())); 
	}

	/**
	 * Remove media attachment to department
	 * @param media Media to remove
	 * @param department Department
	 * @return Rows
	 */
	public int removeMediaAttachmentToDepartment(Media media, Department department) {
		if (media == null || department == null) {
			return -1;
		}
		
		return mda.removeMediaDepartmentAccess(new MediaDepartmentAccess(media.getId(), department.getId()));
	}

	/**
	 * Remove tag list from media
	 * @param tags Tags to remove
	 * @param media Media to remove from
	 * @return Rows
	 */
	public int removeTagListFromMedia(List<Tag> tags, Media media) {
		if (tags == null || media == null) {
			return -1;
		}
		
		return ht.removeHasTagList(tags, media);
	}

	/**
	 * Remove Tag from media
	 * @param tag Tag to remove
	 * @param media Media to remove from
	 * @return Rows
	 */
	public int removeTagFromMedia(Tag tag, Media media) {
		if (tag == null || media == null) {
			return -1;
		}
		
		return ht.removeHasTag(new HasTag(media.getId(), tag.getId()));
	}

	/**
	 * Remove sub media attachment to media
	 * @param subMedia sub media to remove
	 * @param media media to remove from
	 * @return Rows
	 */
	public int removeSubMediaAttachmentToMedia(Media subMedia, Media media) {
		if (subMedia == null || media == null) {
			return -1;
		}
		
		return hl.removeHasLink(new HasLink(media.getId(), subMedia.getId()));
	}

	/**
	 * Insert media
	 * @param media Media containing data
	 * @return media id
	 */

	public long insertMedia(Media media) {
		if (media == null) {
			return -1;
		}
		
		ContentValues cv = getContentValues(media);
		Uri uri = _context.getContentResolver().insert(MediaMetaData.CONTENT_URI, cv);
		long id = Integer.parseInt(uri.getPathSegments().get(1));
		
		mpa.insertMediaProfileAccess(new MediaProfileAccess(media.getOwnerId(), id));

		return id;
	}

	/**
	 * Add tags to media
	 * @param tags List of tags to add
	 * @param media Media to add to
	 * @return Rows
	 */
	public int addTagsToMedia(List<Tag> tags, Media media) {
		if (tags == null || media == null) {
			return -1;
		}
		
		int result = 0;
		for (Tag t : tags) {
			HasTag htModel = new HasTag();
			htModel.setIdMedia(media.getId());
			htModel.setIdTag(t.getId());
			ht.insertHasTag(htModel);
			result++;
		}

		return result;
	}

	/**
	 * Attach media to profile
	 * @param media Media to attach to profile
	 * @param profile Profile to attach to
	 * @param owner Owner profile
	 * @return Rows
	 */
	public int attachMediaToProfile(Media media, Profile profile, Profile owner) {
		if (media == null || profile == null) {
			return -1;
		}
		
		int result = -2;
		if (media.isMPublic() || (owner != null && media.getOwnerId() == owner.getId())) {
			result = mpa.insertMediaProfileAccess(new MediaProfileAccess(profile.getId(), media.getId()));
		}
		
		return result;
	}

	/**
	 * Attach media to department
	 * @param media Media to attach
	 * @param department Department to attach to
	 * @param owner Profile owner
	 * @return Rows
	 */
	public int attachMediaToDepartment(Media media, Department department, Profile owner) {
		if (media == null || department == null) {
			return -1;
		}
		
		int result = -2;
		if (media.isMPublic() || (owner != null && media.getOwnerId() == owner.getId())) {
			result = mda.insertMediaDepartmentAccess(new MediaDepartmentAccess(media.getId(), department.getId()));
		}
		
		return result;
	}

	/**
	 * Attach sub media to media
	 * @param subMedia Sub media to attach
	 * @param media Media to attach to
	 * @return Rows
	 */
	public int attachSubMediaToMedia(Media subMedia, Media media) {
		if (subMedia == null || media == null) {
			return -1;
		}
		
		return hl.insertHasLink(new HasLink(media.getId(), subMedia.getId()));
	}

	/**
	 * Modify media
	 * @param media Media containing data to modify
	 */
	public int modifyMedia(Media media) {
		if (media == null) {
			return -1;
		}
		
		Uri uri = ContentUris.withAppendedId(MediaMetaData.CONTENT_URI, media.getId());
		ContentValues cv = getContentValues(media);
		return _context.getContentResolver().update(uri, cv, null, null);
	}

	/**
	 * Get all media
	 * @return List<Media>, containing all media
	 */
	public List<Media> getMedia() {
		List<Media> media = new ArrayList<Media>();

		Cursor c = _context.getContentResolver().query(MediaMetaData.CONTENT_URI, columns, null, null, null);

		if (c != null) {
			media = cursorToMedia(c);
			c.close();
		}

		return media;
	}
	
	/**
	 * Get my pictures
	 * @param profile Current profile
	 * @return List of picture media
	 */
	public List<Media> getMyPictures(Profile profile) {
		List<Media> returnedMedia = getMyMedia(profile); 
		if (profile == null) {
			return returnedMedia;
		}
		
		List<Media> media = new ArrayList<Media>();
		
		for (Media m : returnedMedia) {
			if(m.getMType().toLowerCase().equals("picture")) {
				media.add(m);
			}
		}
		return media;
	}
	
	/**
	 * Get my Sounds
	 * @param profile Current profile
	 * @return List of sound media
	 */
	public List<Media> getMySounds(Profile profile) {
		List<Media> returnedMedia = getMyMedia(profile); 
		
		if (profile == null) {
			return returnedMedia;
		}
		
		List<Media> media = new ArrayList<Media>();
		
		for (Media m : returnedMedia) {
			if(m.getMType().toLowerCase().equals("sound")) {
				media.add(m);
			}
		}
		return media;
	}
	
	/**
	 * Get my Words
	 * @param profile Current profile
	 * @return List of word media
	 */
	public List<Media> getMyWords(Profile profile) {
		List<Media> returnedMedia = getMyMedia(profile); 
		if (profile == null) {
			return returnedMedia;
		}
		
		List<Media> media = new ArrayList<Media>();
		
		for (Media m : returnedMedia) {
			if(m.getMType().toLowerCase().equals("word")) {
				media.add(m);
			}
		}
		return media;
	}

	/**
	 * Get all media of a person
	 * @param profile The person
	 * @return a list consisting of the persons media
	 */
	//getAccessableMediaByProfile
	public List<Media> getMyMedia(Profile profile) {
		List<Media> result = getMediaByProfile(profile);
		if (profile == null) {
			return result;
		}
		
		List<Department> depList = dh.getDepartmentsByProfile(profile);
		
		for (Department dep : depList) {
			List<Media> tmp = getMediaByDepartment(dep);
			for (Media m : tmp) {
				if (!result.contains(m)) {
					result.add(m);
				}
			}
		}
		
		return result;
	}

	/**
	 * Get all media a person owns
	 * @param profile the person that owns the media
	 * @return a list containing the media
	 */
	//getMediaByOwner(Profile owner) 
	public List<Media> getMediaIOwn(Profile profile) {
		List<Media> result = new ArrayList<Media>();
		
		if (profile == null) {
			return result;
		}

		Cursor c = _context.getContentResolver().query(MediaMetaData.CONTENT_URI, columns, 
				MediaMetaData.Table.COLUMN_OWNERID + " = '" + profile.getId() + "'", null, null);
		if (c != null) {
			result = cursorToMedia(c);
			c.close();
		}

		return result;
	}
	
	/**
	 * Get all public media
	 * @return a list containing all public media
	 */
	public List<Media> getPublicMedia() {
		List<Media> result = new ArrayList<Media>();

		Cursor c = _context.getContentResolver().query(MediaMetaData.CONTENT_URI, columns, 
				MediaMetaData.Table.COLUMN_PUBLIC + " = '" + 1 + "'", null, null);
		if (c != null) {
			result = cursorToMedia(c);
			c.close();
		}

		return result;
	}

	/**
	 * Get sub media by media
	 * @param media Media
	 * @return List of sub medias
	 */
	public List<Media> getSubMediaByMedia(Media media) {
		List<Media> medias = new ArrayList<Media>();
		
		if (media == null) {
			return medias;
		}

		for (HasLink link : hl.getSubMediaByMedia(media)) {
			medias.add(getSingleMediaById(link.getIdSubMedia()));
		}

		return medias;
	}

	/**
	 * Get media by id
	 * @param id the id of the media
	 * @return the media or null
	 */
	public Media getSingleMediaById(long id) {
		Media media = null;
		
		if (id <= 0) {
			return null;
		}
		
		Uri uri = ContentUris.withAppendedId(MediaMetaData.CONTENT_URI, id);
		Cursor c = _context.getContentResolver().query(uri, columns, null, null, null);

		if (c != null) {
			if(c.moveToFirst()) {
				media = cursorToSingleMedia(c);
			}

			c.close();
		}

		return media;
	}

	/**
	 * Get media by name
	 * @param name the name of the media
	 * @return List<Media>, containing all media of that name
	 */
	public List<Media> getMediaByName(String name) {
		List<Media> media = new ArrayList<Media>();
		if (name == null) {
			return media;
		}
		
		Cursor c = _context.getContentResolver().query(MediaMetaData.CONTENT_URI, columns, MediaMetaData.Table.COLUMN_NAME + " LIKE '%" + name + "%'", null, null);

		if (c != null) {
			media = cursorToMedia(c);
			c.close();
		}
		return media;
	}

	/**
	 * Get media by tags
	 * @param tags List of tags
	 * @return List of media
	 */
	public List<Media> getMediaByTags(List<Tag> tags) {
		List<Media> resultList = new ArrayList<Media>();

		if (tags == null) {
			return resultList;
		}
		
		for (Tag t : tags) {
			for (HasTag htModel : ht.getHasTagsByTag(t)) {
				resultList.add(getMediaById(htModel.getIdMedia()));
			}
		}

		return resultList;
	}

	/**
	 * Get media by department
	 * @param department Department
	 * @return List of media
	 */
	public List<Media> getMediaByDepartment(Department department) {
		List<Media> mediaList = new ArrayList<Media>();
		
		if (department == null) {
			return mediaList;
		}

		for (MediaDepartmentAccess mdaModel : mda.getMediaByDepartment(department)) {
			mediaList.add(getMediaById(mdaModel.getIdMedia()));
		}

		return mediaList;
	}

	/**
	 * Get media by profile
	 * @param profile Profile
	 * @return List of media
	 */
	public List<Media> getMediaByProfile(Profile profile) {
		List<Media> mediaList = new ArrayList<Media>();
		
		if (profile == null) {
			return mediaList;
		}

		for (MediaProfileAccess mpaModel : mpa.getMediaProfileAccessByProfile(profile)) {
			mediaList.add(getMediaById(mpaModel.getIdMedia()));
		}

		return mediaList;
	}

	/**
	 * Get media by id
	 * @param id Id 
	 * @return Media
	 */
	public Media getMediaById(long id) {
		Media media = null;
		Cursor c = _context.getContentResolver().query(MediaMetaData.CONTENT_URI, columns, MediaMetaData.Table.COLUMN_ID + " = '" + id + "'", null, null);

		if (c != null) {
			if (c.moveToFirst()) {
				media = cursorToSingleMedia(c);
			}

			c.close();
		}

		return media;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//PRIVATE METHODS - Keep out!
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Cursor to media
	 * @param cursor Input cursor
	 * @return List<Media>, containing all the media from the cursor
	 */
	private List<Media> cursorToMedia(Cursor cursor) {
		List<Media> media = new ArrayList<Media>();

		if(cursor.moveToFirst()) {
			while(!cursor.isAfterLast()) {
				media.add(cursorToSingleMedia(cursor));
				cursor.moveToNext();
			}
		}

		return media;
	}

	/**
	 * Cursor to single media
	 * @param cursor Input cursor
	 * @return Single Media
	 */
	private Media cursorToSingleMedia(Cursor cursor) {
		Media media = new Media();
		media.setId(cursor.getLong(cursor.getColumnIndex(MediaMetaData.Table.COLUMN_ID)));
		media.setName(cursor.getString(cursor.getColumnIndex(MediaMetaData.Table.COLUMN_NAME)));
		media.setMPath(cursor.getString(cursor.getColumnIndex(MediaMetaData.Table.COLUMN_PATH)));
		media.setMType(cursor.getString(cursor.getColumnIndex(MediaMetaData.Table.COLUMN_TYPE)));
		media.setOwnerId(cursor.getLong(cursor.getColumnIndex(MediaMetaData.Table.COLUMN_OWNERID)));
		if (cursor.getLong(cursor.getColumnIndex(MediaMetaData.Table.COLUMN_PUBLIC)) == 1) {
			media.setMPublic(true);
		} else {
			media.setMPublic(false);
		}
		return media;
	}

	/**
	 * Getter for the content values
	 * @param media the media which values should be used
	 * @return the contentValues
	 */
	private ContentValues getContentValues(Media media) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(MediaMetaData.Table.COLUMN_PATH, media.getMPath());
		contentValues.put(MediaMetaData.Table.COLUMN_NAME, media.getName());
		contentValues.put(MediaMetaData.Table.COLUMN_PUBLIC, media.isMPublic());
		contentValues.put(MediaMetaData.Table.COLUMN_TYPE, media.getMType());
		contentValues.put(MediaMetaData.Table.COLUMN_OWNERID, media.getOwnerId());

		return contentValues;
	}
}