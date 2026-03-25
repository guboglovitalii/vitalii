package kafka.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaMock {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaMock(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "test-topic", groupId = "stub-group2")
    public void kafkaListener(String message) {
        System.out.println("Received: " + message);

        String response;

        try {
            if (message.startsWith("GET ") || message.startsWith("Get ")) {
                response = handleGet(message);
            } else if (message.startsWith("POST ") || message.startsWith("Post ")) {
                response = handlePost(message);
            } else {
                response = "InternalServerError: неизвестный тип запроса";
            }
        } catch (Exception e) {
            response = "InternalServerError: " + e.getMessage();
        }

        kafkaTemplate.send("test-topic2", response);
        System.out.println("Sent: " + response);
    }

    // ================= GET =================
    private String handleGet(String message) {

        if (!message.startsWith("GET /app/v1/getRequest") &&
                !message.startsWith("Get /app/v1/getRequest")) {
            return "InternalServerError: неправильный путь GET запроса";
        }

        String[] parts = message.split("\\?");
        if (parts.length < 2) {
            return "InternalServerError: неправильный GET запрос";
        }

        String query = parts[1];
        String[] params = query.split("&");

        Integer id = null;
        String name = null;

        for (String param : params) {
            String[] keyValue = param.split("=");

            if (keyValue.length < 2) {
                continue;
            }

            if (keyValue[0].equals("id")) {
                if (keyValue[1].isEmpty()) {
                    return "InternalServerError: id пустой";
                }
                id = Integer.parseInt(keyValue[1]);
            }

            if (keyValue[0].equals("name")) {
                name = keyValue[1];
            }
        }

        if (id == null) {
            return "InternalServerError: id пустой";
        }

        if (name == null || name.isEmpty()) {
            return "InternalServerError: name пустой";
        }

        if (id <= 10) {
            return "InternalServerError: id должен быть больше 10";
        }

        if (name.length() <= 5) {
            return "InternalServerError: name должен быть длиннее 5 символов";
        }

        return "Hello, " + name;
    }

    // ================= POST =================
    private String handlePost(String message) throws Exception {

        if (!message.startsWith("POST /app/v1/postRequest") &&
                !message.startsWith("Post /app/v1/postRequest")) {
            return "InternalServerError: неправильный путь POST запроса";
        }

        int jsonStart = message.indexOf("{");
        if (jsonStart == -1) {
            return "InternalServerError: нет тела запроса";
        }

        String json = message.substring(jsonStart);

        Map<String, Object> body = objectMapper.readValue(json, Map.class);

        String name = (String) body.get("name");
        String surname = (String) body.get("surname");
        Integer age = (Integer) body.get("age");

        if (name == null || name.isEmpty()) {
            return "InternalServerError: name пустой";
        }

        if (surname == null || surname.isEmpty()) {
            return "InternalServerError: surname пустой";
        }

        if (age == null) {
            return "InternalServerError: age пустой";
        }

        Map<String, Object> person1 = new HashMap<>();
        person1.put("name", name);
        person1.put("surname", surname);
        person1.put("age", age);

        Map<String, Object> person2 = new HashMap<>();
        person2.put("name", surname);
        person2.put("surname", name);
        person2.put("age", age * 2);

        Map<String, Object> response = new HashMap<>();
        response.put("Person1", person1);
        response.put("Person2", person2);

        return objectMapper.writeValueAsString(response);
    }
}