package com.app.project;

import java.time.format.DateTimeFormatter;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Utility {
    
    // Constructor
    public Utility() {}

    // Formatear Fecha Personalizada
    public static DateTimeFormatter getDateFormat() {
        String format = "yyyy-MM-dd";
        return DateTimeFormatter.ofPattern(format);
    }
}
