package com.example.SpringAiLecture13Onwards.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.SpringAiLecture13Onwards.Beans.ResponseType;

import reactor.core.publisher.Flux;

@Service
public interface ChatService {

	public Flux<String> streamChat(String query);
	public String callChat(String query,String userid);
	public ResponseType callEntity(String Query);
	public void saveData(List<String> data);
}
