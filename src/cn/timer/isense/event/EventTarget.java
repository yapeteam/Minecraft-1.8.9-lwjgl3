package cn.timer.isense.event;

import java.lang.annotation.*;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface EventTarget {
    byte priority() default 1;
}