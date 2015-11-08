package click.rmx.engine.model.interfaces;

/**
 * Created by bilbowm on 28/10/2015.
 */
public interface CollisionBody extends GameComponent {

    int getCollisionGroup();

    /**
     * Custom groups should be larger than 0.
     */
    class Group {
        public static final int
                DEFAULT = 0, TRANSIENT = -1;
    }
}
