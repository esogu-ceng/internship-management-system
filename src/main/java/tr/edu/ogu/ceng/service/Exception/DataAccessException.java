package tr.edu.ogu.ceng.service.Exception;

@SuppressWarnings("serial")
public class DataAccessException extends ServiceException {

	public DataAccessException(String message) {
		super(message);
	}

	public DataAccessException() {
		super("Data access error.");
	}

}
