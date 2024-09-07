package pet.store.controller.error;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalErrorHandler.class);
	
	@ExceptionHandler(NoSuchElementException.class)
	public Map<String, String> handleNoSuchElementException(NoSuchElementException e) {
		logger.error("No such Element: {}", e.getMessage());
		
		Map<String, String> response = new HashMap<>();
		response.put("Message", e.toString());
		
		return response;
	}
	
}
