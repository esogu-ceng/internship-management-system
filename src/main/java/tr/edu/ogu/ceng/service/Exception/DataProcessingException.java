package tr.edu.ogu.ceng.service.Exception;

@SuppressWarnings("serial")
public class DataProcessingException extends ServiceException {

	public DataProcessingException() {
		super("An error occurred while processing the data.");
	}
}