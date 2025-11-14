package models;
import jakarta.validation.constraints.NotNull;
import models.enums.EstadoEjemplar;
public class Ejemplar {

    private int id;
    private EstadoEjemplar estado;
    private boolean disponible;

    @NotNull(message = "un ejemplar debe tener un libro asociado")
    private Libro libro;

    private Prestamo prestamo;

    public Ejemplar(boolean disponible, EstadoEjemplar estado, int id, Libro libro, Prestamo prestamo) {
        this.disponible = disponible;
        this.estado = estado;
        this.id = id;
        this.libro = libro;
        this.prestamo = prestamo;
    }

    public Ejemplar() {
    }

    public boolean isDisponible() {
        return disponible;
    }


    /**
     *  cambia de verdadero a falso y viceversa
     */
    private void cambiarDisponibilidad() {
        this.disponible = !this.disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public EstadoEjemplar getEstado() {
        return estado;
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

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {

        this.prestamo = prestamo;
        this.cambiarDisponibilidad();
    }
    public void devolver() {
        if (this.prestamo == null) {
            throw new IllegalStateException("No hay préstamo activo para este ejemplar");
        }
        this.cambiarDisponibilidad();
        this.prestamo = null;
    }
}
