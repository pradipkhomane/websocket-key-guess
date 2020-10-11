package com.socket.keygame.client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.glassfish.tyrus.client.ClientManager;

import com.socket.keygame.exception.KeyGameException;
import com.socket.keygame.handler.MessageHandler;
import com.socket.keygame.handler.impl.ClientMessageHandler;
import com.socket.keygame.io.MessageDecoder;
import com.socket.keygame.io.MessageEncoder;
import com.socket.keygame.model.Message;
import com.socket.keygame.model.MessageType;

@ClientEndpoint(decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class KeyGameClient {

	private static CountDownLatch latch;
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private MessageHandler clientMessageHandler = new ClientMessageHandler();

	@OnOpen
	public void onOpen(Session session) {
		logger.info("Connected ... " + session.getId());
		initConnection(session);
	}

	private void initConnection(Session session) {
		Message message = new Message();
		message.setMessageType(MessageType.START);
		try {
			session.getBasicRemote().sendObject(message);
		} catch (IOException | EncodeException e) {
			throw new KeyGameException(e);
		}
	}

	@OnMessage
	public Message onMessage(Message message, Session session) {
		return clientMessageHandler.handlerMessage(message, session);
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		logger.info(String.format("Session %s close because of %s", session.getId(), closeReason));
		latch.countDown();
	}

	public static void main(String[] args) {
		latch = new CountDownLatch(1);
		ClientManager client = ClientManager.createClient();
		try {
			client.connectToServer(KeyGameClient.class, new URI("ws://localhost:8025/websockets/game"));
			latch.await();

		} catch (DeploymentException | URISyntaxException | InterruptedException e) {
			throw new KeyGameException(e);
		}
	}
}
