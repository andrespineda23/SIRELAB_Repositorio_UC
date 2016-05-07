/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.cargue;

import com.sirelab.utilidades.ReporteCargueAsignatura;
import com.sirelab.utilidades.ReporteCargueAsignaturaPlan;
import com.sirelab.utilidades.ReporteCargueEstudiante;
import com.sirelab.utilidades.ReporteCarguePlan;

/**
 *
 * @author ELECTRONICA
 */
public interface AdministrarCargueArchivoEstudianteBOInterface {

    public void almacenarNuevoAsignaturaEnSistema(ReporteCargueAsignatura reporte);

    public void almacenarNuevoPlanEnSistema(ReporteCarguePlan reporte);

    public void almacenarNuevoAsignaturaPlanEnSistema(ReporteCargueAsignaturaPlan reporte);

    public ReporteCargueAsignatura cargarDatosArchivoAisgnatura(String path);

    public ReporteCargueAsignaturaPlan cargarDatosArchivoAsignaturaPlan(String path);

    public ReporteCarguePlan cargarDatosArchivoPlan(String path);

    public void almacenarNuevoEstudianteEnSistema(ReporteCargueEstudiante reporte);

    public ReporteCargueEstudiante cargarDatosArchivoEstudiante(String path);
}
