package com.bootcamptoprod.service;

// Necessary imports for the service
import com.bootcamptoprod.model.common.Message;
import com.bootcamptoprod.model.request.ChatGPTRequest;
import com.bootcamptoprod.model.response.ChatGPTResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Arrays;

// Marks the class as a Spring service
@Service
public class ChatGPTService {

    // Read the API key from application properties
	@Value("${OPENAI_API_KEY}")
    private String apiKey;

    // Define the OpenAI API endpoint URL
    private static final String OPEN_AI_CHAT_ENDPOINT = "https://api.openai.com/v1/chat/completions";

    // Declare a class variable for the RestTemplate
    private RestTemplate restTemplate;

    // Constructor for the ChatGPTService
    public ChatGPTService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Method to get a ChatGPTResponse from the OpenAI API based on the input prompt
    public ChatGPTResponse getChatCPTResponse(String prompt) {

        // Set up HTTP headers with content type and authorization
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        // Create a ChatGPTRequest object and set its properties
        ChatGPTRequest chatGPTRequest = new ChatGPTRequest();
        chatGPTRequest.setModel("gpt-4");
        chatGPTRequest.setMessages(Arrays.asList(new Message("user", prompt)));

        // Create a RestTemplate object
        RestTemplate restTemplate = new RestTemplate();

        // Create an HttpEntity object with the ChatGPTRequest and headers
        HttpEntity<ChatGPTRequest> request = new HttpEntity<>(chatGPTRequest, headers);

        // Send the POST request to the OpenAI API and return the response as a ChatGPTResponse object
        return restTemplate.postForObject(OPEN_AI_CHAT_ENDPOINT, request, ChatGPTResponse.class);
    }
}
