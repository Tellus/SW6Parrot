package dk.aau.cs.giraf.oasis.lib.models;

public class MediaProfileAccess {

	private long idProfile;
	private long idMedia;
	private static String _output = "{0}, {1}";
	
	/**
	 * Constructor with arguments
	 * @param idProfile the profile id to set
	 * @param idMedia the media id to set
	 */
	public MediaProfileAccess(long idProfile, long idMedia) {
		this.idProfile = idProfile;
		this.idMedia = idMedia;
	}
	/**
	 * Empty constructor
	 */
	public MediaProfileAccess() {
		
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
	 * @return the idMedia
	 */
	public long getIdMedia() {
		return idMedia;
	}

	/**
	 * @param idMedia the idMedia to set
	 */
	public void setIdMedia(long idMedia) {
		this.idMedia = idMedia;
	}

	/**
	 * @param _output the _output to set
	 */
	public static void set_output(String _output) {
		MediaProfileAccess._output = _output;
	}
	
	@Override
	public String toString() {

		String localOutput = _output;
		localOutput = localOutput.replace("{0}", String.valueOf(getIdProfile()));
		localOutput = localOutput.replace("{1}", String.valueOf(getIdMedia()));
		
		return localOutput;
	}
	
	@Override public boolean equals(Object aMediaProfileAccess) {
		if ( this == aMediaProfileAccess ) return true;

		if ( !(aMediaProfileAccess instanceof MediaProfileAccess) ) return false;

		MediaProfileAccess mediaProfileAccess = (MediaProfileAccess)aMediaProfileAccess;

		return
				EqualsUtil.areEqual(this.getIdProfile(), mediaProfileAccess.getIdProfile()) &&
				EqualsUtil.areEqual(this.getIdMedia(), mediaProfileAccess.getIdMedia());
	}
}
