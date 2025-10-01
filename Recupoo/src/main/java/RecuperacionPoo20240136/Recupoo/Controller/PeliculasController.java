package RecuperacionPoo20240136.Recupoo.Controller;

import RecuperacionPoo20240136.Recupoo.Exceptions.ExceptionColumnDuplicate;
import RecuperacionPoo20240136.Recupoo.Exceptions.ExceptionPeliculaNotFound;
import RecuperacionPoo20240136.Recupoo.Models.DTO.PeliculasDTO;
import RecuperacionPoo20240136.Recupoo.Services.peliculasServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/Peliculas")
@CrossOrigin
public class PeliculasController {

    @Autowired
    private peliculasServices services;

    @GetMapping("/getDataPeliculas")
    private ResponseEntity<List<PeliculasDTO>> getData(){
        List<PeliculasDTO> peliculas = services.getAllPelicula();
        if (peliculas == null){
            ResponseEntity.badRequest().body(Map.of(
                    "status", "No hay Peliculas registradas"
            ));
        }
        return ResponseEntity.ok(peliculas);
    }

    @PostMapping("/newPelicula")
    private ResponseEntity<Map<String, Object>> inserCategory(@Valid @RequestBody PeliculasDTO json, HttpServletRequest request){
        try{
            PeliculasDTO response =services.insert(json);
            if (response == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "Error", "Inserción incorrecta",
                        "Estatus", "Inserción incorrecta",
                        "Descripción", "Verifique los valores"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "Estado", "Completado",
                    "data", response
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "status", "error",
                            "message", "Error al registrar Categoria",
                            "detail", e.getMessage()
                    ));
        }
    }

    @PutMapping("/updatePeliculas/{id}")
    public ResponseEntity<?> cambiaPelicula(
            @PathVariable Long id,
            @Valid @RequestBody PeliculasDTO peliculas,
            BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try{
            PeliculasDTO PeliculaActualizado = services.update(id, peliculas);
            return ResponseEntity.ok(PeliculaActualizado);
        }
        catch (ExceptionPeliculaNotFound e){
            return ResponseEntity.notFound().build();
        }
        catch (ExceptionColumnDuplicate e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("error", "Datos duplicados","campo", e.getColumnDuplicate() )
            );
        }
    }



}

