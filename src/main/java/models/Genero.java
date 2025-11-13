package models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Genero {
    private int id;

    @NotBlank(message = "la descripcion del genero no puede estar vacia")
    @Size(min = 2, max = 50, message = "la descripcion debe tener entre 2 y 50 caracteres")
    private String descripcion;


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
