package com.example.SpringAiLecture13Onwards.Controller;

import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringAiLecture13Onwards.Beans.ResponseType;
import com.example.SpringAiLecture13Onwards.Service.ChatService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ollama")
public class ChatController {
	public ChatService chatService;
	@Autowired
	public ChatController(ChatService chatService) {
		
		this.chatService = chatService;
	}


	//session seperation 
	@GetMapping("/callchat")
	public ResponseEntity<String> callChat(@RequestParam("q") String query,@RequestHeader("userId") String userid){
	
		return ResponseEntity.ok(chatService.callChat(query,userid));
	}
	
	@GetMapping("/streamchat")
	public ResponseEntity<Flux<String>> streamChat(@RequestParam("q") String query){
	
		return ResponseEntity.ok(chatService.streamChat(query));
	}
	
	@GetMapping("/entitychat")
	public ResponseEntity<ResponseType> entityChat(@RequestParam("q") String query){
	
		return ResponseEntity.ok(chatService.callEntity(query));
	}
}
