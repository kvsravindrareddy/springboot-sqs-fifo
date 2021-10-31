package com.veera.config;

import javax.jms.JMSException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

//https://docs.aws.amazon.com/AWSSimpleQueueService/latest/SQSDeveloperGuide/getting-started.html#creating-queue-FIFO

@Configuration
public class SQSConfig {

	@Bean
	public SQSConnection sQSConnection() {
		// Create a new connection factory with all defaults (credentials and region)
		// set automatically
		SQSConnectionFactory connectionFactory = new SQSConnectionFactory(new ProviderConfiguration(),
				AmazonSQSClientBuilder.standard().withRegion("us-east-1")
						.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("AKIAS5J66HT6LM7WCAVH", "8Y1L+tmOcxZL1c0lIXkB++ksZw1iY1qzYEVgC1Do"))).build());

		SQSConnection sQSConnection = null;
		// Create the connection.
		try {
			sQSConnection = connectionFactory.createConnection();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return sQSConnection;
	}
}