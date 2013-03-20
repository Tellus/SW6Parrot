package dk.aau.cs.giraf.oasis.lib.models;

public class App {

	protected long id; 
	protected String name;
	protected String version;
	protected String icon;
	protected String aPackage;
	protected String activity;
	protected Setting<String, String, String> settings;
	protected Stat<String, String, String> stats;
	protected static String _output = "{0}, {1}, {2}, {3}, {4}, {5}";

	/**
	 * Constructor with arguments
	 * @param name the name to set
	 * @param version the version to set
	 * @param icon the icon to set
	 * @param aPackage the package to set
	 * @param activity the activity to set
	 */
	public App(String name, String version, String icon, String aPackage, String activity) {
		this.name = name;
		this.version = version;
		this.icon = icon;
		this.aPackage = aPackage;
		this.activity = activity;
	}
	/**
	 * Constructor with arguments
	 * @param name the name to set
	 * @param aPackage the package to set
	 * @param activity the activity to set
	 */
	public App(String name, String aPackage, String activity) {
		this.name = name;
		this.version = null;
		this.icon = null;
		this.aPackage = aPackage;
		this.activity = activity;
	}

	/**
	 * Empty constructor
	 */
	public App() {

	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * @return the settings
	 */
	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the aPackage
	 */
	public String getaPackage() {
		return aPackage;
	}

	/**
	 * @param aPackage the aPackage to set
	 */
	public void setaPackage(String aPackage) {
		this.aPackage = aPackage;
	}

	/**
	 * @return the activity
	 */
	public String getActivity() {
		return activity;
	}

	/**
	 * @param activity the activity to set
	 */
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public Setting<String, String, String> getSettings() {
		return settings;
	}
	/**
	 * @param settings the settings to set
	 */
	public void setSettings(Setting<String, String, String> settings) {
		this.settings = settings;
	}
	/**
	 * @return the stats
	 */
	public Stat<String, String, String> getStats() {
		return stats;
	}
	/**
	 * @param stats the stats to set
	 */
	public void setStats(Stat<String, String, String> stats) {
		this.stats = stats;
	}
	/**
	 * Set output
	 * {0} = Id
	 * {1} = Name
	 * {2} = Version
	 * {3} = Icon path
	 * {4} = package
	 * {5} = activity
	 * @param output the output to set
	 */
	public static void setOutput(String output) {
		_output = output;
	}

	/**
	 * toString for app
	 */
	@Override
	public String toString() {

		String localOutput = _output;
		localOutput = localOutput.replace("{0}", String.valueOf(getId()));
		localOutput = localOutput.replace("{1}", getName());
		if (getVersion() != null) {
			localOutput = localOutput.replace("{2}", getVersion());
		}
		if (getIcon() != null) {
			localOutput = localOutput.replace("{3}", getIcon());
		}
		localOutput = localOutput.replace("{4}", getaPackage());
		localOutput = localOutput.replace("{5}", getActivity());

		return localOutput;
	}
	
	@Override public boolean equals(Object anApp) {
		if ( this == anApp ) return true;

		if ( !(anApp instanceof App) ) return false;

		App app = (App)anApp;

		return
				EqualsUtil.areEqual(this.getId(), app.getId()) &&
				EqualsUtil.areEqual(this.getName(), app.getName()) &&
				EqualsUtil.areEqual(this.getVersion(), app.getVersion()) &&
				EqualsUtil.areEqual(this.getIcon(), app.getIcon()) &&
				EqualsUtil.areEqual(this.getaPackage(), app.getaPackage()) &&
				EqualsUtil.areEqual(this.getActivity(), app.getActivity());
	}
}