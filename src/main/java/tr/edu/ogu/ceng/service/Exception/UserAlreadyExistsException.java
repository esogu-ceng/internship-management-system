package tr.edu.ogu.ceng.service.Exception;

@SuppressWarnings("serial")
public class UserAlreadyExistsException extends ServiceException{

	public UserAlreadyExistsException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	public UserAlreadyExistsException() {
		super("User already exists");
	}

}
