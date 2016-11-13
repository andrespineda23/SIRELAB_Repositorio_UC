/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.controller.reportes;

import com.sirelab.ayuda.ReservasGenerador;
import com.sirelab.bo.interfacebo.reporte.AdministradorGeneradorReportesBOInterface;
import com.sirelab.entidades.EquipoElemento;
import com.sirelab.entidades.PeriodoAcademico;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.ReservaModuloLaboratorio;
import com.sirelab.entidades.ReservaSala;
import com.sirelab.entidades.SalaLaboratorio;
import com.sirelab.utilidades.Utilidades;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
/*Librerías para trabajar con archivos excel*/
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author Administrator
 */
@ManagedBean
@SessionScoped
public class ControllerGeneradorReportes implements Serializable {

    @EJB
    AdministradorGeneradorReportesBOInterface administradorGeneradorReportesBO;

    private String nombreReporte;
    private String paginaAnterior;
    private String tipoUsuarioReserva;
    private List<PeriodoAcademico> listaPeriodosAcademicos;
    private PeriodoAcademico periodo;
    private List<SalaLaboratorio> listaSalasLaboratorio;
    private SalaLaboratorio salaLaboratorio;
    private Integer tipoUsuario;
    private List<ReservasGenerador> listaReservasGenerador;
    private String fechaInicio, fechaFin;
    private Integer tipoReserva;

    public ControllerGeneradorReportes() {
    }

    @PostConstruct
    public void init() {
        tipoReserva = 1;
        nombreReporte = "";
        listaReservasGenerador = null;
        tipoUsuarioReserva = "";
        listaPeriodosAcademicos = null;
        periodo = null;
        fechaFin = null;
        fechaInicio = null;
        listaSalasLaboratorio = null;
        tipoUsuario = null;
        salaLaboratorio = null;
    }

    public void recibirPaginaAnterior(String pagina) {
        paginaAnterior = pagina;
    }

    public String paginaAnterior() {
        nombreReporte = "";
        tipoUsuarioReserva = "";
        listaPeriodosAcademicos = null;
        fechaFin = null;
        fechaInicio = null;
        periodo = null;
        listaSalasLaboratorio = null;
        tipoReserva = 1;
        tipoUsuario = null;
        salaLaboratorio = null;
        return paginaAnterior;
    }

    public void reporteUsuariosSistema() throws Exception {
        String rutaArchivo = "";
        if (validarNombreReporte()) {
            rutaArchivo = System.getProperty("user.home") + "/" + nombreReporte + ".xls";
        } else {
            rutaArchivo = System.getProperty("user.home") + "/" + "USUARIOS_REGISTRADOS_SIRELAB" + ".xls";
        }
        File archivoXLS = new File(rutaArchivo);
        if (archivoXLS.exists()) {
            archivoXLS.delete();
        }
        archivoXLS.createNewFile();
        Workbook libro = new HSSFWorkbook();
        FileOutputStream archivo = new FileOutputStream(archivoXLS);
        Sheet hoja = libro.createSheet("USUARIOS REGISTRADOS");
        List<Persona> equipos = administradorGeneradorReportesBO.obtenerPersonasDelSistema();
        int tamtotal = equipos.size();
        for (int f = 0; f < tamtotal; f++) {
            Row fila = hoja.createRow(f);
            Persona equipo = equipos.get(f);
            for (int c = 0; c < 10; c++) {
                Cell celda = fila.createCell(c);
                if (f == 0) {
                    if (c == 0) {
                        celda.setCellValue("NOMBRES_USUARIO");
                    } else if (c == 1) {
                        celda.setCellValue("APELLIDOS_USUARIO");
                    } else if (c == 2) {
                        celda.setCellValue("IDENTIFICACION");
                    } else if (c == 3) {
                        celda.setCellValue("CORREO");
                    } else if (c == 4) {
                        celda.setCellValue("TELEFONO_1");
                    } else if (c == 5) {
                        celda.setCellValue("TELEFONO_2");
                    } else if (c == 6) {
                        celda.setCellValue("DIRECCION");
                    } else if (c == 7) {
                        celda.setCellValue("USUARIO");
                    } else if (c == 8) {
                        celda.setCellValue("TIPO_USUARIO");
                    } else if (c == 9) {
                        celda.setCellValue("ESTADO");
                    }
                } else {
                    if (c == 0) {
                        celda.setCellValue(equipo.getNombrespersona());
                    } else if (c == 1) {
                        celda.setCellValue(equipo.getApellidospersona());
                    } else if (c == 2) {
                        celda.setCellValue(equipo.getIdentificacionpersona());
                    } else if (c == 3) {
                        celda.setCellValue(equipo.getEmailpersona());
                    } else if (c == 4) {
                        celda.setCellValue(equipo.getTelefono1persona());
                    } else if (c == 5) {
                        celda.setCellValue(equipo.getTelefono2persona());
                    } else if (c == 6) {
                        celda.setCellValue(equipo.getDireccionpersona());
                    } else if (c == 7) {
                        celda.setCellValue(equipo.getUsuario().getNombreusuario());
                    } else if (c == 8) {
                        celda.setCellValue(equipo.getUsuario().getTipousuario().getNombretipousuario());
                    } else if (c == 9) {
                        celda.setCellValue(equipo.getUsuario().getStrEstado());
                    }
                }
            }
        }
        libro.write(archivo);
        archivo.close();
        descargarArchivo(rutaArchivo);
    }

    public boolean validarNombreReporte() {
        boolean retorno = true;
        if (Utilidades.validarNulo(nombreReporte) && !nombreReporte.isEmpty() && nombreReporte.trim().length() > 0) {
            if (Utilidades.validarCaracteresAlfaNumericos(nombreReporte)) {
                retorno = true;
            } else {
                retorno = false;
            }
        } else {
            retorno = false;
        }
        return retorno;
    }

    public void reporteSalasLaboratorio() throws Exception {
        String rutaArchivo = "";
        if (validarNombreReporte()) {
            rutaArchivo = System.getProperty("user.home") + "/" + nombreReporte + ".xls";
        } else {
            rutaArchivo = System.getProperty("user.home") + "/" + "SALAS_LABORATORIO" + ".xls";
        }
        File archivoXLS = new File(rutaArchivo);
        if (archivoXLS.exists()) {
            archivoXLS.delete();
        }
        archivoXLS.createNewFile();
        Workbook libro = new HSSFWorkbook();
        FileOutputStream archivo = new FileOutputStream(archivoXLS);
        Sheet hoja = libro.createSheet("SALAS LABORATORIO");
        List<SalaLaboratorio> salas = administradorGeneradorReportesBO.obtenerSalasLaboratorio();
        int tamtotal = salas.size();
        for (int f = 0; f < tamtotal; f++) {
            Row fila = hoja.createRow(f);
            SalaLaboratorio sala = salas.get(f);
            for (int c = 0; c < 8; c++) {
                Cell celda = fila.createCell(c);
                if (f == 0) {
                    if (c == 0) {
                        celda.setCellValue("DEPARTAMENTO");
                    } else if (c == 1) {
                        celda.setCellValue("LABORATORIO");
                    } else if (c == 2) {
                        celda.setCellValue("NOMBRE");
                    } else if (c == 3) {
                        celda.setCellValue("CODIGO");
                    } else if (c == 4) {
                        celda.setCellValue("ESTADO");
                    } else if (c == 5) {
                        celda.setCellValue("SEDE");
                    } else if (c == 6) {
                        celda.setCellValue("EDIFICIO");
                    } else if (c == 7) {
                        celda.setCellValue("UBICACION");
                    }
                } else {
                    if (c == 0) {
                        celda.setCellValue(sala.getLaboratorio().getDepartamento().getNombredepartamento());
                    } else if (c == 1) {
                        celda.setCellValue(sala.getLaboratorio().getNombrelaboratorio());
                    } else if (c == 2) {
                        celda.setCellValue(sala.getNombresala());
                    } else if (c == 3) {
                        celda.setCellValue(sala.getCodigosala());
                    } else if (c == 4) {
                        celda.setCellValue(sala.getStrEstado());
                    } else if (c == 5) {
                        celda.setCellValue(sala.getEdificio().getSede().getNombresede());
                    } else if (c == 6) {
                        celda.setCellValue(sala.getEdificio().getDescripcionedificio());
                    } else if (c == 7) {
                        celda.setCellValue(sala.getPisoubicacion());
                    }
                }
            }
        }
        libro.write(archivo);
        archivo.close();
        descargarArchivo(rutaArchivo);
    }

    public void reporteReservasPorPeriodo() throws Exception {
        listaReservasGenerador = null;
        String rutaArchivo = "";
        if (validarNombreReporte()) {
            rutaArchivo = System.getProperty("user.home") + "/" + nombreReporte + ".xls";
        } else {
            rutaArchivo = System.getProperty("user.home") + "/" + "RESERVAS_POR_PERIODO" + ".xls";
        }
        File archivoXLS = new File(rutaArchivo);
        if (archivoXLS.exists()) {
            archivoXLS.delete();
        }
        archivoXLS.createNewFile();
        Workbook libro = new HSSFWorkbook();
        FileOutputStream archivo = new FileOutputStream(archivoXLS);
        Sheet hoja = libro.createSheet("RESERVAS");
        BigInteger idPeriodo = null;
        if (null != periodo) {
            idPeriodo = periodo.getIdperiodoacademico();
        }
        List<ReservaModuloLaboratorio> reservamodulo = administradorGeneradorReportesBO.obtenerReservasModuloLaboratorioPorPeriodoAcademico(idPeriodo);
        convertirReservasModulo(reservamodulo);
        List<ReservaSala> reservasala = administradorGeneradorReportesBO.obtenerReservasSalaPorPeriodoAcademico(idPeriodo);
        convertirReservasSala(reservasala);
        int tamtotal = listaReservasGenerador.size();
        for (int f = 0; f < tamtotal; f++) {
            Row fila = hoja.createRow(f);
            ReservasGenerador reserva = listaReservasGenerador.get(f);
            for (int c = 0; c < 13; c++) {
                Cell celda = fila.createCell(c);
                if (f == 0) {
                    if (c == 0) {
                        celda.setCellValue("ID_RESERVA");
                    } else if (c == 1) {
                        celda.setCellValue("RESERVA");
                    } else if (c == 2) {
                        celda.setCellValue("FECHA_RESERVA");
                    } else if (c == 3) {
                        celda.setCellValue("HORA_RESERVA");
                    } else if (c == 4) {
                        celda.setCellValue("HORA_REAL");
                    } else if (c == 5) {
                        celda.setCellValue("TIPO_RESERVA");
                    } else if (c == 6) {
                        celda.setCellValue("SERVICIO");
                    } else if (c == 7) {
                        celda.setCellValue("LABORATORIO");
                    } else if (c == 8) {
                        celda.setCellValue("SALA_LABORATORIO");
                    } else if (c == 9) {
                        celda.setCellValue("ESTADO");
                    } else if (c == 10) {
                        celda.setCellValue("TIPO_USUARIO");
                    } else if (c == 11) {
                        celda.setCellValue("USUARIO");
                    } else if (c == 12) {
                        celda.setCellValue("ID_USUARIO");
                    }
                } else {
                    if (c == 0) {
                        celda.setCellValue(reserva.getID_RESERVA());
                    } else if (c == 1) {
                        celda.setCellValue(reserva.getRESERVA());
                    } else if (c == 2) {
                        celda.setCellValue(reserva.getFECHA_RESERVA());
                    } else if (c == 3) {
                        celda.setCellValue(reserva.getHORA_RESERVA());
                    } else if (c == 4) {
                        celda.setCellValue(reserva.getHORA_REAL());
                    } else if (c == 5) {
                        celda.setCellValue(reserva.getTIPO_RESERVA());
                    } else if (c == 6) {
                        celda.setCellValue(reserva.getSERVICIO());
                    } else if (c == 7) {
                        celda.setCellValue(reserva.getLABORATORIO());
                    } else if (c == 8) {
                        celda.setCellValue(reserva.getSALA_LABORATORIO());
                    } else if (c == 9) {
                        celda.setCellValue(reserva.getESTADO());
                    } else if (c == 10) {
                        celda.setCellValue(reserva.getTIPO_USUARIO());
                    } else if (c == 11) {
                        celda.setCellValue(reserva.getUSUARIO());
                    } else if (c == 12) {
                        celda.setCellValue(reserva.getID_USUARIO());
                    }
                }
            }
        }
        libro.write(archivo);
        archivo.close();
        descargarArchivo(rutaArchivo);
    }

    public void reporteReservasPorTipoUsuario() throws Exception {
        listaReservasGenerador = null;
        String rutaArchivo = "";
        if (validarNombreReporte()) {
            rutaArchivo = System.getProperty("user.home") + "/" + nombreReporte + ".xls";
        } else {
            rutaArchivo = System.getProperty("user.home") + "/" + "RESERVAS_POR_TIPO_USUARIO" + ".xls";
        }
        File archivoXLS = new File(rutaArchivo);
        if (archivoXLS.exists()) {
            archivoXLS.delete();
        }
        archivoXLS.createNewFile();
        Workbook libro = new HSSFWorkbook();
        FileOutputStream archivo = new FileOutputStream(archivoXLS);
        Sheet hoja = libro.createSheet("RESERVAS");
        List<ReservaModuloLaboratorio> reservamodulo = administradorGeneradorReportesBO.obtenerReservasModuloLaboratorioPorTipoUsuario(tipoUsuario);
        convertirReservasModulo(reservamodulo);
        List<ReservaSala> reservasala = administradorGeneradorReportesBO.obtenerReservasSalaPorTipoUsuario(tipoUsuario);
        convertirReservasSala(reservasala);
        int tamtotal = listaReservasGenerador.size();
        for (int f = 0; f < tamtotal; f++) {
            Row fila = hoja.createRow(f);
            ReservasGenerador reserva = listaReservasGenerador.get(f);
            for (int c = 0; c < 13; c++) {
                Cell celda = fila.createCell(c);
                if (f == 0) {
                    if (c == 0) {
                        celda.setCellValue("ID_RESERVA");
                    } else if (c == 1) {
                        celda.setCellValue("RESERVA");
                    } else if (c == 2) {
                        celda.setCellValue("FECHA_RESERVA");
                    } else if (c == 3) {
                        celda.setCellValue("HORA_RESERVA");
                    } else if (c == 4) {
                        celda.setCellValue("HORA_REAL");
                    } else if (c == 5) {
                        celda.setCellValue("TIPO_RESERVA");
                    } else if (c == 6) {
                        celda.setCellValue("SERVICIO");
                    } else if (c == 7) {
                        celda.setCellValue("LABORATORIO");
                    } else if (c == 8) {
                        celda.setCellValue("SALA_LABORATORIO");
                    } else if (c == 9) {
                        celda.setCellValue("ESTADO");
                    } else if (c == 10) {
                        celda.setCellValue("TIPO_USUARIO");
                    } else if (c == 11) {
                        celda.setCellValue("USUARIO");
                    } else if (c == 12) {
                        celda.setCellValue("ID_USUARIO");
                    }
                } else {
                    if (c == 0) {
                        celda.setCellValue(reserva.getID_RESERVA());
                    } else if (c == 1) {
                        celda.setCellValue(reserva.getRESERVA());
                    } else if (c == 2) {
                        celda.setCellValue(reserva.getFECHA_RESERVA());
                    } else if (c == 3) {
                        celda.setCellValue(reserva.getHORA_RESERVA());
                    } else if (c == 4) {
                        celda.setCellValue(reserva.getHORA_REAL());
                    } else if (c == 5) {
                        celda.setCellValue(reserva.getTIPO_RESERVA());
                    } else if (c == 6) {
                        celda.setCellValue(reserva.getSERVICIO());
                    } else if (c == 7) {
                        celda.setCellValue(reserva.getLABORATORIO());
                    } else if (c == 8) {
                        celda.setCellValue(reserva.getSALA_LABORATORIO());
                    } else if (c == 9) {
                        celda.setCellValue(reserva.getESTADO());
                    } else if (c == 10) {
                        celda.setCellValue(reserva.getTIPO_USUARIO());
                    } else if (c == 11) {
                        celda.setCellValue(reserva.getUSUARIO());
                    } else if (c == 12) {
                        celda.setCellValue(reserva.getID_USUARIO());
                    }
                }
            }
        }
        libro.write(archivo);
        archivo.close();
        descargarArchivo(rutaArchivo);
    }

    public void reporteReservasPorSala() throws Exception {
        listaReservasGenerador = null;
        String rutaArchivo = "";
        if (validarNombreReporte()) {
            rutaArchivo = System.getProperty("user.home") + "/" + nombreReporte + ".xls";
        } else {
            rutaArchivo = System.getProperty("user.home") + "/" + "RESERVAS_POR_SALA" + ".xls";
        }
        File archivoXLS = new File(rutaArchivo);
        if (archivoXLS.exists()) {
            archivoXLS.delete();
        }
        archivoXLS.createNewFile();
        Workbook libro = new HSSFWorkbook();
        FileOutputStream archivo = new FileOutputStream(archivoXLS);
        Sheet hoja = libro.createSheet("RESERVAS");
        BigInteger idSala = null;
        if (null != salaLaboratorio) {
            idSala = salaLaboratorio.getIdsalalaboratorio();
        }
        List<ReservaModuloLaboratorio> reservamodulo = administradorGeneradorReportesBO.obtenerReservasModuloLaboratorioPorSala(idSala);
        convertirReservasModulo(reservamodulo);
        List<ReservaSala> reservasala = administradorGeneradorReportesBO.obtenerReservasSalaPorSala(idSala);
        convertirReservasSala(reservasala);
        int tamtotal = listaReservasGenerador.size();
        for (int f = 0; f < tamtotal; f++) {
            Row fila = hoja.createRow(f);
            ReservasGenerador reserva = listaReservasGenerador.get(f);
            for (int c = 0; c < 13; c++) {
                Cell celda = fila.createCell(c);
                if (f == 0) {
                    if (c == 0) {
                        celda.setCellValue("ID_RESERVA");
                    } else if (c == 1) {
                        celda.setCellValue("RESERVA");
                    } else if (c == 2) {
                        celda.setCellValue("FECHA_RESERVA");
                    } else if (c == 3) {
                        celda.setCellValue("HORA_RESERVA");
                    } else if (c == 4) {
                        celda.setCellValue("HORA_REAL");
                    } else if (c == 5) {
                        celda.setCellValue("TIPO_RESERVA");
                    } else if (c == 6) {
                        celda.setCellValue("SERVICIO");
                    } else if (c == 7) {
                        celda.setCellValue("LABORATORIO");
                    } else if (c == 8) {
                        celda.setCellValue("SALA_LABORATORIO");
                    } else if (c == 9) {
                        celda.setCellValue("ESTADO");
                    } else if (c == 10) {
                        celda.setCellValue("TIPO_USUARIO");
                    } else if (c == 11) {
                        celda.setCellValue("USUARIO");
                    } else if (c == 12) {
                        celda.setCellValue("ID_USUARIO");
                    }
                } else {
                    if (c == 0) {
                        celda.setCellValue(reserva.getID_RESERVA());
                    } else if (c == 1) {
                        celda.setCellValue(reserva.getRESERVA());
                    } else if (c == 2) {
                        celda.setCellValue(reserva.getFECHA_RESERVA());
                    } else if (c == 3) {
                        celda.setCellValue(reserva.getHORA_RESERVA());
                    } else if (c == 4) {
                        celda.setCellValue(reserva.getHORA_REAL());
                    } else if (c == 5) {
                        celda.setCellValue(reserva.getTIPO_RESERVA());
                    } else if (c == 6) {
                        celda.setCellValue(reserva.getSERVICIO());
                    } else if (c == 7) {
                        celda.setCellValue(reserva.getLABORATORIO());
                    } else if (c == 8) {
                        celda.setCellValue(reserva.getSALA_LABORATORIO());
                    } else if (c == 9) {
                        celda.setCellValue(reserva.getESTADO());
                    } else if (c == 10) {
                        celda.setCellValue(reserva.getTIPO_USUARIO());
                    } else if (c == 11) {
                        celda.setCellValue(reserva.getUSUARIO());
                    } else if (c == 12) {
                        celda.setCellValue(reserva.getID_USUARIO());
                    }
                }
            }
        }
        libro.write(archivo);
        archivo.close();
        descargarArchivo(rutaArchivo);
    }

    public void reporteReservasPorParametros() throws Exception {
        listaReservasGenerador = null;
        String rutaArchivo = "";
        if (validarNombreReporte()) {
            rutaArchivo = System.getProperty("user.home") + "/" + nombreReporte + ".xls";
        } else {
            rutaArchivo = System.getProperty("user.home") + "/" + "RESERVAS_POR_PARAMETROS" + ".xls";
        }
        File archivoXLS = new File(rutaArchivo);
        if (archivoXLS.exists()) {
            archivoXLS.delete();
        }
        archivoXLS.createNewFile();
        Workbook libro = new HSSFWorkbook();
        FileOutputStream archivo = new FileOutputStream(archivoXLS);
        Sheet hoja = libro.createSheet("RESERVAS");
        if (null == tipoReserva) {
            List<ReservaModuloLaboratorio> reservamodulo = administradorGeneradorReportesBO.obtenerReservasModuloLaboratorioPorFechas(fechaInicio, fechaFin);
            convertirReservasModulo(reservamodulo);
            List<ReservaSala> reservasala = administradorGeneradorReportesBO.obtenerReservasSalaPorFechas(fechaInicio, fechaFin);
            convertirReservasSala(reservasala);
        } else {
            if (tipoReserva == 1) {
                List<ReservaModuloLaboratorio> reservamodulo = administradorGeneradorReportesBO.obtenerReservasModuloLaboratorioPorFechas(fechaInicio, fechaFin);
                convertirReservasModulo(reservamodulo);
            } else {
                List<ReservaSala> reservasala = administradorGeneradorReportesBO.obtenerReservasSalaPorFechas(fechaInicio, fechaFin);
                convertirReservasSala(reservasala);
            }
        }
        int tamtotal = listaReservasGenerador.size();
        for (int f = 0; f < tamtotal; f++) {
            Row fila = hoja.createRow(f);
            ReservasGenerador reserva = listaReservasGenerador.get(f);
            for (int c = 0; c < 13; c++) {
                Cell celda = fila.createCell(c);
                if (f == 0) {
                    if (c == 0) {
                        celda.setCellValue("ID_RESERVA");
                    } else if (c == 1) {
                        celda.setCellValue("RESERVA");
                    } else if (c == 2) {
                        celda.setCellValue("FECHA_RESERVA");
                    } else if (c == 3) {
                        celda.setCellValue("HORA_RESERVA");
                    } else if (c == 4) {
                        celda.setCellValue("HORA_REAL");
                    } else if (c == 5) {
                        celda.setCellValue("TIPO_RESERVA");
                    } else if (c == 6) {
                        celda.setCellValue("SERVICIO");
                    } else if (c == 7) {
                        celda.setCellValue("LABORATORIO");
                    } else if (c == 8) {
                        celda.setCellValue("SALA_LABORATORIO");
                    } else if (c == 9) {
                        celda.setCellValue("ESTADO");
                    } else if (c == 10) {
                        celda.setCellValue("TIPO_USUARIO");
                    } else if (c == 11) {
                        celda.setCellValue("USUARIO");
                    } else if (c == 12) {
                        celda.setCellValue("ID_USUARIO");
                    }
                } else {
                    if (c == 0) {
                        celda.setCellValue(reserva.getID_RESERVA());
                    } else if (c == 1) {
                        celda.setCellValue(reserva.getRESERVA());
                    } else if (c == 2) {
                        celda.setCellValue(reserva.getFECHA_RESERVA());
                    } else if (c == 3) {
                        celda.setCellValue(reserva.getHORA_RESERVA());
                    } else if (c == 4) {
                        celda.setCellValue(reserva.getHORA_REAL());
                    } else if (c == 5) {
                        celda.setCellValue(reserva.getTIPO_RESERVA());
                    } else if (c == 6) {
                        celda.setCellValue(reserva.getSERVICIO());
                    } else if (c == 7) {
                        celda.setCellValue(reserva.getLABORATORIO());
                    } else if (c == 8) {
                        celda.setCellValue(reserva.getSALA_LABORATORIO());
                    } else if (c == 9) {
                        celda.setCellValue(reserva.getESTADO());
                    } else if (c == 10) {
                        celda.setCellValue(reserva.getTIPO_USUARIO());
                    } else if (c == 11) {
                        celda.setCellValue(reserva.getUSUARIO());
                    } else if (c == 12) {
                        celda.setCellValue(reserva.getID_USUARIO());
                    }
                }
            }
        }
        libro.write(archivo);
        archivo.close();
        descargarArchivo(rutaArchivo);
    }

    private void descargarArchivo(String rutaArchivo) throws Exception {
        File ficheroXLS = new File(rutaArchivo);
        FacesContext ctx = FacesContext.getCurrentInstance();
        FileInputStream fis = new FileInputStream(ficheroXLS);
        byte[] bytes = new byte[1000];
        int read = 0;

        if (!ctx.getResponseComplete()) {
            String fileName = ficheroXLS.getName();
            String contentType = "application/vnd.ms-excel";
            //String contentType = "application/pdf";
            HttpServletResponse response
                    = (HttpServletResponse) ctx.getExternalContext().getResponse();

            response.setContentType(contentType);

            response.setHeader("Content-Disposition",
                    "attachment;filename=\"" + fileName + "\"");

            ServletOutputStream out = response.getOutputStream();

            while ((read = fis.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            out.flush();
            out.close();
            ctx.responseComplete();
        }
    }

    private void convertirReservasSala(List<ReservaSala> reservasala) {
        if (null == listaReservasGenerador) {
            listaReservasGenerador = new ArrayList<ReservasGenerador>();
        }
        if (null != reservasala) {
            for (ReservaSala reserva : reservasala) {
                ReservasGenerador reservasGenerador = new ReservasGenerador();
                reservasGenerador.setID_RESERVA(reserva.getReserva().getNumeroreserva());
                reservasGenerador.setRESERVA("RESERVA SALA LABORATORIO");
                String pattern = "dd/MM/yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String date = simpleDateFormat.format(reserva.getReserva().getFechareserva());
                reservasGenerador.setFECHA_RESERVA(date);
                reservasGenerador.setHORA_RESERVA(reserva.getReserva().getHorainicio() + ":" + reserva.getReserva().getHorafin());
                if (null != reserva.getReserva().getHorainicioefectiva() && null != reserva.getReserva().getHorafinefectiva()) {
                    reservasGenerador.setHORA_REAL(reserva.getReserva().getHorainicioefectiva() + ":" + reserva.getReserva().getHorafinefectiva());
                } else {
                    String horaInicio = "NN";
                    String horaFin = "NN";
                    if (null == reserva.getReserva().getHorainicioefectiva()) {
                        reservasGenerador.setHORA_REAL(horaInicio + ":" + horaFin);
                    } else {
                        reservasGenerador.setHORA_REAL(reserva.getReserva().getHorainicioefectiva() + ":" + horaFin);
                    }
                }

                reservasGenerador.setTIPO_RESERVA(reserva.getReserva().getTiporeserva().getNombretiporeserva());
                reservasGenerador.setSERVICIO(reserva.getReserva().getServiciosala().getNombreservicio());
                reservasGenerador.setLABORATORIO(reserva.getSalalaboratorio().getLaboratorio().getNombrelaboratorio());
                reservasGenerador.setSALA_LABORATORIO(reserva.getSalalaboratorio().getNombresala());
                reservasGenerador.setESTADO(reserva.getReserva().getEstadoreserva().getNombreestadoreserva());
                reservasGenerador.setUSUARIO(reserva.getReserva().getPersona().getUsuario().getNombreusuario());
                reservasGenerador.setID_USUARIO(reserva.getReserva().getPersona().getIdentificacionpersona());
                reservasGenerador.setTIPO_USUARIO(reserva.getReserva().getPersona().getUsuario().getTipousuario().getNombretipousuario());
                listaReservasGenerador.add(reservasGenerador);
            }
        }
    }

    private void convertirReservasModulo(List<ReservaModuloLaboratorio> reservamodulo) {
        if (null == listaReservasGenerador) {
            listaReservasGenerador = new ArrayList<ReservasGenerador>();
        }
        if (null != reservamodulo) {
            for (ReservaModuloLaboratorio reserva : reservamodulo) {
                ReservasGenerador reservasGenerador = new ReservasGenerador();
                reservasGenerador.setID_RESERVA(reserva.getReserva().getNumeroreserva());
                reservasGenerador.setRESERVA("RESERVA MÓDULO LABORATORIO");
                String pattern = "dd/MM/yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String date = simpleDateFormat.format(reserva.getReserva().getFechareserva());
                reservasGenerador.setFECHA_RESERVA(date);
                reservasGenerador.setHORA_RESERVA(reserva.getReserva().getHorainicio() + ":" + reserva.getReserva().getHorafin());
                if (null != reserva.getReserva().getHorainicioefectiva() && null != reserva.getReserva().getHorafinefectiva()) {
                    reservasGenerador.setHORA_REAL(reserva.getReserva().getHorainicioefectiva() + ":" + reserva.getReserva().getHorafinefectiva());
                } else {
                    String horaInicio = "NN";
                    String horaFin = "NN";
                    if (null == reserva.getReserva().getHorainicioefectiva()) {
                        reservasGenerador.setHORA_REAL(horaInicio + ":" + horaFin);
                    } else {
                        reservasGenerador.setHORA_REAL(reserva.getReserva().getHorainicioefectiva() + ":" + horaFin);
                    }
                }
                reservasGenerador.setTIPO_RESERVA(reserva.getReserva().getTiporeserva().getNombretiporeserva());
                reservasGenerador.setSERVICIO(reserva.getReserva().getServiciosala().getNombreservicio());
                reservasGenerador.setLABORATORIO(reserva.getModulolaboratorio().getSalalaboratorio().getLaboratorio().getNombrelaboratorio());
                reservasGenerador.setSALA_LABORATORIO(reserva.getModulolaboratorio().getSalalaboratorio().getNombresala());
                reservasGenerador.setESTADO(reserva.getReserva().getEstadoreserva().getNombreestadoreserva());
                reservasGenerador.setUSUARIO(reserva.getReserva().getPersona().getUsuario().getNombreusuario());
                reservasGenerador.setID_USUARIO(reserva.getReserva().getPersona().getIdentificacionpersona());
                reservasGenerador.setTIPO_USUARIO(reserva.getReserva().getPersona().getUsuario().getTipousuario().getNombretipousuario());
                listaReservasGenerador.add(reservasGenerador);
            }
        }
    }

    public void reporteEquiposdeTrabajo() throws Exception {
        String rutaArchivo = "";
        if (validarNombreReporte()) {
            rutaArchivo = System.getProperty("user.home") + "/" + nombreReporte + ".xls";
        } else {
            rutaArchivo = System.getProperty("user.home") + "/" + "EQUIPOS_DE_TRABAJO" + ".xls";
        }
        File archivoXLS = new File(rutaArchivo);
        if (archivoXLS.exists()) {
            archivoXLS.delete();
        }
        archivoXLS.createNewFile();
        Workbook libro = new HSSFWorkbook();
        FileOutputStream archivo = new FileOutputStream(archivoXLS);
        Sheet hoja = libro.createSheet("EQUIPOS DE TRABAJO");
        List<EquipoElemento> equipos = administradorGeneradorReportesBO.consultarEquiposdeTrabajoRegistrados();
        int tamtotal = equipos.size();
        for (int f = 0; f < tamtotal; f++) {
            Row fila = hoja.createRow(f);
            EquipoElemento equipo = equipos.get(f);
            for (int c = 0; c < 10; c++) {
                Cell celda = fila.createCell(c);
                if (f == 0) {
                    if (c == 0) {
                        celda.setCellValue("NOMBRE_EQUIPO");
                    } else if (c == 1) {
                        celda.setCellValue("CODIGO_EQUIPO");
                    } else if (c == 2) {
                        celda.setCellValue("MARCA");
                    } else if (c == 3) {
                        celda.setCellValue("MODELO");
                    } else if (c == 4) {
                        celda.setCellValue("SERIE");
                    } else if (c == 5) {
                        celda.setCellValue("CANTIDAD");
                    } else if (c == 6) {
                        celda.setCellValue("TIPO_ACTIVO");
                    } else if (c == 7) {
                        celda.setCellValue("PROVEEDOR");
                    } else if (c == 8) {
                        celda.setCellValue("NIT_PROVEEDOR");
                    } else if (c == 9) {
                        celda.setCellValue("LABORATORIO");
                    } else if (c == 10) {
                        celda.setCellValue("SALA_LABORATORIO");
                    } else if (c == 11) {
                        celda.setCellValue("MODULO_LABORATORIO");
                    } else if (c == 12) {
                        celda.setCellValue("ESTADO_EQUIPO");
                    }
                } else {
                    if (c == 0) {
                        celda.setCellValue(equipo.getNombreequipo());
                    } else if (c == 1) {
                        celda.setCellValue(equipo.getInventarioequipo());
                    } else if (c == 2) {
                        celda.setCellValue(equipo.getMarcaequipo());
                    } else if (c == 3) {
                        celda.setCellValue(equipo.getModeloequipo());
                    } else if (c == 4) {
                        celda.setCellValue(equipo.getSeriequipo());
                    } else if (c == 5) {
                        celda.setCellValue(equipo.getCantidadequipo());
                    } else if (c == 6) {
                        celda.setCellValue(equipo.getTipoactivo().getNombretipoactivo());
                    } else if (c == 7) {
                        celda.setCellValue(equipo.getProveedor().getNombreproveedor());
                    } else if (c == 8) {
                        celda.setCellValue(equipo.getProveedor().getNitproveedor());
                    } else if (c == 9) {
                        celda.setCellValue(equipo.getModulolaboratorio().getSalalaboratorio().getLaboratorio().getNombrelaboratorio());
                    } else if (c == 10) {
                        celda.setCellValue(equipo.getModulolaboratorio().getSalalaboratorio().getNombresala());
                    } else if (c == 11) {
                        celda.setCellValue(equipo.getModulolaboratorio().getDetallemodulo());
                    } else if (c == 12) {
                        celda.setCellValue(equipo.getEstadoequipo().getNombreestadoequipo());
                    }
                }
            }
        }
        libro.write(archivo);
        archivo.close();
        descargarArchivo(rutaArchivo);
    }

    public void setListaSalasLaboratorio(List<SalaLaboratorio> listaSalasLaboratorio) {
        this.listaSalasLaboratorio = listaSalasLaboratorio;
    }

    public SalaLaboratorio getSalaLaboratorio() {
        return salaLaboratorio;
    }

    public void setSalaLaboratorio(SalaLaboratorio salaLaboratorio) {
        this.salaLaboratorio = salaLaboratorio;
    }

    public Integer getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(Integer tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getNombreReporte() {
        return nombreReporte;
    }

    public void setNombreReporte(String nombreReporte) {
        this.nombreReporte = nombreReporte;
    }

    public String getTipoUsuarioReserva() {
        return tipoUsuarioReserva;
    }

    public void setTipoUsuarioReserva(String tipoUsuarioReserva) {
        this.tipoUsuarioReserva = tipoUsuarioReserva;
    }

    public String getPaginaAnterior() {
        return paginaAnterior;
    }

    public void setPaginaAnterior(String paginaAnterior) {
        this.paginaAnterior = paginaAnterior;
    }

    public List<PeriodoAcademico> getListaPeriodosAcademicos() {
        if (null == listaPeriodosAcademicos) {
            listaPeriodosAcademicos = administradorGeneradorReportesBO.obtenerPeriodosAcademicos();
        }
        return listaPeriodosAcademicos;
    }

    public void setListaPeriodosAcademicos(List<PeriodoAcademico> listaPeriodosAcademicos) {
        this.listaPeriodosAcademicos = listaPeriodosAcademicos;
    }

    public PeriodoAcademico getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoAcademico periodo) {
        this.periodo = periodo;
    }

    public List<SalaLaboratorio> getListaSalasLaboratorio() {
        if (null == listaSalasLaboratorio) {
            listaSalasLaboratorio = administradorGeneradorReportesBO.obtenerSalasLaboratorio();
        }
        return listaSalasLaboratorio;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getTipoReserva() {
        return tipoReserva;
    }

    public void setTipoReserva(Integer tipoReserva) {
        this.tipoReserva = tipoReserva;
    }

}
