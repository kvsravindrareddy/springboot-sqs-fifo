package com.veera.service;


import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazon.sqs.javamessaging.SQSConnection;

import io.awspring.cloud.messaging.listener.annotation.SqsListener;

@Service
public class SQSMessageReceiver {
	
	@Value("${FIFO_QUEUE}")
	private String inputFifoQueue;
	
	@Autowired
	public SQSConnection sQSConnection;

	
	public void receiveStringMessage() throws JMSException {
		Session session = sQSConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue = session.createQueue(inputFifoQueue);
		// Create a consumer for the 'MyQueue'
		MessageConsumer consumer = session.createConsumer(queue);
		// Start receiving incoming messages
		sQSConnection.start();
		// Receive a message from 'MyQueue' and wait up to 1 second
		Message receivedMessage = consumer.receive(1000);
		// Cast the received message as TextMessage and display the text
		if (receivedMessage != null) {
		    System.out.println("Received: " + ((TextMessage) receivedMessage).getText());
		    System.out.println("Group id: " + receivedMessage.getStringProperty("JMSXGroupID"));
		    System.out.println("Message deduplication id: " + receivedMessage.getStringProperty("JMS_SQS_DeduplicationId"));
		    System.out.println("Message sequence number: " + receivedMessage.getStringProperty("JMS_SQS_SequenceNumber"));
		}
	}
	
	@SqsListener("${FIFO_QUEUE}")
	public void receive(String json)
	{
		System.out.println("........listener received........");
	}
}
