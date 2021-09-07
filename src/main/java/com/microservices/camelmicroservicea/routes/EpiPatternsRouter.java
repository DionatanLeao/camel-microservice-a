package com.microservices.camelmicroservicea.routes;

import java.util.List;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/***
 * 
 * @author Dionatan Leão
 *
 */

@Component
public class EpiPatternsRouter extends RouteBuilder {
	
	@Autowired
	private SplitterComponent splitter;

	@Override
	public void configure() throws Exception {
		
//		from("timer:multicast?period=10000")
//			.multicast()
//		.to("log:something1", "log:something2", "log:something3");
		
//		from("file:files/csv")
//		.unmarshal().csv()
//		.split(body())
//		.to("activemq:split-queue");
		
		//Message, Message2, Message3
		from("file:files/csv")
		.convertBodyTo(String.class)
//		.split(body(),",")
		.split(method(splitter))
		.to("activemq:split-queue");
	}

}

@Component
class SplitterComponent {
	public List<String> splitInput(String body) {
		return List.of("ABC", "DFG", "GHI");
	}
}
