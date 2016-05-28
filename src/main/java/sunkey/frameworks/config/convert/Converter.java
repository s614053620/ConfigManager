package sunkey.frameworks.config.convert;

import sunkey.frameworks.config.exception.ConvertException;

public interface Converter {
	
	int getOrder();//优先级 
	
	Object convert(Object bean, Class<?> beanClass, Class<?> targetClass) throws ConvertException;
	
}
