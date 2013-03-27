package dk.aau.cs.giraf.oasis.app.test;

import java.util.List;
import java.util.Random;

import android.test.ActivityInstrumentationTestCase2;
import dk.aau.cs.giraf.oasis.app.MainActivity;
import dk.aau.cs.giraf.oasis.lib.models.Department;
import dk.aau.cs.giraf.oasis.lib.models.Media;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

public class DepartmentsHelperTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private MainActivity mActivity;


	public DepartmentsHelperTest() {
		super("dk.aau.cs.giraf.oasis.app", MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		setActivityInitialTouchMode(false);

		mActivity = getActivity();
	}

	public void testRemoveDepartment() {
		Department newDepartment = new Department("TestDep", "Test", 12345678, "test@test.com");
		long id = mActivity.helper.departmentsHelper.insertDepartment(newDepartment);
		newDepartment.setId(id);
		int result = mActivity.helper.departmentsHelper.removeDepartment(newDepartment);
		Department d = mActivity.helper.departmentsHelper.getDepartmentById(id);

		assertNull("Should be null, because Department is removed", d);
		assertNotSame("Should not return -1", -1, result);
	}

	public void testRemoveProfileAttachmentToDepartment() {
		Profile newProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idP = mActivity.helper.profilesHelper.insertProfile(newProfile);
		newProfile.setId(idP);
		Department newDepartment = new Department("TestDep", "Test", 12345678, "test@test.com");
		long idD = mActivity.helper.departmentsHelper.insertDepartment(newDepartment);
		newDepartment.setId(idD);
		
		mActivity.helper.departmentsHelper.attachProfileToDepartment(newProfile, newDepartment);
		int result = mActivity.helper.departmentsHelper.removeProfileAttachmentToDepartment(newProfile, newDepartment);
		
		assertEquals("Should return 1", 1, result);
		assertNotSame("Should not return -1", -1, result);
	}
	
	public void testRemoveSubDepartmentAttachmentToDepartment() {
		Department newSubDepartment = new Department("TestSubDep", "Test", 12345678, "test@test.com");
		long idSD = mActivity.helper.departmentsHelper.insertDepartment(newSubDepartment);
		newSubDepartment.setId(idSD);
		Department newDepartment = new Department("TestDep", "Test", 12345678, "test@test.com");
		long idD = mActivity.helper.departmentsHelper.insertDepartment(newDepartment);
		newDepartment.setId(idD);
		
		mActivity.helper.departmentsHelper.attachSubDepartmentToDepartment(newDepartment, newSubDepartment);
		int result = mActivity.helper.departmentsHelper.removeSubDepartmentAttachmentToDepartment(newDepartment, newSubDepartment);
		
		assertEquals("Should return 1", 1, result);
		assertNotSame("Should not return -1", -1, result);
	}
	
	public void testRemoveMediaAttachmentToDepartment() {
		Media newMedia = new Media("TestMedia", "testpath", true, "picture", 2);
		long idM = mActivity.helper.mediaHelper.insertMedia(newMedia);
		newMedia.setId(idM);
		Department newDepartment = new Department("TestDep", "Test", 12345678, "test@test.com");
		long idD = mActivity.helper.departmentsHelper.insertDepartment(newDepartment);
		newDepartment.setId(idD);
		
		mActivity.helper.departmentsHelper.attachMediaToDepartment(newMedia, newDepartment);
		int result = mActivity.helper.departmentsHelper.removeMediaAttachmentToDepartment(newMedia, newDepartment);
		
		assertEquals("Should return 1", 1, result);
		assertNotSame("Should not return -1", -1, result);
	}
	
	public void testInsertDepartment() {
		Department newDepartment = new Department("TestDep", "Test", 12345678, "test@test.com");
		long idD = mActivity.helper.departmentsHelper.insertDepartment(newDepartment);
		
		assertNotSame("Should not be -1", -1, idD);
	}
	
	public void testAttachProfileToDepartment() {
		Profile newProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idP = mActivity.helper.profilesHelper.insertProfile(newProfile);
		newProfile.setId(idP);
		Department newDepartment = new Department("TestDep", "Test", 12345678, "test@test.com");
		long idD = mActivity.helper.departmentsHelper.insertDepartment(newDepartment);
		newDepartment.setId(idD);
		
		int result = mActivity.helper.departmentsHelper.attachProfileToDepartment(newProfile, newDepartment);
		
		assertEquals("Should return 0", 0, result);
		assertNotSame("Should not return -1", -1, result);
	}
	
	public void testAttachSubDepartmentToDepartment() {
		Department newSubDepartment = new Department("TestSubDep", "Test", 12345678, "test@test.com");
		long idSD = mActivity.helper.departmentsHelper.insertDepartment(newSubDepartment);
		newSubDepartment.setId(idSD);
		Department newDepartment = new Department("TestDep", "Test", 12345678, "test@test.com");
		long idD = mActivity.helper.departmentsHelper.insertDepartment(newDepartment);
		newDepartment.setId(idD);
		
		int result = mActivity.helper.departmentsHelper.attachSubDepartmentToDepartment(newDepartment, newSubDepartment);
		
		assertEquals("Should return 0", 0, result);
		assertNotSame("Should not return -1", -1, result);
	}
	
	public void testAttachMediaToDepartment() {
		Media newMedia = new Media("TestMedia", "testpath", true, "picture", 2);
		long idM = mActivity.helper.mediaHelper.insertMedia(newMedia);
		newMedia.setId(idM);
		Department newDepartment = new Department("TestDep", "Test", 12345678, "test@test.com");
		long idD = mActivity.helper.departmentsHelper.insertDepartment(newDepartment);
		newDepartment.setId(idD);
		
		int result = mActivity.helper.departmentsHelper.attachMediaToDepartment(newMedia, newDepartment);
		
		assertEquals("Should return 0", 0, result);
		assertNotSame("Should not return -1", -1, result);
	}
	
	public void testModifyDepartment() {
		Department newDepartment = new Department("TestDep", "Test", 12345678, "test@test.com");
		long idD = mActivity.helper.departmentsHelper.insertDepartment(newDepartment);
		newDepartment.setId(idD);
		
		int result = mActivity.helper.departmentsHelper.modifyDepartment(newDepartment);
		
		assertEquals("Should return 1", 1, result);
		assertNotSame("Should not return -1", -1, result);
	}
	
	public void testAuthenticateDepartment() {
		Random rnd = new Random();
		StringBuilder cert = new StringBuilder();
		for (int i = 0; i < 200; i++)
		{
			cert.append((char)(rnd.nextInt(26) + 97));
		}
		String certificate = cert.toString();
		Department newDepartment = new Department("TestDep", "Test", 12345678, "test@test.com");
		long idD = mActivity.helper.departmentsHelper.insertDepartment(newDepartment);
		newDepartment.setId(idD);
		
		mActivity.helper.departmentsHelper.setCertificate(certificate, newDepartment);
		Department d = mActivity.helper.departmentsHelper.authenticateDepartment(certificate);
		
		assertEquals("Should return Department; TestDep", newDepartment, d);
	}
	
	public void testSetCertificate() {
		Random rnd = new Random();
		StringBuilder cert = new StringBuilder();
		for (int i = 0; i < 200; i++)
		{
			cert.append((char)(rnd.nextInt(26) + 97));
		}
		String certificate = cert.toString();
		Department newDepartment = new Department("TestDep", "Test", 12345678, "test@test.com");
		long idD = mActivity.helper.departmentsHelper.insertDepartment(newDepartment);
		newDepartment.setId(idD);
		
		int result = mActivity.helper.departmentsHelper.setCertificate(certificate, newDepartment);

		assertNotSame("Should not be -1", -1, result);
		assertEquals("Should return 1", 1, result);
	}
	
	public void testGetDepartments() {
		List<Department> list = mActivity.helper.departmentsHelper.getDepartments();
		
		assertNotNull("Should not be empty", list);
	}
	
	public void testGetCertificatesByDepartment() {
		Department newDepartment = new Department("TestDep", "Test", 12345678, "test@test.com");
		long idD = mActivity.helper.departmentsHelper.insertDepartment(newDepartment);
		newDepartment.setId(idD);
		
		List<String> list = mActivity.helper.departmentsHelper.getCertificatesByDepartment(newDepartment);
		
		assertNotNull("Should not be empty", list);
	}
	
	public void testGetDepartmentById() {
		Department newDepartment = new Department("TestDep", "Test", 12345678, "test@test.com");
		long idD = mActivity.helper.departmentsHelper.insertDepartment(newDepartment);
		newDepartment.setId(idD);
		
		Department foundDepartment = mActivity.helper.departmentsHelper.getDepartmentById(idD);
		
		assertEquals("Should return Department; TestDep", newDepartment, foundDepartment);
	}
	
	public void testGetDepartmentByName() {
		String name = "Test";
		List<Department> list = mActivity.helper.departmentsHelper.getDepartmentByName(name);

		assertNotNull("Should not be empty", list);
	}
	
	public void testGetDepartmentsByProfile() {
		Profile newProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idP = mActivity.helper.profilesHelper.insertProfile(newProfile);
		newProfile.setId(idP);
		
		List<Department> list = mActivity.helper.departmentsHelper.getDepartmentsByProfile(newProfile);

		assertNotNull("Should not be empty", list);
	}
	
	public void testGetSubDepartments() {
		Department newDepartment = new Department("TestDep", "Test", 12345678, "test@test.com");
		long idD = mActivity.helper.departmentsHelper.insertDepartment(newDepartment);
		newDepartment.setId(idD);
		
		List<Department> list = mActivity.helper.departmentsHelper.getSubDepartments(newDepartment);

		assertNotNull("Should not be empty", list);
	}
}