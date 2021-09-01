package com.microservices.camelmicroservicea.routes.c;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/***
 * 
 * @author Dionatan Leão
 *
 */

@Component
public class ActiveMqSenderRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		//timer
		from("timer:active-mq-timer?period=10000").
			transform().constant("My message for Active MQ").
		//queue
		to("activemq:my-activemq-queue");
		
	}

}
