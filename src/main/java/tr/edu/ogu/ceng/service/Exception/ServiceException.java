package tr.edu.ogu.ceng.service.Exception;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class ServiceException extends RuntimeException {

	private String code;

	public ServiceException(String message) {
		super(message);
	}
}
