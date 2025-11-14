package services;

import data.PersonaRepository;
import data.PrestamoRepository;
import data.EjemplarRepository;
import models.Prestamo;
import models.Ejemplar;

import java.util.Date;
import java.util.LinkedList;


public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final EjemplarRepository ejemplarRepository;
    private final PersonaRepository personaRepository;


    public PrestamoService() {
        this.prestamoRepository = new PrestamoRepository();
        this.ejemplarRepository = new EjemplarRepository();
        this.personaRepository = new PersonaRepository();
    }

    public LinkedList<Prestamo> listarPrestamos() {
        LinkedList<Prestamo> prestamos = prestamoRepository.getAllPrestamos();
        LinkedList<Ejemplar> ejemplares = ejemplarRepository.getAllEjemplaresPrestados();
        for (Prestamo prestamo : prestamos) {
            prestamo.setPersona(personaRepository.getPersonaById(prestamo.getPersona().getId()));
        }
        for (Ejemplar ejemplar : ejemplares) {
            Prestamo prestamoEjemplar = ejemplar.getPrestamo();
            for (Prestamo prestamo : prestamos) {
                if (prestamo.getId() == prestamoEjemplar.getId()) {
                    prestamo.ejemplares.add(ejemplar);
 ;
                    break;
                }
            }
        }
        return prestamos;
    }
    public void crearPrestamo(Ejemplar ejemplar, Prestamo prestamo) {
        prestamoRepository.insertPrestamo(prestamo);
        ejemplar.setPrestamo(prestamo);
        ejemplarRepository.updateEjemplar(ejemplar);



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
