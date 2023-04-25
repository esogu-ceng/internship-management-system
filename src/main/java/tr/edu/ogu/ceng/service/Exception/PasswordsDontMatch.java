package tr.edu.ogu.ceng.service.Exception;

@SuppressWarnings("serial")
public class PasswordsDontMatch extends ServiceException {

	public PasswordsDontMatch() {
		super("Passwords do not match.");
	}
}
