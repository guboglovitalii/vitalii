package kafka.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaMock {

    private KafkaTemplate<String, String> kafkaTemplate;

    public KafkaMock(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "test-topic", groupId = "stub-group")
    public void kafkaListener(String message) {
        System.out.println("Received " + message);
        String customMessage = message + " " + message;
        kafkaTemplate.send("test-topic2", customMessage);
        System.out.println("Sent: " + customMessage);
    }

}
