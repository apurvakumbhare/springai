package com.example.SpringAiLecture13Onwards.Beans;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.net.Nio2Endpoint.SendfileData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.DefaultChatClientBuilder;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.SpringAiLecture13Onwards.Service.ChatService;
import com.example.SpringAiLecture13Onwards.advisors.TokenPrintAdvisor;
import com.example.SpringAiLecture13Onwards.helpers.helper;




@Configuration
public class ChatModelConfigurations {
	List<String> sensitive_data=new ArrayList<String>();
	
	private Logger logger=LoggerFactory.getLogger(ChatModelConfigurations.class);
	@Bean
	public ChatMemory chatMemory(JdbcChatMemoryRepository jdbcChatMemoryRepository) {
		return MessageWindowChatMemory
				.builder()
				.chatMemoryRepository(jdbcChatMemoryRepository)
				.build();
		
	}
	 @Bean
	    CommandLineRunner loadVectorData(ChatService chatService, helper help1) {
	        return args -> {
	            System.out.println("🌟 Loading data into VectorStore...");
	            chatService.saveData(help1.getData());
	            System.out.println("✅ Data successfully saved into vector_store!");
	        };
	    }
	@Bean
	public ChatClient chatClient(ChatClient.Builder builder, ChatMemory chatMemory) {
		 //chat memory implementation
		logger.info("chatmemory implementation class"+ chatMemory.getClass().getName());
		
		MessageChatMemoryAdvisor messageChatMemoryAdvisor=MessageChatMemoryAdvisor.builder(chatMemory).build();
		sensitive_data.add("diwali");
		return builder
				.defaultSystem(system->system.text("you are an expert programmer"))
				
				.defaultOptions(OllamaOptions.builder()
						.build()
						)
				.defaultAdvisors(messageChatMemoryAdvisor,new SimpleLoggerAdvisor(),new TokenPrintAdvisor(),new SafeGuardAdvisor(sensitive_data, "Sorry , we dont response to sensitive constraints", 0))
				.build();
				}
}
