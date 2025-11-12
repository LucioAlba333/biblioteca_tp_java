package models.enums;

public enum EstadoEjemplar {
    NUEVO(1), BUENO(2), DEGASTADO(3), PERDIDO(4);

    private final int codigo;

    EstadoEjemplar(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
}
