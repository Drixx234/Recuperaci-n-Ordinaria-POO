package RecuperacionPoo20240136.Recupoo.Services;

import RecuperacionPoo20240136.Recupoo.Entities.peliculasEntity;
import RecuperacionPoo20240136.Recupoo.Exceptions.ExceptionPeliculaNotFound;
import RecuperacionPoo20240136.Recupoo.Models.DTO.PeliculasDTO;
import Repositories.peliculasRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class peliculasServices {

    @Autowired
    private peliculasRepository repo;

    public List<PeliculasDTO> getAllPelicula(){
        List<peliculasEntity> pageEntity = repo.findAll();
        return pageEntity.stream()
                .map(this::ConvertirADTO)
                .collect(Collectors.toList());
    }

    public PeliculasDTO insert(@Valid PeliculasDTO jsonData){
        if(jsonData == null){
            throw new IllegalArgumentException("Titulo no puede ser nulo");
        }
        try {
            peliculasEntity peliculasEntity = ConvertirAEntity(jsonData);
            peliculasEntity peliculasSave = repo.save(peliculasEntity);
            return ConvertirADTO(peliculasSave);
        }catch (Exception e){
            log.error("error al ingresar pelicula" + e.getMessage());
            throw new ExceptionPeliculaNotFound("Error al ingresar pelicula");
        }
    }

    public PeliculasDTO update(Long id, @Valid PeliculasDTO jsonDto ){
        if (jsonDto == null){
            throw new IllegalArgumentException("La pelicula no puede ser nula");
        }
        peliculasEntity peliculasData = repo.findById(id).orElseThrow(()-> new ExceptionPeliculaNotFound("pelicula no encontrada"));
        peliculasData.setTitulo(jsonDto.getTitulo());
        peliculasData.setDirector(jsonDto.getDirector());

        peliculasEntity peliculasUpdate = repo.save(peliculasData);
        return ConvertirADTO(peliculasUpdate);
    }

    public boolean Delete(Long id){
        try{
            peliculasEntity objEntity = repo.findById(id).orElse(null);

            if (objEntity != null){
                repo.deleteById(id);
                return true;
            }
            return false;
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("no se encontro la pelicula con id"+id,1);
        }
    }



    public PeliculasDTO ConvertirADTO(peliculasEntity objEntity){
        PeliculasDTO dto = new PeliculasDTO();
        objEntity.setId(objEntity.getId());
        objEntity.setTitulo(objEntity.getTitulo());
        objEntity.setDirector(objEntity.getDirector());
        objEntity.setAño(objEntity.getAño());
        objEntity.setGenero(objEntity.getGenero());
        objEntity.setDuracion_Minutos(objEntity.getDuracion_Minutos());
        objEntity.setPuntuacion(objEntity.getPuntuacion());
        return dto;
    }

    private peliculasEntity ConvertirAEntity(@Valid PeliculasDTO json){
        peliculasEntity objEntity = new peliculasEntity();
        objEntity.setTitulo(objEntity.getTitulo());
        objEntity.setPuntuacion(objEntity.getPuntuacion());
        objEntity.setGenero(objEntity.getGenero());
        objEntity.setDirector(objEntity.getDirector());
        return objEntity;
    }

    public Page<PeliculasDTO> getAllPeliculas(int page, int size){
        Pageable pageable = PageRequest.of(page, size); //linea 1
        Page<peliculasEntity> pageEntity = repo.findAll(pageable); // linea 2
        return pageEntity.map(this::ConvertirADTO);
    }

}
