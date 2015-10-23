package click.rmx.debug;

/**
 * Created by bilbowm on 23/10/2015.
 */
public class RMXDebugInstance {

    private static Bugger instance;
    public static Bugger getInstance() {
        return instance != null ? instance : (instance = new Bugger());
    }

}
