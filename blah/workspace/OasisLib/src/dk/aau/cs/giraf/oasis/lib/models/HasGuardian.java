package dk.aau.cs.giraf.oasis.lib.models;

public class HasGuardian {

	private long idGuardian;
	private long idChild;
	private static String _output = "{0}, {1}";
	/**
	 * Constructor with arguments
	 * @param idGuardian the guardian id to set
	 * @param idChild the child id to set
	 */
	public HasGuardian(long idGuardian, long idChild) {
		this.idGuardian = idGuardian;
		this.idChild = idChild;
	}
	/**
	 * Empty constructor
	 */
	public HasGuardian() {
		
	}
	/**
	 * @return the idGuardian
	 */
	public long getIdGuardian() {
		return idGuardian;
	}
	
	/**
	 * @param idGuardian the idGuardian to set
	 */
	public void setIdGuardian(long idGuardian) {
		this.idGuardian = idGuardian;
	}
	
	/**
	 * @return the idChild
	 */
	public long getIdChild() {
		return idChild;
	}
	
	/**
	 * @param idChild the idChild to set
	 */
	public void setIdChild(long idChild) {
		this.idChild = idChild;
	}

	/**
	 * @param _output the _output to set
	 */
	public static void set_output(String _output) {
		HasGuardian._output = _output;
	}
	
	@Override
	public String toString() {
		String localOutput = _output;
		
		localOutput = localOutput.replace("{0}", String.valueOf(getIdGuardian()));
		localOutput = localOutput.replace("{1}", String.valueOf(getIdChild()));

		return localOutput;
	}
	@Override public boolean equals(Object aHasGuardian) {
		if ( this == aHasGuardian ) return true;

		if ( !(aHasGuardian instanceof HasGuardian) ) return false;

		HasGuardian hasGuardian = (HasGuardian)aHasGuardian;

		return
				EqualsUtil.areEqual(this.getIdGuardian(), hasGuardian.getIdGuardian()) &&
				EqualsUtil.areEqual(this.getIdChild(), hasGuardian.getIdChild());
	}
}
