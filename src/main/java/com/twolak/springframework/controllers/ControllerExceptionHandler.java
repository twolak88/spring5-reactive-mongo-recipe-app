/**
 * 
 */
package com.twolak.springframework.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import lombok.extern.slf4j.Slf4j;

/**
 * @author twolak
 *
 */
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
	
//	@ResponseStatus(code = HttpStatus.NOT_FOUND)
//	@ExceptionHandler({NotFoundException.class, TemplateInputException.class, WebExchangeBindException.class})
//	public String handleNotFound(NotFoundException exception, Model model) {
//		return handleException(exception, model, HttpStatus.NOT_FOUND);
//	}
//	
//	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
//	@ExceptionHandler(NumberFormatException.class)
//	public String handleNumberFormat(NumberFormatException exception, Model model) {
//		return handleException(exception, model, HttpStatus.BAD_REQUEST);
//	}
//	
//	private String handleException(Exception exception, Model model, HttpStatus httpStatus) {
//		log.error("Handling " + exception.getClass());
//		log.error(exception.getMessage());
//		log.error(ExceptionUtils.getStackTrace(exception));
//		
//		model.addAttribute("exception", exception);
//		model.addAttribute("httpStatus", httpStatus);
//		return "recipe/error/404error";
//	}
}
