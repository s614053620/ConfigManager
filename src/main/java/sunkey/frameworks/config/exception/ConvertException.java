package sunkey.frameworks.config.exception;

public class ConvertException extends RuntimeException{


	private static final long serialVersionUID = 7129244274614478951L;

	public ConvertException() {
		super();
	}

	public ConvertException(String message) {
		super(message);
	}

	public ConvertException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConvertException(Throwable cause) {
		super(cause);
	}

	protected ConvertException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
