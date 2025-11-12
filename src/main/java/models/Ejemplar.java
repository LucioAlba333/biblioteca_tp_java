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

    public Ejemplar(boolean disponible, EstadoEjemplar estado, int id, Libro libro) {
        this.disponible = disponible;
        this.estado = estado;
        this.id = id;
        this.libro = libro;
    }

    public boolean isDisponible() {
        return disponible;
    }

    /**
     *  cambia de verdadero a falso y viceversa
     */
    public void cambiarDisponibilidad() {
        this.disponible = !this.disponible;
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
        if(this.isDisponible() || this.estado == EstadoEjemplar.PERDIDO){
            this.prestamo = prestamo;
            this.cambiarDisponibilidad();
        }
        throw new IllegalStateException("El ejemplar no está disponible para préstamo");
    }
    public void devolver() {
        if (this.prestamo == null) {
            throw new IllegalStateException("No hay préstamo activo para este ejemplar");
        }
        this.cambiarDisponibilidad();
        this.prestamo = null;
    }
}
