package tr.edu.ogu.ceng.service.Exception;

@SuppressWarnings("serial")
public class InvalidTokenException extends ServiceException {

    public InvalidTokenException() {
        super("Invalid or expired token!");
    }
}