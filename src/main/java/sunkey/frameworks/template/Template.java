package sunkey.frameworks.template;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sunkey.
 * Multi-Thread Supported.
 */

public class Template implements Serializable {

	private static final long serialVersionUID = 153558874770994255L;

	public static final String USE_PARAM_NAME = "%$PARAM%$";
	
	private final String expression;
	private final String[] templateStrings;
	
	public Template(String expression) {
		this.expression = expression;
		List<String> list = Expressions.explainExpression(expression);
		templateStrings = list.toArray(new String[list.size()]);
		if(templateStrings == null || templateStrings.length == 0)
			throw new IllegalStateException();
	}
	
	public String[] getParams() {
		return templateStrings;
	}
	
	public String render(Map<String, Object> datas) {
		return render(datas, (NullHolder) null);
	}
	
	public String render(Map<String, Object> datas, NullHolder holder){
		if(templateStrings.length == 1)
			return templateStrings[0];
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < templateStrings.length; i++){
			if(i % 2 == 0) {
				//is a template string.
				sb.append(templateStrings[i]);
			} else {
				//is a parameter string.
				Object data = datas.get(templateStrings[i]);
				if(data != null) {
					//if not null append to string.
					sb.append(data);
				} else {
					//else append default (null is "")
					if(holder != null) {
						String result = holder.get(templateStrings[i]);
						if(result != null)
							sb.append(result);
					} else {
						// there not do anything.
						//sb.append("");
					}
				}
			}
		}
		return sb.toString();
	}
	
	public String render(Map<String, Object> datas, String def){
		if(templateStrings.length == 1)
			return templateStrings[0];
		//cache the $param placeholder index before execute.
		int index = def == null ? -1 : def.indexOf(USE_PARAM_NAME);
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < templateStrings.length; i++){
			if(i % 2 == 0) {
				//is a template string.
				sb.append(templateStrings[i]);
			} else {
				//is a parameter string.
				Object data = datas.get(templateStrings[i]);
				if(data != null) {
					//if not null append to string.
					sb.append(data);
				} else {
					//else append default (null is "")
					if(def != null) {
						if(index > -1) {
							/** 
							 * support expression:
							 * 		if null:  
							 * 		"*** {paramName} ***".render(data, "${" + Template.USE_PARAM_NAME + "}")
							 * 		display : *** ${paramName} ***
							 */
							sb.append(def.substring(0, index)).append(templateStrings[i]).append(def.substring(index + USE_PARAM_NAME.length()));
						} else {
							sb.append(def);
						}
					} else {
						// there not do anything.
						//sb.append("");
					}
				}
			}
		}
		return sb.toString();
	}

	public String getExpression() {
		return expression;
	}

	@Override
	public String toString() {
		return "SMSTemplate [expression=" + expression + "]";
	}

	
	/*****************          STATIC USE            ******************/

	private static final HashMap<String, Template> templates
		= new HashMap<String, Template>();

	public static final Template forName(String template){
		Template tpl = templates.get(template);
		if(tpl == null){
			tpl = new Template(template);
			templates.put(template, tpl);
		}
		return tpl;
	}
	
	public static String f(String fmt, Map<String, Object> datas){
		return forName(fmt).render(datas);
	}
	
	public static String f(String fmt, Object ... params){
		Template tpl = forName(fmt);
		HashMap<String, Object> datas = new HashMap<String, Object>();
		for(int i=0;i<params.length;i++){
			datas.put(i+1+"", params[i]);
		}
		return tpl.render(datas);
	}
	
	public static interface NullHolder{
		/**
		 * @return 
		 * 		null	: notAppend
		 * 		""  	: append("")
		 * 		"null"	: append("null")
		 */
		public String get(String key);
	}

	public static class Expressions {
		
		/**
		 * can write-custom it.
		 */
		public static final char REPLACE_PREFIX = '\\';
		public static final char SPLIT_EXP_PREFIX = '{';
		public static final char SPLIT_EXP_SUFFIX = '}';
		
		public static final List<String> explainExpression(String exp){
			char[] chars = exp.toCharArray();
			List<String> temps=new ArrayList<String>();
			// use StringBuilder to use least memory and fast.
			StringBuilder curStr = new StringBuilder("");
			for(int i = 0; i < chars.length; i++){
				switch (chars[i]) {
					/** 
					 *  support show '{' and '}':
					 *  	"*** \\{  \\}" = "*** {  }"
					 */
					case REPLACE_PREFIX:
						if(i<chars.length-1){
							if(chars[i+1] == SPLIT_EXP_PREFIX || chars[i+1] == SPLIT_EXP_SUFFIX){
								curStr.append(chars[i+1]);
								i++;
							}else{
								curStr.append(REPLACE_PREFIX);
							}
						}else{
							curStr.append(REPLACE_PREFIX);
						}
						break;
					case SPLIT_EXP_PREFIX:
					case SPLIT_EXP_SUFFIX:
						temps.add(curStr.toString());
						curStr = new StringBuilder();
						break;
					default:
						curStr.append(chars[i]);
						break;
				}
			}
			temps.add(curStr.toString());
			return temps;
		}
	}
	
	
	
}
