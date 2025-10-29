package com.example.SpringAiLecture13Onwards.advisors;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;

import reactor.core.publisher.Flux;

public class TokenPrintAdvisor implements CallAdvisor,StreamAdvisor{

	private Logger logger=LoggerFactory.getLogger(TokenPrintAdvisor.class);
	@Override
	public String getName() {
		return TokenPrintAdvisor.class.getName();
	}

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public Flux<ChatClientResponse> adviseStream(ChatClientRequest chatClientRequest,
			StreamAdvisorChain streamAdvisorChain) {
			logger.info("My  TokenPrintAdvisor is streamed");
			if(chatClientRequest.prompt()!=null && chatClientRequest.prompt().getOptions()!=null)logger.info("promt token consumed:"+chatClientRequest.prompt().getOptions().getMaxTokens());

		
			Flux<ChatClientResponse> response=streamAdvisorChain.nextStream(chatClientRequest);
		
		logger.info("My  TokenPrintAdvisor response it received");
//		logger.info("token consumed:");
		return response;
	}

	@Override
	public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
		logger.info("My  TokenPrintAdvisor is called");
		if(chatClientRequest.prompt()!=null && chatClientRequest.prompt().getOptions()!=null)logger.info("promt token consumed:"+chatClientRequest.prompt().getOptions().getMaxTokens());

		ChatClientResponse response=callAdvisorChain.nextCall(chatClientRequest);
		
		logger.info("My  TokenPrintAdvisor response it received");
		logger.info("response token consumed:"+response.chatResponse().getMetadata().getUsage().getCompletionTokens());
		logger.info("total token consumed:"+response.chatResponse().getMetadata().getUsage().getTotalTokens());
		

		return response;
	}

}
