package com.microservices.camelmicroservicea.routes.a;

import java.time.LocalDateTime;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/***
 * 
 * @author Dionatan Leão
 *
 */

@Component
public class MyFirstTimerRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		//timer 
		//transformation
		//log 
		
		//Exchange[ExchangePattern: InOnly, BodyType: null, Body: [Body is null]]
		from("timer:first-timer"). //null
//			transform().constant("My Constant Message").
			transform().constant("Time now is " + LocalDateTime.now()).		
		to("log:first-timer"); // database
		
	}
	
	
}
