package models.enums;

public enum EstadoEjemplar {
    DISPONIBLE(1), PRESTADO(2), RESERVADO(3);

    private final int codigo;

    EstadoEjemplar(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
}
