package com.example.SpringAiLecture13Onwards.Service;

import java.util.logging.Logger;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.SpringAiLecture13Onwards.Beans.ResponseType;

import reactor.core.publisher.Flux;

@Service
public class ChatServiceImpl implements ChatService{
	
	
	public ChatClient chatClient;
	Logger logger=Logger.getLogger("logger");
	
	@Autowired
	public ChatServiceImpl(ChatClient chatClient) {
		logger.info("chatService constructor");
		this.chatClient = chatClient;
	}


	@Override
	public Flux<String> streamChat(String Query) {
		logger.info("stream chat started");
		Flux<String> response=this.chatClient.prompt(Query).
				stream().content();
		logger.info("stream chat end");
		return response;
	}
	@Override
	public String callChat(String Query,String userid) {
		logger.info("call chat started");
		String response=this.chatClient.prompt(Query)
				.advisors(advisorspec-> advisorspec.param(ChatMemory.CONVERSATION_ID, userid))
				.call().content();
		logger.info("call chat end");
		return response;
	}
	@Override
	public ResponseType callEntity(String Query) {
		logger.info("call chat started");
		ResponseType response=this.chatClient.prompt(Query)
				.call().entity(ResponseType.class);
		logger.info("call chat end");
		return response;
	}
}
