package sunkey.frameworks.config.extension;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class DateFunction implements Function{

	@Override
	public String getName() {
		return "date";
	}

	@Override
	public String getValue(String paramName, Map<String, Object> params) {
		return new SimpleDateFormat(paramName).format(new Date());
	}

	@Override
	public Map<String, Object> getInnerParams() {
		return null;
	}

}
