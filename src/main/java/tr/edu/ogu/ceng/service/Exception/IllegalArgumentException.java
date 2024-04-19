package tr.edu.ogu.ceng.service.Exception;

public class IllegalArgumentException extends ServiceException{

        public IllegalArgumentException(String message) {
            super(message);
        }

        public IllegalArgumentException() {
            super("Illegal Argument");
        }
}
