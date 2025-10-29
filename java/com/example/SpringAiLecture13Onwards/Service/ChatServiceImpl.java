package com.example.SpringAiLecture13Onwards.Service;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.ai.document.Document;
import org.springframework.ai.ollama.api.OllamaApi.ChatRequest;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.SpringAiLecture13Onwards.Beans.ResponseType;

import reactor.core.publisher.Flux;

@Service
public class ChatServiceImpl implements ChatService{
	@Autowired
	public VectorStore vectorStore;
	public ChatClient chatClient;
	Logger logger=Logger.getLogger("logger");
	
	@Value("classpath:testFiles/system.txt")
	public Resource systemMessage;
	@Value("classpath:testFiles/user.txt")
	public Resource userMessage;
	
	@Autowired
	public ChatServiceImpl(ChatClient chatClient, VectorStore vectorStore) {
		logger.info("chatService constructor");
		this.chatClient = chatClient;
		this.vectorStore=vectorStore;
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
		System.out.print(Query);
//		SearchRequest searchRequest =SearchRequest.builder().query(Query).topK(10).similarityThreshold(0.6).build();
//		List<Document> document=vectorStore.similaritySearch(searchRequest);
//		List<String> documentData=document.stream().map(Document::getText).toList();
//		String Context=String.join(",", documentData);
//		logger.info("call chat started  "+Context);
//
//		 if (Context == null || Context.trim().isEmpty()) {
//		        logger.info("No documents found for query: " + Query);
//		        return "This query has no solution in my database.";
//		    }
		RetrievalAugmentationAdvisor adv=RetrievalAugmentationAdvisor.builder()
			
				.documentRetriever(VectorStoreDocumentRetriever.builder().similarityThreshold(0.5)
				.topK(5)
				.vectorStore(vectorStore).build()
				).build();
			
				
				//		QuestionAnswerAdvisor qnAadAdvisor=QuestionAnswerAdvisor.builder(vectorStore).searchRequest(SearchRequest.builder().topK(8).similarityThreshold(0.8d).build()).build();
		String response=this.chatClient.prompt()
				.advisors(adv)
//				.advisors(advisorspec-> advisorspec.param(ChatMemory.CONVERSATION_ID,UUID.randomUUID().toString()))
				.system(this.systemMessage)
				.user(user->user.text(userMessage).param("query", Query))
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


	@Override
	public void saveData(List<String> data) {
		List<Document> datatobestored=data.stream().map(Document::new).toList();
		vectorStore.add(datatobestored);
		
	}
}
