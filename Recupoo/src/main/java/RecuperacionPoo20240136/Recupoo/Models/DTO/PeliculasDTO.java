package RecuperacionPoo20240136.Recupoo.Models.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString @EqualsAndHashCode
public class PeliculasDTO {

    private Long Id;

    @NotBlank
    private String Titulo;

    @NotBlank
    private String Director;

    @NotBlank
    private int Año;

    @NotBlank
    private String Genero;

    @NotBlank
    private int Duracion_Minutos;

    @NotBlank
    private int Puntuacion;


}
