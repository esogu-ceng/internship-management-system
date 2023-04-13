package tr.edu.ogu.ceng.service.Exception;

@SuppressWarnings("serial")
public class AccessDeniedException extends ServiceException {

    public AccessDeniedException() {
        super("Access to the requested resource is denied.");
    }
}
