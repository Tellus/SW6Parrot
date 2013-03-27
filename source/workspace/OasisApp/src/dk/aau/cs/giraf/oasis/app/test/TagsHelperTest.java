package dk.aau.cs.giraf.oasis.app.test;

import java.util.List;

import android.test.ActivityInstrumentationTestCase2;
import dk.aau.cs.giraf.oasis.app.MainActivity;
import dk.aau.cs.giraf.oasis.lib.models.Tag;

public class TagsHelperTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private MainActivity mActivity;


	public TagsHelperTest() {
		super("dk.aau.cs.giraf.oasis.app", MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		setActivityInitialTouchMode(false);

		mActivity = getActivity();
	}

	public void testRemoveTag() {
		Tag newTag = new Tag("TestTag");
		long idT = mActivity.helper.tagsHelper.insertTag(newTag);
		newTag.setId(idT);

		int result = mActivity.helper.tagsHelper.removeTag(newTag);
		Tag foundTag = mActivity.helper.tagsHelper.getTagById(idT);

		assertNull("Should be null, because Tag is removed", foundTag);
		assertNotSame("Should not return -1", -1, result);
	}

	public void testInsertTag() {
		Tag newTag = new Tag("TestTag");

		long idT = mActivity.helper.tagsHelper.insertTag(newTag);

		assertNotSame("Should not return -1", -1, idT);
	}

	public void testModifyTag() {
		Tag newTag = new Tag("TestTag");
		long idT = mActivity.helper.tagsHelper.insertTag(newTag);
		newTag.setId(idT);

		int result = mActivity.helper.tagsHelper.modifyTag(newTag);

		assertEquals("Should return 1", 1, result);
		assertNotSame("Should not return -1", -1, result);
	}
	
	public void testGetTags() {
		Tag newTag = new Tag("TestTag");
		long idT = mActivity.helper.tagsHelper.insertTag(newTag);
		newTag.setId(idT);

		List<Tag> list = mActivity.helper.tagsHelper.getTags();
		
		assertNotSame("Should not be 0", 0, list.size());
		assertNotNull("Should not be null", list);
	}
	
	public void testGetTagsByCaption() {
		String caption = "TestTag";
		
		Tag newTag = new Tag("TestTag");
		long idT = mActivity.helper.tagsHelper.insertTag(newTag);
		newTag.setId(idT);
		
		List<Tag> list = mActivity.helper.tagsHelper.getTagsByCaption(caption);
		
		assertNotSame("Should not be 0", 0, list.size());
		assertNotNull("Should not be null", list);
	}
	
	public void testGetTagById() {
		Tag newTag = new Tag("TestTag");
		long idT = mActivity.helper.tagsHelper.insertTag(newTag);
		newTag.setId(idT);
		
		Tag foundTag = mActivity.helper.tagsHelper.getTagById(idT);
		
		assertEquals("Should return Tag; TestTag", newTag, foundTag);
	}
}