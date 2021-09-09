package com.microservices.camelmicroservicea.routes;

import java.util.List;
import java.util.Map;

import org.apache.camel.Body;
import org.apache.camel.ExchangeProperties;
import org.apache.camel.Headers;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microservices.camelmicroservicea.CurrencyExchange;

/***
 * 
 * @author Dionatan Leão
 *
 */

//@Component
public class EpiPatternsRouter extends RouteBuilder {
	
//	@Autowired
//	private SplitterComponent splitter;
	
	@Autowired
	private DynamicRouterBean dynamicRouterBean;

	@Override
	public void configure() throws Exception {
		
		getContext().setTracing(true);
		
		errorHandler(deadLetterChannel("activemq:dead-letter-queue"));
		
//		from("timer:multicast?period=10000")
//			.multicast()
//		.to("log:something1", "log:something2", "log:something3");
		
//		from("file:files/csv")
//		.unmarshal().csv()
//		.split(body())
//		.to("activemq:split-queue");
		
		//Message, Message2, Message3
//		from("file:files/csv")
//		.convertBodyTo(String.class)
////		.split(body(),",")
//		.split(method(splitter))
//		.to("activemq:split-queue");
		
		//Aggregate
		//Messages => Aggregate
		//to, 3		
		
		from("file:files/aggregate-json")
		.unmarshal().json(JsonLibrary.Jackson, CurrencyExchange.class)
		.aggregate(simple("${body.to}"), new ArrayListAggregationStrategy())
		.completionSize(3)
//		.completionTimeout(HIGHEST)
		.to("log:aggregate-json");
		
		
		//Routing Splip
		String routingSlip = "direct:endpoint1, direct:endpoint2";
		
		from("timer:routingSlip?period={{timePeriod}}")
		.transform().constant("My message is Hardcoded")
		.routingSlip(simple(routingSlip));
		
		
		from("direct:endpoint1")
			.wireTap("log:wire-tap") //add
		.to("{{endpoint-for-logging}}");
		
		from("direct:endpoint2")
		.to("log:endpoint2");
		
		//Dynamic Routing
		
		//Step 1, Step 2, Step 3
		
		from("timer:dynamicRouting?period=10000")
		.transform().constant("My dynamic routing")
		.dynamicRouter(method(dynamicRouterBean));
		
	}

}

@Component
class SplitterComponent {
	public List<String> splitInput(String body) {
		return List.of("ABC", "DFG", "GHI");
	}
}

@Component
class DynamicRouterBean {
	
	Logger logger = LoggerFactory.getLogger(DynamicRouterBean.class);
	
	int invocations;
	
	public String decideNextEndpoint(@ExchangeProperties Map<String, String> properties,
			@Headers Map<String, String> headers,
			@Body String body) {
		
		logger.info("{} {} {}", properties, headers, body);
		
		invocations++;
		
		if (invocations %3==0)
			return "direct:endpoint1";
		if (invocations %3==1)
			return "direct:endpoint1,direct:endpoint2";
		
		return null;
		
	}
}

