package dk.aau.cs.giraf.oasis.lib.models;

public class AuthUser {

	private long id;
	private String certificate;
	private long aRole;
	private static String _output = "{0}, {1}, {2}";
	public static enum aRole {PROFILE, DEPARTMENT};
	
	/**
	 * Constructor with arguments
	 * @param id the id to set
	 * @param certificate the certificate to set
	 * @param role the role to set
	**/
	public AuthUser(long id, String certificate, long aRole) {
		this.id = id;
		this.certificate = certificate;
		this.aRole = aRole;
	}
	
	/**
	 * Empty constructor
	 */
	public AuthUser() {
		
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
	 * @return the certificate
	 */
	public String getCertificate() {
		return certificate;
	}
	/**
	 * @param certificate the certificate to set
	 */
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
	/**
	 * @return the aRole
	 */
	public long getaRole() {
		return aRole;
	}
	/**
	 * @param aRole the aRole to set
	 */
	public void setaRole(long aRole) {
		this.aRole = aRole;
	}

	/**
	 * Set output
	 * {0} = Id
	 * {1} = Certificate
	 * {2} = Role
	 * @param output the output to set
	 */
	public static void setOutput(String output) {
		_output = output;
	}

	/**
	 * toString for authuser
	 */
	@Override
	public String toString() {

		String localOutput = _output;
		localOutput = localOutput.replace("{0}", String.valueOf(getId()));
		localOutput = localOutput.replace("{1}", getCertificate());
		localOutput = localOutput.replace("{2}", String.valueOf(getaRole()));

		return localOutput;
	}
	@Override public boolean equals(Object aAuthUser) {
		if ( this == aAuthUser ) return true;

		if ( !(aAuthUser instanceof AuthUser) ) return false;

		AuthUser authUser = (AuthUser)aAuthUser;

		return
				EqualsUtil.areEqual(this.getId(), authUser.getId()) &&
				EqualsUtil.areEqual(this.getCertificate(), authUser.getCertificate()) &&
				EqualsUtil.areEqual(this.getaRole(), authUser.getaRole());
	}
}
