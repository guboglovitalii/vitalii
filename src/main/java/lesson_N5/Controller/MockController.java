package lesson_N5.Controller;

import lesson_N5.DTO.PersonaDto;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lesson_N5.DTO.Persona1;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/app/v1")
public class MockController {

    @GetMapping("/getRequest")
    public ResponseEntity<?> getAnswer(@RequestParam("id") int id, @RequestParam("name") String name) throws Exception {

        if (id > 10 && id < 50) {
            Thread.sleep(1000);
        } else {
            Thread.sleep(500);

        }

        if (id <= 10 || name.length() <= 5) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка: id должен быть более 10, количестов букв в составе name <= 5");
        }
        Persona1 users = new Persona1(id, name);
        return ResponseEntity.ok(users);

    }

    @PostMapping("/postRequest")
    public ResponseEntity<?> postAnswer(@RequestBody PersonaDto persona) {

        // проверки
        if (persona.getName() == null || persona.getName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка: name пустой");
        }

        if (persona.getSurname() == null || persona.getSurname().isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка: surname пустой");
        }

        if (persona.getAge() == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка: age пустой");
        }

        // Person1
        Map<String, Object> person1 = new HashMap<>();
        person1.put("name", persona.getName());
        person1.put("surname", persona.getSurname());
        person1.put("age", persona.getAge());

        // Person2
        Map<String, Object> person2 = new HashMap<>();
        person2.put("name", persona.getSurname());
        person2.put("surname", persona.getName());
        person2.put("age", persona.getAge() * 2);

        // итоговый ответ
        Map<String, Object> response = new HashMap<>();
        response.put("Person1", person1);
        response.put("Person2", person2);

        return ResponseEntity.ok(response);
    }
}