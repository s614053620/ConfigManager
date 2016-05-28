package test;

import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import sunkey.frameworks.config.Config;
import sunkey.frameworks.config.annotation.Prop;
import sunkey.frameworks.config.annotation.Props;
import sunkey.frameworks.template.Template;

//@Component
public class MyObject {

	@Prop(loc="cp:test_{locale}.properties",key="date_{locale}")
	private Date date;
	@Prop(loc="cp:test_{date:yyyy}.properties",key="user")
	private String user;
	@Prop(loc="classpath:test.properties",key="url",def=Prop.THROWS_EXCEPTION)
	private String url;
	@Prop(loc="cp:test.properties",key="template_unexists",def="{date:yyyyMMdd}")
	private String templateTest;
	@Prop(loc="cp:test")
	private String templateStr;
	@Props(loc="cp:test.properties")
	private Map<String, Object> props;
	@Props(loc="cp:test")
	private Config config;
	@Prop(loc = "cp:test")
	private Template template;
	
	
	@PostConstruct
	public void init(){
		System.out.println(date);
		System.out.println(user);
		System.out.println(url);
		System.out.println(templateTest);
		System.out.println(props);
		
		System.out.println(templateStr);
		System.out.println(config);
		System.out.println("===========================");
		
		System.out.println(template.render(props));
		
	}
	
}
