package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = OllamaApplication.class)
class OllamaOpenAiChatModelTest {

  @Autowired private OpenAiChatModel openAiChatModel;

  @Test
  void testChat() {
    String res = openAiChatModel.call("who are you");
    System.out.println(res);
  }
}
