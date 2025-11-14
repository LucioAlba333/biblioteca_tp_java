package models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.LinkedList;

public class Libro {
    private int id;
    @NotBlank(message = "El titulo del libro no puede estar vacio")
    @Size(min = 1, max = 50, message = "el titulo debe tener entre 1 y 50 caracteres")
    private String titulo;


    @NotNull(message = "el libro debe tener un autor")
    private Autor autor;

    @NotNull(message = "el libro debe tener un genero")
    private Genero genero;

    @NotNull(message = "el libro debe tener una editorial")
    private Editorial editorial;
    public final LinkedList<Ejemplar> ejemplares;
    public Libro(Autor autor, Editorial editorial, Genero genero, int id, String titulo) {
        this.autor = autor;
        this.editorial = editorial;
        this.genero = genero;
        this.id = id;
        this.titulo = titulo;
        this.ejemplares = new LinkedList<>();
    }
    public Libro() {this.ejemplares = new LinkedList<>();}

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }
}
