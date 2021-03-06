package click.rmx.core;

import click.rmx.debug.Tests;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.List;

public final class RMX {
	public static final float PI_OVER_180 = (float) (Math.PI / 180);

	/**
	 * Events
	 */
	public static final String
	END_OF_GAMELOOP = "END_OF_GAMELOOP";



	public static float rmxGetCurrentFramerate() {
		return 0.0167f;
	}

	public static String rmxGarbageCollectorInfo() {
		List<GarbageCollectorMXBean> list = ManagementFactory.getGarbageCollectorMXBeans();
		String s = "Beans:";
		for (GarbageCollectorMXBean bean : list) {
			if (!bean.isValid()) 
				continue;
			s += "\n" + bean.getName();
			s += "\n  - Count: " + bean.getCollectionCount();
			s += "\n  -  Time: " + bean.getCollectionTime();
		}
		return s;
	}

	public static String rmxGetPackageUrl(Class<?> aClass) {
		return aClass.getPackage().getName().replace('.', '/');
	}

	public static String rmxGetPackageUrl(Object object) {
		return rmxGetPackageUrl(object.getClass());
	}

	public static void rmxTodo() {
		Tests.note(null, 1);
	}

	private static HashMap<String,Integer> liveObjects = new HashMap<>();

	public static synchronized void rmxIncrementObjectCount(Categorizable object) {
		
//			Tests.note(object.getCategoryName() + " was added");

			int count = liveObjects.getOrDefault(object.getCategoryName(),0);
			liveObjects.put(object.getCategoryName(), count + 1);
		
	}

	public static synchronized void rmxDecrimentObjectCount(Categorizable object) {
//		Tests.note(object + " was removed");
		int count = liveObjects.getOrDefault(object.getCategoryName(),0);
		liveObjects.put(object.getCategoryName(), count - 1);
		if (count == 0)
			throw new IllegalArgumentException(object.getCategoryName() + " count should not be " + count);
	}


	public static int rmxObjectCount(String category) {
		return liveObjects.getOrDefault(category,0);
	}

	public static void main(String[]args)
	{
		System.out.println("rmx-debug");
	}
}
