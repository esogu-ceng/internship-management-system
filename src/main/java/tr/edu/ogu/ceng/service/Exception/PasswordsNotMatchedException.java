package tr.edu.ogu.ceng.service.Exception;

@SuppressWarnings("serial")
public class PasswordsNotMatchedException extends ServiceException {

    public PasswordsNotMatchedException() {
        super("Passwords do not match!");
    }
}
