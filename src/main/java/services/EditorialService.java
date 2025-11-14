package services;

import data.EditorialRepository;
import models.Editorial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

public class EditorialService {
    private static final Logger LOG = LoggerFactory.getLogger(EditorialService.class);
    private final EditorialRepository editorialRepository;

    public EditorialService() {
        editorialRepository = new EditorialRepository();
    }

    public LinkedList<Editorial> listarEditoriales() {
        LOG.info("Llamando getAllEditoriales");
        return editorialRepository.getAllEditoriales();
    }

    public Editorial getEditorialById(int id) {
        LOG.info("Llamando getEditorialById con id '{}'", id);
        return editorialRepository.getEditorialById(id);
    }

    public void crearEditorial(Editorial nueva) {
        LOG.info("Creando nueva editorial con nombre '{}'", nueva.getNombre());
        editorialRepository.insertEditorial(nueva);
    }

    public boolean eliminarEditorial(int id) {
        LOG.info("Eliminando editorial con id '{}'", id);
        return editorialRepository.deleteEditorial(id);
    }

    public boolean editarEditorial(Editorial editorial) {
        LOG.info("Editando editorial con id '{}'", editorial.getId());
        return editorialRepository.updateEditorial(editorial);
    }
}
