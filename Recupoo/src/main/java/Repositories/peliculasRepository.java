package Repositories;

import RecuperacionPoo20240136.Recupoo.Entities.peliculasEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface peliculasRepository extends JpaRepository<peliculasEntity, Long> {
    Page<peliculasEntity>  findAll(Pageable pageable);
}
