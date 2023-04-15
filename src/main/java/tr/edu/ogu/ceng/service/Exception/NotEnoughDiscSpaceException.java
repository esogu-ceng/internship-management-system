package tr.edu.ogu.ceng.service.Exception;

@SuppressWarnings("serial")
public class NotEnoughDiscSpaceException extends ServiceException {

	public NotEnoughDiscSpaceException(String message) {
		super(message);
	}

	public NotEnoughDiscSpaceException() {
		super("There is not enough disk space.");
	}

}
