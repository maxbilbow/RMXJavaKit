package click.rmx.core;

import click.rmx.debug.Bugger;

import java.lang.reflect.InvocationTargetException;

public interface IMessageable {
	/**
     *   @author Max Bilbow, 15-08-04 16:08:55
     *
     *   Receives a message
     *   Has to be overridden for to add specific method handing
     *   as it is currently not automatic to call a method this way
     *   @param message Name of selector or any other message
     *   @param args    any object.
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
     *   @since 0.1
     */
    default Object sendMessage(String message, Object args){//} throws SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        if (args != null && this.implementsMethod(message,args.getClass())) {
            try {
                return this.getClass().getMethod(message, args.getClass()).invoke(this,args);
            } catch (NoSuchMethodException e) {
                Bugger.log( e.getClass().getSimpleName() + ": " + message + "()");
                e.printStackTrace();
                System.exit(1);
            } catch (InvocationTargetException e) {
                Bugger.log( e.getClass().getSimpleName() + ": " + message + "()");
                e.printStackTrace();
                System.exit(1);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                Bugger.log( e.getClass().getSimpleName() + ": " + message + "()");
                System.out.println("Likely cause: receiver was abstract");
                System.exit(1);
            }
        }

        return this.sendMessageOnly(message);
    }


    @Deprecated
    default Object sendMessageOnly(String message) {//} throws SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        if (this.implementsMethod(message)) {
            try {
                return this.getClass().getMethod(message).invoke(this);
            } catch (NoSuchMethodException e) {
                Bugger.log( e.getClass().getSimpleName() + ": " + message + "()");
                e.printStackTrace();
                System.exit(1);
            } catch (InvocationTargetException e) {
                Bugger.log( e.getClass().getSimpleName() + ": " + message + "()");
                e.printStackTrace();
                System.exit(1);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                Bugger.log( e.getClass().getSimpleName() + ": " + message + "()");
                System.out.println("Likely cause: receiver was abstract");
                System.exit(1);

            }
        }
        return null;
    }
    
    /**
     *   @author Max Bilbow, 15-08-04 16:08:55
     *
     *   Sends message to any children who are listenting
     *   @param message Name of selector or any other message
     *   @since 0.1
     */
    void broadcastMessage(String message);
    
    /**
     *   @author Max Bilbow, 15-08-04 16:08:55
     *
     *   Sends message to any children who are listenting
     *   @param message Name of selector or any other message
     *   @param args    any object.
     *   @since 0.1
     */
    void broadcastMessage(String message, Object args);


    /**
     * Enter the name of a method (not including arguments). e.g."onEventDidEnd(String,Object)" becomes "onEventDidEnd"
     * @param method name: "onEventDidEnd"
     * @return
     */
    boolean implementsMethod(String method, Class<?>... args);
    
    
}
