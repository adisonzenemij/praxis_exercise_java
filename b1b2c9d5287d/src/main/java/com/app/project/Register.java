package com.app.project;

import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class Register {
    // Instancia del Register en la clase Utils
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(
        Utility.class.getName()
    );
    
    // Constructor privado para evitar instanciación
    private Register() {
        String message = "Logger class cannot be instantiated";
        // Prevenir creacion de la instancia en la misma clase
        throw new UnsupportedOperationException(message);
    }
    
    // Método para obtener el logger configurado
    public static java.util.logging.Logger getLogger() {
        // Verificar si ya existen handlers, para evitar duplicados
        if (logger.getHandlers().length == 0) {
            // Crear un nuevo ConsoleHandler solo si no existe uno
            ConsoleHandler consoleHandler = new ConsoleHandler();
            // Usar un formateador personalizado que omite la fecha y hora
            consoleHandler.setFormatter(new SimpleFormatterWithoutDate());
            // Añadir el handler al logger
            logger.addHandler(consoleHandler);
            // Desactivar el uso del formateador predeterminado
            // Esto evita que se duplique el resultado
            logger.setUseParentHandlers(false);
        }
        // Retornar configuración
        return logger;
    }

    // Formateador personalizado sin fecha y hora
    public static class SimpleFormatterWithoutDate extends Formatter {
        @Override
        public String format(LogRecord record) {
            // Retornar mensaje sin fecha y hora
            return record.getMessage() + "\n";
        }
    }
}
