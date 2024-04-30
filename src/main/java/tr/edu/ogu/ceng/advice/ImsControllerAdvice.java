package tr.edu.ogu.ceng.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tr.edu.ogu.ceng.internationalization.MessageResource;
import tr.edu.ogu.ceng.service.Exception.DataAccessException;
import tr.edu.ogu.ceng.service.Exception.EntityNotFoundException;
import tr.edu.ogu.ceng.service.Exception.ServiceException;

@Slf4j
@AllArgsConstructor
@ControllerAdvice
public class ImsControllerAdvice {

	private MessageResource messageResource;

	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<ErrorResponseDTO> dataAccessException(DataAccessException ex) {
		log.error(ex.getMessage());
		ErrorResponseDTO errorResponse = new ErrorResponseDTO(null, ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorResponseDTO> entityNotFoundException(EntityNotFoundException ex) {
		log.error(ex.getMessage());
		ErrorResponseDTO errorResponse = new ErrorResponseDTO(null, ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<ErrorResponseDTO> serviceException(ServiceException ex) {
		log.error(ex.getMessage());
		ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getCode(), ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/*
	 * JSON Validation Exception Start
	 */

	/**
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> globalException(MethodArgumentNotValidException ex) {
		log.error(ex.getMessage());
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponseDTO> globalException(Exception ex) {
		log.error(ex.getMessage());
		ex.printStackTrace(); // FIXME hold when development stage
		ErrorResponseDTO errorResponse = new ErrorResponseDTO("500", messageResource.getMessage("unexpected.error"));
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
