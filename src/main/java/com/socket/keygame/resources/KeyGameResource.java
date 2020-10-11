package com.socket.keygame.resources;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.socket.keygame.handler.MessageHandler;
import com.socket.keygame.handler.impl.ServerMessageHandler;
import com.socket.keygame.io.MessageDecoder;
import com.socket.keygame.io.MessageEncoder;
import com.socket.keygame.model.Message;

@ServerEndpoint(value = "/game", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class KeyGameResource {

	private Logger logger = Logger.getLogger(this.getClass().getName());
	MessageHandler serverMessageHandler = new ServerMessageHandler();

	@OnOpen
	public void onOpen(Session session) {
		logger.info("Connected ... " + session.getId());
	}

	@OnMessage
	public Message onMessage(Message message, Session session) {
		return serverMessageHandler.handlerMessage(message, session);
	}

	@OnClose

	public void onClose(Session session, CloseReason closeReason) {
		logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
	}
}
