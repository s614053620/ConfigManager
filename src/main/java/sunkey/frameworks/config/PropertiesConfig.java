package sunkey.frameworks.config;

import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import sunkey.frameworks.config.utils.CollectionUtils;
import sunkey.frameworks.config.utils.PathUtils;
import sunkey.frameworks.config.utils.ResourceUtils;

public class PropertiesConfig implements Config{

	private boolean isClasspath = false;
	private String name;
	private String path;
	private final Properties target;
	private final Map<String, Object> map;
	
	public PropertiesConfig(String cfg) {
		this.name = cfg;
		target = new Properties();
		InputStream in = null;
		if(PathUtils.isClasspath(cfg)){
			this.path = PathUtils.getClasspath(cfg);
			this.path = PathUtils.appendIfNotEndWith(this.path, Constants.SUFFIX_PROPERTIES_TYPE);
			in = ResourceUtils.getClasspathResource(this.path);
			isClasspath = true;
		}else{
			this.path = cfg;
			this.path = PathUtils.appendIfNotEndWith(this.path, Constants.SUFFIX_PROPERTIES_TYPE);
			in = ResourceUtils.getPathResource(cfg);
		}
		try {
			target.load(in);
			map = Collections.unmodifiableMap(CollectionUtils.fromProperties(target));
			in.close();
		} catch (Exception e) {
			throw new RuntimeException("config load failed ["+cfg+"] !",e);
		}
	}
	
	@Override
	public boolean isClasspath() {
		return isClasspath;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public String getString(String key) {
		return target.getProperty(key);
	}

	@Override
	public String getString(String key, String def) {
		return target.getProperty(key, def);
	}

	@Override
	public Map<String, Object> getPropsAsMap() {
		return map;
	}

}
