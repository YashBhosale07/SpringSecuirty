package in.yash.security.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import in.yash.security.exceptionClasses.UserAlreadyPresentException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserAlreadyPresentException.class)
	public ResponseEntity<?>handleUserAlreadyPresentException(UserAlreadyPresentException alreadyPresentException){
		return new ResponseEntity<>(alreadyPresentException.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
}
