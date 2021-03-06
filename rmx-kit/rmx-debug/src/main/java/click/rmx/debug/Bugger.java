package click.rmx.debug;



import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.LinkedList;

import static java.nio.file.Files.createDirectories;

@Deprecated
public class Bugger{
	public static Bugger getInstance() {
		return instance != null ? instance : (instance = new Bugger());
	}

	public static final int
			DEBUG_NONE = -1,
			DEBUG_INFO = 2,
			DEBUG_WARNING = 1,
			DEBUG_ERROR = 0;
	private int debugLevel = DEBUG_INFO;
	private boolean printLogOnExit = false;
	private boolean printLogImmediately = true;
	private static Bugger instance;

	public boolean willUpdateLogFile() {
		return updateLogFile;
	}

	public void setUpdateLogFile(boolean updateLogFile) {
		this.updateLogFile = updateLogFile;
	}

	public boolean willPrintLogOnExit() {
		return printLogOnExit;
	}

	public void setPrintLogOnExit(boolean printLogOnExit) {
		this.printLogOnExit = printLogOnExit;
	}

	private boolean updateLogFile = false;
	private final LinkedList<String> logs = new LinkedList<>();


	public Bugger()
	{
		logFilePath = null;
	}

	private final String logFilePath;

	public Bugger(String logFilePath){
		this.logFilePath = logFilePath;
        final Bugger b = this;
		Runtime.getRuntime().addShutdownHook(new Thread() {
                    @Override
		    public void run() {
		        	b.printAll();
		    }
		});
	}

	public static String timestamp()
	{
		return DateTimeFormatter.ISO_INSTANT
				.format(Instant.now()).split("T")[1];//.split(".")[0];
	}

	int count = 1;

	@Deprecated
	static final String
			DECLARED_MEMBERS_ONLY = "declared",
			SHOW_ALL_MEMBERS = "verbose";

	public static void PrintTrace() {
		StackTraceElement[] trace = Thread.currentThread().getStackTrace();//[2];
		System.out.println("TRACE:");
		int i =0;
		for (StackTraceElement e : trace) {
			if (i++ > 0)
			System.out.println(e);
		}
	}
	
	String logMessage(Object o) {
		
		StackTraceElement trace = Thread.currentThread().getStackTrace()[3];
		String theClass = trace.getClassName();
		String method = trace.getMethodName();
		int line = trace.getLineNumber();
		String time = java.time.LocalTime.now().toString();
		
		String newLog =  theClass + "::" + method + " on line " + line + ": " + String.valueOf(o);
		if (!this.logs.isEmpty() && logs.getLast().endsWith(newLog)) {
			logs.removeLast();
			count++;
		} else {
			count = 1;
		}
		String timestamp = "[" + time + "x" + count + "] ";
		newLog = timestamp + newLog;
		
		
		this.logs.addLast(newLog);	
		return newLog;
	}

	public String info(Object o, int depth)
	{
		if (debugLevel < DEBUG_INFO)
			return "";
		final String log = makeLog(o,depth + 1, "INFO: ");
		logMessage(log);
		System.out.println(
				log
		);
		return log;
	}

	public String warn(Object o, int depth)
	{
		if (debugLevel < DEBUG_WARNING)
			return "";
		final String log = makeLog(o,depth + 1, "WARNING: ");
		logMessage(log);
		System.err.println(
				log
		);
		return log;
	}

	public String error(Object o, int depth)
	{
		if (debugLevel == DEBUG_NONE)
			return "";
		final String log = makeLog(o,depth + 1, "ERROR: ");
		logMessage(log);
		System.err.println(
				log
		);
		return log;
	}


	public String info(Object o)
	{
		return info(o,1);
	}

	public String warn(Object o)
	{
		return warn(o,1);
	}

	public String error(Object o)
	{
		return error(o,1);
	}

	/**
	 * Print but do not store output in log
	 * @param o
	 */
	public static void print(Object o)
	{
		Tests.note(String.valueOf(o),1);
	}

	/**
	 * Print but do not store output in log
	 * @param o
	 */
	private void print(Object o, int depth)
	{
		Tests.note(String.valueOf(o),1 + depth);
	}

	/**
	 * Print but do not store output in log
	 * @param o
	 */
	private String makeLog(Object o, int depth, String prefix)
	{
		return prefix + Tests.getNote(String.valueOf(o),1 + depth);
	}

	public void printAll() {
		String systemLog = "====== BEGIN LOG ======\n";
		Iterator<String> i = getInstance().logs.iterator();
//		String systemLog = "";
		while (i.hasNext()) { 
			systemLog +=  i.next() + "\n";
		}
		systemLog += "====== END OF LOG ======\n\n"; //= systemLog.substring(0, systemLog.length()) +
		if (printLogOnExit)
			System.out.println(systemLog);
		if (logFilePath != null) {
			FileWriter writer = null;
			try {
				createDirectories(Paths.get(logFilePath));
				writer = new FileWriter("rmx_bugger" + ".log", true);
				systemLog = systemLog.replace("\n", "\r\n");
				writer.write(systemLog);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (writer != null)
					try {
						writer.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
	}


	public boolean willPrintLogImmediately() {
		return printLogImmediately;
	}

	public void setPrintLogImmediately(boolean printLogImmediately) {
		this.printLogImmediately = printLogImmediately;
	}


	/**
	 * @Depricated Use @{link:print} instead
	 * @param o
	 * @param keep
	 */
	@Deprecated
	public static void logAndPrint(Object o, boolean keep) {
		print(o,keep);
	}

	@Deprecated
	public static void print(Object o, boolean andLog) {
		final Bugger instance = Bugger.getInstance();
		if (instance.willPrintLogOnExit() && andLog)
			instance.logMessage(o);
		if (instance.willPrintLogImmediately())
			Tests.note(String.valueOf(o),1);
	}

	@Deprecated
	public static void log(Object o) {
		Bugger.getInstance().logMessage(o);
	}

	public void setDebugLevel(int debugLevel) {
		this.debugLevel = debugLevel;
	}
}
