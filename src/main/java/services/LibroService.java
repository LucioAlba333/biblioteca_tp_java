package services;

import data.*;
import models.Autor;
import models.Editorial;
import models.Genero;
import models.Libro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

public class LibroService {
    private static final Logger LOG = LoggerFactory.getLogger(LibroService.class);

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final EditorialRepository editorialRepository;
    private final GeneroRepository generoRepository;
    private final EjemplarRepository ejemplarRepository;

    public LibroService() {
        this.libroRepository = new LibroRepository();
        this.autorRepository = new AutorRepository();
        this.editorialRepository = new EditorialRepository();
        this.generoRepository = new GeneroRepository();
        this.ejemplarRepository = new EjemplarRepository();
    }

    public LinkedList<Libro> listarLibros() {
        LOG.info("Llamando getAllLibros");
        LinkedList<Libro> libros = libroRepository.getAllLibros();

        for (Libro libro : libros) {
            mapLibro(libro);
            libro.ejemplares.addAll(ejemplarRepository.getAllEjemplaresByLibro(libro.getId()));
        }
        return libros;
    }

    public Libro getLibroById(int id) {
        LOG.info("Llamando getLibroById con id '{}'", id);
        Libro libro = libroRepository.getLibroById(id);
        if (libro != null) {
            mapLibro(libro);
        }
        return libro;
    }

    public void crearLibro(Libro nuevo) {
        LOG.info("Creando nuevo libro con título '{}'", nuevo.getTitulo());
        libroRepository.insertLibro(nuevo);
    }

    public boolean eliminarLibro(int id) {
        LOG.info("Eliminando libro con id '{}'", id);
        return libroRepository.deleteLibro(id);
    }

    public boolean editarLibro(Libro libro) {
        LOG.info("Editando libro con id '{}'", libro.getId());
        return libroRepository.updateLibro(libro);
    }

    private void mapLibro(Libro libro) {
        Autor autor = autorRepository.getAutorById(libro.getAutor().getId());
        Editorial editorial = editorialRepository.getEditorialById(libro.getEditorial().getId());
        Genero genero = generoRepository.getGeneroById(libro.getGenero().getId());

        if (autor != null) libro.setAutor(autor);
        if (editorial != null) libro.setEditorial(editorial);
        if (genero != null) libro.setGenero(genero);
    }
}
