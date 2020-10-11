package com.socket.keygame.handler;

import javax.websocket.Session;

import com.socket.keygame.model.Message;

public interface MessageHandler {
	public Message handlerMessage(Message message, Session session);
}
