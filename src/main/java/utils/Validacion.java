package utils;
import jakarta.validation.Validator;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class Validacion {
    private static final Logger LOG = LoggerFactory.getLogger(Validacion.class);
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    public static <T> void validar(T entity) {
        Set<ConstraintViolation<T>> errores = validator.validate(entity);

        if (!errores.isEmpty()) {
            StringBuilder mensajes = new StringBuilder("Error: ");
            for (ConstraintViolation<T> error : errores) {
                LOG.info(error.getMessage());
                mensajes
                        .append(" ")
                        .append(error.getPropertyPath())
                        .append(": ")
                        .append(error.getMessage())
                        .append("\n");
            }
            throw new IllegalArgumentException(mensajes.toString());
        }
    }
}
