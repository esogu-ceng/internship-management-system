package tr.edu.ogu.ceng.service.Exception;

@SuppressWarnings("serial")
public class ServiceUnavailableException extends ServiceException {

	public ServiceUnavailableException(String message) {
		super(message);
	}

	public ServiceUnavailableException() {
		super("The server is busy or unavailable.");
	}

}
