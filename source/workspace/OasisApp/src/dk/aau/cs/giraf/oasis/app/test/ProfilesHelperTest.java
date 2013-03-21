package dk.aau.cs.giraf.oasis.app.test;

import java.util.List;
import java.util.Random;

import android.test.ActivityInstrumentationTestCase2;
import dk.aau.cs.giraf.oasis.app.MainActivity;
import dk.aau.cs.giraf.oasis.lib.models.Department;
import dk.aau.cs.giraf.oasis.lib.models.Profile;

public class ProfilesHelperTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private MainActivity mActivity;


	public ProfilesHelperTest() {
		super("dk.aau.cs.giraf.oasis.app", MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		setActivityInitialTouchMode(false);

		mActivity = getActivity();
	}
	
	public void testRemoveProfile() {
		Profile newGuard = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long id = mActivity.helper.profilesHelper.insertProfile(newGuard);
		newGuard.setId(id);
		
		Profile newChild = new Profile("TestRemoveChildAttachmentToGuardian", "Child", null, Profile.pRoles.CHILD.ordinal(), 12345678, null, null);
		long idChild = mActivity.helper.profilesHelper.insertProfile(newChild);
		newChild.setId(idChild);
		
		mActivity.helper.profilesHelper.attachChildToGuardian(newChild, newGuard);
		
		int result = mActivity.helper.profilesHelper.removeProfile(newGuard);
		Profile p = mActivity.helper.profilesHelper.getProfileById(id);
		
		List<Profile> guardians = mActivity.helper.profilesHelper.getGuardiansByChild(newChild);
		
		assertNull("Should be null, because Profile is removed", p);
		assertNotSame("Should not return -1", -1, result);
		assertEquals("Should be 0", 0, guardians.size());
	}

	public void testRemoveChildAttachmentToGuardian() {
		Profile newGuard = new Profile("TestRemoveChildAttachmentToGuardian", "Guard", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idGuard = mActivity.helper.profilesHelper.insertProfile(newGuard);
		newGuard.setId(idGuard);
		
		Profile newChild = new Profile("TestRemoveChildAttachmentToGuardian", "Child", null, Profile.pRoles.CHILD.ordinal(), 12345678, null, null);
		long idChild = mActivity.helper.profilesHelper.insertProfile(newChild);
		newChild.setId(idChild);
		
		mActivity.helper.profilesHelper.attachChildToGuardian(newChild, newGuard);
		
		int result = mActivity.helper.profilesHelper.removeChildAttachmentToGuardian(newChild, newGuard);
		
		assertEquals("Should return 1", 1, result);
		assertNotSame("Should not return -1", -1, result);
	}

	public void testInsertProfile() {
		Profile newProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long id = mActivity.helper.profilesHelper.insertProfile(newProfile);

		assertNotSame("Should not be -1", -1, id);
	}

	public void testAttachChildToGuardian() {
		Profile newChild = new Profile("Test", "Child", null, Profile.pRoles.CHILD.ordinal(), 12345678, null, null);
		long idChild = mActivity.helper.profilesHelper.insertProfile(newChild);
		newChild.setId(idChild);
		Profile newGuard = new Profile("Test", "Guard", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idGuard = mActivity.helper.profilesHelper.insertProfile(newGuard);
		newGuard.setId(idGuard);
		int result = mActivity.helper.profilesHelper.attachChildToGuardian(newChild, newGuard);

		assertEquals("Should return 0", 0, result);
		assertNotSame("Should not return -1", -1, result);
	}

	public void testModifyProfile() {
		Profile newProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long id = mActivity.helper.profilesHelper.insertProfile(newProfile);
		newProfile.setId(id);
		int result = mActivity.helper.profilesHelper.modifyProfile(newProfile);

		assertNotSame("Should not be -1", -1, result);
		assertEquals("Should return 1", 1, result);
	}
	
	public void testAuthenticateProfileWithValidCertificate() {
		Random rnd = new Random();
		StringBuilder cert = new StringBuilder();
		for (int i = 0; i < 200; i++)
		{
			cert.append((char)(rnd.nextInt(26) + 97));
		}
		String certificate = cert.toString();
		Profile expectedProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);

		long id = mActivity.helper.profilesHelper.insertProfile(expectedProfile);
		expectedProfile.setId(id);
		
		mActivity.helper.profilesHelper.setCertificate(certificate, expectedProfile);

		Profile actualProfile = mActivity.helper.profilesHelper.authenticateProfile(certificate);

		assertEquals("Should return profile; Test Profile", expectedProfile, actualProfile);
	}
	
	public void testAuthenticateProfileWithInvalidCertificate() {
		Random rnd = new Random();
		StringBuilder cert = new StringBuilder();
		for (int i = 0; i < 200; i++)
		{
			cert.append((char)(rnd.nextInt(26) + 65));
		}
		String certificate = cert.toString();
		Profile expectedProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);

		long id = mActivity.helper.profilesHelper.insertProfile(expectedProfile);
		expectedProfile.setId(id);
		
		mActivity.helper.profilesHelper.setCertificate(certificate, expectedProfile);
				
		Profile actualProfile = mActivity.helper.profilesHelper.authenticateProfile(certificate);

		assertNull("Should return null", actualProfile);
	}
	
	public void testAuthenticateProfileWithShortCertificate() {
		Random rnd = new Random();
		StringBuilder cert = new StringBuilder();
		for (int i = 0; i < 100; i++)
		{
			cert.append((char)(rnd.nextInt(26) + 97));
		}
		String certificate = cert.toString();
		Profile expectedProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);

		long id = mActivity.helper.profilesHelper.insertProfile(expectedProfile);
		expectedProfile.setId(id);
		
		mActivity.helper.profilesHelper.setCertificate(certificate, expectedProfile);

		Profile actualProfile = mActivity.helper.profilesHelper.authenticateProfile(certificate);

		assertNull("Should return null", actualProfile);
	}
	
	public void testAuthenticateProfileWithLongCertificate() {
		Random rnd = new Random();
		StringBuilder cert = new StringBuilder();
		for (int i = 0; i < 300; i++)
		{
			cert.append((char)(rnd.nextInt(26) + 97));
		}
		String certificate = cert.toString();
		Profile expectedProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);

		long id = mActivity.helper.profilesHelper.insertProfile(expectedProfile);
		expectedProfile.setId(id);
		
		mActivity.helper.profilesHelper.setCertificate(certificate, expectedProfile);

		Profile actualProfile = mActivity.helper.profilesHelper.authenticateProfile(certificate);

		assertNull("Should return null", actualProfile);
	}

	public void testSetCertificate() {
		Random rnd = new Random();
		StringBuilder cert = new StringBuilder();
		for (int i = 0; i < 200; i++)
		{
			cert.append((char)(rnd.nextInt(26) + 97));
		}
		String certificate = cert.toString();
		Profile expectedProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long id = mActivity.helper.profilesHelper.insertProfile(expectedProfile);
		expectedProfile.setId(id);
		int result = mActivity.helper.profilesHelper.setCertificate(certificate, expectedProfile);

		assertNotSame("Should not be -1", -1, result);
		assertEquals("Should return 1", 1, result);
	}

	public void testGetProfiles() {
		Profile newChild = new Profile("Test", "Child", null, Profile.pRoles.CHILD.ordinal(), 12345678, null, null);
		long idChild = mActivity.helper.profilesHelper.insertProfile(newChild);
		newChild.setId(idChild);
		
		Profile newGuard = new Profile("Test", "Guard", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idGuard = mActivity.helper.profilesHelper.insertProfile(newGuard);
		newGuard.setId(idGuard);
				
		List<Profile> list = mActivity.helper.profilesHelper.getProfiles();

		assertNotSame("Should not be 0", 0, list.size());
		assertNotNull("Should not be empty", list);
	}

	public void testGetGuardians() {
		Profile newChild = new Profile("Test", "Child", null, Profile.pRoles.CHILD.ordinal(), 12345678, null, null);
		long idChild = mActivity.helper.profilesHelper.insertProfile(newChild);
		newChild.setId(idChild);
		
		Profile newGuard = new Profile("Test", "Guard", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idGuard = mActivity.helper.profilesHelper.insertProfile(newGuard);
		newGuard.setId(idGuard);
		
		List<Profile> list = mActivity.helper.profilesHelper.getGuardians();

		assertNotSame("Should not be 0", 0, list.size());
		assertNotNull("Should not be empty", list);
	}

	public void testGetChildren() {
		Profile newChild = new Profile("Test", "Child", null, Profile.pRoles.CHILD.ordinal(), 12345678, null, null);
		long idChild = mActivity.helper.profilesHelper.insertProfile(newChild);
		newChild.setId(idChild);
		
		Profile newGuard = new Profile("Test", "Guard", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idGuard = mActivity.helper.profilesHelper.insertProfile(newGuard);
		newGuard.setId(idGuard);
		
		List<Profile> list = mActivity.helper.profilesHelper.getChildren();

		assertNotSame("Should not be 0", 0, list.size());
		assertNotNull("Should not be empty", list);
	}

	public void testGetCertificatesByProfile() {
		Profile newProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long id = mActivity.helper.profilesHelper.insertProfile(newProfile);
		newProfile.setId(id);
		List<String> list = mActivity.helper.profilesHelper.getCertificatesByProfile(newProfile);

		assertEquals("Should be 1", 1, list.size());
		assertNotNull("Should not be empty", list);
	}

	public void testGetChildrenByDepartment() {
		Profile newChild = new Profile("Test", "Child1", null, Profile.pRoles.CHILD.ordinal(), 12345678, null, null);
		long idC = mActivity.helper.profilesHelper.insertProfile(newChild);
		newChild.setId(idC);
		
		Department newDepartment = new Department("TestDep", "TestAddress", 12345678, "test@test.dk");
		long idD = mActivity.helper.departmentsHelper.insertDepartment(newDepartment);
		newDepartment.setId(idD);
		
		mActivity.helper.departmentsHelper.attachProfileToDepartment(newChild, newDepartment);
		
		List<Profile> list = mActivity.helper.profilesHelper.getChildrenByDepartment(newDepartment);

		assertEquals("Should be 1", 1, list.size());
		assertNotNull("Should not be empty", list);
	}

	public void testGetChildrenByDepartmentAndSubDepartments() {
		
		Profile newChild1 = new Profile("Test", "Child1", null, Profile.pRoles.CHILD.ordinal(), 12345678, null, null);
		long idC1 = mActivity.helper.profilesHelper.insertProfile(newChild1);
		newChild1.setId(idC1);
		
		Profile newChild2 = new Profile("Test", "Child2", null, Profile.pRoles.CHILD.ordinal(), 12345678, null, null);
		long idC2 = mActivity.helper.profilesHelper.insertProfile(newChild2);
		newChild2.setId(idC2);
		
		Profile newChild3 = new Profile("Test", "Child3", null, Profile.pRoles.CHILD.ordinal(), 12345678, null, null);
		long idC3 = mActivity.helper.profilesHelper.insertProfile(newChild3);
		newChild3.setId(idC3);
		
		Department newDepartment1 = new Department("TestDep1", "TestAddress", 12345678, "test@test.dk");
		long idD1 = mActivity.helper.departmentsHelper.insertDepartment(newDepartment1);
		newDepartment1.setId(idD1);
		
		Department newDepartment2 = new Department("TestDep2", "TestAddress", 12345678, "test@test.dk");
		long idD2 = mActivity.helper.departmentsHelper.insertDepartment(newDepartment2);
		newDepartment2.setId(idD2);
		
		Department newDepartment3 = new Department("TestDep3", "TestAddress", 12345678, "test@test.dk");
		long idD3 = mActivity.helper.departmentsHelper.insertDepartment(newDepartment3);
		newDepartment3.setId(idD3);
		
		mActivity.helper.departmentsHelper.attachProfileToDepartment(newChild1, newDepartment1);
		mActivity.helper.departmentsHelper.attachProfileToDepartment(newChild2, newDepartment2);
		mActivity.helper.departmentsHelper.attachProfileToDepartment(newChild3, newDepartment3);
		
		mActivity.helper.departmentsHelper.attachSubDepartmentToDepartment(newDepartment1, newDepartment2);
		mActivity.helper.departmentsHelper.attachSubDepartmentToDepartment(newDepartment2, newDepartment3);
		
		List<Profile> list = mActivity.helper.profilesHelper.getChildrenByDepartmentAndSubDepartments(newDepartment1);
		
		assertEquals("Should be 3", 3, list.size());
		assertNotNull("Should not be empty", list);
	}

	public void testGetChildrenByGuardian() {
		Profile newChild = new Profile("Test", "Child", null, Profile.pRoles.CHILD.ordinal(), 12345678, null, null);
		long idChild = mActivity.helper.profilesHelper.insertProfile(newChild);
		newChild.setId(idChild);
		
		Profile newGuard = new Profile("Test", "Guard", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idGuard = mActivity.helper.profilesHelper.insertProfile(newGuard);
		newGuard.setId(idGuard);
		
		mActivity.helper.profilesHelper.attachChildToGuardian(newChild, newGuard);
		
		List<Profile> list = mActivity.helper.profilesHelper.getChildrenByGuardian(newGuard);

		assertEquals("Should be 1", 1, list.size());
		assertNotNull("Should not be empty", list);
	}

	public void testGetChildrenWithNoDepartment() {
		Profile newChild = new Profile("Test", "Child", null, Profile.pRoles.CHILD.ordinal(), 12345678, null, null);
		long idChild = mActivity.helper.profilesHelper.insertProfile(newChild);
		newChild.setId(idChild);
		
		Profile newGuard = new Profile("Test", "Guard", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idGuard = mActivity.helper.profilesHelper.insertProfile(newGuard);
		newGuard.setId(idGuard);
				
		List<Profile> list = mActivity.helper.profilesHelper.getChildrenWithNoDepartment();
		
		assertNotSame("Should not be 0", 0, list.size());
		assertNotNull("Should not be empty", list);
	}

	public void testGetProfilesByDepartment() {
		Profile newChild = new Profile("Test", "Child", null, Profile.pRoles.CHILD.ordinal(), 12345678, null, null);
		long idChild = mActivity.helper.profilesHelper.insertProfile(newChild);
		newChild.setId(idChild);
		
		Profile newGuard = new Profile("Test", "Guard", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idGuard = mActivity.helper.profilesHelper.insertProfile(newGuard);
		newGuard.setId(idGuard);
		
		Department newDepartment = new Department("TestDep", "TestAddress", 12345678, "test@test.dk");
		long id = mActivity.helper.departmentsHelper.insertDepartment(newDepartment);
		newDepartment.setId(id);
		
		mActivity.helper.departmentsHelper.attachProfileToDepartment(newGuard, newDepartment);
		mActivity.helper.departmentsHelper.attachProfileToDepartment(newChild, newDepartment);
		
		List<Profile> list = mActivity.helper.profilesHelper.getProfilesByDepartment(newDepartment);
		
		assertEquals("Should be 2", 2, list.size());
		assertNotNull("Should not be empty", list);
	}

	public void testGetGuardiansWithNoDepartment() {
		Profile newChild = new Profile("Test", "Child", null, Profile.pRoles.CHILD.ordinal(), 12345678, null, null);
		long idChild = mActivity.helper.profilesHelper.insertProfile(newChild);
		newChild.setId(idChild);
		
		Profile newGuard = new Profile("Test", "Guard", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idGuard = mActivity.helper.profilesHelper.insertProfile(newGuard);
		newGuard.setId(idGuard);
				
		List<Profile> list = mActivity.helper.profilesHelper.getGuardiansWithNoDepartment();

		assertNotSame("Should not be 0", 0, list.size());
		assertNotNull("Should not be empty", list);
	}

	public void testGetGuardiansByDepartment() {
		Profile newGuard = new Profile("Test", "Guard", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idG = mActivity.helper.profilesHelper.insertProfile(newGuard);
		newGuard.setId(idG);
		
		Department newDepartment = new Department("TestDep", "TestAddress", 12345678, "test@test.dk");
		long id = mActivity.helper.departmentsHelper.insertDepartment(newDepartment);
		newDepartment.setId(id);
		
		mActivity.helper.departmentsHelper.attachProfileToDepartment(newGuard, newDepartment);
		
		List<Profile> list = mActivity.helper.profilesHelper.getGuardiansByDepartment(newDepartment);

		assertNotNull("Should not be empty", list);
		assertEquals("Should be 1", 1, list.size());
	}

	public void testGetGuardiansByChild() {
		Profile newGuard = new Profile("Test", "Guard", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idG = mActivity.helper.profilesHelper.insertProfile(newGuard);
		newGuard.setId(idG);
		
		Profile newChild = new Profile("Test", "Child", null, Profile.pRoles.CHILD.ordinal(), 12345678, null, null);
		long idC = mActivity.helper.profilesHelper.insertProfile(newChild);
		newChild.setId(idC);
		
		mActivity.helper.profilesHelper.attachChildToGuardian(newChild, newGuard);
		
		List<Profile> list = mActivity.helper.profilesHelper.getGuardiansByChild(newChild);
		
		assertNotNull("Should not be empty", list);
		assertEquals("Should be 1", 1, list.size());
	}
	
	public void testGetProfileByValidId() {
		Profile expectedProfile = new Profile("Test", "Profile", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long id = mActivity.helper.profilesHelper.insertProfile(expectedProfile);
		expectedProfile.setId(id);
		Profile foundProfile = mActivity.helper.profilesHelper.getProfileById(id);

		assertEquals("Should return profile; Test Profile", expectedProfile, foundProfile);
	}
	
	public void testGetProfileByInvalidId() {
		Profile foundProfile = mActivity.helper.profilesHelper.getProfileById(Long.MAX_VALUE);

		assertNull("Should return null", foundProfile);
	}
	
	public void testGetProfileByNegativeId() {
		Profile foundProfile = mActivity.helper.profilesHelper.getProfileById(-1);

		assertNull("Should return null", foundProfile);
	}
	
	public void testGetProfilesByName() {
		String name = "Test";
		
		Profile newChild = new Profile("Test", "Child", null, Profile.pRoles.CHILD.ordinal(), 12345678, null, null);
		long idChild = mActivity.helper.profilesHelper.insertProfile(newChild);
		newChild.setId(idChild);
		
		Profile newGuard = new Profile("Test", "Guard", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
		long idGuard = mActivity.helper.profilesHelper.insertProfile(newGuard);
		newGuard.setId(idGuard);
				
		List<Profile> list = mActivity.helper.profilesHelper.getProfilesByName(name);

		assertNotSame("Should not be 0", 0, list.size());
		assertNotNull("Should not be empty", list);
	}
}
