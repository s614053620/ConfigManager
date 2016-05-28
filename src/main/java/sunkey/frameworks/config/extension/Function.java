package sunkey.frameworks.config.extension;

import java.util.Map;

public interface Function {
	
	String getName();
	
	String getValue(String paramName, Map<String, Object> params);
	
	Map<String, Object> getInnerParams();
	
}
