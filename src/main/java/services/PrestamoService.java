package services;

import data.PrestamoRepository;
import data.EjemplarRepository;
import models.Prestamo;
import models.Ejemplar;

import java.util.Date;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final EjemplarRepository ejemplarRepository;


    public PrestamoService() {
        this.prestamoRepository = new PrestamoRepository();
        this.ejemplarRepository = new EjemplarRepository();
    }

    public LinkedList<Prestamo> listarPrestamos() {
        LinkedList<Prestamo> prestamos = prestamoRepository.getAllPrestamos();
        LinkedList<Ejemplar> ejemplares = ejemplarRepository.getAllEjemplaresPrestados();
        for (Ejemplar ejemplar : ejemplares) {
            Prestamo prestamoEjemplar = ejemplar.getPrestamo();
            for (Prestamo prestamo : prestamos) {
                if (prestamo.getId() == prestamoEjemplar.getId()) {
                    prestamo.ejemplares.add(ejemplar);
                    break;
                }
            }
        }
        return prestamos;
    }
    public void crearPrestamo(Ejemplar ejemplar, Prestamo prestamo) {

        ejemplar.setPrestamo(prestamo);
        if(ejemplarRepository.updateEjemplar(ejemplar)) {
            prestamoRepository.insertPrestamo(prestamo);
        }

    }

    public void finalizarPrestamo(Prestamo  prestamo) {
        prestamo.setFechaDevolucion(new Date());
        if(prestamoRepository.updatePrestamo(prestamo)){
            for(Ejemplar e : prestamo.ejemplares) {
                e.devolver();
                ejemplarRepository.updateEjemplar(e);
            }
        }
    }
}
