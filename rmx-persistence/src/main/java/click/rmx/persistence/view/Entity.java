package click.rmx.persistence.view;


/**
 * Created by Max on 03/10/2015.
 */

public @interface Entity {
    String name() default "";
    javax.persistence.Entity entity() default @javax.persistence.Entity;
}

