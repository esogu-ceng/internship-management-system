package tr.edu.ogu.ceng.service.Exception;

@SuppressWarnings("serial")
public class NotAuthorizedException extends ServiceException {

	public NotAuthorizedException(String message) {
		super(message);
	}

	public NotAuthorizedException() {
		super("Authentication error.");
	}

}
