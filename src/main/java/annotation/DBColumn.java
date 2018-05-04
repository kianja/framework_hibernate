package annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DBColumn
{
	String name() default "";
	boolean isPrimary() default false;
	String getMethod() default "";
	String setMethod() default "";
}
