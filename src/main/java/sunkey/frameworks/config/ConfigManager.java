package sunkey.frameworks.config;

import java.lang.reflect.Field;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import sunkey.frameworks.config.annotation.Prop;
import sunkey.frameworks.config.annotation.Props;
import sunkey.frameworks.config.convert.ConverterChain;
import sunkey.frameworks.config.extension.ValueParser;

@Component
public class ConfigManager implements BeanPostProcessor{

	private static final Log loger = LogFactory.getLog(ConfigManager.class);
	
	@Autowired(required = false)
	private ValueParser valueParser;
	@Autowired(required = false)
	private ConverterChain converterChain;
	
	public ValueParser getValueParser() {
		if(valueParser == null){
			valueParser = new ValueParser();
		}
		return valueParser;
	}
	
	public ConverterChain getConverterChain() {
		if(converterChain == null){
			converterChain = new ConverterChain();
		}
		return converterChain;
	}
	
	@Override
	public Object postProcessBeforeInitialization(final Object bean, String beanName)
			throws BeansException {
		Class<?> clazz = AopUtils.getTargetClass(bean);
		ReflectionUtils.doWithFields(clazz, new ReflectionUtils.FieldCallback() {
			public void doWith(Field field) throws IllegalArgumentException,
					IllegalAccessException {
				if(field.isAnnotationPresent(Prop.class) || field.isAnnotationPresent(Props.class)){
					boolean isAccess = field.isAccessible();
					if(!isAccess){
						field.setAccessible(true);
					}
					inject(bean, field);
					if(!isAccess){
						field.setAccessible(false);
					}
				}
			}
		});
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}
	
	private void inject(Object target, Field field){
		Props props = field.getAnnotation(Props.class);
		if(props != null){
			injectByProps(props, target, field);
		}else{
			Prop prop = field.getAnnotation(Prop.class);
			if(prop != null)
				injectByProp(prop, target, field);
		}
	}
	
	private void injectByProp(Prop prop, Object target, Field field){
		Map<String, Object> innerParams = getValueParser().getInnerParams();
		String location = getValueParser().render(prop.loc(), innerParams);
		String key = null;
		if(Prop.FIELD_NAME.equals(prop.key())){
			key = field.getName();
		}else{
			key = getValueParser().render(prop.key(), innerParams);
		}
		String def = getValueParser().render(prop.def(), innerParams);
		Config cfg = Configs.forPath(location);
		if(cfg != null){
			String propVal = cfg.getString(key);
			if(propVal == null){
				String err = "required field ["+field+"] inject failed because property["+key+"] not found@"+location+"!";
				loger.error(err);
				if(Prop.THROWS_EXCEPTION.equals(prop.def())){
					throw new RuntimeException(err);
				}else{
					propVal = def;
				}
			}
			injectField(target, propVal, field);
		}else{
			if(prop.required()){
				String err = "required field ["+field+"] inject failed because resource ["+location+"] not found!";
				loger.error(err);
				throw new RuntimeException(err);
			}
		}
	}
	
	private void injectByProps(Props props, Object target, Field field){
		Map<String, Object> innerParams = getValueParser().getInnerParams();
		String location = getValueParser().render(props.loc(), innerParams);
		Config cfg = Configs.forPath(location);
		if(cfg != null){
			injectField(target, cfg, field);
		}else{
			if(props.required()){
				String err = "required field ["+field+"] inject failed because resource ["+location+"] not found!";
				loger.error(err);
				throw new RuntimeException(err);
			}
		}
	}
	
	private void injectField(Object target, Object val, Field field){
		Object result = getConverterChain().convert(val, val.getClass(), field.getType());
		try {
			field.set(target, result);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
