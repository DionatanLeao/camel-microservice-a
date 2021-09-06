package com.microservices.camelmicroservicea.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/***
 * 
 * @author Dionatan Leão
 *
 */

@Component
public class EpiPatternsRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		from("timer:multicast?period=10000")
			.multicast()
		.to("log:something1", "log:something2", "log:something3");
		
	}

}
