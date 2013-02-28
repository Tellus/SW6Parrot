package dk.aau.cs.giraf.oasis.lib.models;

public class HasTag {

	private long idMedia;
	private long idTag;
	private static String _output = "{0}, {1}";
	
	/**
	 * Constructor with arguments
	 * @param idMedia the media id to set
	 * @param idTag the tag id to set
	 */
	public HasTag(long idMedia, long idTag) {
		this.idMedia = idMedia;
		this.idTag = idTag;
	}
	
	/**
	 * Empty constructor
	 */
	public HasTag() {
		
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
	 * @return the idTag
	 */
	public long getIdTag() {
		return idTag;
	}

	/**
	 * @param idTag the idTag to set
	 */
	public void setIdTag(long idTag) {
		this.idTag = idTag;
	}

	/**
	 * @param _output the _output to set
	 */
	public static void set_output(String _output) {
		HasTag._output = _output;
	}
	
	@Override
	public String toString() {

		String localOutput = _output;
		localOutput = localOutput.replace("{0}", String.valueOf(getIdMedia()));
		localOutput = localOutput.replace("{1}", String.valueOf(getIdTag()));
		
		return localOutput;
	}
	
	@Override public boolean equals(Object aHasTag) {
		if ( this == aHasTag ) return true;

		if ( !(aHasTag instanceof HasTag) ) return false;

		HasTag hasTag = (HasTag)aHasTag;

		return
				EqualsUtil.areEqual(this.getIdTag(), hasTag.getIdTag()) &&
				EqualsUtil.areEqual(this.getIdMedia(), hasTag.getIdMedia());
	}
}
