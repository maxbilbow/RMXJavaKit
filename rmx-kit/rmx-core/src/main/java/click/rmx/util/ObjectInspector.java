package click.rmx.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by bilbowm (Max Bilbow) on 27/11/2015.
 */
public class ObjectInspector {

    public boolean canBeWrittenAsList(Object object)
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

    public String inspectObject(Object object, String... args)
    {
        boolean gettersOnly = false;
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
                case "short":
                    fullList = false;
                    break;
                case "g":
                case "get":
                case "getters":
                    gettersOnly = true;
                    break;

            }
        }

        info += "\n" + (fullList ? "Showing All Members" : "Showing Declared Methods") + ":";
        Method[] methods = fullList ? object.getClass().getMethods() : object.getClass().getDeclaredMethods();
        for (Method m : methods) {
            boolean isGetter = m.getReturnType() != Void.TYPE && m.getParameterCount() == 0;
            if (gettersOnly && (!isGetter || m.getName().equals("toString")))
                continue;
            Parameter[] parameters = m.getParameters();//Types();
            String params = "";
            if (parameters.length > 0) {
                params = parameters[0].getType().getSimpleName();// + " " + parameters[0].getName();
                for (Parameter p : parameters)
                    params += ", "+p.getType().getSimpleName();// + " " + p.getName();
            }
            info += "\n --(m) " + m.getReturnType().getSimpleName() + " " + m.getName() + "("+params+")";
            if (isGetter)
                try {
                    info += " == " + stringify(m.invoke(object));
                } catch (Exception e) {
                    info += " != FAIL: " + e;
                }
        }
        Field[] fields = object.getClass().getFields();
        if (fields.length > 0) {
            info += "\nFields:";
            for (Field f : fields) {
                info += "\n --(f) " + f.getType().getSimpleName() + " " + f.getName();
            }
        }

        if (!gettersOnly) {
            Annotation[] annotations = object.getClass().getAnnotations();
            if (annotations.length > 0) {
                info += "\nAnnotations:";
                for (Annotation a : annotations) {
                    info += "\n --(a) " + a.getClass().getSimpleName();
                }
            }
        }

        return info;
    }



    public String stringify(Object array)
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
}
