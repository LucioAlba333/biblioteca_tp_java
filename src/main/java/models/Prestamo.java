package models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.FutureOrPresent;
import java.util.Date;


public class Prestamo {
    private int id;

    @PastOrPresent(message = "la fecha de inicio no puede ser un valor futuro")
    private Date fechaInicio;

    @PastOrPresent(message = "la fecha de devolucion no puede ser un valor futuro")
    private Date fechaDevolucion;

    @FutureOrPresent(message = "la fecha limite no puede ser un valor pasado")
    private Date fechaLimite;

    @NotNull(message = "los prestamos deben tener una persona asociada")
    private Persona persona;

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}


