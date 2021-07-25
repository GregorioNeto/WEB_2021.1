package com.eventoapp.repository;

import com.eventoapp.models.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface EventoRepository extends CrudRepository<Evento, String>{
//public interface EventoRepository extends JpaRepository<Evento,Long> {
    Evento findByCodigo(long codigo);
}
