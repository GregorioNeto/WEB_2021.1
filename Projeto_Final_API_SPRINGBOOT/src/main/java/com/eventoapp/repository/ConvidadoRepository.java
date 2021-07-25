package com.eventoapp.repository;

import com.eventoapp.models.Convidado;
import org.springframework.data.repository.CrudRepository;
import com.eventoapp.models.Evento;

import java.io.StringReader;


public interface ConvidadoRepository extends CrudRepository<Convidado, String> {
    Iterable<Convidado> findByEvento(Evento evento);
    public Convidado findByRg(String rg);

}
