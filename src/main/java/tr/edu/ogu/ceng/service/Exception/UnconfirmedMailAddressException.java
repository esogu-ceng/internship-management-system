package tr.edu.ogu.ceng.service.Exception;

public class UnconfirmedMailAddressException extends ServiceException {
    public UnconfirmedMailAddressException() {
        super("Supervisor's e-mail address does not match confirmed company's e-mail address!");
    }
}
