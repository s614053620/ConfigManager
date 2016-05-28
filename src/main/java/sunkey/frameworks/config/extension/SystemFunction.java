package sunkey.frameworks.config.extension;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SystemFunction implements Function{

	@Override
	public String getName() {
		return "system";
	}

	@Override
	public String getValue(String paramName, Map<String, Object> params) {
		switch (paramName) {
		case "locale":
			return Locale.getDefault().toString();
		default:
			return String.valueOf(params.get(paramName));
		}
	}

	@Override
	public Map<String, Object> getInnerParams() {
		return new HashMap<String, Object>();
	}
}
