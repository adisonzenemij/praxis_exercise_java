package com.app.project;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Student {
    // Instancia: Compartida por todas las instancias de la clase.
    // Modificacion: No puede ser reasignado después de la inicialización.
    // Memoria: Eficiente, ya que solo hay una instancia global.
    // Acceso: Se accede de forma estática a través de dicha función.
    private static final Logger logger = Register.getLogger();
    
    // Atributos
    private String uuid;
    private String names;
    private String srnms;
    private String birth;
    
    // Constructor
    public Student(
        String uuid,
        String names,
        String srnms,
        String birth
    ) {
        this.uuid = uuid;
        this.names = names;
        this.srnms = srnms;
        this.birth = birth;
    }

    // Obtener valores del atributo
    public String getUuid() {
        return uuid;
    }

    // Modificar valores del atributo
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    // Obtener valores del atributo
    public String getNames() {
        return names;
    }

    // Modificar valores del atributo
    public void setNames(String names) {
        this.names = names;
    }

    // Obtener valores del atributo
    public String getSrnms() {
        return srnms;
    }

    // Modificar valores del atributo
    public void setSrnms(String srnms) {
        this.srnms = srnms;
    }

    // Obtener valores del atributo
    public String getBirth() {
        return birth;
    }

    // Modificar valores del atributo
    public void setBirth(String birth) {
        this.birth = birth;
    }

    // Visualizar Datos
    public void information() {
        // Utilizar placeholders en el logger para evitar la concatenación de cadenas innecesaria
        // Verificar si el log a nivel INFO es necesario antes de invocar String.format()
        if (logger.isLoggable(Level.INFO)) {
            logger.info(String.format("Registro: %s", getUuid()));
            logger.info(String.format("Nombres: %s", getNames()));
            logger.info(String.format("Apellidos: %s", getSrnms()));
            logger.info(String.format("Nacimiento: %s", getBirth()));
        }
    }
}
