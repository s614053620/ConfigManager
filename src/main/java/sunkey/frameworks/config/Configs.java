package sunkey.frameworks.config;

import java.util.HashMap;

public class Configs {
	
	private static final HashMap<String, Config> configs = new HashMap<String, Config>();
	
	public static Config forPath(String path){
		Config cfg = configs.get(path);
		if(cfg == null){
			try{
				cfg = new PropertiesConfig(path);
				configs.put(path, cfg);
			}catch(Exception e){
				return null;
			}
		}
		return cfg;
	}
	
}
