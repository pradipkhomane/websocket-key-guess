package com.socket.keygame.handler.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import javax.websocket.Session;

import com.socket.keygame.exception.KeyGameException;
import com.socket.keygame.handler.MessageHandler;
import com.socket.keygame.model.Message;
import com.socket.keygame.model.MessageType;

public class ClientMessageHandler implements MessageHandler {

	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Override
	public Message handlerMessage(Message message, Session session) {
		if (!message.isGameOn()) {
			logger.info("Server - " + message.getMessage());
		}

		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		try {
			logger.info("Your current score is " + message.getCurrentScore());
			logger.info("Timeout in second is " + message.getTimeOutForAnswer());
			logger.info("Server - " + message.getMessage());
			String userKey = bufferRead.readLine();
			message.setCurrentKey(userKey);
			if(MessageType.QUIT.name().equalsIgnoreCase(userKey))
				message.setMessageType(MessageType.QUIT);
			else if(MessageType.START.name().equalsIgnoreCase(userKey))
				message.setMessageType(MessageType.START);
			return message;
		} catch (IOException e) {
			throw new KeyGameException(e);
		}
	}

}
