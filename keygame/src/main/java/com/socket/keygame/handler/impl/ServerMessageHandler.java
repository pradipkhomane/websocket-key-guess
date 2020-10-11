package com.socket.keygame.handler.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.logging.Logger;

import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.Session;

import com.socket.keygame.data.KeyRepository;
import com.socket.keygame.exception.KeyGameException;
import com.socket.keygame.handler.MessageHandler;
import com.socket.keygame.model.Message;
import com.socket.keygame.model.MessageType;
import com.socket.keygame.utils.GameValidatorUtils;

public class ServerMessageHandler implements MessageHandler {

	private Logger logger = Logger.getLogger(this.getClass().getName());
	private static final int TIME_OUT_IN_SCONDS = 10;

	@Override
	public Message handlerMessage(Message message, Session session) {
		if (message.getMessageType() == MessageType.START) {
			logger.info("Starting the game by sending first key.");
			String key = KeyRepository.getInstance().getRandomKey();
			session.getUserProperties().put("key", key);
			return initMessage(key);
		} else if (message.getMessageType() == MessageType.QUIT) {
			logger.info("Quitting the game");
			try {
				session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE, "Game finished"));
			} catch (IOException e) {
				throw new KeyGameException(e);
			}
		}
		String currentKey = (String) session.getUserProperties().get("key");

		return checkLastKeyAndSendNewKey(currentKey, message, session);
	}

	private Message initMessage(String key) {
		Message newMessage = new Message();
		newMessage.setCurrentKey(key);
		newMessage.setCurrentScore(0);
		newMessage.setTimeOutForAnswer(TIME_OUT_IN_SCONDS);
		newMessage.setIncorrectKeyCount(0);
		newMessage.setGameOn(true);
		newMessage.setKeyGenerationTime(new Timestamp(System.currentTimeMillis()));
		newMessage.setMessage("Guess correct key!");
		return newMessage;
	}

	private Message checkLastKeyAndSendNewKey(String currentKey, Message message, Session session) {

		if (!message.isGameOn()) {
			finishGame(session);
		}

		KeyRepository repository = KeyRepository.getInstance();
		String key = repository.getKey(currentKey);
		String nextKey = repository.getRandomKey();
		session.getUserProperties().put("key", nextKey);

		if (key == null || !currentKey.equals(message.getCurrentKey())) {
			updateMessagForIncorrectAnswer(message, currentKey);
		} else {
			updateMessagForCorrectAnswer(message);
		}

		if (!GameValidatorUtils.isGameOn(message)) {
			return finishGame(message);
		}

		return message;
	}

	private void finishGame(Session session) {
		try {
			session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE, "Game finished"));
		} catch (IOException e) {
			throw new KeyGameException(e);
		}
	}

	private void updateMessagForCorrectAnswer(Message message) {
		String userMessage = "You guessed it right. Try the next key!";
		message.setCurrentScore(
				!GameValidatorUtils.isTimeOut(message) ? message.getCurrentScore() + 1 : message.getCurrentScore());
		message.setIncorrectKeyCount(0);
		message.setMessage(userMessage);
	}

	private void updateMessagForIncorrectAnswer(Message message, String currentKey) {
		String userMessage = String.format("You guessed it wrong. Correct answer %s. Try the next one", currentKey);
		message.setCurrentScore(
				!GameValidatorUtils.isTimeOut(message) ? message.getCurrentScore() - 1 : message.getCurrentScore());
		message.setIncorrectKeyCount(message.getIncorrectKeyCount() + 1);
		message.setMessage(userMessage);
	}

	private Message finishGame(Message message) {
		String userMessage = String.format("The game is over with score %s!", message.getCurrentScore());
		message.setMessage(userMessage);
		message.setGameOn(false);
		return message;
	}
}
