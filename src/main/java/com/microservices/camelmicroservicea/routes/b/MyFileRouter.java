package com.microservices.camelmicroservicea.routes.b;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/***
 * 
 * @author Dionatan Leão
 *
 */

@Component
public class MyFileRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		from("file:files/input")
			.routeId("Files-Input-Route")
			.transform().body(String.class)
				.choice()
					.when(simple("${file:ext} ends  with 'xml'"))
						.log("XML FILE")
					.when(simple("${body} contains 'USD'"))
						.log("Not a XML FILE BUT contains USD")
					.otherwise()
						.log("Not an XML FILE")
				.end()
			.log("${messageHistory} ${file:absolute.path}")
		.to("file:files/output");
		
	}

}
