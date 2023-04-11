package tr.edu.ogu.ceng.service.Exception;

@SuppressWarnings("serial")
public class InvalidArgumentException extends ServiceException {
	public InvalidArgumentException(String message){
		super(message);
	}
	
	public InvalidArgumentException(){
		super("Invalid argument.");
	}
}
