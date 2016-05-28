package sunkey.frameworks.config.extension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import sunkey.frameworks.template.Template;

@Component
public class ValueParser {
	
	public static final String SPLIT = ":";
	
	private Map<String, Function> functionsMap = new HashMap<>();
	private Map<String, Template> templatesMap = new HashMap<>();
	private static final Function SYSTEM_FUNCTION = new SystemFunction();
	private Map<String, Object> innerParams = new HashMap<>();
	
	{
		addFunction(SYSTEM_FUNCTION);
		addFunction(new DateFunction());
	}
	
	public Map<String, Object> getInnerParams() {
		return innerParams;
	}
	
	public void addFunction(Function function){
		functionsMap.put(function.getName(), function);
		if(function.getInnerParams() != null){
			innerParams.putAll(function.getInnerParams());
		}
	}
	
	public void setFunctions(List<Function> functions){
		if(functions != null){
			for(Function func : functions){
				addFunction(func);
			}
		}
	}
	
	public List<Function> getFunctions(){
		return new ArrayList<Function>(functionsMap.values());
	}
	
	public String render(String template, Map<String, Object> params){
		Template tpl = getTemplate(template);
		Map<String, Object> tplParams = new HashMap<>();
		String[] tplStrs = tpl.getParams();
		for(int i = 0; i < tplStrs.length; i++){
			if(i % 2 == 0)
				continue;
			String param = tplStrs[i];
			int indexOfSplit = param.indexOf(SPLIT);
			if(indexOfSplit == -1){
				//system
				tplParams.put(param, SYSTEM_FUNCTION.getValue(param, params));
			}else{
				//extension
				String funcName = param.substring(0, indexOfSplit);
				String paramName = param.substring(indexOfSplit+1);
				Function func = functionsMap.get(funcName);
				if(func != null){
					tplParams.put(param, func.getValue(paramName, params));
				}
			}
		}
		return tpl.render(tplParams);
	}
	
	protected Template getTemplate(String template){
		Template tpl = templatesMap.get(template);
		if(tpl == null){
			return addTemplate(template);
		}else{
			return tpl;
		}
	}
	
	protected Template addTemplate(String template){
		Template tpl = new Template(template);
		templatesMap.put(template, tpl);
		return tpl;
	}
	
}
