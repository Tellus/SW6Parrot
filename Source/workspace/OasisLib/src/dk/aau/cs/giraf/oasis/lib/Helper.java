package dk.aau.cs.giraf.oasis.lib;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import dk.aau.cs.giraf.oasis.lib.controllers.AppsHelper;
import dk.aau.cs.giraf.oasis.lib.controllers.DepartmentsHelper;
import dk.aau.cs.giraf.oasis.lib.controllers.MediaHelper;
import dk.aau.cs.giraf.oasis.lib.controllers.ProfilesHelper;
import dk.aau.cs.giraf.oasis.lib.controllers.ServerHelper;
import dk.aau.cs.giraf.oasis.lib.controllers.TagsHelper;
import dk.aau.cs.giraf.oasis.lib.models.App;
import dk.aau.cs.giraf.oasis.lib.models.Department;
import dk.aau.cs.giraf.oasis.lib.models.Media;
import dk.aau.cs.giraf.oasis.lib.models.Profile;
import dk.aau.cs.giraf.oasis.lib.models.Tag;

// Imports for private controllers
/*
import dk.aau.cs.giraf.oasis.lib.controllers.AuthUsersController;
import dk.aau.cs.giraf.oasis.lib.controllers.HasDepartmentController;
import dk.aau.cs.giraf.oasis.lib.controllers.HasGuardianController;
import dk.aau.cs.giraf.oasis.lib.controllers.HasLinkController;
import dk.aau.cs.giraf.oasis.lib.controllers.HasSubDepartmentController;
import dk.aau.cs.giraf.oasis.lib.controllers.HasTagController;
import dk.aau.cs.giraf.oasis.lib.controllers.ListOfAppsController;
import dk.aau.cs.giraf.oasis.lib.controllers.MediaDepartmentAccessController;
import dk.aau.cs.giraf.oasis.lib.controllers.MediaProfileAccessController;
*/

/**
 * Helper class, instantiating all the helper classes into one
 * @author Admin
 *
 */
public class Helper {

	private static Context _context;
	public ProfilesHelper profilesHelper;
	public MediaHelper mediaHelper;
	public DepartmentsHelper departmentsHelper;
	public AppsHelper appsHelper;
	public TagsHelper tagsHelper;
	public ServerHelper serverHelper;

	//Controllers only for viewDB
	/*
	public AuthUsersController au;
	public HasDepartmentController hd;
	public HasGuardianController hg;
	public HasLinkController hl;
	public HasSubDepartmentController hsd;
	public HasTagController ht;
	public ListOfAppsController loa;
	public MediaDepartmentAccessController mda;
	public MediaProfileAccessController mpa;
	*/

	/**
	 * Constructor
	 * @param context Current context
	 */
	public Helper(Context context){
		_context = context;
		profilesHelper = new ProfilesHelper(_context);
		mediaHelper = new MediaHelper(_context);
		departmentsHelper = new DepartmentsHelper(_context);
		appsHelper = new AppsHelper(_context);
		tagsHelper = new TagsHelper(_context);
		serverHelper = new ServerHelper();

		//Controller only for viewDB
		/*
		au = new AuthUsersController(_context);
		hd = new HasDepartmentController(_context);
		hg = new HasGuardianController(_context);
		hl = new HasLinkController(_context);
		hsd = new HasSubDepartmentController(_context);
		ht = new HasTagController(_context);
		loa = new ListOfAppsController(_context);
		mda = new MediaDepartmentAccessController(_context);
		mpa = new MediaProfileAccessController(_context);
		*/
	}

	//	/**
	//	 * Clear all method
	//	 */
	//	public void ClearAll() {
	//		profilesHelper.clearProfilesTable();
	//		mediaHelper.clearMediaTable();
	//		departmentsHelper.clearDepartmentsTable();
	//		appsHelper.clearAppsTable();
	//		tagsHelper.clearTagsTable();
	//		
	//		//Controller only for viewDB
	//		/*
	//		au.clearAuthUsersTable();
	//		hd.clearHasDepartmentTable();
	//		hg.clearHasGuardianTable();
	//		hl.clearHasLinkTable();
	//		hsd.clearHasSubDepartmentTable();
	//		ht.clearHasTagTable();
	//		loa.clearListOfAppsTable();
	//		mda.clearMediaDepartmentAccessTable();
	//		mpa.clearMediaProfileAccessTable();
	//		*/
	//	}

	/**
	 * Dummy data method
	 */
	public void CreateDummyData() {

		List<String> guardianCertificates = new ArrayList<String>();
		List<String> childCertificates = new ArrayList<String>();
		List<String> departmentCertificates= new ArrayList<String>();
		List<Profile> guardians = new ArrayList<Profile>();
		List<Long> guardiansIds = new ArrayList<Long>();
		List<Profile> guardiansLoaded = new ArrayList<Profile>();
		List<Profile> children = new ArrayList<Profile>();
		List<Long> childrenIds = new ArrayList<Long>();
		List<Profile> childrenLoaded = new ArrayList<Profile>();
		List<Media> media = new ArrayList<Media>();
		List<Long> mediaIds = new ArrayList<Long>();
		List<Media> mediaLoaded = new ArrayList<Media>();

		/*Define hardcoded guardian certificates*/
		guardianCertificates.add("jkkxlagqyrztlrexhzofekyzrnppajeobqxcmunkqhsbrgpxdtqgygnmbhrgnpphaxsjshlpupgakmirhpyfaivvtpynqarxsghhilhkqvpelpreevykxurtppcggkzfaepihlodgznrmbrzgqucstflhmndibuymmvwauvdlyqnnlxkurinuypmqypspmkqavuhfwsh");
		guardianCertificates.add("ldsgjrtvuiwclpjtuxysmyjgpzsqnrtbwbdmgpalnlwtxzdubhbbtkioukaiwgbebhwovfuevykgbbnktnbzhxwugnkkllgjyovisfzzghyuqvxaoscblwqtvujqzgctslihoqetymxfupblcegpfjrfzyrfnjwevgeimxkrdixocyqmaxmyelptofyrsrtrggffmgak");
		guardianCertificates.add("atntrqkxlnftvxquwrwcdloaumdhzkgyglezzsebpvnethrlstvmlorrolymdynjcyonkrtvcuagwigdqqkftsxxhklcnbhznthcqjxnjzzdoqvmfdlxrudcyakvrnfcbohdumawlwmfndjascmvrsoxfjgwzhdvcvqcroxoyjeazmxtrjtlkldoevgdrqvgfbklhtgm");
		guardianCertificates.add("juuaygujymvacvldvhgirtvtumbdtfhmthtumpgqjvlhzvpzmwezifupvfhpjermlckxdvmjfpmqfadepwdvgdtwvoqkruyeuklsrurgirioqiqdzdxnbuemdmezycyncjqkvcjhgfusfggckxaispgbrzcxmtrgztnbshucxpaoodjvqujhwyeccnsxfjgkrjfoszvu");
		guardianCertificates.add("qyrmohbmcnsljhknvggvcdifarowqvpckzxfvlkwnglztjormumiroifttxbqzmybyvbulrvnoxrdidieoxeeayxkohqrwapdnszdnnegsgdnwdoenjlwcgurjtvmufwhjfnkcpyalzkrvzmspdliaodnlkookaszjyurwjclxufomktgucbsaknxztrpkhxutbelrrc");
		guardianCertificates.add("bphiomxvbsricewxcpuzpdtqjdcywlaplsmzjqzayhdyxeawyaeeofkpvfhwaudzwaafihtfuddsbrjhuztepopztbdgcokafnrgqrbaydsryfianltscyitukssklazgubhkdvvjqolmwiyyhuidhyqxoxwabmvdnnxatvzhvkawyiktbswjdcqlustzermuytgqvae");
		guardianCertificates.add("qldstjxxvbdacxfqjfwbjysjzmuobkajrdnofbtewuwpfkrobhqeblvpolnwtrhxiovuepqgssemakkjvpqoworokauseymbhafvmyhcnpdfxvpevsnjvbcwzlbordoaifgjixztsadmhldzbnvbaaxvmhssijnhvrqfretxqxhxvxsjuwcknxbktfigctbwppndwxpj");
		guardianCertificates.add("duzogdegzhtazsqmjwmxfktmnqcbpuxuvgxmbhpkzcnomoxrtrqlfisqdvfmnhmrmssocxifquqtfnzczzznunywesepobaiikgzlaecairmrlcqzdtfrxmispgamrwwcgzvlfnaysrexwdtmhytgpnncelikvrfozixdtsixwipnfactxywyeqvjhosqwekdnbcbcac");
		guardianCertificates.add("wzkbaaogonrkgckgfrjrwdvklpcwpmloamhlfqmytotpqkrixkwqnqamazcbybhjfdalsvqpdpiwlyctcuvtyclgreonxkqqevokjdbjwdcrgkhozleidpnoiwkdmcaylmosmwbfsrcnmlwlstgfljfwdgodinjrjrygeurrxyjpudsqukqkdgwwerlotgafhqhlxszv");
		guardianCertificates.add("osrvixzwyklicmkcwymiccawlbgctvigycafvftciuznhqrrztnyoafqrfuskqdbddrrppnadthngsfsdvooybjfwdfcdzxfdpzyvaxibxcbqnebgifdusgldvdkeonkvdmcmwffghreolhfxhrwgcogpsfayzxsoeyqwddposjdqwwiovnwabefudybzapihunhluaj");

		/*Test if dumme guardians is present in the DB*/
		for (String certificate : guardianCertificates) {
			Profile guardian = profilesHelper.authenticateProfile(certificate); 
			if (guardian != null) {
				guardians.add(guardian);
				guardiansIds.add(guardian.getId());
			}
		}

		/*If dummy guardians are not present in the DB then add them*/
		if (guardians.isEmpty()) {
			/*Guardians*/
			Profile guardian1 = new Profile("Tony", "Stark", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
			Profile guardian2 = new Profile("Steve", "Rogers", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
			Profile guardian3 = new Profile("Bruce", "Banner", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
			Profile guardian4 = new Profile("Thor", "Odinson", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
			Profile guardian5 = new Profile("Natasha", "Romanoff", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
			Profile guardian6 = new Profile("Clint", "Barton", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
			Profile guardian7 = new Profile("Helle", "Klarup", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
			Profile guardian8 = new Profile("Nick", "Fury", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
			Profile guardian9 = new Profile("Bruce", "Wayne", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);
			Profile guardian0 = new Profile("Holger", "Danske", null, Profile.pRoles.GUARDIAN.ordinal(), 12345678, null, null);

			guardians.add(guardian1);
			guardians.add(guardian2);
			guardians.add(guardian3);
			guardians.add(guardian4);
			guardians.add(guardian5);
			guardians.add(guardian6);
			guardians.add(guardian7);
			guardians.add(guardian8);
			guardians.add(guardian9);
			guardians.add(guardian0);

			/*Add guardians*/
			for (Profile guardian : guardians) {
				guardian.setId(profilesHelper.insertProfile(guardian));
				guardiansIds.add(guardian.getId());
			}

			/*Set Hardcoded certificates*/
			for (int i = 0; i < guardians.size(); i++) {
				profilesHelper.setCertificate(guardianCertificates.get(i), guardians.get(i));
			}
		}

		/*Load guardians*/
		for (long id : guardiansIds) {	
			guardiansLoaded.add(profilesHelper.getProfileById(id));
		}

		/*Define hardcoded child certificates*/
		childCertificates.add("childagqyrztlrexhzofekyzrnppajeobqxcmunkqhsbrgpxdtqgygnmbhrgnpphaxsjshlpupgakmirhpyfaivvtpynqarxsghhilhkqvpelpreevykxurtppcggkzfaepihlodgznrmbrzgqucstflhmndibuymmvwauvdlyqnnlxkurinuypmqypspmkqavuhfwsh");
		childCertificates.add("childrtvuiwclpjtuxysmyjgpzsqnrtbwbdmgpalnlwtxzdubhbbtkioukaiwgbebhwovfuevykgbbnktnbzhxwugnkkllgjyovisfzzghyuqvxaoscblwqtvujqzgctslihoqetymxfupblcegpfjrfzyrfnjwevgeimxkrdixocyqmaxmyelptofyrsrtrggffmgak");
		childCertificates.add("childqkxlnftvxquwrwcdloaumdhzkgyglezzsebpvnethrlstvmlorrolymdynjcyonkrtvcuagwigdqqkftsxxhklcnbhznthcqjxnjzzdoqvmfdlxrudcyakvrnfcbohdumawlwmfndjascmvrsoxfjgwzhdvcvqcroxoyjeazmxtrjtlkldoevgdrqvgfbklhtgm");
		childCertificates.add("childgujymvacvldvhgirtvtumbdtfhmthtumpgqjvlhzvpzmwezifupvfhpjermlckxdvmjfpmqfadepwdvgdtwvoqkruyeuklsrurgirioqiqdzdxnbuemdmezycyncjqkvcjhgfusfggckxaispgbrzcxmtrgztnbshucxpaoodjvqujhwyeccnsxfjgkrjfoszvu");
		childCertificates.add("childhbmcnsljhknvggvcdifarowqvpckzxfvlkwnglztjormumiroifttxbqzmybyvbulrvnoxrdidieoxeeayxkohqrwapdnszdnnegsgdnwdoenjlwcgurjtvmufwhjfnkcpyalzkrvzmspdliaodnlkookaszjyurwjclxufomktgucbsaknxztrpkhxutbelrrc");
		childCertificates.add("childmxvbsricewxcpuzpdtqjdcywlaplsmzjqzayhdyxeawyaeeofkpvfhwaudzwaafihtfuddsbrjhuztepopztbdgcokafnrgqrbaydsryfianltscyitukssklazgubhkdvvjqolmwiyyhuidhyqxoxwabmvdnnxatvzhvkawyiktbswjdcqlustzermuytgqvae");
		childCertificates.add("childjxxvbdacxfqjfwbjysjzmuobkajrdnofbtewuwpfkrobhqeblvpolnwtrhxiovuepqgssemakkjvpqoworokauseymbhafvmyhcnpdfxvpevsnjvbcwzlbordoaifgjixztsadmhldzbnvbaaxvmhssijnhvrqfretxqxhxvxsjuwcknxbktfigctbwppndwxpj");
		childCertificates.add("childdegzhtazsqmjwmxfktmnqcbpuxuvgxmbhpkzcnomoxrtrqlfisqdvfmnhmrmssocxifquqtfnzczzznunywesepobaiikgzlaecairmrlcqzdtfrxmispgamrwwcgzvlfnaysrexwdtmhytgpnncelikvrfozixdtsixwipnfactxywyeqvjhosqwekdnbcbcac");
		childCertificates.add("childaogonrkgckgfrjrwdvklpcwpmloamhlfqmytotpqkrixkwqnqamazcbybhjfdalsvqpdpiwlyctcuvtyclgreonxkqqevokjdbjwdcrgkhozleidpnoiwkdmcaylmosmwbfsrcnmlwlstgfljfwdgodinjrjrygeurrxyjpudsqukqkdgwwerlotgafhqhlxszv");
		childCertificates.add("childxzwyklicmkcwymiccawlbgctvigycafvftciuznhqrrztnyoafqrfuskqdbddrrppnadthngsfsdvooybjfwdfcdzxfdpzyvaxibxcbqnebgifdusgldvdkeonkvdmcmwffghreolhfxhrwgcogpsfayzxsoeyqwddposjdqwwiovnwabefudybzapihunhluaj");

		/*Test if dumme children is present in the DB*/
		for (String certificate : childCertificates) {
			Profile child = profilesHelper.authenticateProfile(certificate); 
			if (child != null) {
				children.add(child);
				childrenIds.add(child.getId());
			}
		}

		/*If dummy children are not present in the DB then add them*/
		if (children.isEmpty()) {
			/*Children*/
			Profile child1 = new Profile("William", "Jensen", null, Profile.pRoles.CHILD.ordinal(), 88888888, null, null);
			Profile child2 = new Profile("Noah", "Nielsen", null, Profile.pRoles.CHILD.ordinal(), 88888888, null, null);
			Profile child3 = new Profile("Johnathan", "Doerwald", null, Profile.pRoles.CHILD.ordinal(), 88888888, null, null);
			Profile child4 = new Profile("Magnus", "Pedersen", null, Profile.pRoles.CHILD.ordinal(), 88888888, null, null);
			Profile child5 = new Profile("Mikkel", "Andersen", null, Profile.pRoles.CHILD.ordinal(), 88888888, null, null);
			Profile child6 = new Profile("Ida", "Christiansen", null, Profile.pRoles.CHILD.ordinal(), 88888888, null, null);
			Profile child7 = new Profile("Janet", "Doeleman", null, Profile.pRoles.CHILD.ordinal(), 88888888, null, null);
			Profile child8 = new Profile("Anna", "Sørensen", null, Profile.pRoles.CHILD.ordinal(), 88888888, null, null);
			Profile child9 = new Profile("Lone", "Klarup", null, Profile.pRoles.CHILD.ordinal(), 88888888, null, null);
			Profile child0 = new Profile("Freja", "Lemming", null, Profile.pRoles.CHILD.ordinal(), 88888888, null, null);

			children.add(child1);
			children.add(child2);
			children.add(child3);
			children.add(child4);
			children.add(child5);
			children.add(child6);
			children.add(child7);
			children.add(child8);
			children.add(child9);
			children.add(child0);

			/*Add children*/
			for (Profile child : children) {
				child.setId(profilesHelper.insertProfile(child));
				childrenIds.add(child.getId());
			}

			/*Set Hardcoded certificates*/
			for (int i = 0; i < children.size(); i++) {
				profilesHelper.setCertificate(childCertificates.get(i), children.get(i));
			}
		}

		/*Load children*/
		for (long id : childrenIds) {	
			childrenLoaded.add(profilesHelper.getProfileById(id));
		}

		/*Attach all children to guardian1*/
		for (Profile child : childrenLoaded) {
			profilesHelper.attachChildToGuardian(child, guardiansLoaded.get(0));
		}

		/*Attach one child per guardian*/
		for (int i = 0; i < guardiansLoaded.size(); i++) {
			profilesHelper.attachChildToGuardian(childrenLoaded.get(i), guardiansLoaded.get(i));
		}

		Department department1;
		Department department2;
		Department subdepartment11;
		Department subdepartment12;
		Department subsubdepartment111;
		Department subsubdepartment112;
		Department subsubdepartment121;

		long department1Id;
		long department2Id;
		long subdepartment11Id;
		long subdepartment12Id;
		long subsubdepartment111Id;
		long subsubdepartment112Id;
		long subsubdepartment121Id;

		/*Define hardcoded department certificates*/
		departmentCertificates.add("departmentztlrexhzofekyzrnppajeobqxcmunkqhsbrgpxdtqgygnmbhrgnpphaxsjshlpupgakmirhpyfaivvtpynqarxsghhilhkqvpelpreevykxurtppcggkzfaepihlodgznrmbrzgqucstflhmndibuymmvwauvdlyqnnlxkurinuypmqypspmkqavuhfwsh");
		departmentCertificates.add("departmentldsgjrtvuiwclpjtuxysmyjgpzsqnrtbwbdmgpalnlwtxzdubhbbtkioukaiwgbebhwovfuevykgbbnktnbzhxwugnkkllgjyovisfzzghyuqvxaoscblwqtvujqzgctslihoqetymxfupblcegpfjrfzyrfnjwevgeimxkrdixocyqmaxmyelptofyrsr");
		departmentCertificates.add("departmentatntrqkxlnftvxquwrwcdloaumdhzkgyglezzsebpvnethrlstvmlorrolymdynjcyonkrtvcuagwigdqqkftsxxhklcnbhznthcqjxnjzzdoqvmfdlxrudcyakvrnfcbohdumawlwmfndjascmvrsoxfjgwzhdvcvqcroxoyjeazmxtrjtlkldoevgdrq");
		departmentCertificates.add("departmentjuuaygujymvacvldvhgirtvtumbdtfhmthtumpgqjvlhzvpzmwezifupvfhpjermlckxdvmjfpmqfadepwdvgdtwvoqkruyeuklsrurgirioqiqdzdxnbuemdmezycyncjqkvcjhgfusfggckxaispgbrzcxmtrgztnbshucxpaoodjvqujhwyeccnsxfj");
		departmentCertificates.add("departmentqyrmohbmcnsljhknvggvcdifarowqvpckzxfvlkwnglztjormumiroifttxbqzmybyvbulrvnoxrdidieoxeeayxkohqrwapdnszdnnegsgdnwdoenjlwcgurjtvmufwhjfnkcpyalzkrvzmspdliaodnlkookaszjyurwjclxufomktgucbsaknxztrpk");
		departmentCertificates.add("departmentbphiomxvbsricewxcpuzpdtqjdcywlaplsmzjqzayhdyxeawyaeeofkpvfhwaudzwaafihtfuddsbrjhuztepopztbdgcokafnrgqrbaydsryfianltscyitukssklazgubhkdvvjqolmwiyyhuidhyqxoxwabmvdnnxatvzhvkawyiktbswjdcqlustze");
		departmentCertificates.add("departmentqldstjxxvbdacxfqjfwbjysjzmuobkajrdnofbtewuwpfkrobhqeblvpolnwtrhxiovuepqgssemakkjvpqoworokauseymbhafvmyhcnpdfxvpevsnjvbcwzlbordoaifgjixztsadmhldzbnvbaaxvmhssijnhvrqfretxqxhxvxsjuwcknxbktfigct");

		Department department1Loaded = departmentsHelper.authenticateDepartment(departmentCertificates.get(0));
		Department department2Loaded = departmentsHelper.authenticateDepartment(departmentCertificates.get(1));
		Department subdepartment11Loaded = departmentsHelper.authenticateDepartment(departmentCertificates.get(2));
		Department subdepartment12Loaded = departmentsHelper.authenticateDepartment(departmentCertificates.get(3));
		Department subsubdepartment111Loaded = departmentsHelper.authenticateDepartment(departmentCertificates.get(4));
		Department subsubdepartment112Loaded = departmentsHelper.authenticateDepartment(departmentCertificates.get(5));
		Department subsubdepartment121Loaded = departmentsHelper.authenticateDepartment(departmentCertificates.get(6));

		/*Test if dumme guardians is present in the DB*/
		if (department1Loaded == null) {
			/*Departments*/
			department1 = new Department("Egebakken", "Hjoernet", 88888888, "Egebakken@dep.com");
			department2 = new Department("Birken", "Hjoernet", 88888888, "Birken@dep.com");
			subdepartment11 = new Department("Bikuben", "Hjoernet", 88888888, "Bikuben@dep.com");
			subdepartment12 = new Department("Myretuen", "Hjoernet", 88888888, "Myretuen@dep.com");
			subsubdepartment111= new Department("Hvepseboet", "Hjoernet", 88888888, "Hvepseboet@dep.com");
			subsubdepartment112 = new Department("Musehullet", "Hjoernet", 88888888, "Musehullet@dep.com");
			subsubdepartment121 = new Department("Termitboet", "Hjoernet", 88888888, "Termitboet@dep.com");

			/*Add departments*/
			department1Id = departmentsHelper.insertDepartment(department1);
			department2Id = departmentsHelper.insertDepartment(department2);
			subdepartment11Id = departmentsHelper.insertDepartment(subdepartment11);
			subdepartment12Id = departmentsHelper.insertDepartment(subdepartment12);
			subsubdepartment111Id = departmentsHelper.insertDepartment(subsubdepartment111);
			subsubdepartment112Id = departmentsHelper.insertDepartment(subsubdepartment112);
			subsubdepartment121Id = departmentsHelper.insertDepartment(subsubdepartment121);

			/*Load Departments*/
			department1Loaded = departmentsHelper.getDepartmentById(department1Id);
			department2Loaded = departmentsHelper.getDepartmentById(department2Id);
			subdepartment11Loaded = departmentsHelper.getDepartmentById(subdepartment11Id);
			subdepartment12Loaded = departmentsHelper.getDepartmentById(subdepartment12Id);
			subsubdepartment111Loaded = departmentsHelper.getDepartmentById(subsubdepartment111Id);
			subsubdepartment112Loaded = departmentsHelper.getDepartmentById(subsubdepartment112Id);
			subsubdepartment121Loaded = departmentsHelper.getDepartmentById(subsubdepartment121Id);
			
			departmentsHelper.setCertificate(departmentCertificates.get(0), department1Loaded);
			departmentsHelper.setCertificate(departmentCertificates.get(1), department2Loaded);
			departmentsHelper.setCertificate(departmentCertificates.get(2), subdepartment11Loaded);
			departmentsHelper.setCertificate(departmentCertificates.get(3), subdepartment12Loaded);
			departmentsHelper.setCertificate(departmentCertificates.get(4), subsubdepartment111Loaded);
			departmentsHelper.setCertificate(departmentCertificates.get(5), subsubdepartment112Loaded);
			departmentsHelper.setCertificate(departmentCertificates.get(6), subsubdepartment121Loaded);
		}

		/*Add subdepartments*/
		departmentsHelper.attachSubDepartmentToDepartment(department1Loaded, subdepartment11Loaded);
		departmentsHelper.attachSubDepartmentToDepartment(department1Loaded, subdepartment12Loaded);
		departmentsHelper.attachSubDepartmentToDepartment(subdepartment11Loaded, subsubdepartment111Loaded);
		departmentsHelper.attachSubDepartmentToDepartment(subdepartment11Loaded, subsubdepartment112Loaded);
		departmentsHelper.attachSubDepartmentToDepartment(subdepartment12Loaded, subsubdepartment121Loaded);

		/*Attach guardians to departments*/
		departmentsHelper.attachProfileToDepartment(guardiansLoaded.get(0), department1Loaded);
		departmentsHelper.attachProfileToDepartment(guardiansLoaded.get(1), department2Loaded);
		departmentsHelper.attachProfileToDepartment(guardiansLoaded.get(2), subdepartment11Loaded);
		departmentsHelper.attachProfileToDepartment(guardiansLoaded.get(3), subdepartment12Loaded);
		departmentsHelper.attachProfileToDepartment(guardiansLoaded.get(4), subsubdepartment111Loaded);
		departmentsHelper.attachProfileToDepartment(guardiansLoaded.get(5), subsubdepartment112Loaded);
		departmentsHelper.attachProfileToDepartment(guardiansLoaded.get(6), subsubdepartment121Loaded);
		
		/*Attach children to department*/
		departmentsHelper.attachProfileToDepartment(childrenLoaded.get(0), department1Loaded);
		departmentsHelper.attachProfileToDepartment(childrenLoaded.get(1), department2Loaded);
		departmentsHelper.attachProfileToDepartment(childrenLoaded.get(2), subdepartment11Loaded);
		departmentsHelper.attachProfileToDepartment(childrenLoaded.get(3), subdepartment12Loaded);
		departmentsHelper.attachProfileToDepartment(childrenLoaded.get(4), subsubdepartment111Loaded);
		departmentsHelper.attachProfileToDepartment(childrenLoaded.get(5), subsubdepartment112Loaded);
		departmentsHelper.attachProfileToDepartment(childrenLoaded.get(6), subsubdepartment121Loaded);
		
		List<Media> medias = mediaHelper.getMediaByProfile(childrenLoaded.get(0));
		
		if (medias.isEmpty()) {
			/*Media*/
			Media mediaDog = new Media("Dog", "/sdcard/Pictures/giraf/public/dog.jpg", true, "picture", childrenLoaded.get(0).getId());
			Media mediaCat = new Media("Cat", "/sdcard/Pictures/giraf/public/cat.jpg", true, "picture", childrenLoaded.get(1).getId());
			Media mediaBat = new Media("Bat", "/sdcard/Pictures/giraf/private/bat.jpg", false, "picture", childrenLoaded.get(0).getId());
			Media subMedia = new Media("Sound", "/sdcard/Pictures/giraf/public/sound.jpg", true, "picture", childrenLoaded.get(0).getId());
			Media mediaFood = new Media("Food", "/sdcard/Pictures/giraf/public/food.jpg", true, "picture", childrenLoaded.get(2).getId());
			Media mediaHouse = new Media ("House", "/sdcard/Pictures/giraf/private/house.jpg", false, "picture", childrenLoaded.get(3).getId());
			
			media.add(mediaDog);
			media.add(mediaCat);
			media.add(mediaBat);
			media.add(subMedia);
			media.add(mediaFood);
			media.add(mediaHouse);

			/*Add media*/
			for (Media singleMedia : media) {
				mediaIds.add(mediaHelper.insertMedia(singleMedia));
			}

			/*Load media*/
			for (long id : mediaIds) {
				mediaLoaded.add(mediaHelper.getMediaById(id));
			}

			/*Attach submedia to media*/
			for (Media singleMedia : mediaLoaded) {
				if (!singleMedia.getName().equals(subMedia.getName())) {
					mediaHelper.attachSubMediaToMedia(mediaLoaded.get(3), singleMedia);
				}
			}

			/*Attach media to profile*/
			for (Media singleMedia : mediaLoaded) {
				for (Profile child : childrenLoaded) {
					mediaHelper.attachMediaToProfile(singleMedia, child, null);
				}
			}

			/*Attach media to department*/
			for (Media singleMedia : mediaLoaded) {
				mediaHelper.attachMediaToDepartment(singleMedia, department1Loaded, null);
			}
		}
		
		/*Tags*/
		Tag tag1 = new Tag("Dog");
		Tag tag2 = new Tag("Cat");
		Tag tag3 = new Tag("Bat");
		Tag tag4 = new Tag("Sound");
		Tag tag5 = new Tag("Picture");

		long tag1Id = tagsHelper.insertTag(tag1);
		long tag2Id = tagsHelper.insertTag(tag2);
		long tag3Id = tagsHelper.insertTag(tag3);
		long tag4Id = tagsHelper.insertTag(tag4);
		long tag5Id = tagsHelper.insertTag(tag5);

		Tag tag1Loaded = tagsHelper.getTagById(tag1Id);
		Tag tag2Loaded = tagsHelper.getTagById(tag2Id);
		Tag tag3Loaded = tagsHelper.getTagById(tag3Id);
		Tag tag4Loaded = tagsHelper.getTagById(tag4Id);
		Tag tag5Loaded = tagsHelper.getTagById(tag5Id);

		List<Tag> tags1 = new ArrayList<Tag>();
		tags1.add(tag1Loaded);
		tags1.add(tag5Loaded);
		List<Tag> tags2 = new ArrayList<Tag>();
		tags2.add(tag2Loaded);
		tags2.add(tag5Loaded);
		List<Tag> tags3 = new ArrayList<Tag>();
		tags3.add(tag3Loaded);
		tags3.add(tag5Loaded);
		List<Tag> tags4 = new ArrayList<Tag>();
		tags4.add(tag4Loaded);
		
		if (!mediaLoaded.isEmpty()) {
			/*Attach tag to media*/
			mediaHelper.addTagsToMedia(tags1, mediaLoaded.get(0));
			mediaHelper.addTagsToMedia(tags2, mediaLoaded.get(1));
			mediaHelper.addTagsToMedia(tags3, mediaLoaded.get(2));
			mediaHelper.addTagsToMedia(tags4, mediaLoaded.get(3));
		}
		
		/*Apps*/
		String basePackageName = "dk.aau.cs.giraf.";

		App launcher = new App("Launcher", basePackageName + "launcher", basePackageName + "launcher.HomeActivity");
		App parrot = new App("Parrot", basePackageName + "parrot", basePackageName + "parrot.PARROTActivity");
		App wombat = new App("Wombat", basePackageName + "wombat", basePackageName + "wombat.MainActivity");
		App admin = new App("Admin", basePackageName + "oasis.app", basePackageName + "oasis.app.MainActivity");

		long launcherId = appsHelper.insertApp(launcher);
		long parrotId = appsHelper.insertApp(parrot);
		long wombatId = appsHelper.insertApp(wombat);
		long adminId = appsHelper.insertApp(admin);

		List<App> apps = new ArrayList<App>();

		if (launcherId == -1) {
			apps = appsHelper.getAppsByName("Launcher");
			if (apps.size() == 1) {
				launcherId = apps.get(0).getId();
			}
		}

		if (parrotId == -1) {
			apps = appsHelper.getAppsByName("Parrot");
			if (apps.size() == 1) {
				parrotId = apps.get(0).getId();
			}
		}

		if (wombatId == -1) {
			apps = appsHelper.getAppsByName("Wombat");
			if (apps.size() == 1) {
				wombatId = apps.get(0).getId();
			}
		}

		if (adminId == -1) {
			apps = appsHelper.getAppsByName("Admin");
			if (apps.size() == 1) {
				adminId = apps.get(0).getId();
			}
		}

		/*Get apps*/
		App launcherLoaded = appsHelper.getAppById(launcherId);
		App parrotLoaded = appsHelper.getAppById(parrotId);
		App wombatLoaded = appsHelper.getAppById(wombatId);
		App adminLoaded = appsHelper.getAppById(adminId);

		/*Attach app to profile*/
		for (Profile guardian : guardiansLoaded) {
			appsHelper.attachAppToProfile(launcherLoaded, guardian);
			appsHelper.attachAppToProfile(parrotLoaded, guardian);
			appsHelper.attachAppToProfile(wombatLoaded, guardian);
			appsHelper.attachAppToProfile(adminLoaded, guardian);
		}

		for (Profile child : childrenLoaded) {
			appsHelper.attachAppToProfile(launcherLoaded, child);
			appsHelper.attachAppToProfile(parrotLoaded, child);
			appsHelper.attachAppToProfile(wombatLoaded, child);
		}
	}
}