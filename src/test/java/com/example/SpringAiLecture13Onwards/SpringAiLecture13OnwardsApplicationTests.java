package com.example.SpringAiLecture13Onwards;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.SpringAiLecture13Onwards.Service.ChatService;
import com.example.SpringAiLecture13Onwards.helpers.helper;

@SpringBootTest
class SpringAiLecture13OnwardsApplicationTests {
	@Autowired
	public ChatService chatService;
	@Autowired
	public helper help1;
	
	@Test
	void contextLoads() {
		chatService.saveData(help1.getData());
	}

}
