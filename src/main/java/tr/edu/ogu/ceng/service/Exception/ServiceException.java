package tr.edu.ogu.ceng.service.Exception;

@SuppressWarnings("serial")
public class ServiceException extends RuntimeException {
	
	public ServiceException(String message) {
		super(message);
	}
}
