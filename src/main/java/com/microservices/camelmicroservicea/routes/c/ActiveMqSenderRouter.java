package com.microservices.camelmicroservicea.routes.c;

import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.crypto.CryptoDataFormat;
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

		// timer
		from("timer:active-mq-timer?period=10000")
			.transform().constant("My message for Active MQ")
			.marshal(createEncryptor())
		// queue
		.to("activemq:my-active-mq-queue");

//		from("file:files/json").
//			log("${body}").
//		to("activemq:my-active-mq-queue");

//		from("file:files/xml").
//		log("${body}").
//		to("activemq:my-active-mq-xml-queue");
	}

	private CryptoDataFormat createEncryptor() throws KeyStoreException, IOException, NoSuchAlgorithmException,
			CertificateException, UnrecoverableKeyException {
		KeyStore keyStore = KeyStore.getInstance("JCEKS");
		ClassLoader classLoader = getClass().getClassLoader();
		keyStore.load(classLoader.getResourceAsStream("myDesKey.jceks"), "someKeystorePassword".toCharArray());
		Key sharedKey = keyStore.getKey("myDesKey", "someKeyPassword".toCharArray());

		CryptoDataFormat sharedKeyCrypto = new CryptoDataFormat("DES", sharedKey);
		return sharedKeyCrypto;
	}

}
