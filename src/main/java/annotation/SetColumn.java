package annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SetColumn
{
	String column();
	boolean isPrimary() default false;
}