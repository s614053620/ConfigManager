package sunkey.frameworks.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Prop {

	String THROWS_EXCEPTION = "__THROWS_EXCEPTION";
	String EMPTY_STRING = "";
	String NULL_STRING = "null";
	
	String FIELD_NAME = "__FIELD_NAME";
	
	/**
	 * the location of config
	 * support {param}
	 */
	String loc();
	
	/**
	 * the key of property
	 * support {param}
	 */
	String key() default FIELD_NAME;
	
	/**
	 * the default value
	 * support {param}
	 */
	String def() default EMPTY_STRING;
	
	boolean required() default true;
	
}
