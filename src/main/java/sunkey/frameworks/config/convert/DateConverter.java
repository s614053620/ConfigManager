package sunkey.frameworks.config.convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import sunkey.frameworks.config.Config;
import sunkey.frameworks.config.Configs;
import sunkey.frameworks.config.exception.ConvertException;

public class DateConverter implements Converter {

	private static final Config CONFIG = Configs.forPath("cp:date-convert");
	
	private static final List<String> DATE_FORMATS = getDateFormats(CONFIG);
	
	@Override
	public int getOrder() {
		return 1;
	}

	private static List<String> getDateFormats(Config cfg) {
		List<String> fmts = new ArrayList<String>();
		for(Map.Entry<String, Object> e : cfg.getPropsAsMap().entrySet()){
			if(e.getKey().startsWith("date.format.")){
				if(e.getValue()!=null)
					fmts.add(String.valueOf(e.getValue()));
			}
		}
		return fmts;
	}

	@Override
	public Object convert(Object bean, Class<?> beanClass, Class<?> targetClass)
			throws ConvertException {
		if(beanClass == String.class){
			if(targetClass == Date.class){
				for(String fmt : DATE_FORMATS){
					try{
						return parse(fmt, (String) bean);
					}catch(ParseException e){}
				}
				throw new ConvertException("can't parse Date ["+bean+"]!");
			}
		}
		return bean;
	}

	public static Date parse(String fmt, String date) throws ParseException{
		return new SimpleDateFormat(fmt).parse(date);
	}
	
}
