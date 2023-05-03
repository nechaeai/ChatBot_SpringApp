package com.bootcamptoprod.controller;

import com.bootcamptoprod.model.response.ChatGPTResponse;
import com.bootcamptoprod.service.ChatGPTService;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.HashMap;
// Marks the class as a Spring MVC controller
@Controller
public class ChatGPTController {

    private ChatGPTService chatGPTService;

    public ChatGPTController(ChatGPTService chatGPTService) {
        this.chatGPTService = chatGPTService;
    }

    @GetMapping("/")
    public String chatGPTService() {
        return "index";
    }

    @RequestMapping(value = "/chat", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> processInputRequest(@RequestParam String message) {
        ChatGPTResponse chatCPTResponse = chatGPTService.getChatCPTResponse(message);

        StringBuilder responseBuilder = new StringBuilder();
        for (int i = 0; i < chatCPTResponse.choices.size(); i++) {
            responseBuilder.append(chatCPTResponse.choices.get(i).getMessage().content);
            responseBuilder.append("\n");
        }

        Map<String, String> response = new HashMap<>();
        response.put("input", message);
        response.put("response", responseBuilder.toString());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}