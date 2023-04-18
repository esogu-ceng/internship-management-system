package tr.edu.ogu.ceng.service.Exception;

@SuppressWarnings("serial")
public class passwordsDontMatch extends ServiceException {

	public passwordsDontMatch() {
		super("Passwords do not match.");
	}
}
