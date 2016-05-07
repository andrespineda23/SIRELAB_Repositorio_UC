/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller;

import com.sirelab.bo.interfacebo.cargue.AdministrarCargueArchivoEstudianteBOInterface;
import com.sirelab.utilidades.ReporteCargueAsignatura;
import com.sirelab.utilidades.ReporteCargueAsignaturaPlan;
import com.sirelab.utilidades.ReporteCargueEstudiante;
import com.sirelab.utilidades.ReporteCarguePlan;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

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
    private ReporteCargueAsignatura reporteCargueAsignatura;
    private ReporteCargueAsignaturaPlan reporteCargueAsignaturaPlan;
    private ReporteCarguePlan reporteCarguePlan;
    private String paginaAnterior;

    private Logger logger = Logger.getLogger(getClass().getName());

    public ControllerCargueArchivos() {
    }

    @PostConstruct
    public void init() {
        mensajeValidacion = "";
        disabledAceptar = true;
        tipoCargue = 0;
        disableAcciones = true;
        BasicConfigurator.configure();
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
            String rutaArchivoInicial = pathArchivo + filename;
            String extension = "";
            int i = rutaArchivoInicial.lastIndexOf('.');
            if (i > 0) {
                extension = rutaArchivoInicial.substring(i + 1);
            }
            if ("xls".equals(extension)) {
                InputStream is = archivo.getInputStream();
                SimpleDateFormat formatFecha = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy");
                String fecha = formatFecha.format(new Date());
                String rutaArchivoFinal = pathArchivo + " - " + fecha + " - " + filename;
                FileOutputStream os = new FileOutputStream(rutaArchivoFinal);
                int ch = is.read();
                while (ch != -1) {
                    os.write(ch);
                    ch = is.read();
                }
                os.close();
                switch (tipoCargue) {
                    case 1: {
                        retorno = analizarCargueEstudiante(rutaArchivoFinal);
                        break;
                    }
                    case 2: {
                        retorno = analizarCarguePlanEstudio(rutaArchivoFinal);
                        break;
                    }
                    case 3: {
                        retorno = analizarCargueAsignatura(rutaArchivoFinal);
                        break;
                    }
                    case 4: {
                        retorno = analizarCargueHorario(rutaArchivoFinal);
                        break;
                    }
                    case 5: {
                        retorno = analizarCargueAsignaturaPlan(rutaArchivoFinal);
                        break;
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

    private void generarProcesoErrorArchivo() throws IOException {
        SimpleDateFormat formatFecha = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy");
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
        } catch (IOException ex) {
            logger.error("Error en creación archivo txt de errores ControllerCargueArchivos:  " + ex.toString());
            logger.error("Error en creación archivo txt de errores ControllerCargueArchivos: " + ex.toString());
        }
        descargarArchivoErrores(nombreArchivo);
    }

    private void descargarArchivoErrores(String nombreArchivo) throws IOException {
        HttpServletResponse response = null;
        FileInputStream descarga = null;
        ServletOutputStream ouputStream = null;
        try {
            FacesContext ctx = FacesContext.getCurrentInstance();
            response = (HttpServletResponse) ctx.getExternalContext().getResponse();
            descarga = new FileInputStream(pathArchivoErrores + nombreArchivo);
            int longitud = descarga.available();
            byte[] datos = new byte[longitud];
            descarga.read(datos);
            descarga.close();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + nombreArchivo);
            ouputStream = response.getOutputStream();
            ouputStream.write(datos);
            ouputStream.flush();
            ouputStream.close();
        } catch (Exception e) {
            logger.error("Error en descargarArchivoErrores txt ControllerCargueArchivos:  " + e.toString(), e);
            if (ouputStream.isReady()) {
                ouputStream.close();
            }
        }
    }

    public void imprimirArchivo() {
        try {
            switch (tipoCargue) {
                case 1: {
                    administrarCargueArchivoEstudianteBO.almacenarNuevoEstudianteEnSistema(reporteCargueEstudiante);
                    break;
                }
                case 2: {
                    administrarCargueArchivoEstudianteBO.almacenarNuevoPlanEnSistema(reporteCarguePlan);
                    break;
                }
                case 3: {
                    administrarCargueArchivoEstudianteBO.almacenarNuevoAsignaturaEnSistema(reporteCargueAsignatura);
                    break;
                }
                case 4: {
                    //administrarCargueArchivoEstudianteBO.almacenarNuevoEstudianteEnSistema(reporteCargueEstudiante);
                    break;
                }
                case 5: {
                    administrarCargueArchivoEstudianteBO.almacenarNuevoAsignaturaPlanEnSistema(reporteCargueAsignaturaPlan);
                    break;
                }
            }
            mensajeValidacion = "El cargue de información ha sido satisfactorio.";
            disabledAceptar = true;
            tipoCargue = 0;
            archivo = null;
            disableAcciones = true;
        } catch (Exception e) {
            logger.error("Error ControllerCargueArchivos imprimirArchivo: " + e.toString(), e);
            logger.error("Error ControllerCargueArchivos imprimirArchivo: " + e.toString(), e);
        }
    }

    public void recibirPaginaAnterior(String pagina) {
        this.paginaAnterior = pagina;
    }

    public String regresarPaginaAnterior() {
        disabledAceptar = true;
        mensajeValidacion = "";
        tipoCargue = 0;
        archivo = null;
        disableAcciones = true;
        reporteCargueAsignatura = null;
        reporteCargueAsignaturaPlan = null;
        reporteCargueEstudiante = null;
        reporteCarguePlan = null;
        return "inicioadministrador";
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

    public void descargarArchivoTemplate(int formato) throws FileNotFoundException, IOException {
        FacesContext ctx = null;
        FileInputStream fis = null;
        HttpServletResponse response = null;
        ServletOutputStream out = null;
        try {
            File ficheroXLS = null;
            switch (formato) {
                case 1: {
                    ficheroXLS = new File("C:\\SIRELAB\\Archivos Cargue\\Template\\Template_Estudiante.xls");
                    break;
                }
                case 2: {
                    ficheroXLS = new File("C:\\SIRELAB\\Archivos Cargue\\Template\\Template_Plan_Estudio.xls");
                    break;
                }
                case 3: {
                    ficheroXLS = new File("C:\\SIRELAB\\Archivos Cargue\\Template\\Template_Asignatura.xls");
                    break;
                }
                case 4: {
                    ficheroXLS = new File("C:\\SIRELAB\\Archivos Cargue\\Template\\Template_Horario.xls");
                    break;
                }
                case 5: {
                    ficheroXLS = new File("C:\\SIRELAB\\Archivos Cargue\\Template\\Template_Asignatura_PlanEstudio.xls");
                    break;
                }
            }

            ctx = FacesContext.getCurrentInstance();
            fis = new FileInputStream(ficheroXLS);
            byte[] bytes = new byte[1000];
            int read = 0;
            if (!ctx.getResponseComplete()) {
                String fileName = ficheroXLS.getName();
                String contentType = "application/vnd.ms-excel";
                response = (HttpServletResponse) ctx.getExternalContext().getResponse();
                response.setContentType(contentType);
                response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
                out = response.getOutputStream();
                while ((read = fis.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                out.flush();
                out.close();
                ctx.responseComplete();
            }
        } catch (Exception e) {
            logger.error("ERROR: EL ARCHIVO A DESCARGAR NO ESTA PARAMETRIZADO: " + e.toString(), e);
            out.close();
        }
    }

    private boolean analizarCargueEstudiante(String rutaArchivoFinal) throws IOException {
        boolean retorno = false;
        reporteCargueEstudiante = administrarCargueArchivoEstudianteBO.cargarDatosArchivoEstudiante(rutaArchivoFinal);
        if (null != reporteCargueEstudiante) {
            if (reporteCargueEstudiante.isArchivoVacio() == false) {
                int tamanio = 0;
                if (null == reporteCargueEstudiante.getListaErrores()) {
                    tamanio = reporteCargueEstudiante.getListaErrores().size();
                }
                if (tamanio == 0) {
                    mensajeValidacion = "Validación exitosa. El archivo se encuentra listo para ser almacenado.";
                    disabledAceptar = false;
                    retorno = true;
                } else {
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
        return retorno;
    }

    private boolean analizarCargueAsignatura(String rutaArchivoFinal) throws IOException {
        boolean retorno = false;
        reporteCargueAsignatura = administrarCargueArchivoEstudianteBO.cargarDatosArchivoAisgnatura(rutaArchivoFinal);
        if (null != reporteCargueAsignatura) {
            if (reporteCargueAsignatura.isArchivoVacio() == false) {
                int tamanio = 0;
                if (null == reporteCargueAsignatura.getListaErrores()) {
                    tamanio = reporteCargueAsignatura.getListaErrores().size();
                }
                if (tamanio == 0) {
                    mensajeValidacion = "Validación exitosa. El archivo se encuentra listo para ser almacenado.";
                    disabledAceptar = false;
                    retorno = true;
                } else {
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
        return retorno;
    }

    private boolean analizarCarguePlanEstudio(String rutaArchivoFinal) throws IOException {
        boolean retorno = false;
        reporteCarguePlan = administrarCargueArchivoEstudianteBO.cargarDatosArchivoPlan(rutaArchivoFinal);
        if (null != reporteCarguePlan) {
            if (reporteCarguePlan.isArchivoVacio() == false) {
                int tamanio = 0;
                if (null == reporteCarguePlan.getListaErrores()) {
                    tamanio = reporteCarguePlan.getListaErrores().size();
                }
                if (tamanio == 0) {
                    mensajeValidacion = "Validación exitosa. El archivo se encuentra listo para ser almacenado.";
                    disabledAceptar = false;
                    retorno = true;
                } else {
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
        return retorno;
    }

    private boolean analizarCargueHorario(String rutaArchivoFinal) throws IOException {
        boolean retorno = false;
        return retorno;
    }

    private boolean analizarCargueAsignaturaPlan(String rutaArchivoFinal) throws IOException {
        boolean retorno = false;
        reporteCargueAsignaturaPlan = administrarCargueArchivoEstudianteBO.cargarDatosArchivoAsignaturaPlan(rutaArchivoFinal);
        if (null != reporteCargueAsignaturaPlan) {
            if (reporteCargueAsignaturaPlan.isArchivoVacio() == false) {
                int tamanio = 0;
                if (null == reporteCargueAsignaturaPlan.getListaErrores()) {
                    tamanio = reporteCargueAsignaturaPlan.getListaErrores().size();
                }
                if (tamanio == 0) {
                    mensajeValidacion = "Validación exitosa. El archivo se encuentra listo para ser almacenado.";
                    disabledAceptar = false;
                    retorno = true;
                } else {
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
        return retorno;
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
