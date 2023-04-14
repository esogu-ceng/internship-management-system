package tr.edu.ogu.ceng.service.Exception;

@SuppressWarnings("serial")
public class OperationNotAllowedException extends ServiceException {
	
	public OperationNotAllowedException() {
		super("Error!!The requested operation is not allowed.");
	}
}