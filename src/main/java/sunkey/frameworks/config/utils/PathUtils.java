package sunkey.frameworks.config.utils;

import org.springframework.util.StringUtils;

import sunkey.frameworks.config.Constants;

public class PathUtils implements Constants{
	
	public static boolean isClasspath(String path){
		if(StringUtils.hasText(path)){
			if(path.startsWith(PREFIX_CLASSPATH) || path.startsWith(PREFIX_CLASSPATH_SHORT)){
				return true;
			}
		}
		return false;
	}
	
	public static String getClasspath(String path){
		if(StringUtils.hasText(path)){
			if(path.startsWith(PREFIX_CLASSPATH)){
				return path.substring(PREFIX_CLASSPATH.length());
			}else if(path.startsWith(PREFIX_CLASSPATH_SHORT)){
				return path.substring(PREFIX_CLASSPATH_SHORT.length());
			}else{
				return path;
			}
		}
		return null;
	}
	
	public static String appendIfNotEndWith(String str, String suffix){
		if(str != null){
			if(str.endsWith(suffix)){
				return str;
			}else{
				return str + suffix;
			}
		}
		return str;
	}
	
}
