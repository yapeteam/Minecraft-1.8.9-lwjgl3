package cn.timer.isense.mixin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SuppressWarnings("unused")
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface Inject {
    String method() default "None";

    String at() default "None";

    String atField() default "None";

    String atMethod() default "None";

    Place atPlace() default Place.BEFORE;

    enum Place {
        BEFORE, AFTER
    }
}
