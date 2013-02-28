package dk.aau.cs.giraf.oasis.app.test;

import java.util.ArrayList;
import java.util.List;

import android.test.ActivityInstrumentationTestCase2;
import dk.aau.cs.giraf.oasis.app.MainActivity;
import dk.aau.cs.giraf.oasis.lib.models.Department;
import dk.aau.cs.giraf.oasis.lib.models.Media;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.oasis.lib.models.Tag;

public class MediaHelperTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private MainActivity mActivity;


	public MediaHelperTest() {
		super("dk.aau.cs.giraf.oasis.app", MainActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();

		setActivityInitialTouchMode(false);

		mActivity = getActivity();
	}

	public void testRemoveMedia() {
		Media newMedia = new Media("TestMedia", "TPath", true, "picture", 2);
		long id = mActivity.helper.mediaHelper.insertMedia(newMedia);
		newMedia.setId(id);

		int result = mActivity.helper.mediaHelper.removeMedia(newMedia);
		Media foundMedia = mActivity.helper.mediaHelper.getMediaById(id);

		assertNull("Should be null, because Media is removed", foundMedia);
		assertNotSame("Should not return -1", -1, result);
	}

	public void testRemoveMediaAttachmentToProfile() {
		Media newMedia = new Media("TestMedia", "TPath", true, "picture", 2);
		long id = mActivity.helper.mediaHelper.insertMedia(newMedia);
		newMedia.setId(id);
		Profile newProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idP = mActivity.helper.profilesHelper.insertProfile(newProfile);
		newProfile.setId(idP);
		mActivity.helper.mediaHelper.attachMediaToProfile(newMedia, newProfile, null);

		int result = mActivity.helper.mediaHelper.removeMediaAttachmentToProfile(newMedia, newProfile);

		assertEquals("Should return 1", 1, result);
		assertNotSame("Should not return -1", -1, result);
	}

	public void testRemoveMediaAttachmentToDepartment() {
		Media newMedia = new Media("TestRemoveMedia", "TPath", true, "picture", 2);
		long id = mActivity.helper.mediaHelper.insertMedia(newMedia);
		newMedia.setId(id);
		
		Department newDepartment = new Department("TestDepartment", "Test", 12345678, "test@test.com");
		long idD = mActivity.helper.departmentsHelper.insertDepartment(newDepartment);
		newDepartment.setId(idD);
		
		mActivity.helper.mediaHelper.attachMediaToDepartment(newMedia, newDepartment, null);
		
		int result = mActivity.helper.mediaHelper.removeMediaAttachmentToDepartment(newMedia, newDepartment);

		assertEquals("Should return 1", 1, result);
		assertNotSame("Should not return -1", -1, result);
	}

	public void testRemoveTagListFromMedia() {
		Tag newTag = new Tag("RemoveTestTag");
		long idT = mActivity.helper.tagsHelper.insertTag(newTag);
		newTag.setId(idT);
		List<Tag> tList = new ArrayList<Tag>();
		tList.add(newTag);
		
		Media newMedia = new Media("TestMedia", "TPath", true, "picture", 2);
		long id = mActivity.helper.mediaHelper.insertMedia(newMedia);
		newMedia.setId(id);

		mActivity.helper.mediaHelper.addTagsToMedia(tList, newMedia);
		
		int result = mActivity.helper.mediaHelper.removeTagListFromMedia(tList, newMedia);
		
		List<Media> media = mActivity.helper.mediaHelper.getMediaByTags(tList);
		
		assertEquals("Should be 0", 0, media.size());
		assertNotSame("Should not return -1", -1, result);
	}

	public void testRemoveTagFromMedia() {
		Tag newTag = new Tag("TestTag");
		long idT = mActivity.helper.tagsHelper.insertTag(newTag);
		newTag.setId(idT);
		List<Tag> tList = new ArrayList<Tag>();
		tList.add(newTag);
		Media newMedia = new Media("TestMedia", "TPath", true, "picture", 2);
		long id = mActivity.helper.mediaHelper.insertMedia(newMedia);
		newMedia.setId(id);
		mActivity.helper.mediaHelper.addTagsToMedia(tList, newMedia);

		int result = mActivity.helper.mediaHelper.removeTagFromMedia(newTag, newMedia);

		assertEquals("Should return 1", 1, result);
		assertNotSame("Should not return -1", -1, result);
	}

	public void testRemoveSubMediaAttachmentToMedia() {
		Media newMedia = new Media("TestMedia", "TPath", true, "picture", 2);
		long id = mActivity.helper.mediaHelper.insertMedia(newMedia);
		newMedia.setId(id);
		Media newSubMedia = new Media("TestSubMedia", "TSPath", true, "picture", 2);
		long idS = mActivity.helper.mediaHelper.insertMedia(newSubMedia);
		newSubMedia.setId(idS);
		mActivity.helper.mediaHelper.attachSubMediaToMedia(newSubMedia, newMedia);

		int result = mActivity.helper.mediaHelper.removeSubMediaAttachmentToMedia(newSubMedia, newMedia);

		assertEquals("Should return 1", 1, result);
		assertNotSame("Should not return -1", -1, result);
	}

	public void testInsertMedia() {
		Media newMedia = new Media("TestMedia", "TPath", true, "picture", 2);
		long id = mActivity.helper.mediaHelper.insertMedia(newMedia);

		assertNotSame("Should not return -1", -1, id);
	}

	public void testAddTagsToMedia() {
		Tag newTag = new Tag("TestTag");
		long idT = mActivity.helper.tagsHelper.insertTag(newTag);
		newTag.setId(idT);
		List<Tag> tList = new ArrayList<Tag>();
		tList.add(newTag);
		Media newMedia = new Media("TestMedia", "TPath", true, "picture", 2);
		long id = mActivity.helper.mediaHelper.insertMedia(newMedia);
		newMedia.setId(id);

		int result = mActivity.helper.mediaHelper.addTagsToMedia(tList, newMedia);

		assertNotSame("Should not return -1", -1, result);
	}

	public void testAttachMediaToProfile() {
		Media newMedia = new Media("TestMedia", "TPath", true, "picture", 2);
		long id = mActivity.helper.mediaHelper.insertMedia(newMedia);
		newMedia.setId(id);
		Profile newProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idP = mActivity.helper.profilesHelper.insertProfile(newProfile);
		newProfile.setId(idP);

		int result = mActivity.helper.mediaHelper.attachMediaToProfile(newMedia, newProfile, null);

		assertEquals("Should return 0", 0, result);
		assertNotSame("Should not return -1", -1, result);
	}

	public void testAttachMediaToDepartment() {
		Media newMedia = new Media("TestMedia", "TPath", true, "picture", 2);
		long id = mActivity.helper.mediaHelper.insertMedia(newMedia);
		newMedia.setId(id);
		Department newDepartment = new Department("TestDep", "Test", 12345678, "test@test.com");
		long idD = mActivity.helper.departmentsHelper.insertDepartment(newDepartment);
		newDepartment.setId(idD);

		int result = mActivity.helper.mediaHelper.attachMediaToDepartment(newMedia, newDepartment, null);

		assertEquals("Should return 0", 0, result);
		assertNotSame("Should not return -1", -1, result);
	}

	public void testAttachSubMediaToMedia() {
		Media newMedia = new Media("TestMedia", "TPath", true, "picture", 2);
		long id = mActivity.helper.mediaHelper.insertMedia(newMedia);
		newMedia.setId(id);
		Media newSubMedia = new Media("TestSubMedia", "TSPath", true, "picture", 2);
		long idS = mActivity.helper.mediaHelper.insertMedia(newSubMedia);
		newSubMedia.setId(idS);

		int result = mActivity.helper.mediaHelper.attachSubMediaToMedia(newSubMedia, newMedia);

		assertEquals("Should return 0", 0, result);
		assertNotSame("Should not return -1", -1, result);
	}

	public void testModifyMedia() {
		Media newMedia = new Media("TestMedia", "TPath", true, "picture", 2);
		long id = mActivity.helper.mediaHelper.insertMedia(newMedia);
		newMedia.setId(id);

		int result = mActivity.helper.mediaHelper.modifyMedia(newMedia);
				
		assertEquals("Should return 1", 1, result);
		assertNotSame("Should not return -1", -1, result);
	}

	public void testGetMedia() {
		List<Media> list = mActivity.helper.mediaHelper.getMedia();

		assertNotNull("Should not be null", list);
	}

	public void testGetMyPictures() {
		Profile newProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idP = mActivity.helper.profilesHelper.insertProfile(newProfile);
		newProfile.setId(idP);
		List<Media> list = mActivity.helper.mediaHelper.getMyPictures(newProfile);

		assertNotNull("Should not be null", list);
	}

	public void testGetMySounds() {
		Profile newProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idP = mActivity.helper.profilesHelper.insertProfile(newProfile);
		newProfile.setId(idP);
		List<Media> list = mActivity.helper.mediaHelper.getMySounds(newProfile);

		assertNotNull("Should not be null", list);
	}

	public void testGetMyWords() {
		Profile newProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idP = mActivity.helper.profilesHelper.insertProfile(newProfile);
		newProfile.setId(idP);
		List<Media> list = mActivity.helper.mediaHelper.getMyWords(newProfile);

		assertNotNull("Should not be null", list);
	}

	public void testGetMediaIOwn() {
		Profile newProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idP = mActivity.helper.profilesHelper.insertProfile(newProfile);
		newProfile.setId(idP);
		List<Media> list = mActivity.helper.mediaHelper.getMediaIOwn(newProfile);

		assertNotNull("Should not be null", list);
	}

	public void testGetPublicMedia() {
		List<Media> list = mActivity.helper.mediaHelper.getPublicMedia();

		assertNotNull("Should not be null", list);
	}

	public void testGetSubMediaMedia() {
		Media newMedia = new Media("TestMedia", "TPath", true, "picture", 2);
		long id = mActivity.helper.mediaHelper.insertMedia(newMedia);
		newMedia.setId(id);

		List<Media> list = mActivity.helper.mediaHelper.getSubMediaByMedia(newMedia);

		assertNotNull("Should not be null", list);
	}

	public void testGetSingleMediaById() {
		Media newMedia = new Media("TestMedia", "TPath", true, "picture", 2);
		long id = mActivity.helper.mediaHelper.insertMedia(newMedia);

		Media m = mActivity.helper.mediaHelper.getSingleMediaById(id);

		assertNotNull("Should not be null", m);
	}

	public void testGetMediaByName() {
		String name = "Test";

		List<Media> list = mActivity.helper.mediaHelper.getMediaByName(name);

		assertNotNull("Should not be null", list);
	}

	public void testGetMediaByTags() {
		Tag newTag = new Tag("TestTag");
		long idT = mActivity.helper.tagsHelper.insertTag(newTag);
		newTag.setId(idT);
		List<Tag> tList = new ArrayList<Tag>();
		tList.add(newTag);

		List<Media> list = mActivity.helper.mediaHelper.getMediaByTags(tList);

		assertNotNull("Should not be null", list);
	}

	public void testGetMediaByDepartment() {
		Department newDepartment = new Department("TestDep", "Test", 12345678, "test@test.com");
		long idD = mActivity.helper.departmentsHelper.insertDepartment(newDepartment);
		newDepartment.setId(idD);

		List<Media> list = mActivity.helper.mediaHelper.getMediaByDepartment(newDepartment);

		assertNotNull("Should not be null", list);
	}
	
	public void testGetMediaByProfile() {
		Profile newProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idP = mActivity.helper.profilesHelper.insertProfile(newProfile);
		newProfile.setId(idP);
		
		List<Media> list = mActivity.helper.mediaHelper.getMediaByProfile(newProfile);

		assertNotNull("Should not be null", list);
	}
	
	public void testGetMediaById() {
		Media newMedia = new Media("TestMedia", "TPath", true, "picture", 2);
		long id = mActivity.helper.mediaHelper.insertMedia(newMedia);
		newMedia.setId(id);
		
		Media foundMedia = mActivity.helper.mediaHelper.getMediaById(id);
		
		assertEquals("Should return Media; TestMedia", newMedia, foundMedia);
	}
}