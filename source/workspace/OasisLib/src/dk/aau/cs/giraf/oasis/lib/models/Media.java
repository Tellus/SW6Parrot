package dk.aau.cs.giraf.oasis.lib.models;


public class Media {

	private long id;
	private String mPath;
	private String name;
	private boolean mPublic;
	private String mType;
	private long ownerId;
	private static String _output = "{0}, {1}, {2}, {3}, {4}, {5}";

	/**
	 * Constructor with arguments
	 * @param name the name to set
	 * @param mPath the path to set
	 * @param mPublic the public to set
	 * @param mType the type to set
	 * @param ownerId the ownerID to set
	 */
	public Media(String name, String mPath, boolean mPublic, String mType, long ownerId) {
		this.name = name;
		this.mPath = mPath;
		this.mPublic = mPublic;
		this.mType = mType;
		this.ownerId = ownerId;
	}

	/**
	 * Empty constructor
	 */
	public Media() {
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
	 * @return the path
	 */
	public String getMPath() {
		return mPath;
	}
	/**
	 * @param mPath the path to set
	 */
	public void setMPath(String mPath) {
		this.mPath = mPath;
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
	 * @return the mPublic
	 */
	public boolean isMPublic() {
		return mPublic;
	}
	/**
	 * @param mPublic the public to set
	 */
	public void setMPublic(boolean mPublic) {
		this.mPublic = mPublic;
	}
	/**
	 * @return the mType
	 */
	public String getMType() {
		return mType;
	}
	/**
	 * @param mType the type to set
	 */
	public void setMType(String mType) {
		this.mType = mType;
	}
	/**
	 * @return the ownerId
	 */
	public long getOwnerId() {
		return ownerId;
	}
	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 *  Set output
	 *  {0} = ID
	 *  {1} = Path
	 *  {2} = Name
	 *  {3} = Public
	 *  {4} = Type
	 *  {5} = OwnerId
	 * @param output
	 */
	public static void setOutput(String output) {
		_output = output;
	}

	@Override
	/**
	 * toString for Media
	 */
	public String toString() {
		String localOutput = _output;
		localOutput = localOutput.replace("{0}", String.valueOf(getId()));
		localOutput = localOutput.replace("{1}", getMPath());
		localOutput = localOutput.replace("{2}", getName());
		localOutput = localOutput.replace("{3}", String.valueOf(isMPublic()));
		localOutput = localOutput.replace("{4}", getMType());
		localOutput = localOutput.replace("{5}", String.valueOf(getOwnerId())); 

		return localOutput;
	}

	@Override public boolean equals(Object aMedia) {
		if ( this == aMedia ) return true;

		if ( !(aMedia instanceof Media) ) return false;

		Media media = (Media)aMedia;

		return
				EqualsUtil.areEqual(this.getId(), media.getId()) &&
				EqualsUtil.areEqual(this.getMPath(), media.getMPath()) &&
				EqualsUtil.areEqual(this.getName(), media.getName()) &&
				EqualsUtil.areEqual(this.isMPublic(), media.isMPublic()) &&
				EqualsUtil.areEqual(this.getMType(), media.getMType()) &&
				EqualsUtil.areEqual(this.getOwnerId(), media.getOwnerId());
	}
}
