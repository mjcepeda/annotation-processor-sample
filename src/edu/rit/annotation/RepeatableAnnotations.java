package edu.rit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention( RetentionPolicy.CLASS ) //recorded by the compiler
@Target( ElementType.TYPE ) //class declaration
public @interface RepeatableAnnotations {
	StreamsFactory[] value();
}
