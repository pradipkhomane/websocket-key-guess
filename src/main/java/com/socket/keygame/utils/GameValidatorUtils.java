package com.socket.keygame.utils;

import java.time.Duration;
import java.time.Instant;

import com.socket.keygame.model.Message;

public class GameValidatorUtils {
	
	private GameValidatorUtils(){
	}
	
	public static boolean isTimeOut(Message message) {
		Instant instant = message.getKeyGenerationTime().toInstant();
		if(Duration.between(instant, Instant.now()).getSeconds() > 10) {
			return true;
		}
		return false;
	}

	public static boolean isGameOn(Message message) {
		return message.getCurrentScore() <= 10 && message.getCurrentScore() >= -3
				&& message.getIncorrectKeyCount() <= 3;
	}
}
