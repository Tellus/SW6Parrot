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


		// SW6F13 - PARROT ADDITIONAL INFO BEGIN
		if (medias.isEmpty()) {
			/*Media*/
			Media mediaDog = new Media("Dog", "/sdcard/Pictures/giraf/public/dog.jpg", true, "IMAGE", childrenLoaded.get(0).getId());
			Media mediaCat = new Media("Cat", "/sdcard/Pictures/giraf/public/cat.jpg", true, "IMAGE", childrenLoaded.get(1).getId());
			Media mediaBat = new Media("Bat", "/sdcard/Pictures/giraf/private/bat.jpg", false, "IMAGE", childrenLoaded.get(0).getId());
			Media subMedia = new Media("Sound", "/sdcard/Pictures/giraf/public/sound.jpg", true, "IMAGE", childrenLoaded.get(0).getId());
			Media mediaFood = new Media("Food", "/sdcard/Pictures/giraf/public/food.jpg", true, "IMAGE", childrenLoaded.get(2).getId());
			Media mediaHouse = new Media ("House", "/sdcard/Pictures/giraf/private/house.jpg", false, "IMAGE", childrenLoaded.get(3).getId());

			Media mediaBathImage = new Media("Bade","/sdcard/Pictures/giraf/public/Bade.png", true, "IMAGE", childrenLoaded.get(0).getId());
			Media mediaBathWord = new Media("Bade","/sdcard/Pictures/giraf/public/bade.wma", true, "WORD", childrenLoaded.get(0).getId());
			Media mediaThoothImage = new Media("Børste Tænder","/sdcard/Pictures/giraf/public/Børste_Tænder.png", true, "IMAGE", childrenLoaded.get(0).getId());
			Media mediaThoothWord = new Media("Børste Tænder","/sdcard/Pictures/giraf/public/børste_tænder.wma", true, "WORD", childrenLoaded.get(0).getId());
			Media mediaDrinkImage = new Media("Drikke","/sdcard/Pictures/giraf/public/Drikke.png", true, "IMAGE", childrenLoaded.get(0).getId());
			Media mediaDrinkWord = new Media("Drikke","/sdcard/Pictures/giraf/public/drikke.wma", true, "WORD", childrenLoaded.get(0).getId());
			Media mediaYouImage = new Media("Du","/sdcard/Pictures/giraf/public/Du.png", true, "IMAGE", childrenLoaded.get(0).getId());
			Media mediaYouWord = new Media("Du","/sdcard/Pictures/giraf/public/du.wma", true, "WORD", childrenLoaded.get(0).getId());
			Media mediaMovieImage = new Media("Film","/sdcard/Pictures/giraf/public/Film.png", true, "IMAGE", childrenLoaded.get(0).getId());
			Media mediaMovieWord = new Media("Film","/sdcard/Pictures/giraf/public/film.wma", true, "WORD", childrenLoaded.get(0).getId());
			Media mediaHighImage = new Media("For Højt","/sdcard/Pictures/giraf/public/For_Højt.png", true, "IMAGE", childrenLoaded.get(0).getId());
			Media mediaHighWord = new Media("For Højt","/sdcard/Pictures/giraf/public/for_højt.wma", true, "WORD", childrenLoaded.get(0).getId());
			Media mediaWalkImage = new Media("Gå","/sdcard/Pictures/giraf/public/Gå.png", true, "IMAGE", childrenLoaded.get(0).getId());
			Media mediaWalkWord = new Media("Gå","/sdcard/Pictures/giraf/public/gå.wma", true, "WORD", childrenLoaded.get(0).getId());
			Media mediaYesImage = new Media("Ja","/sdcard/Pictures/giraf/public/Ja.png", true, "IMAGE", childrenLoaded.get(0).getId());
			Media mediaYesWord = new Media("Ja","/sdcard/Pictures/giraf/public/ja.wma", true, "WORD", childrenLoaded.get(0).getId());
			Media mediaDriveImage = new Media("Køre","/sdcard/Pictures/giraf/public/Køre.png", true, "IMAGE", childrenLoaded.get(0).getId());
			Media mediaDriveWord = new Media("Køre","/sdcard/Pictures/giraf/public/køre.wma", true, "WORD", childrenLoaded.get(0).getId());
			Media mediaFoodImage = new Media("Lave Mad","/sdcard/Pictures/giraf/public/Lave_Mad.png", true, "IMAGE", childrenLoaded.get(0).getId());
			Media mediaFoodWord = new Media("Lave Mad","/sdcard/Pictures/giraf/public/lave_mad.wma", true, "WORD", childrenLoaded.get(0).getId());
			Media mediaPlayImage = new Media("Lege","/sdcard/Pictures/giraf/public/Lege.png", true, "IMAGE", childrenLoaded.get(0).getId());
			Media mediaWord = new Media("Lege","/sdcard/Pictures/giraf/public/lege.wma", true, "WORD", childrenLoaded.get(0).getId());
			Media mediaMeImage = new Media("Mig","/sdcard/Pictures/giraf/public/Mig.png", true, "IMAGE", childrenLoaded.get(0).getId());
			Media mediaMeWord = new Media("Mig","/sdcard/Pictures/giraf/public/mig.wma", true, "WORD", childrenLoaded.get(0).getId());
			Media mediaMorgenImage = new Media("Morgen Routine","/sdcard/Pictures/giraf/public/Morgen_Routine.png", true, "IMAGE", childrenLoaded.get(0).getId());
			Media mediaMorgenWord = new Media("Morgen Routine","/sdcard/Pictures/giraf/public/morgen_routine.wma", true, "WORD", childrenLoaded.get(0).getId());
			Media mediaNoImage = new Media("Nej","/sdcard/Pictures/giraf/public/Nej.png", true, "IMAGE", childrenLoaded.get(0).getId());
			Media mediaNoWord = new Media("Nej","/sdcard/Pictures/giraf/public/nej.wma", true, "WORD", childrenLoaded.get(0).getId());
			Media mediaSeeImage = new Media("Se","/sdcard/Pictures/giraf/public/Se.png", true, "IMAGE", childrenLoaded.get(0).getId());
			Media mediaSeeWord = new Media("Se","/sdcard/Pictures/giraf/public/se.wma", true, "WORD", childrenLoaded.get(0).getId());
			Media mediaSitdownImage = new Media("Sidde Ned","/sdcard/Pictures/giraf/public/Side_Ned.png", true, "IMAGE", childrenLoaded.get(0).getId());
			Media mediaSitdownWord = new Media("Sidde Ned","/sdcard/Pictures/giraf/public/side_ned.wma", true, "WORD", childrenLoaded.get(0).getId());
			Media mediaGamingImage = new Media("Spille Computer","/sdcard/Pictures/giraf/public/Spille_Computer.png", true, "IMAGE", childrenLoaded.get(0).getId());
			Media mediaGamingWord = new Media("Spille Computer","/sdcard/Pictures/giraf/public/spille_computer.wma", true, "WORD", childrenLoaded.get(0).getId());
			Media mediaStopImage = new Media("Stop","/sdcard/Pictures/giraf/public/Stop.png", true, "IMAGE", childrenLoaded.get(0).getId());
			Media mediaStopWord = new Media("Stop","/sdcard/Pictures/giraf/public/stop.wma", true, "WORD", childrenLoaded.get(0).getId());			
			Media mediaHungryImage = new Media("Sulten","/sdcard/Pictures/giraf/public/Sulten.png", true, "IMAGE", childrenLoaded.get(0).getId());
			Media mediaHungryWord = new Media("Sulten","/sdcard/Pictures/giraf/public/sulten.wma", true, "WORD", childrenLoaded.get(0).getId());			
			Media mediaSleepImage = new Media("Søvnig","/sdcard/Pictures/giraf/public/Søvnig.png", true, "IMAGE", childrenLoaded.get(0).getId());
			Media mediaSleepWord = new Media("Søvnig","/sdcard/Pictures/giraf/public/søvnig.wma", true, "WORD", childrenLoaded.get(0).getId());			
			Media mediaTalkImage = new Media("Tale Sammen","/sdcard/Pictures/giraf/public/Tale_Sammen.png", true, "IMAGE", childrenLoaded.get(0).getId());
			Media mediaTalkWord = new Media("Tale Sammen","/sdcard/Pictures/giraf/public/tale_sammen.wma", true, "WORD", childrenLoaded.get(0).getId());			
			Media mediaThirstyImage = new Media("Tørstig","/sdcard/Pictures/giraf/public/Tørstig.png", true, "IMAGE", childrenLoaded.get(0).getId());
			Media mediaThirstyWord = new Media("Tørstig","/sdcard/Pictures/giraf/public/tørstig.wma", true, "WORD", childrenLoaded.get(0).getId());
			Media mediaSilentImage = new Media("Være Stille","/sdcard/Pictures/giraf/public/Være_Stille.png", true, "IMAGE", childrenLoaded.get(0).getId());
			Media mediaSilentWord = new Media("Være Stille", "/sdcard/Pictures/giraf/public/være_stille.wma", true, "WORD", childrenLoaded.get(0).getId());

			media.add(mediaDog);
			media.add(mediaCat);
			media.add(mediaBat);
			media.add(subMedia);
			media.add(mediaFood);
			media.add(mediaHouse);

			media.add(mediaBathImage);
			media.add(mediaBathWord);
			media.add(mediaThoothImage);
			media.add(mediaThoothWord);
			media.add(mediaDrinkImage);
			media.add(mediaDrinkWord);
			media.add(mediaYouImage);
			media.add(mediaYouWord );
			media.add(mediaMovieImage ); 
			media.add(mediaMovieWord);
			media.add(mediaHighImage);
			media.add(mediaHighWord);
			media.add(mediaWalkImage);
			media.add(mediaWalkWord);
			media.add(mediaYesImage);
			media.add(mediaYesWord);
			media.add(mediaDriveImage);
			media.add(mediaDriveWord);
			media.add(mediaFoodImage);
			media.add(mediaFoodWord);
			media.add(mediaPlayImage);
			media.add(mediaWord);
			media.add(mediaMeImage);
			media.add(mediaMeWord);
			media.add(mediaMorgenImage);
			media.add(mediaMorgenWord);
			media.add(mediaNoImage);
			media.add(mediaNoWord);
			media.add(mediaSeeImage );
			media.add(mediaSeeWord ); 
			media.add(mediaSitdownImage);
			media.add(mediaSitdownWord); 
			media.add(mediaGamingImage);
			media.add(mediaGamingWord);
			media.add(mediaStopImage);
			media.add(mediaStopWord);
			media.add(mediaHungryImage);
			media.add(mediaHungryWord); 
			media.add(mediaSleepImage);
			media.add(mediaSleepWord);
			media.add(mediaTalkImage);
			media.add(mediaTalkWord);
			media.add(mediaThirstyImage);
			media.add(mediaThirstyWord);
			media.add(mediaSilentImage);
			media.add(mediaSilentWord);
			
	
			
			/*Add media*/
			for (Media singleMedia : media) {
					mediaIds.add(mediaHelper.insertMedia(singleMedia));
			}
			
			/*Load media*/
			for (long id : mediaIds) {
				mediaLoaded.add(mediaHelper.getMediaById(id));
			}
			
			ArrayList<Media> imageMedia= new ArrayList<Media>();
			ArrayList<Media> wordMedia= new ArrayList<Media>();
			ArrayList<Tag> tagList= new ArrayList<Tag>();
			//add submedia and tag
			for(Media singleMedia: mediaLoaded)
			{
				if(singleMedia.getMType().equalsIgnoreCase("image"))
				{
					imageMedia.add(singleMedia);
				}
				else if(singleMedia.getMType().equalsIgnoreCase("word"))
				{
					wordMedia.add(singleMedia);
				}
			}
			
			for(Media image : imageMedia)
			{
				Tag newTag =new Tag(image.getName());
				long tagId = tagsHelper.insertTag(newTag);
				newTag = tagsHelper.getTagById(tagId);
				tagList.add(newTag); 
				mediaHelper.addTagsToMedia(tagList, image);
				
				
				for(Media word : wordMedia)
				{
					if(image.getName().equalsIgnoreCase(word.getName()))
					{
						mediaHelper.attachSubMediaToMedia(word, image);						
						mediaHelper.addTagsToMedia(tagList, word);
					}
				}
				tagList.clear();
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
		
		// SW6F13 - PARROT ADDITIONAL INFO END
	
	
		/*Apps*/
		String basePackageName = "dk.aau.cs.giraf.";

		App launcher = new App("Launcher", basePackageName + "launcher", basePackageName + "launcher.HomeActivity");
		App parrot = new App("Parrot", basePackageName + "parrot", basePackageName + "parrot.PARROTActivity");
		App wombat = new App("Wombat", basePackageName + "wombat", basePackageName + "wombat.MainActivity");
		App admin = new App("Admin", basePackageName + "oasis.app", basePackageName + "oasis.app.MainActivity");
		
		// SW6F13
		App zebra = new App("Zebra", basePackageName + "zebra", basePackageName + "zebra.MainActivity");
		App tortoise = new App("TORTOISE", basePackageName + "tortoise", basePackageName + "tortoise.MainActivity");
		App train = new App("Train", basePackageName + "train", basePackageName + "train.MainActivity");
		App croc = new App("Croc", basePackageName + "pictocreator", basePackageName + "train.crocActivity");
		App pictoAdmin = new App("PictoAdmin", basePackageName + "pictoadmin", basePackageName + "train.PictoAdminMain");

		long launcherId = appsHelper.insertApp(launcher);
		long parrotId = appsHelper.insertApp(parrot);
		long wombatId = appsHelper.insertApp(wombat);
		long adminId = appsHelper.insertApp(admin);
		
		// SW6F13
		long zebraId = appsHelper.insertApp(zebra);
		long tortoiseId = appsHelper.insertApp(tortoise);
		long trainId = appsHelper.insertApp(train);
		long crocId = appsHelper.insertApp(croc);
		long pictoAdminId = appsHelper.insertApp(pictoAdmin);

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

		// SW6F13
		if (zebraId == -1) {
			apps = appsHelper.getAppsByName("Zebra");
			if (apps.size() == 1) {
				zebraId = apps.get(0).getId();
			}
		}
		
		if (tortoiseId == -1) {
			apps = appsHelper.getAppsByName("TORTOISE");
			if (apps.size() == 1) {
				tortoiseId = apps.get(0).getId();
			}
		}
		
		if (trainId == -1) {
			apps = appsHelper.getAppsByName("Train");
			if (apps.size() == 1) {
				trainId = apps.get(0).getId();
			}
		}
		
		if (crocId == -1) {
			apps = appsHelper.getAppsByName("Croc");
			if (apps.size() == 1) {
				crocId = apps.get(0).getId();
			}
		}
		
		if (pictoAdminId == -1) {
			apps = appsHelper.getAppsByName("PictoAdmin");
			if (apps.size() == 1) {
				pictoAdminId = apps.get(0).getId();
			}
		}

		/*Get apps*/
		App launcherLoaded = appsHelper.getAppById(launcherId);
		App parrotLoaded = appsHelper.getAppById(parrotId);
		App wombatLoaded = appsHelper.getAppById(wombatId);
		App adminLoaded = appsHelper.getAppById(adminId);
		
		// SW6F13
		App zebraLoaded = appsHelper.getAppById(zebraId);
		App tortoiseLoaded = appsHelper.getAppById(tortoiseId);
		App trainLoaded = appsHelper.getAppById(trainId);
		App crocLoaded = appsHelper.getAppById(crocId);
		App pictoAdminLoaded = appsHelper.getAppById(pictoAdminId);

		/*Attach app to profile*/
		for (Profile guardian : guardiansLoaded) {
			appsHelper.attachAppToProfile(launcherLoaded, guardian);
			appsHelper.attachAppToProfile(parrotLoaded, guardian);
			appsHelper.attachAppToProfile(wombatLoaded, guardian);
			appsHelper.attachAppToProfile(adminLoaded, guardian);
			
			// SW6F13
			appsHelper.attachAppToProfile(zebraLoaded, guardian);
			appsHelper.attachAppToProfile(tortoiseLoaded, guardian);
			appsHelper.attachAppToProfile(trainLoaded, guardian);
			appsHelper.attachAppToProfile(crocLoaded, guardian);
			appsHelper.attachAppToProfile(pictoAdminLoaded, guardian);
		}

		for (Profile child : childrenLoaded) {
			appsHelper.attachAppToProfile(launcherLoaded, child);
			appsHelper.attachAppToProfile(parrotLoaded, child);
			appsHelper.attachAppToProfile(wombatLoaded, child);

			// SW6F13
			appsHelper.attachAppToProfile(zebraLoaded, child);
			appsHelper.attachAppToProfile(tortoiseLoaded, child);
			appsHelper.attachAppToProfile(trainLoaded, child);
			appsHelper.attachAppToProfile(crocLoaded, child);
			appsHelper.attachAppToProfile(pictoAdminLoaded, child);
		}
	}
}