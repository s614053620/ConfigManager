package sunkey.frameworks.config;

import java.util.Map;

public interface Config {
	
	boolean isClasspath();
	
	String getName();
	
	String getPath();
	
	String getString(String key);
	
	String getString(String key, String def);
	
	Map<String, Object> getPropsAsMap();
	
}
