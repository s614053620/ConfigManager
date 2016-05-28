package sunkey.frameworks.config.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class CollectionUtils {
	
	public static Map<String, Object> fromProperties(Properties prop){
		if(prop == null)
			return null;
		Map<String, Object> map = new HashMap<String, Object>();
		for(Entry<Object, Object> e : prop.entrySet()){
			map.put((String) e.getKey(), e.getValue());
		}
		return map;
	}
	
}
