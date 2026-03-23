package lesson_N5.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wrapper {
    @JsonProperty("Persona1")
    private PersonaDto persona1;
    @JsonProperty("Persona2")
    private PersonaDto persona2;

}