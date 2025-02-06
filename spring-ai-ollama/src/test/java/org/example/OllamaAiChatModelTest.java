package org.example;


import org.junit.jupiter.api.Test;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = OllamaApplication.class)
class OllamaAiChatModelTest {

  @Autowired private OllamaChatModel ollamaChatModel;

  @Test
  void testChat() {
    String res = ollamaChatModel.call("who are you");
    System.out.println(res);
  }
}
