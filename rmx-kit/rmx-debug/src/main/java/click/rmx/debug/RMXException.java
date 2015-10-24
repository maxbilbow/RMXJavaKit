package click.rmx.debug;

public class RMXException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4545896278223910564L;
	public static RMXException expected(Exception e) {
		return new RMXException(e, RMXError.Expected, getLocation(1));
	}
	private static String getLocation(int depth) {
		StackTraceElement trace = Thread.currentThread().getStackTrace()[depth + 2];
		return trace.getClassName() + "::" + trace.getMethodName() + "()";
	}

	/**
	 *
 	 * @param type
	 * @param depth use this if the Exception was declared in a deeper method than when the exception first occurred
	 * @return
	 */
	static RMXException newInstance(RMXError type, int depth) {
		RMXException e = new RMXException(type, "", getLocation(1 + depth));
		if (type.isSerious())
			e.printStackTrace();
		return e;
	}

	public static RMXException newInstance(RMXError type) {
		RMXException e = new RMXException(type, "", getLocation(1));
		if (type.isSerious())
			e.printStackTrace();
		return e;
	}

	/**
	 *
	 * @param message
	 * @param type
	 * @param depth use this if the Exception was declared in a deeper method than when the exception first occurred
	 * @return
	 */
	static RMXException newInstance(String message, RMXError type, int depth) {
		RMXException e = new RMXException(message, type, getLocation(1 + depth));
		if (type.isSerious())
			e.printStackTrace();
		return e;
	}

	public static RMXException newInstance(String message, RMXError type) {
		RMXException e = new RMXException(message, type, getLocation(1));
		if (type.isSerious())
			e.printStackTrace();
		return e;
	}

	/**
	 *
	 * @param e
	 * @param message
	 * @param depth use this if the Exception was declared in a deeper method than when the exception first occurred
	 * @return
	 */
	static RMXException unexpected(Exception e, String message, int depth) {
		e.printStackTrace();
		RMXException err = new RMXException(e, RMXError.Unexpected, getLocation(1 + depth));
		err.addLog(message);
		return err;
	}

	public static RMXException unexpected(Exception e, String message) {
		e.printStackTrace();
		RMXException err = new RMXException(e, RMXError.Unexpected, getLocation(1));
		err.addLog(message);
		return err;
	}

	/**
	 *
	 * @param e
	 * @param depth use this if the Exception was declared in a deeper method than when the exception first occurred
	 * @return
	 */
	static RMXException unexpected(Exception e, int depth)
	{
		if (RMXException.class.isAssignableFrom(e.getClass()))
			return (RMXException) e;
		e.printStackTrace();
		return new RMXException(e, RMXError.Unexpected, getLocation(1 + depth));
	}


	public static RMXException unexpected(Exception e)
	{
		if (RMXException.class.isAssignableFrom(e.getClass()))
			return (RMXException) e;
		e.printStackTrace();
		return new RMXException(e, RMXError.Unexpected, getLocation(1));
	}

	public static RMXException unexpected(String message) {
		RMXException e = new RMXException(message, RMXError.Unexpected, getLocation(1));
		e.printStackTrace();
		return e;
	}
	
	private final RMXError type;
	
	private String log;
	
	private final String reportLocation;

	private final Exception originalException;
	
	private RMXException(Exception e, RMXError type, String r) {
		super();
		this.type = type;
		this.originalException = e;//.getStackTrace();
		this.reportLocation = r;
		this.log = e.toString();
	}


	private RMXException(RMXError type, String message, String location) {
		super(type.toString());
		this.originalException = this;
		this.type = type;
		this.reportLocation = location;
		this.log = message;
	}

	private RMXException(String log, RMXError type, String reportLocation) {
		super();
		this.originalException = this;
		this.type = type;
		this.reportLocation = reportLocation;
		this.log = log;
	}
	
	public void addLog(String log) {
		this.log += "\n" + log;
	}

	public String errorLog() {
		return this.reportLocation + " >> " + this.log;
	}

	@Override
	public String getLocalizedMessage() {
		return errorLog();
	}

	public String html() {
		return this.type.html() + " >> " + this.errorLog().replace("\n", "<br/>");
	}

	public boolean isSerious() {
		return this.type.isSerious();
	}

	@Override
	public void printStackTrace() {
		System.err.print( type + ": An RMXException has occurred >> ");
		switch (type) {
		case MethodNotImplementedException:
			System.out.println(this.getLocalizedMessage());
			break;
		case Expected:
			System.out.println("Expected but should be managed: ");
			System.out.println(this.getLocalizedMessage());
			break;
			case JustForFun:
				System.out.println("Oh Crikey!");
				System.out.println(this.getLocalizedMessage());
				break;
		case Unexpected:
			System.err.println("UNEXPECTED ERROR!");
			if (originalException == this)
				super.printStackTrace();
			else 
				this.originalException.printStackTrace();
			break;
		}
	}

	/**
	 *
	 * @param message
	 * @param depth use this if the Exception was declared in a deeper method than when the exception first occurred
	 * @return
	 */
	static RMXException expected(String message, int depth) {
		RMXException e = new RMXException(RMXError.Expected, message, getLocation(1 + depth));
		return e;
	}

	public static RMXException expected(String message) {
		RMXException e = new RMXException(RMXError.Expected, message, getLocation(1));
		return e;
	}

}
