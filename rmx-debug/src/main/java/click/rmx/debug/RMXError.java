package click.rmx.debug;

public enum RMXError {
	/**
	 * Nothing to worry about
	 */
	 MethodNotImplementedException("blue", false), 
	 
	 /**
	  * These need to be fixed but are expected
	  */
	 Expected("orange", true),
	 
	 
	 /**
	  * We were not expecting this!
	  */
	 Unexpected("red", true);
//	 /**
//	  * Undefined error type
//	  */
//	 Default("red");
	
	private final String color;
	private final boolean serious;
	private RMXError(String color, boolean serious){
		this.color = color;
		this.serious = serious;
	}
	
	public String html() {
		return "<span style=\"color: " + color + ";\">" + toString() + "</span>";
	}

	public boolean isSerious() {
		return this.serious;
	}
	
	
}
