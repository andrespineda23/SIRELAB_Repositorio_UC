/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller;

import com.sirelab.bo.interfacebo.cargue.AdministrarCargueArchivoEstudianteBOInterface;
import com.sirelab.utilidades.ReporteCargueEstudiante;
import com.sirelab.utilidades.Utilidades;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author ELECTRONICA
 */
@ManagedBean
@SessionScoped
public class ControllerCargueArchivos implements Serializable {

    @EJB
    AdministrarCargueArchivoEstudianteBOInterface administrarCargueArchivoEstudianteBO;

    private Part archivo;
    private boolean disableAcciones;
    private int tipoCargue;
    private boolean disabledAceptar;
    private String mensajeValidacion;
    private final String pathArchivo = "C:\\SIRELAB\\Archivos Cargue\\";
    private final String pathArchivoErrores = "C:\\SIRELAB\\Archivos Cargue\\Errores\\";
    private ReporteCargueEstudiante reporteCargueEstudiante;

    public ControllerCargueArchivos() {
    }

    @PostConstruct
    public void init() {
        mensajeValidacion = "";
        disabledAceptar = true;
        tipoCargue = 0;
        disableAcciones = true;
    }

    public void actualizarTipoCargue() {
        if (0 == tipoCargue) {
            disableAcciones = true;
        } else {
            disableAcciones = false;
            archivo = null;
        }
    }

    public boolean validarArchivoCargue() throws FileNotFoundException, IOException {
        boolean retorno = false;
        if (Utilidades.validarNulo(archivo)) {
            String filename = getFilename(archivo);
            String rutaArchivo = pathArchivo + filename;
            String extension = "";
            int i = rutaArchivo.lastIndexOf('.');
            if (i > 0) {
                extension = rutaArchivo.substring(i + 1);
            }
            if ("xls".equals(extension)) {
                InputStream is = archivo.getInputStream();
                FileOutputStream os = new FileOutputStream(rutaArchivo);
                int ch = is.read();
                while (ch != -1) {
                    os.write(ch);
                    ch = is.read();
                }
                os.close();
                reporteCargueEstudiante = administrarCargueArchivoEstudianteBO.cargarDatosArchivoEstudiante(rutaArchivo);
                if (null != reporteCargueEstudiante) {
                    if (reporteCargueEstudiante.isArchivoVacio() == false) {
                        int tamanio = 0;
                        if (null == reporteCargueEstudiante.getListaErrores()) {
                            tamanio = reporteCargueEstudiante.getListaErrores().size();
                        }
                        System.out.println("tamanio : " + tamanio);
                        if (tamanio == 0) {
                            mensajeValidacion = "Validación exitosa. El archivo se encuentra listo para ser almacenado.";
                            disabledAceptar = false;
                            retorno = true;
                        } else {
                            System.out.println("No Nulo");
                            mensajeValidacion = "Se presentarón errores en el proceso de validación. Un archivo de salida ha sido generado.";
                            disabledAceptar = true;
                            retorno = false;
                            generarProcesoErrorArchivo();
                        }
                    } else {
                        mensajeValidacion = "Archivo vacio. Operación abortada.";
                        disabledAceptar = true;
                        retorno = false;
                    }
                }
            } else {
                mensajeValidacion = "Formato Incorrecto. Solo se permiten archivos XLS.";
                disabledAceptar = true;
                retorno = false;
            }
        } else {
            mensajeValidacion = "Seleccione un archivo para el proceso.";
            disabledAceptar = true;
            retorno = false;
        }
        return retorno;
    }

    private void generarProcesoErrorArchivo() {
        DateFormat formatFecha = DateFormat.getDateInstance(DateFormat.LONG);
        String fecha = formatFecha.format(new Date());
        String nombreArchivo = "Archivo Errores Cargue Estudiante - " + fecha + ".txt";
        FileWriter fw = null;
        try {
            fw = new FileWriter(pathArchivoErrores + nombreArchivo);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter salArch = new PrintWriter(bw);
            for (int j = 0; j < reporteCargueEstudiante.getListaErrores().size(); j++) {
                salArch.print(reporteCargueEstudiante.getListaErrores().get(j));
                salArch.println();
            }
            salArch.close();
            descargarArchivoErrores(nombreArchivo);
        } catch (IOException ex) {
            System.out.println("Error en creación archivo txt de errores ControllerCargueArchivos: " + ex.toString());
        }
    }

    private void descargarArchivoErrores(String nombreArchivo) {
        try {
            FacesContext ctx = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
            FileInputStream archivo = new FileInputStream(pathArchivoErrores + nombreArchivo);
            int longitud = archivo.available();
            byte[] datos = new byte[longitud];
            archivo.read(datos);
            archivo.close();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + nombreArchivo);
            ServletOutputStream ouputStream = response.getOutputStream();
            ouputStream.write(datos);
            ouputStream.flush();
            ouputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void imprimirArchivo() {
        try {
            administrarCargueArchivoEstudianteBO.almacenarNuevoEstudianteEnSistema(reporteCargueEstudiante);
            mensajeValidacion = "El cargue de información ha sido satisfactorio.";
            disabledAceptar = true;
            tipoCargue = 0;
            archivo = null;
            disableAcciones = true;
        } catch (Exception e) {
            System.out.println("Error ControllerCargueArchivos imprimirArchivo: " + e.toString());
        }
    }

    private static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

    public void descargarArchivoTemplate() throws FileNotFoundException, IOException {
        File ficheroXLS = new File("C:\\SIRELAB\\Archivos Cargue\\Template\\Template Estudiante.xls");
        FacesContext ctx = FacesContext.getCurrentInstance();
        FileInputStream fis = new FileInputStream(ficheroXLS);
        byte[] bytes = new byte[1000];
        int read = 0;
        if (!ctx.getResponseComplete()) {
            String fileName = ficheroXLS.getName();
            String contentType = "application/vnd.ms-excel";
            HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
            response.setContentType(contentType);
            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
            ServletOutputStream out = response.getOutputStream();
            while ((read = fis.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
            System.out.println("\nDescargado\n");
            ctx.responseComplete();
        }
    }

    public Part getArchivo() {
        return archivo;
    }

    public void setArchivo(Part archivo) {
        this.archivo = archivo;
    }

    public boolean isDisableAcciones() {
        return disableAcciones;
    }

    public void setDisableAcciones(boolean disableAcciones) {
        this.disableAcciones = disableAcciones;
    }

    public int getTipoCargue() {
        return tipoCargue;
    }

    public void setTipoCargue(int tipoCargue) {
        this.tipoCargue = tipoCargue;
    }

    public boolean isDisabledAceptar() {
        return disabledAceptar;
    }

    public void setDisabledAceptar(boolean disabledAceptar) {
        this.disabledAceptar = disabledAceptar;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

}
