package models;
import models.enums.EstadoEjemplar;
public class Ejemplar {
    private int id;
    private EstadoEjemplar estado;
    private Libro libro;

    public Ejemplar(EstadoEjemplar estado, int id, Libro libro) {
        this.estado = estado;
        this.id = id;
        this.libro = libro;
    }

    public void setEstado(EstadoEjemplar estado) {
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }


    public boolean isAvailable(){
        return estado == EstadoEjemplar.DISPONIBLE;
    }
}
