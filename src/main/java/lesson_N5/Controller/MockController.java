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
import lesson_N5.DTO.Wrapper;

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
    public ResponseEntity<?> postAnswer(@RequestBody Wrapper wrapper) {

        if (wrapper == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ошибка: тело запроса отсутствует");
        }

        PersonaDto persona1 = wrapper.getPersona1();
        PersonaDto persona2 = wrapper.getPersona2();

        if (persona1 == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ошибка: persona1 отсутствует");
        }

        if (persona2 == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ошибка: persona2 отсутствует");
        }

        if (persona1.getName() == null || persona1.getName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ошибка: persona1.name пустой");
        }

        if (persona1.getSurname() == null || persona1.getSurname().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ошибка: persona1.surname пустой");
        }

        if (persona1.getAge() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ошибка: persona1.age пустой");
        }

        if (persona2.getName() == null || persona2.getName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ошибка: persona2.name пустой");
        }

        if (persona2.getSurname() == null || persona2.getSurname().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ошибка: persona2.surname пустой");
        }

        if (persona2.getAge() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ошибка: persona2.age пустой");
        }

        persona2.setAge(persona2.getAge() * 2);

        return ResponseEntity.ok(wrapper);
    }
}