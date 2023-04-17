package tr.edu.ogu.ceng.service.Exception;

@SuppressWarnings("serial")
public class SQLException extends ServiceException {

	public SQLException(String message) {
		super(message);
	}

	public SQLException() {
		super("SQL query error.");
	}

}
