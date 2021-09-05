package com.microservices.camelmicroservicea.routes.c;

import org.apache.camel.builder.RouteBuilder;

/***
 * 
 * @author Dionatan Leão
 *
 */

//@Component
public class ActiveMqSenderRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		//timer
//		from("timer:active-mq-timer?period=10000").
//			transform().constant("My message for Active MQ").
//		//queue
//		to("activemq:my-active-mq-queue");

//		from("file:files/json").
//			log("${body}").
//		to("activemq:my-active-mq-queue");
		
		from("file:files/xml").
		log("${body}").
		to("activemq:my-active-mq-xml-queue");
	}

}
