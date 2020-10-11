package com.socket.keygame.model;

import java.sql.Timestamp;

public class Message {
	private String currentKey;
	private int currentScore;
	private int incorrectKeyCount;
	private long timeOutForAnswer;
	private String message;
	private Timestamp keyGenerationTime;
	private boolean isGameOn;
	private MessageType messageType;
	
	public Message(String currentKey, int currentScore, int incorrectKeyCount, long timeOutForAnswer, String message) {
		super();
		this.currentKey = currentKey;
		this.currentScore = currentScore;
		this.incorrectKeyCount = incorrectKeyCount;
		this.timeOutForAnswer = timeOutForAnswer;
		this.message = message;
	}

	public Message() {
	}

	public String getCurrentKey() {
		return currentKey;
	}
	public void setCurrentKey(String currentKey) {
		this.currentKey = currentKey;
	}
	public int getCurrentScore() {
		return currentScore;
	}
	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}
	public int getIncorrectKeyCount() {
		return incorrectKeyCount;
	}
	public void setIncorrectKeyCount(int incorrectKeyCount) {
		this.incorrectKeyCount = incorrectKeyCount;
	}
	public long getTimeOutForAnswer() {
		return timeOutForAnswer;
	}
	public void setTimeOutForAnswer(long timeOutForAnswer) {
		this.timeOutForAnswer = timeOutForAnswer;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isGameOn() {
		return isGameOn;
	}

	public void setGameOn(boolean isGameOn) {
		this.isGameOn = isGameOn;
	}

	public Timestamp getKeyGenerationTime() {
		return keyGenerationTime;
	}

	public void setKeyGenerationTime(Timestamp keyGenerationTime) {
		this.keyGenerationTime = keyGenerationTime;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

}
