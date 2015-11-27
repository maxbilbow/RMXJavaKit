package click.rmx.debug;

import click.rmx.debug.logger.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.LinkedList;

import static java.nio.file.Files.createDirectories;


public class Bugger implements RMXDebugInstance {
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


	private Logger logger;

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


	public Bugger(){
        Bugger b = this;
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
		if (logger != null)
			logger.logMessage(o);
		
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

	void info(Object o, int depth)
	{
		if (debugLevel < DEBUG_INFO)
			return;
		final String log = "INFO: " + String.valueOf(o);
		logMessage(log);
		print(log,depth + 1);
	}

	void warn(Object o, int depth)
	{
		if (debugLevel < DEBUG_WARNING)
			return;
		final String log = "WARNING: " + String.valueOf(o);
		logMessage(log);
		print(log,depth + 1);
	}

	void error(Object o, int depth)
	{
		if (debugLevel == DEBUG_NONE)
			return;
		final String log = "ERROR: " + String.valueOf(o);
		logMessage(log);
		print(log,depth + 1);
	}


	public void info(Object o)
	{
		info(o,1);
	}

	public void warn(Object o)
	{
		warn(o,1);
	}

	public void error(Object o)
	{
		error(o,1);
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
		if (updateLogFile) {
			FileWriter writer = null;
			try {
				createDirectories(Paths.get("bugger"));
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
	 *
	 * @param object
	 * @param args
	 * @return
	 */
	@Deprecated
	public static String inspectObject(Object object, String... args)
	{
		final ObjectInspector inspector = new ObjectInspector();
		return inspector.inspectObject(object,args);
	}


	/**
	 * Use {@Link ObjectInspector#stringify()} instead
	 * @param array
	 * @return
	 */
	@Deprecated
	public static String stringify(Object array)
	{
		final ObjectInspector inspector = new ObjectInspector();
		return inspector.stringify(array);
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
