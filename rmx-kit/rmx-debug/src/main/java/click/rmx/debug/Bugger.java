package click.rmx.debug;

import click.rmx.debug.logger.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.nio.file.Files.createDirectories;


public class Bugger extends RMXDebugInstance {

	private boolean printLogOnExit = false;
	private boolean printLogImmediately = true;
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

	public static boolean canBeWrittenAsList(Object object)
	{
		return object != null && (
				object.getClass().isArray() ||
						Enumeration.class.isAssignableFrom(object.getClass()) ||
						Iterable.class.isAssignableFrom(object.getClass()) ||
						Map.class.isAssignableFrom(object.getClass())
		);

	}


	public static final String
			DECLARED_MEMBERS_ONLY = "declared",
			SHOW_ALL_MEMBERS = "verbose";
	public static String inspectObject(Object object, String... args)
	{
		String info = object.getClass().getName() +
				"\n==========================";
		boolean fullList = false;
		if (canBeWrittenAsList(object)) {
			info = "\n"+ stringify(object);
			fullList = false;
		}
		for (String arg : args) {
			switch (arg){
				case "v":
				case SHOW_ALL_MEMBERS:
					fullList = true;
					break;
				case DECLARED_MEMBERS_ONLY:
				case "d":
				case "compact":
					fullList = false;
					break;

			}
		}

		info += "\n" + (fullList ? "Showing All Members" : "Showing Declared Methods") + ":";
		Method[] methods = fullList ? object.getClass().getMethods() : object.getClass().getDeclaredMethods();
		for (Method m : methods) {
			Parameter[] parameters = m.getParameters();//Types();
			String params = "";
			if (parameters.length > 0) {
				params = parameters[0].getClass().getSimpleName();// + " " + parameters[0].getName();
				for (Parameter p : parameters)
					params += ", "+p.getClass().getSimpleName();// + " " + p.getName();
			}
			info += "\n --(m) " + m.getReturnType().getSimpleName() + " " + m.getName() + "("+params+")";
			if (m.getReturnType() != Void.TYPE && m.getParameterCount() == 0)
				try {
					info += " == " + stringify(m.invoke(object));
				} catch (Exception e) {
					info += " != FAIL: " + e;
				}
		}
		info += "\nFields:";
		Field[] fields = object.getClass().getFields();
		for (Field f: fields) {
			info += "\n --(f) " + f.getType().getSimpleName() + " " + f.getName();
		}
		info += "\nAnnotations:";
		Annotation[] annotations = object.getClass().getAnnotations();
		for (Annotation a: annotations) {
			info += "\n --(a) " + a.getClass().getSimpleName();
		}
		return info;
	}



	public static String stringify(Object array)
	{
		if (array == null)
			return "NULL";

		if (array.getClass().isArray()) {
			String arrString = "{ " + (Array.getLength(array) > 0 ? String.valueOf(Array.get(array, 0)) : "");
			for (int i = 1; i < Array.getLength(array); ++i)
				arrString += ", " + String.valueOf(Array.get(array, i));
			arrString += " }";
			return arrString;
		}


		if (Enumeration.class.isAssignableFrom(array.getClass())) {
			Enumeration e = (Enumeration) array;
			String arrString = "{ " + (e.hasMoreElements() ? e.nextElement() : "");
			while (e.hasMoreElements())
				arrString += ", " + String.valueOf(e.nextElement());
			arrString += " }";
			return arrString;
		}

		if (Iterable.class.isAssignableFrom(array.getClass())) {
			Iterable i = (Iterable) array;
			final String[] arrString = {"{ "};
			i.forEach(o ->
					arrString[0] += String.valueOf(o) + ", "
			);
			return arrString[0].substring(0,arrString[0].length()-2) + " }";
		}
		return String.valueOf(array);
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
		if (getInstance().printLogOnExit)
			getInstance().logMessage(o);
		if (getInstance().printLogImmediately)
			Tests.note(String.valueOf(o),1);
	}
	public static void print(Object o, boolean andLog) {
		if (getInstance().printLogOnExit && andLog)
			getInstance().logMessage(o);
		if (getInstance().printLogImmediately)
			Tests.note(String.valueOf(o),1);
	}

	
	public static void log(Object o) {
		getInstance().logMessage(o);
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
