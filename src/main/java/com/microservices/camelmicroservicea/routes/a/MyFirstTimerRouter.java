package com.microservices.camelmicroservicea.routes.a;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/***
 * 
 * @author Dionatan Leão
 *
 */

//@Component
public class MyFirstTimerRouter extends RouteBuilder {
	

	@Autowired
	private GetCurrentTimeBean getCurrentTimeBean;
	
	@Autowired
	private SimpleLoggingProcessingComponent loggingComponent;

	@Override
	public void configure() throws Exception {
		
		//timer 
		//transformation
		//log 
		
		//Exchange[ExchangePattern: InOnly, BodyType: null, Body: [Body is null]]
		from("timer:first-timer"). //null
			log("${body}"). //null
			transform().constant("My Constant Message").
			log("${body}"). //My Constant Message
//			transform().constant("Time now is " + LocalDateTime.now()).	
//			bean("getCurrentTimeBean").
		
		//Processing
		//Transformation
		
			bean(getCurrentTimeBean).
			log("${body}"). //Time now is
			bean(loggingComponent).
			log("${body}"). //Logger
			process(new SimpleLoggingProcessor()).
		to("log:first-timer"); // database
		
	}
	
}

@Component
class GetCurrentTimeBean {	
	public String getCurrentTimeBean() {
		return "Time now is " + LocalDateTime.now();
	}

}
@Component
class SimpleLoggingProcessingComponent {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);
	
	public void process(String message) {
		logger.info("SimpleLoggingProcessingComponent {}", message);
	}
}

class SimpleLoggingProcessor implements Processor {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(SimpleLoggingProcessor.class);
	
	@Override
	public void process(Exchange exchange) throws Exception {
		logger.info("SimpleLoggingProcessor {}", exchange.getMessage().getBody());			
	}
	
}
