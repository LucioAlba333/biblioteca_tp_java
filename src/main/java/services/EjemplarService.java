package services;

import data.EjemplarRepository;
import data.LibroRepository;
import models.Ejemplar;
import models.Libro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

public class EjemplarService {
    private static final Logger LOG = LoggerFactory.getLogger(EjemplarService.class);

    private final EjemplarRepository ejemplarRepository;
    private final LibroRepository libroRepository;

    public EjemplarService() {
        this.ejemplarRepository = new EjemplarRepository();
        this.libroRepository = new LibroRepository();
    }

    public LinkedList<Ejemplar> listarEjemplares() {
        LOG.info("Llamando getAllEjemplares");
        LinkedList<Ejemplar> ejemplares = ejemplarRepository.getAllEjemplares();
        for (Ejemplar ejemplar : ejemplares) {
            mapEjemplar(ejemplar);
        }
        return ejemplares;
    }

    public Ejemplar getEjemplarById(int id) {
        LOG.info("Llamando getEjemplarById con id '{}'", id);
        Ejemplar ejemplar = ejemplarRepository.getEjemplarById(id);
        if (ejemplar != null) {
            mapEjemplar(ejemplar);
        }
        return ejemplar;
    }

    public void crearEjemplar(Ejemplar nuevo) {
        LOG.info("Creando nuevo ejemplar para libro con id '{}'", nuevo.getLibro().getId());

        Libro libro = libroRepository.getLibroById(nuevo.getLibro().getId());
        if (libro == null) {
            LOG.error("No se pudo crear el ejemplar: libro inválido");
            throw new IllegalArgumentException("Libro inválido");
        }
        nuevo.setLibro(libro);
        ejemplarRepository.insertEjemplar(nuevo);
    }

    public boolean eliminarEjemplar(int id) {
        LOG.info("Eliminando ejemplar con id '{}'", id);
        return ejemplarRepository.deleteEjemplar(id);
    }

    public boolean editarEjemplar(Ejemplar ejemplar) {
        LOG.info("Editando ejemplar con id '{}'", ejemplar.getId());
        Libro libro = libroRepository.getLibroById(ejemplar.getLibro().getId());
        if (libro == null) {
            LOG.error("No se pudo editar el ejemplar: libro inválido");
            throw new IllegalArgumentException("Libro inválido");
        }

        ejemplar.setLibro(libro);
        return ejemplarRepository.updateEjemplar(ejemplar);
    }


    private void mapEjemplar(Ejemplar ejemplar) {
        Libro libro = libroRepository.getLibroById(ejemplar.getLibro().getId());
        if (libro != null) {
            ejemplar.setLibro(libro);
        }
    }
}
