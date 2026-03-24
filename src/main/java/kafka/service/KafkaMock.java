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
            if (message.startsWith("GET")) {
                response = handleGet(message);
            } else if (message.startsWith("POST")) {
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

        // пример: GET /app/v1/getRequest?id=15&name=Vitaliy
        String[] parts = message.split("\\?");
        if (parts.length < 2) {
            return "InternalServerError: неправильный GET запрос";
        }

        String query = parts[1]; // id=15&name=Vitaliy
        String[] params = query.split("&");

        Integer id = null;
        String name = null;

        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue[0].equals("id")) {
                id = Integer.parseInt(keyValue[1]);
            }
            if (keyValue[0].equals("name")) {
                name = keyValue[1];
            }
        }

        // валидация
        if (id == null || id <= 10) {
            return "InternalServerError: id должен быть больше 10";
        }

        if (name == null || name.length() <= 5) {
            return "InternalServerError: name должен быть длиннее 5 символов";
        }

        // имитация getAnswer.txt
        return "Hello, " + name;
    }

    // ================= POST =================
    private String handlePost(String message) throws Exception {

        // пример:
        // POST /app/v1/postRequest {"name":"Igor","surname":"Sidorov","age":12}

        int jsonStart = message.indexOf("{");
        if (jsonStart == -1) {
            return "InternalServerError: нет тела запроса";
        }

        String json = message.substring(jsonStart);

        Map<String, Object> body = objectMapper.readValue(json, Map.class);

        String name = (String) body.get("name");
        String surname = (String) body.get("surname");
        Integer age = (Integer) body.get("age");

        // валидация
        if (name == null || name.isEmpty()) {
            return "InternalServerError: name пустой";
        }
        if (surname == null || surname.isEmpty()) {
            return "InternalServerError: surname пустой";
        }
        if (age == null) {
            return "InternalServerError: age пустой";
        }

        // формируем ответ как в postAnswer.txt
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