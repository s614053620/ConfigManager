package sunkey.frameworks.config.convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.Properties;

import sunkey.frameworks.config.Config;
import sunkey.frameworks.config.exception.ConvertException;
import sunkey.frameworks.config.utils.CollectionUtils;

public class SystemConverter implements Converter {

	@Override
	public int getOrder() {
		return Integer.MAX_VALUE;
	}

	@Override
	public Object convert(Object bean, Class<?> beanClass, Class<?> targetClass) {
		if(targetClass == String.class){
			String str = (String) bean;
			if(targetClass.isPrimitive()){
				return stringConvertToPrimitive(str, targetClass);
			}else if(Number.class.isAssignableFrom(targetClass)){
				BigDecimal number = new BigDecimal(str);
				Object to = numberToType(number, targetClass);
				if(number != to){
					return to;
				}else{
					return bean;
				}
			}else if(targetClass == Character.class){
				return str.charAt(0);
			}else if(targetClass == Boolean.class){
				return "true".equals(str);
			}
		}else if(beanClass == Properties.class){
			if(targetClass == Map.class){
				return CollectionUtils.fromProperties((Properties) bean);
			}
		}else if(Config.class.isAssignableFrom(beanClass)){
			if(targetClass == Map.class){
				return ((Config) bean).getPropsAsMap();
			}
		}
		return bean;
	}

	public static Object numberToType(BigDecimal number, Class<?> type){
		if(type == Integer.class){
			number.intValue();
		}else if(type == Byte.class){
			number.byteValue();
		}else if(type == Short.class){
			number.shortValue();
		}else if(type == Long.class){
			number.longValue();
		}else if(type == Float.class){
			number.floatValue();
		}else if(type == Double.class){
			number.doubleValue();
		}else if(type == BigDecimal.class){
			return number;
		}else if(type == BigInteger.class){
			return new BigInteger(number.toString());
		}
		return number;
	}
	
	public static Object stringConvertToPrimitive(String str, Class<?> clazz){
		if(clazz == Integer.TYPE){
			return Integer.valueOf(str);
		}else if(clazz == Byte.TYPE){
			return Byte.valueOf(str);
		}else if(clazz == Short.TYPE){
			return Short.valueOf(str);
		}else if(clazz == Long.TYPE){
			return Long.valueOf(str);
		}else if(clazz == Boolean.TYPE){
			return Boolean.valueOf(str);
		}else if(clazz == Character.TYPE){
			return str.charAt(0);
		}else if(clazz == Float.TYPE){
			return Float.valueOf(str);
		}else if(clazz == Double.TYPE){
			return Double.valueOf(str);
		}
		throw new ConvertException("unknown primitive type ["+clazz+"]!");
	}
	
}
