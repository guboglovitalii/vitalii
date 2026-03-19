package lesson_N5.Controller;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lesson_N5.DTO.Persona1;
import lesson_N5.DTO.Persona1_2;

@RestController
@RequestMapping("/app/v1")
public class MockController {

    @GetMapping("/getRequest")
    public ResponseEntity<?> getAnswer(@RequestParam("id") int id, @RequestParam("name") String name) throws Exception {
        
        if(id > 10 && id < 50){
            Thread.sleep(1000);
        }else {
            Thread.sleep(500);

    }

        if(id <= 10 || name.length() <= 5) {
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Ошибка: id должен быть более 10, количестов букв в составе name <= 5");
        }
        Persona1 users = new Persona1(id, name);
        return ResponseEntity.ok(users);
        
    }

    @PostMapping("/postRequest")
    public ResponseEntity<?> postAnswer(@RequestBody Persona1_2 request) {
        if(request.getName() == null || request.getName().isEmpty()){ 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Ошибка: name пустой");
        }
        if(request.getSurname() == null || request.getSurname().isEmpty()){ 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Ошибка: surname пустой");
        }
        if(request.getAge() == null){ 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Ошибка: age пустой");
        }



        return ResponseEntity.ok(request);
    }
    
}
