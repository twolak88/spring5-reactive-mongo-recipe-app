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
//	@ExceptionHandler(NotFoundException.class)
//	public ModelAndView handleNotFound(NotFoundException exception) {
//		return handleException(exception, HttpStatus.NOT_FOUND);
//	}
//	
//	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
//	@ExceptionHandler(NumberFormatException.class)
//	public ModelAndView handleNumberFormat(NumberFormatException exception) {
//		return handleException(exception, HttpStatus.BAD_REQUEST);
//	}
//	
//	private ModelAndView handleException(Exception exception, HttpStatus httpStatus) {
//		log.error("Handling " + exception.getClass());
//		log.error(exception.getMessage());
//		log.error(ExceptionUtils.getStackTrace(exception));
//		
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.setViewName("recipe/error/404error");
//		modelAndView.addObject("exception", exception);
//		modelAndView.addObject("httpStatus", httpStatus);
//		return modelAndView;
//	}
}
