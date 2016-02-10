/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.utilidades;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/**
 *
 * @author ANDRES PINEDA
 */
public final class Utilidades {

    static Logger logger = Logger.getLogger(Utilidades.class);

    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Metodo que valida si un objeto se encuentra vacio
     *
     * @param obj Objeto a validar
     * @return true-Diferente de nulo / false-Es nulo
     */
    public static Boolean validarNulo(Object obj) {
        if (null != obj && !obj.toString().isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Metodo que valida un correo electronico ingresado
     *
     * @param correo Correo electronico
     * @return true-Correo correcto / false-Correo incorrecto
     */
    public static boolean validarCorreoElectronico(String correo) {
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }

    /**
     * Metodo que valida que un caracter string este conformado unicamente por
     * caracteres
     *
     * @param str String a validar
     * @return true-Palabra correcta / false-Palabra incorrecta
     */
    public static boolean validarCaracterString(String str) {
        boolean respuesta = false;
        Pattern pattern = Pattern.compile("([a-z]|[A-Z]|[ÁÉÍÓÚ]|[áéíóú]|[ñÑ]|\\s)+");
        Matcher matcher = pattern.matcher(str);
        respuesta = matcher.matches();
        return respuesta;
    }

    /**
     * Metodo que valida que un caracter string este conformado unicamente por
     * caracteres
     *
     * @param str String a validar
     * @return true-Palabra correcta / false-Palabra incorrecta
     */
    public static boolean validarCaracteresAlfaNumericos(String str) {
        boolean respuesta = false;
        Pattern pattern = Pattern.compile("([a-z]|[A-Z]|[ÁÉÍÓÚ]|[áéíóú]|[0-9]|[-]|[.]|[#]|\\s)+");
        Matcher matcher = pattern.matcher(str);
        respuesta = matcher.matches();
        return respuesta;
    }

    public static boolean validarDirecciones(String str) {
        boolean respuesta = false;
        Pattern pattern = Pattern.compile("([a-z]|[A-Z]|[0-9]|[-]|[.]|[#]|[o]|\\s)+");
        Matcher matcher = pattern.matcher(str);
        respuesta = matcher.matches();
        return respuesta;
    }

    /**
     * Metodo que valida si un numero es numero y no posee algun caracter
     * diferente
     *
     * @param numero Numero a validar
     * @return true-Es numero / false-No es numero
     */
    public static boolean isNumber(String numero) {
        try {
            logger.error("numero : " + numero);
            boolean respuesta = false;
            Pattern pattern = Pattern.compile("([0-9])+");
            Matcher matcher = pattern.matcher(numero);
            respuesta = matcher.matches();
            return respuesta;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean validarNumeroIdentificacion(String id) {
        try {
            boolean respuesta = false;
            Pattern pattern = Pattern.compile("([0-9])+");
            Matcher matcher = pattern.matcher(id);
            respuesta = matcher.matches();
            return respuesta;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Metodo que valida si un numero es numerlongo y no posee algun caracter
     * diferente
     *
     * @param numero Numero long a validar
     * @return true-Es numero / false-No es numero
     */
    public static boolean isNumberLong(String numero) {
        try {
            long validacion = Long.valueOf(numero).longValue();
            if (validacion >= 0) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Metodo encargado de validar si una fecha ingresada es menor o igual a la
     * fecha actual
     *
     * @param fechaValidar Fecha a validar
     * @return true - fecha correcta / false - fecha incorrecta (fecha mayor)
     */
    public static boolean fechaIngresadaCorrecta(String fechaValidar) {
        try {
            boolean retorno = true;

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaHoy = new Date();
            Date fechaAValidar = sdf.parse(fechaValidar);

            sdf.format(fechaHoy);
            sdf.format(fechaAValidar);

            if (fechaHoy.compareTo(fechaAValidar) > 0) {
                retorno = true;
            } else if (fechaHoy.compareTo(fechaAValidar) < 0) {
                retorno = true;
            } else if (fechaHoy.compareTo(fechaAValidar) == 0) {
                retorno = false;
            }
          
            return retorno;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Metodo encargado de validar si una fecha ingresada es mayor a la fecha
     * actual
     *
     * @param fechaValidar Fecha a validar
     * @return true - fecha correcta / false - fecha incorrecta (fecha mayor)
     */
    public static boolean fechaDiferidaIngresadaCorrecta(String fechaValidar) {
        try {
            boolean retorno = false;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaHoy = new Date();
            Date fechaAValidar = sdf.parse(fechaValidar);

            sdf.format(fechaHoy);
            sdf.format(fechaAValidar);

            if (fechaHoy.compareTo(fechaAValidar) > 0) {
                retorno = false;
            } else if (fechaHoy.compareTo(fechaAValidar) < 0) {
                retorno = false;
            } else if (fechaHoy.compareTo(fechaAValidar) == 0) {
                retorno = true;
            }
          
            return retorno;
        } catch (Exception e) {
            return false;
        }
    }

}
