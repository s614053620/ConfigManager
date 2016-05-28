package sunkey.frameworks.config.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import sunkey.frameworks.config.exception.ResourceNotFoundException;

public class ResourceUtils {

	public static InputStream getClasspathResource(String path){
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
	}
	
	public static InputStream getPathResource(String path){
		try {
			return new FileInputStream(path);
		} catch (FileNotFoundException e) {
			throw new ResourceNotFoundException(e);
		}
	}
	
	public static InputStream getResource(String path){
		if(PathUtils.isClasspath(path)){
			return getClasspathResource(PathUtils.getClasspath(path));
		}else{
			return getPathResource(path);
		}
	}
	
}
