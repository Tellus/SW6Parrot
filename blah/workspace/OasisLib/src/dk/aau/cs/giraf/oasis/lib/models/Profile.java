package dk.aau.cs.giraf.oasis.lib.models;

public class Profile {

	private long id;
	private String firstname;
	private String surname;
	private String middlename;
	private long pRole;
	private long phone;
	private String picture;
	private Setting<String, String, String> settings;
	private static String _output = "{0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}";
	public static enum pRoles {ADMIN, PARENT, GUARDIAN, CHILD};

	/**
	 * Constructor with arguments
	 * 
	 * @param firstname the firstname to set
	 * @param surname the surname to set
	 * @param middlename the middlename to set
	 * @param pRole the role to set
	 * @param phone the phone to set
	 * @param picture the picture to set
	 * @param settings the settings to set
	 */
	public Profile(String firstname, String surname, String middlename, long pRole, long phone, String picture, Setting<String, String, String> settings) {
		this.firstname = firstname;
		this.surname = surname;
		this.middlename = middlename;
		this.pRole = pRole;
		this.phone = phone;
		this.picture = picture;
		this.settings = settings;
	}

	/**
	 * Empty constructor
	 */
	public Profile() {

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
	public String getFirstname() {
		return firstname;
	}
	/**
	 * @param firstname the name to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}
	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
	/**
	 * @return the middlename
	 */
	public String getMiddlename() {
		return middlename;
	}
	/**
	 * @param middlename the middlename to set
	 */
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	/**
	 * @return the pRole
	 */
	public long getPRole() {
		return pRole;
	}
	/**
	 * @param pRole the role to set
	 */
	public void setPRole(long pRole) {
		this.pRole = pRole;
	}
	/**
	 * @return the phone
	 */
	public long getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(long phone) {
		this.phone = phone;
	}
	/**
	 * @return the picture path
	 */
	public String getPicture() {
		return picture;
	}
	/**
	 * @param picture the picture to set
	 */
	public void setPicture(String picture) {
		this.picture = picture;
	}
	/**
	 * @return the settings
	 */
	public Setting<String, String, String> getSettings() {
		return settings;
	}

	/***
	 * Set settings
	 * @param settings The settings to change
	 */
	public void setSettings(Setting<String, String, String> settings) {
		this.settings = settings;
	}

	/**
	 * Set output
	 *  {0} = ID
	 *  {1} = Firstname
	 *  {2} = Middlename
	 *  {3} = Surname
	 *  {4} = Role
	 *  {5} = Phone
	 *  {6} = Picture
	 *  {7} = Settings
	 * @param output String to set
	 */
	public static void setOutput(String output) {
		_output = output;
	}

	@Override
	/**
	 * toString for profile
	 */
	public String toString() {
		String localOutput = _output;
		localOutput = localOutput.replace("{0}", String.valueOf(getId()));
		localOutput = localOutput.replace("{1}", getFirstname());
		if (getMiddlename() != null) {
			localOutput = localOutput.replace("{2}", getMiddlename());
		} else {
			localOutput = localOutput.replace("{2}, ", "");
		}
		localOutput = localOutput.replace("{3}", getSurname());
		localOutput = localOutput.replace("{4}", String.valueOf(getPRole()));
		localOutput = localOutput.replace("{5}", String.valueOf(getPhone()));
		if (getPicture() != null) {
			localOutput = localOutput.replace("{6}", getPicture());
		} else {
			localOutput = localOutput.replace("{6}, ", "");
		}
		
//		localOutput = localOutput.replace("{7}", "Setting(Static Setting text)");
//		localOutput = localOutput.replace("{7}", getSetting().toString());

		return localOutput;
	}
	
	@Override public boolean equals(Object aProfile) {
		if ( this == aProfile ) return true;

		if ( !(aProfile instanceof Profile) ) return false;

		Profile profile = (Profile)aProfile;

		return
				EqualsUtil.areEqual(this.getId(), profile.getId()) &&
				EqualsUtil.areEqual(this.getFirstname(), profile.getFirstname()) &&
				EqualsUtil.areEqual(this.getMiddlename(), profile.getMiddlename()) &&
				EqualsUtil.areEqual(this.getSurname(), profile.getSurname()) &&
				EqualsUtil.areEqual(this.getPRole(), profile.getPRole()) &&
				EqualsUtil.areEqual(this.getPhone(), profile.getPhone()) &&
				EqualsUtil.areEqual(this.getPicture(), profile.getPicture());
	}
}
