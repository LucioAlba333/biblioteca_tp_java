package services;

import data.AutorRepository;
import models.Autor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

public class AutorService {
    private static final Logger LOG = LoggerFactory.getLogger(AutorService.class);
    private final AutorRepository autorRepository = new AutorRepository();

    public LinkedList<Autor> listarAutores() {
        LOG.info("llamando getAllAutores");
        return autorRepository.getAllAutores();
    }
    public Autor getAutorById(int id) {
        LOG.info("llamando getAutorById");
        return autorRepository.getAutorById(id);
    }
    public Autor crearAutor(Autor nuevo) {
        LOG.info("Creando nuevo autor con nombre '{}'", nuevo.getNombre());
        autorRepository.insertAutor(nuevo);
        return nuevo;
    }
    public boolean eliminarAutor(int id) {
        LOG.info("Eliminando autor con id '{}'", id);
        return autorRepository.deleteAutor(id);
    }
    public boolean editarAutor(Autor autor)
    {
        LOG.info("Editando autor con id '{}' ", autor.getId());
        return autorRepository.updateAutor(autor);
    }
}
