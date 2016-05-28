package sunkey.frameworks.config.convert;

import sunkey.frameworks.config.exception.ConvertException;
import sunkey.frameworks.template.Template;

public class TemplateConverter implements Converter {

	@Override
	public int getOrder() {
		return 2;
	}

	@Override
	public Object convert(Object bean, Class<?> beanClass, Class<?> targetClass)
			throws ConvertException {
		if(beanClass == String.class){
			if(targetClass == Template.class){
				return Template.forName((String) bean);
			}
		}
		return bean;
	}

}
