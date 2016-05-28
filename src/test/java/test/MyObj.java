package test;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import sunkey.frameworks.config.annotation.Prop;
import sunkey.frameworks.config.annotation.Props;
import sunkey.frameworks.template.Template;
@Component
public class MyObj {
	
	@Prop(loc="cp:test")
	private Template template;
	
	@Props(loc="cp:test")
	private Map<String, Object> config;
	
	@Value("#{new Integer(1)}")
	private String var;
	
	@PostConstruct
	public void init(){
		System.out.println(template.render(config));
		System.out.println(var);
	}
	
}
