/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.cargue;

import com.sirelab.bo.interfacebo.cargue.AdministrarCargueArchivoEstudianteBOInterface;
import com.sirelab.dao.interfacedao.AsignaturaDAOInterface;
import com.sirelab.dao.interfacedao.AsignaturaPorPlanEstudioDAOInterface;
import com.sirelab.dao.interfacedao.CarreraDAOInterface;
import com.sirelab.dao.interfacedao.EstudianteDAOInterface;
import com.sirelab.dao.interfacedao.PersonaDAOInterface;
import com.sirelab.dao.interfacedao.PlanEstudiosDAOInterface;
import com.sirelab.dao.interfacedao.TipoUsuarioDAOInterface;
import com.sirelab.dao.interfacedao.UsuarioDAOInterface;
import com.sirelab.entidades.Asignatura;
import com.sirelab.entidades.AsignaturaPorPlanEstudio;
import com.sirelab.entidades.Carrera;
import com.sirelab.entidades.Estudiante;
import com.sirelab.entidades.Persona;
import com.sirelab.entidades.PlanEstudios;
import com.sirelab.entidades.TipoUsuario;
import com.sirelab.entidades.Usuario;
import com.sirelab.utilidades.EncriptarContrasenia;
import com.sirelab.utilidades.ReporteCargueAsignatura;
import com.sirelab.utilidades.ReporteCargueAsignaturaPlan;
import com.sirelab.utilidades.ReporteCargueEstudiante;
import com.sirelab.utilidades.ReporteCarguePlan;
import com.sirelab.utilidades.Utilidades;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.log4j.Logger;

/**
 *
 * @author ELECTRONICA
 */
@Stateful
public class AdministrarCargueArchivoEstudianteBO implements AdministrarCargueArchivoEstudianteBOInterface {

    static Logger logger = Logger.getLogger(AdministrarCargueArchivoEstudianteBO.class);

    @EJB
    TipoUsuarioDAOInterface tipoUsuarioDAO;
    @EJB
    CarreraDAOInterface carreraDAO;
    @EJB
    PlanEstudiosDAOInterface planEstudiosDAO;
    @EJB
    EstudianteDAOInterface estudianteDAO;
    @EJB
    UsuarioDAOInterface usuarioDAO;
    @EJB
    PersonaDAOInterface personaDAO;
    @EJB
    AsignaturaDAOInterface asignaturaDAO;
    @EJB
    AsignaturaPorPlanEstudioDAOInterface asignaturaPorPlanEstudioDAO;

    @Override
    public ReporteCargueEstudiante cargarDatosArchivoEstudiante(String path) {
        ReporteCargueEstudiante reporteRegistos = null;
        try {
            TipoUsuario tipoUsuario = tipoUsuarioDAO.buscarTipoUsuarioPorID(new BigInteger("3"));
            List<Estudiante> listaEstudiantes = null;
            List<Persona> listaPersona = null;
            List<Usuario> listaUsuarios = null;
            List<String> listaErrores = null;
            Workbook archivoExcel = Workbook.getWorkbook(new java.io.File(path));
            Sheet hoja = archivoExcel.getSheet(0);
            int numColumnas = hoja.getColumns();
            int numFilas = hoja.getRows();
            logger.error("numFilas : " + (numFilas - 1));
            logger.error("getColumns : " + numColumnas);
            if (numColumnas > 0 && numFilas > 1) {
                String data;
                listaEstudiantes = new ArrayList<Estudiante>();
                listaPersona = new ArrayList<Persona>();
                listaUsuarios = new ArrayList<Usuario>();
                listaErrores = new ArrayList<String>();;
                for (int fila = 1; fila < numFilas; fila++) { // Recorre cada fila de la hoja 
                    Estudiante nuevoEstudiante = new Estudiante();
                    Persona nuevaPersona = new Persona();
                    Usuario nuevoUsuario = new Usuario();
                    String mensajeError = "";
                    logger.error("registro numero : " + fila);
                    for (int columna = 0; columna < numColumnas; columna++) { // Recorre cada columna de la fila 
                        data = hoja.getCell(columna, fila).getContents();
                        switch (columna) {//0-NUMERO_ID_PERSONA
                            case 0: {
                                if (Utilidades.validarNulo(data) && (!data.isEmpty())) {
                                    if (Utilidades.validarCaracteresAlfaNumericos(data)) {
                                        Estudiante registro = estudianteDAO.buscarEstudiantePorDocumento(data);
                                        if (null == registro) {
                                            nuevaPersona.setIdentificacionpersona(data);
                                        } else {
                                            mensajeError = "Registro (" + (fila + 1) + "). Documento ya registrado. / ";
                                        }
                                    } else {
                                        mensajeError = "Registro (" + (fila + 1) + "). Documento incorrecto. / ";
                                    }
                                } else {
                                    mensajeError = "Registro (" + (fila + 1) + "). Documento vacio. / ";
                                }
                                break;
                            }
                            case 1: {//1-NOMBRES_PERSONA
                                if (Utilidades.validarNulo(data) && (!data.isEmpty())) {
                                    if (Utilidades.validarCaracterString(data)) {
                                        nuevaPersona.setNombrespersona(data);
                                    } else {
                                        mensajeError = mensajeError + "Registro (" + (fila + 1) + "). Nombre incorrecto. / ";
                                    }
                                } else {
                                    mensajeError = mensajeError + "Registro (" + (fila + 1) + "). Nombre vacio. / ";
                                }
                                break;
                            }
                            case 2: {//2-APELLIDOS_PERSONA
                                if (Utilidades.validarNulo(data) && (!data.isEmpty())) {
                                    if (Utilidades.validarCaracterString(data)) {
                                        nuevaPersona.setApellidospersona(data);
                                    } else {
                                        mensajeError = mensajeError + "Registro (" + (fila + 1) + "). Apellido incorrecto. / ";
                                    }
                                } else {
                                    mensajeError = mensajeError + "Registro (" + (fila + 1) + "). Apellido vacio. / ";
                                }
                                break;
                            }
                            case 3: {//3-EMAIL_INST
                                if (Utilidades.validarNulo(data) && (!data.isEmpty())) {
                                    if (Utilidades.validarCorreoElectronico(data)) {
                                        Estudiante registro = estudianteDAO.buscarEstudiantePorCorreo(data);
                                        if (null == registro) {
                                            nuevaPersona.setEmailpersona(data);
                                        } else {
                                            mensajeError = mensajeError + "Registro (" + (fila + 1) + "). Correo institucional ya registrado. / ";
                                        }
                                    } else {
                                        mensajeError = mensajeError + "Registro (" + (fila + 1) + "). Correo institucional incorrecto. / ";
                                    }
                                } else {
                                    mensajeError = mensajeError + "Registro (" + (fila + 1) + "). Correo institucional vacio. / ";
                                }
                                break;
                            }
                            case 4: {//4-EMAIL_PERSONAL
                                if (Utilidades.validarNulo(data) && (!data.isEmpty())) {
                                    if (Utilidades.validarCorreoElectronico(data)) {
                                        nuevaPersona.setEmailsecundario(data);
                                    } else {
                                        mensajeError = mensajeError + "Registro (" + (fila + 1) + "). Correo personal incorrecto. / ";
                                    }
                                } else {
                                    nuevaPersona.setEmailsecundario("");
                                }
                                break;
                            }
                            case 5: {//5-TELEFONO_CASA
                                if (Utilidades.validarNulo(data) && (!data.isEmpty())) {
                                    if ((Utilidades.isNumber(data)) == true) {
                                        nuevaPersona.setTelefono1persona(data);
                                    } else {
                                        mensajeError = mensajeError + "Registro (" + (fila + 1) + "). Telefono casa incorrecto. / ";
                                    }
                                } else {
                                    nuevaPersona.setTelefono1persona("");
                                }
                                break;
                            }
                            case 6: {//6-TELEFONO_MOVIL
                                if (Utilidades.validarNulo(data) && (!data.isEmpty())) {
                                    if ((Utilidades.isNumber(data)) == true) {
                                        nuevaPersona.setTelefono2persona(data);
                                    } else {
                                        mensajeError = mensajeError + "Registro (" + (fila + 1) + "). Telefono movil incorrecto. / ";
                                    }
                                } else {
                                    nuevaPersona.setTelefono2persona("");
                                }
                                break;
                            }
                            case 7: {//7-DIR_RESIDENCIA
                                if ((Utilidades.validarNulo(data)) && (!data.isEmpty())) {
                                    if (Utilidades.validarCaracteresAlfaNumericos(data)) {
                                        nuevaPersona.setDireccionpersona(data);
                                    } else {
                                        mensajeError = mensajeError + "Registro (" + (fila + 1) + "). Dirección incorrecta. / ";
                                    }
                                } else {
                                    nuevaPersona.setDireccionpersona("");
                                }
                                break;
                            }
                            case 8: {//COD_PLAN_EST
                                if ((Utilidades.validarNulo(data)) && (!data.isEmpty())) {
                                    PlanEstudios plan = planEstudiosDAO.buscarPlanEstudiosPorCodigo(data);
                                    if (null != plan) {
                                        nuevoEstudiante.setPlanestudio(plan);
                                    } else {
                                        mensajeError = mensajeError + "Registro (" + (fila + 1) + "). Plan estudio no existe. / ";
                                    }
                                } else {
                                    mensajeError = mensajeError + "Registro (" + (fila + 1) + "). Plan estudio vacio. / ";
                                }
                                break;
                            }
                            case 9: {//9-TIPO_EST
                                if ((Utilidades.validarNulo(data)) && (!data.isEmpty())) {
                                    int tipo = Integer.parseInt(data);
                                    if (tipo == 1 || tipo == 2) {
                                        nuevoEstudiante.setTipoestudiante(tipo);
                                    } else {
                                        mensajeError = mensajeError + "Registro (" + (fila + 1) + "). Tipo estudiante incorrecto. / ";
                                    }
                                } else {
                                    mensajeError = mensajeError + "Registro (" + (fila + 1) + "). Tipo estudiante vacio. / ";
                                }
                                break;
                            }
                            case 10: {//10-NUM_SEMESTRE
                                if ((Utilidades.validarNulo(data)) && (!data.isEmpty())) {
                                    if (Utilidades.isNumber(data)) {
                                        if (nuevoEstudiante.getTipoestudiante() == 1) {
                                            nuevoEstudiante.setSemestreestudiante(Integer.parseInt(data));
                                        } else {
                                            nuevoEstudiante.setSemestreestudiante(Integer.parseInt("99"));
                                        }
                                    } else {
                                        mensajeError = mensajeError + "Registro (" + (fila + 1) + "). Semestre incorrecto. / ";
                                    }
                                } else {
                                    mensajeError = mensajeError + "Registro (" + (fila + 1) + "). Semestre vacio. / ";
                                }
                                break;
                            }
                        }
                    }
                    nuevoUsuario.setEstado(true);
                    nuevoUsuario.setNumeroconexiones(0);
                    nuevoUsuario.setTipousuario(tipoUsuario);
                    logger.error("mensajeError : " + mensajeError);
                    if (null != mensajeError && (!mensajeError.isEmpty())) {
                        nuevoUsuario.setNombreusuario("error");
                        nuevoUsuario.setPasswordusuario("error");
                    } else {
                        nuevoUsuario.setNombreusuario(nuevaPersona.getIdentificacionpersona());
                        EncriptarContrasenia obj = new EncriptarContrasenia();
                        nuevoUsuario.setPasswordusuario(obj.encriptarContrasenia(nuevaPersona.getIdentificacionpersona()));
                    }
                    listaUsuarios.add(nuevoUsuario);
                    listaPersona.add(nuevaPersona);
                    listaEstudiantes.add(nuevoEstudiante);
                    if (null != mensajeError && (!mensajeError.isEmpty())) {
                        listaErrores.add(mensajeError);
                    }
                }
                reporteRegistos = new ReporteCargueEstudiante();
                reporteRegistos.setArchivoVacio(false);
                reporteRegistos.setCantidadRegistros(numFilas - 1);
                reporteRegistos.setListaErrores(listaErrores);
                reporteRegistos.setListaEstudiantes(listaEstudiantes);
                reporteRegistos.setListaPersonas(listaPersona);
                reporteRegistos.setListaUsuarios(listaUsuarios);
            } else {
                reporteRegistos = new ReporteCargueEstudiante();
                reporteRegistos.setArchivoVacio(true);
                reporteRegistos.setCantidadRegistros(0);
            }
        } catch (IOException | BiffException | IndexOutOfBoundsException ioe) {
            logger.error("Error AdministrarCargueArchivoEstudianteBO cargarDatosArchivoEstudiante: " + ioe.toString());
        }
        return reporteRegistos;
    }

    @Override
    public void almacenarNuevoEstudianteEnSistema(ReporteCargueEstudiante reporte) {
        try {
            for (int i = 0; i < reporte.getCantidadRegistros(); i++) {
                usuarioDAO.crearUsuario(reporte.getListaUsuarios().get(i));
                Usuario usuarioRegistrado = usuarioDAO.obtenerUltimoUsuarioRegistrado();
                reporte.getListaPersonas().get(i).setUsuario(usuarioRegistrado);
                personaDAO.crearPersona(reporte.getListaPersonas().get(i));
                Persona personaRegistrada = personaDAO.obtenerUltimaPersonaRegistrada();
                reporte.getListaEstudiantes().get(i).setPersona(personaRegistrada);
                estudianteDAO.crearEstudiante(reporte.getListaEstudiantes().get(i));
            }
        } catch (Exception e) {
            logger.error("Error AdministrarCargueArchivosBO almacenarNuevoEstudianteEnSistema : " + e.toString(), e);
        }
    }

    @Override
    public void almacenarNuevoAsignaturaEnSistema(ReporteCargueAsignatura reporte) {
        try {
            for (int i = 0; i < reporte.getCantidadRegistros(); i++) {
                asignaturaDAO.crearAsignatura(reporte.getListaAsignaturas().get(i));
            }
        } catch (Exception e) {
            logger.error("Error AdministrarCargueArchivosBO almacenarNuevoAsignaturaEnSistema : " + e.toString(), e);
        }
    }

    @Override
    public void almacenarNuevoPlanEnSistema(ReporteCarguePlan reporte) {
        try {
            for (int i = 0; i < reporte.getCantidadRegistros(); i++) {
                planEstudiosDAO.crearPlanEstudios(reporte.getListaPlanEstudios().get(i));
            }
        } catch (Exception e) {
            logger.error("Error AdministrarCargueArchivosBO almacenarNuevoAsignaturaEnSistema : " + e.toString(), e);
        }
    }

    @Override
    public void almacenarNuevoAsignaturaPlanEnSistema(ReporteCargueAsignaturaPlan reporte) {
        try {
            for (int i = 0; i < reporte.getCantidadRegistros(); i++) {
                asignaturaPorPlanEstudioDAO.crearAsignaturaPorPlanEstudio(reporte.getListaAsignaturaisgnaPlan().get(i));
            }
        } catch (Exception e) {
            logger.error("Error AdministrarCargueArchivosBO almacenarNuevoAsignaturaEnSistema : " + e.toString(), e);
        }
    }

    @Override
    public ReporteCargueAsignatura cargarDatosArchivoAisgnatura(String path) {
        ReporteCargueAsignatura reporteRegistos = null;
        try {
            List<Asignatura> listaAsignatura = null;
            List<String> listaErrores = null;
            Workbook archivoExcel = Workbook.getWorkbook(new java.io.File(path));
            Sheet hoja = archivoExcel.getSheet(0);
            int numColumnas = hoja.getColumns();
            int numFilas = hoja.getRows();
            logger.error("numFilas : " + (numFilas - 1));
            logger.error("getColumns : " + numColumnas);
            if (numColumnas > 0 && numFilas > 1) {
                String data;
                listaAsignatura = new ArrayList<Asignatura>();
                listaErrores = new ArrayList<String>();;
                for (int fila = 1; fila < numFilas; fila++) { // Recorre cada fila de la hoja 
                    Asignatura nuevaAsignatura = new Asignatura();
                    String mensajeError = "";
                    logger.error("registro numero : " + fila);
                    for (int columna = 0; columna < numColumnas; columna++) { // Recorre cada columna de la fila 
                        data = hoja.getCell(columna, fila).getContents();
                        switch (columna) {//0-CODIGO_ASIGNATURA
                            case 0: {
                                if (Utilidades.validarNulo(data) && (!data.isEmpty())) {
                                    if (Utilidades.validarCaracteresAlfaNumericos(data)) {
                                        Asignatura registro = asignaturaDAO.buscarAsignaturaPorCodigo(data);
                                        if (null == registro) {
                                            nuevaAsignatura.setCodigoasignatura(data);
                                        } else {
                                            mensajeError = "Registro (" + (fila + 1) + "). Codigo ya registrado. / ";
                                        }
                                    } else {
                                        mensajeError = "Registro (" + (fila + 1) + "). Codigo incorrecto. / ";
                                    }
                                } else {
                                    mensajeError = "Registro (" + (fila + 1) + "). Codigo vacio. / ";
                                }
                                break;
                            }
                            case 1: {//1-NOMBRE_ASIGNATURA
                                if (Utilidades.validarNulo(data) && (!data.isEmpty())) {
                                    if (Utilidades.validarCaracterString(data)) {
                                        nuevaAsignatura.setNombreasignatura(data);
                                    } else {
                                        mensajeError = mensajeError + "Registro (" + (fila + 1) + "). Nombre incorrecto. / ";
                                    }
                                } else {
                                    mensajeError = mensajeError + "Registro (" + (fila + 1) + "). Nombre vacio. / ";
                                }
                                break;
                            }
                            case 2: {//2-CREDITOS
                                if (Utilidades.validarNulo(data) && (!data.isEmpty())) {
                                    if (Utilidades.isNumber(data)) {
                                        nuevaAsignatura.setNumerocreditos(Integer.valueOf(data));
                                    } else {
                                        mensajeError = mensajeError + "Registro (" + (fila + 1) + "). Número incorrecto. / ";
                                    }
                                } else {
                                    mensajeError = mensajeError + "Registro (" + (fila + 1) + "). Número vacio. / ";
                                }
                                break;
                            }
                        }
                    }
                    nuevaAsignatura.setEstado(true);
                    logger.error("mensajeError : " + mensajeError);
                    listaAsignatura.add(nuevaAsignatura);
                    if (null != mensajeError && (!mensajeError.isEmpty())) {
                        listaErrores.add(mensajeError);
                    }
                }
                reporteRegistos = new ReporteCargueAsignatura();
                reporteRegistos.setArchivoVacio(false);
                reporteRegistos.setCantidadRegistros(numFilas - 1);
                reporteRegistos.setListaErrores(listaErrores);
                reporteRegistos.setListaAsignaturas(listaAsignatura);
            } else {
                reporteRegistos = new ReporteCargueAsignatura();
                reporteRegistos.setArchivoVacio(true);
                reporteRegistos.setCantidadRegistros(0);
            }
        } catch (IOException | BiffException | IndexOutOfBoundsException ioe) {
            logger.error("Error AdministrarCargueArchivoEstudianteBO cargarDatosArchivoAsignatura: " + ioe.toString());
        }
        return reporteRegistos;
    }

    public ReporteCarguePlan cargarDatosArchivoPlan(String path) {
        ReporteCarguePlan reporteRegistos = null;
        try {
            List<PlanEstudios> listaPlanEstudios = null;
            List<String> listaErrores = null;
            Workbook archivoExcel = Workbook.getWorkbook(new java.io.File(path));
            Sheet hoja = archivoExcel.getSheet(0);
            int numColumnas = hoja.getColumns();
            int numFilas = hoja.getRows();
            logger.error("numFilas : " + (numFilas - 1));
            logger.error("getColumns : " + numColumnas);
            if (numColumnas > 0 && numFilas > 1) {
                String data;
                listaPlanEstudios = new ArrayList<PlanEstudios>();
                listaErrores = new ArrayList<String>();;
                for (int fila = 1; fila < numFilas; fila++) { // Recorre cada fila de la hoja 
                    PlanEstudios nuevaPlanEstudios = new PlanEstudios();
                    String mensajeError = "";
                    logger.error("registro numero : " + fila);
                    for (int columna = 0; columna < numColumnas; columna++) { // Recorre cada columna de la fila 
                        data = hoja.getCell(columna, fila).getContents();
                        switch (columna) {//0-CODIGO_PLAN_ESTUDIO
                            case 0: {
                                if (Utilidades.validarNulo(data) && (!data.isEmpty())) {
                                    if (Utilidades.validarCaracteresAlfaNumericos(data)) {
                                        PlanEstudios registro = planEstudiosDAO.buscarPlanEstudiosPorCodigo(data);
                                        if (null == registro) {
                                            nuevaPlanEstudios.setCodigoplanestudio(data);
                                        } else {
                                            mensajeError = "Registro (" + (fila + 1) + "). Codigo ya registrado. / ";
                                        }
                                    } else {
                                        mensajeError = "Registro (" + (fila + 1) + "). Codigo incorrecto. / ";
                                    }
                                } else {
                                    mensajeError = "Registro (" + (fila + 1) + "). Codigo vacio. / ";
                                }
                                break;
                            }
                            case 1: {//1-NOMBRE_PLAN_ESTUDIO
                                if (Utilidades.validarNulo(data) && (!data.isEmpty())) {
                                    if (Utilidades.validarCaracteresAlfaNumericos(data)) {
                                        nuevaPlanEstudios.setNombreplanestudio(data);
                                    } else {
                                        mensajeError = mensajeError + "Registro (" + (fila + 1) + "). Nombre incorrecto. / ";
                                    }
                                } else {
                                    mensajeError = mensajeError + "Registro (" + (fila + 1) + "). Nombre vacio. / ";
                                }
                                break;
                            }
                            case 2: {//2-CREDITOS
                                if (Utilidades.validarNulo(data) && (!data.isEmpty())) {
                                    Carrera carrera = carreraDAO.buscarCarreraPorCodigo(data);
                                    if (Utilidades.validarNulo(carrera)) {
                                        nuevaPlanEstudios.setCarrera(carrera);
                                    } else {
                                        mensajeError = mensajeError + "Registro (" + (fila + 1) + "). Carrera no registrada. / ";
                                    }
                                } else {
                                    mensajeError = mensajeError + "Registro (" + (fila + 1) + "). Carrera vacia. / ";
                                }
                                break;
                            }
                        }
                    }
                    nuevaPlanEstudios.setEstado(true);
                    logger.error("mensajeError : " + mensajeError);
                    listaPlanEstudios.add(nuevaPlanEstudios);
                    if (null != mensajeError && (!mensajeError.isEmpty())) {
                        listaErrores.add(mensajeError);
                    }
                }
                reporteRegistos = new ReporteCarguePlan();
                reporteRegistos.setArchivoVacio(false);
                reporteRegistos.setCantidadRegistros(numFilas - 1);
                reporteRegistos.setListaErrores(listaErrores);
                reporteRegistos.setListaPlanEstudios(listaPlanEstudios);
            } else {
                reporteRegistos = new ReporteCarguePlan();
                reporteRegistos.setArchivoVacio(true);
                reporteRegistos.setCantidadRegistros(0);
            }
        } catch (IOException | BiffException | IndexOutOfBoundsException ioe) {
            logger.error("Error AdministrarCargueArchivoEstudianteBO cargarDatosArchivoPlanEstudios: " + ioe.toString());
        }
        return reporteRegistos;
    }

    public ReporteCargueAsignaturaPlan cargarDatosArchivoAsignaturaPlan(String path) {
        ReporteCargueAsignaturaPlan reporteRegistos = null;
        try {
            List<AsignaturaPorPlanEstudio> listaAsignaturaPorPlanEstudio = null;
            List<String> listaErrores = null;
            Workbook archivoExcel = Workbook.getWorkbook(new java.io.File(path));
            Sheet hoja = archivoExcel.getSheet(0);
            int numColumnas = hoja.getColumns();
            int numFilas = hoja.getRows();
            logger.error("numFilas : " + (numFilas - 1));
            logger.error("getColumns : " + numColumnas);
            if (numColumnas > 0 && numFilas > 1) {
                String data;
                listaAsignaturaPorPlanEstudio = new ArrayList<AsignaturaPorPlanEstudio>();
                listaErrores = new ArrayList<String>();
                for (int fila = 1; fila < numFilas; fila++) { // Recorre cada fila de la hoja 
                    AsignaturaPorPlanEstudio nuevaAsignaturaPorPlanEstudio = new AsignaturaPorPlanEstudio();
                    String mensajeError = "";
                    logger.error("registro numero : " + fila);
                    for (int columna = 0; columna < numColumnas; columna++) { // Recorre cada columna de la fila 
                        data = hoja.getCell(columna, fila).getContents();
                        switch (columna) {//0-CODIGO_PLAN_ESTUDIO
                            case 0: {
                                if (Utilidades.validarNulo(data) && (!data.isEmpty())) {
                                    Asignatura asignatura = asignaturaDAO.buscarAsignaturaPorCodigo(data);
                                    if (Utilidades.validarNulo(data)) {
                                        nuevaAsignaturaPorPlanEstudio.setAsignatura(asignatura);
                                    } else {
                                        mensajeError = "Registro (" + (fila + 1) + "). Asignatura no registrada. / ";
                                    }
                                } else {
                                    mensajeError = "Registro (" + (fila + 1) + "). Asignatura vacia. / ";
                                }
                                break;
                            }
                            case 1: {//1-PLAN_ESTUDIO
                                if (Utilidades.validarNulo(data) && (!data.isEmpty())) {
                                    PlanEstudios plan = planEstudiosDAO.buscarPlanEstudiosPorCodigo(data);
                                    if (Utilidades.validarNulo(plan)) {
                                        nuevaAsignaturaPorPlanEstudio.setPlanestudio(plan);
                                    } else {
                                        mensajeError = mensajeError + "Registro (" + (fila + 1) + "). Plan de Estudio no registrado. / ";
                                    }
                                } else {
                                    mensajeError = mensajeError + "Registro (" + (fila + 1) + "). Plan de Estudio vacio. / ";
                                }
                                break;
                            }
                        }
                    }
                    nuevaAsignaturaPorPlanEstudio.setEstado(true);
                    logger.error("mensajeError : " + mensajeError);
                    listaAsignaturaPorPlanEstudio.add(nuevaAsignaturaPorPlanEstudio);
                    if (null != mensajeError && (!mensajeError.isEmpty())) {
                        listaErrores.add(mensajeError);
                    }
                }
                reporteRegistos = new ReporteCargueAsignaturaPlan();
                reporteRegistos.setArchivoVacio(false);
                reporteRegistos.setCantidadRegistros(numFilas - 1);
                reporteRegistos.setListaErrores(listaErrores);
                reporteRegistos.setListaAsignaturaisgnaPlan(listaAsignaturaPorPlanEstudio);
            } else {
                reporteRegistos = new ReporteCargueAsignaturaPlan();
                reporteRegistos.setArchivoVacio(true);
                reporteRegistos.setCantidadRegistros(0);
            }
        } catch (IOException | BiffException | IndexOutOfBoundsException ioe) {
            logger.error("Error AdministrarCargueArchivoEstudianteBO cargarDatosArchivoAsignaturaPorPlanEstudio: " + ioe.toString());
        }
        return reporteRegistos;
    }
}
