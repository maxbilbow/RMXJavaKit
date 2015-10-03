package click.rmx.persistence.annotations;


/**
 * Created by Max on 03/10/2015.
 */

public @interface Entity {
    String name() default "";
    javax.persistence.Entity entity() default @javax.persistence.Entity;
}

