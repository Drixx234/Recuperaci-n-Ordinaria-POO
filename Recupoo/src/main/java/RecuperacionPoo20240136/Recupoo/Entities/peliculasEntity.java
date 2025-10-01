package RecuperacionPoo20240136.Recupoo.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "PELICULAS")
public class peliculasEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_peliculas_id")
    @SequenceGenerator(sequenceName = "seq_peliculas_id", name = "seq_peliculas_id", allocationSize = 1)
    private Long Id;

    @Column(name = "TITULO")
    private String Titulo;

    @Column(name = "DIRECTOR")
    private String Director;

    @Column(name = "AÑO")
    private int Año;

    @Column(name = "GENERO")
    private String Genero;

    @Column(name = "DURACION_MINUTOS")
    private int Duracion_Minutos;

    @Column(name = "PUNTUACION")
    private int Puntuacion;

}
