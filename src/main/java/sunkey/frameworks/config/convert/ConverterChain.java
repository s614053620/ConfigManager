package sunkey.frameworks.config.convert;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import sunkey.frameworks.config.exception.ConvertException;

@Component
public class ConverterChain {
	
	private TreeSet<Converter> converters = new TreeSet<Converter>(new Comparator<Converter>() {
		public int compare(Converter o1, Converter o2) {
			return o1.getOrder() - o2.getOrder();
		}
	});
	
	public void setConverters(List<Converter> converters){
		if(converters != null){
			for(Converter cvt : converters){
				addConverter(cvt);
			}
		}
	}
	
	{
		addConverter(new SystemConverter());
		addConverter(new DateConverter());
		addConverter(new TemplateConverter());
	}
	
	public void addConverter(Converter converter){
		converters.add(converter);
	}
	
	public TreeSet<Converter> getConverters(){
		return converters;
	}
	
	public Object convert(Object obj, Class<?> beanClass, Class<?> targetClass){
		if(targetClass.isAssignableFrom(beanClass)){
			return obj;
		}
		for(Converter cvt : converters){
			Object res = cvt.convert(obj, beanClass, targetClass);
			if(res != obj){
				return res;
			}
		}
		throw new ConvertException("cannot cast "+beanClass + " to "+targetClass+", bean="+obj);
	}
	
}
