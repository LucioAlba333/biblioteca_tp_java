package services;

import data.PersonaRepository;
import models.Persona;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

public class PersonaService {
    private static final Logger LOG = LoggerFactory.getLogger(PersonaService.class);
    private final PersonaRepository personaRepository;

    public PersonaService() {
        personaRepository = new PersonaRepository();
    }

    public LinkedList<Persona> listarPersonas() {
        LOG.info("Llamando getAllPersonas");
        return personaRepository.getAllPersonas();
    }

    public Persona getPersonaById(int id) {
        LOG.info("Llamando getPersonaById con id '{}'", id);
        return personaRepository.getPersonaById(id);
    }

    public void crearPersona(Persona nueva) {
        LOG.info("Creando nueva persona con nombre '{} {}'", nueva.getNombre(), nueva.getApellido());
        personaRepository.insertPersona(nueva);
    }

    public boolean eliminarPersona(int id) {
        LOG.info("Eliminando persona con id '{}'", id);
        return personaRepository.deletePersona(id);
    }

    public boolean editarPersona(Persona persona) {
        LOG.info("Editando persona con id '{}'", persona.getId());
        return personaRepository.updatePersona(persona);
    }
}
