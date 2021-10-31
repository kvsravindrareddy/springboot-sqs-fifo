package com.veera.controller;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.veera.service.SQSMessageReceiver;
import com.veera.service.SQSService;

@RestController
public class SQSController {
	
	@Autowired
	private SQSService sQSService;
	
	@Autowired
	private SQSMessageReceiver sQSMessageReceiver;
	
	@GetMapping("/sendmessage")
	public void sendMessage(@RequestParam String msg)
	{
		try {
			sQSService.sendToFifoQueue(msg);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping("/receivemessage")
	public void receiveMessages() throws JMSException
	{
		sQSMessageReceiver.receiveStringMessage();
	}

}