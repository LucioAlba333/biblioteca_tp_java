package services;

import data.GeneroRepository;
import models.Genero;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

public class GeneroService {
    private static final Logger LOG = LoggerFactory.getLogger(GeneroService.class);
    private final GeneroRepository generoRepository;

    public GeneroService() {
        generoRepository = new GeneroRepository();
    }

    public LinkedList<Genero> listarGeneros() {
        LOG.info("Llamando getAllGeneros");
        return generoRepository.getAllGeneros();
    }

    public Genero getGeneroById(int id) {
        LOG.info("Llamando getGeneroById con id '{}'", id);
        return generoRepository.getGeneroById(id);
    }

    public void crearGenero(Genero nuevo) {
        LOG.info("Creando nuevo género con descripción '{}'", nuevo.getDescripcion());
        generoRepository.insertGenero(nuevo);
    }

    public boolean eliminarGenero(int id) {
        LOG.info("Eliminando género con id '{}'", id);
        return generoRepository.deleteGenero(id);
    }

    public boolean editarGenero(Genero genero) {
        LOG.info("Editando género con id '{}'", genero.getId());
        return generoRepository.updateGenero(genero);
    }
}
