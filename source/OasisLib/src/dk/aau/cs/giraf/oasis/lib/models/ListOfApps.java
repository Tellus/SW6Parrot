package dk.aau.cs.giraf.oasis.lib.models;

public class ListOfApps {
	
	private long idApp;
	private long idProfile;
	private Setting<String, String, String> setting;
	private Stat<String, String, String> stat;
	private static String _output = "{0}, {1}, {2}, {3}";
	
	/**
	 * Constructor with arguments
	 * @param idApp the app id to set
	 * @param idProfile the profile id to set
	 * @param setting the setting to set
	 * @param stat the stat to set
	 */
	public ListOfApps(long idApp, long idProfile, Setting<String, String, String> setting, Stat<String, String, String> stat) {
		this.idApp = idApp;
		this.idProfile = idProfile;
		this.setting = setting;
		this.stat = stat;
	}
	/**
	 * Constructor with arguments
	 * @param idApp the app id to set
	 * @param idProfile the profile id to set
	 */
	public ListOfApps(long idApp, long idProfile) {
		this.idApp = idApp;
		this.idProfile = idProfile;
	}
	/**
	 * Empty constructor 
	 */
	public ListOfApps() {
		
	}
	
	/**
	 * @return the idApp
	 */
	public long getIdApp() {
		return idApp;
	}

	/**
	 * @param idApp the idApp to set
	 */
	public void setIdApp(long idApp) {
		this.idApp = idApp;
	}

	/**
	 * @return the idProfile
	 */
	public long getIdProfile() {
		return idProfile;
	}

	/**
	 * @param idProfile the idProfile to set
	 */
	public void setIdProfile(long idProfile) {
		this.idProfile = idProfile;
	}

	/**
	 * @return the setting
	 */
	public Setting<String, String, String> getSetting() {
		return setting;
	}

	/**
	 * @param setting the setting to set
	 */
	public void setSetting(Setting<String, String, String> setting) {
		this.setting = setting;
	}

	/**
	 * @return the stat
	 */
	public Stat<String, String, String> getStat() {
		return stat;
	}

	/**
	 * @param stat the stat to set
	 */
	public void setStat(Stat<String, String, String> stat) {
		this.stat = stat;
	}

	/**
	 * @param output the _output to set
	 */
	public static void set_output(String output) {
		ListOfApps._output = output;
	}
	
	@Override
	public String toString() {

		String localOutput = _output;
		localOutput = localOutput.replace("{0}", String.valueOf(getIdApp()));
		localOutput = localOutput.replace("{1}", String.valueOf(getIdProfile()));
		
		return localOutput;
	}
	
	@Override public boolean equals(Object aListOfApps) {
		if ( this == aListOfApps ) return true;

		if ( !(aListOfApps instanceof ListOfApps) ) return false;

		ListOfApps listOfApps = (ListOfApps)aListOfApps;

		return
				EqualsUtil.areEqual(this.getIdApp(), listOfApps.getIdApp()) &&
				EqualsUtil.areEqual(this.getIdProfile(), listOfApps.getIdProfile());
	}
}
