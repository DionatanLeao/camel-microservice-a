package com.microservices.camelmicroservicea.routes.c;

import org.apache.camel.builder.RouteBuilder;

/***
 * 
 * @author Dionatan Leão
 *
 */

//@Component
public class KafkaSenderRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		from("file:files/json").
		log("${body}").
		to("kafka:myKafkaTopic");
	}

}
