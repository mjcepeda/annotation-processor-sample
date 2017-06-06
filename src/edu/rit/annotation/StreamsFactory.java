package edu.rit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention( RetentionPolicy.CLASS ) //recorded by the compiler
@Target( ElementType.TYPE ) //class declaration
@Repeatable( RepeatableAnnotations.class )
public @interface StreamsFactory {

	String query();
	String name();
}
 