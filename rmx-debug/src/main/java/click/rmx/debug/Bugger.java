package click.rmx.debug;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.LinkedList;

import static java.nio.file.Files.createDirectories;




public class Bugger {

	private boolean printLogOnExit = false;
	private boolean printLogImmediately = true;

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

	private static Bugger instance;
	private Bugger(){ 
		Runtime.getRuntime().addShutdownHook(new Thread() {
                    @Override
		    public void run() {
		        if (instance != null)
		        	instance.printAll();
		    }
		});
	}
	
	public static Bugger getInstance() {
		if (instance != null)
			return instance;
		else {
			instance = new Bugger();
		}
		return instance;
	}
	public static String timestamp()
	{
		return DateTimeFormatter.ISO_INSTANT
				.format(Instant.now()).split("T")[1];//.split(".")[0];
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

	/**
	 * Print but do not store output in log
	 * @param o
	 */
	public static void print(Object o)
	{
		if (instance.printLogOnExit)
			instance.logMessage(o);
		if (instance.printLogImmediately)
			Tests.note(String.valueOf(o),1);
	}
	public static void print(Object o, boolean andLog) {
		if (instance.printLogOnExit && andLog)
			instance.logMessage(o);
		if (instance.printLogImmediately)
			Tests.note(String.valueOf(o),1);
	}

	
	public static void log(Object o) {
		instance.logMessage(o);
	}

	int count = 1;

	public static void PrintTrace() {
		StackTraceElement[] trace = Thread.currentThread().getStackTrace();//[2];
		System.out.println("TRACE:");
		int i =0;
		for (StackTraceElement e : trace) {
			if (i++ > 0)
			System.out.println(e);
		}
	}
	
	private String logMessage(Object o) {
		
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
	
	public static void test (String[] args) {
//		Bugger b = Bugger.getInstance();
		log("Hello!");
//		Print(true);
		
		log("Hello again!");
		print("My Friends!", true); print("My Friends!", true);

	
	}

	public static void main (String [] args)
	{
		Bugger.log("Hello!");
	}

	public boolean willPrintLogImmediately() {
		return printLogImmediately;
	}

	public void setPrintLogImmediately(boolean printLogImmediately) {
		this.printLogImmediately = printLogImmediately;
	}
}
