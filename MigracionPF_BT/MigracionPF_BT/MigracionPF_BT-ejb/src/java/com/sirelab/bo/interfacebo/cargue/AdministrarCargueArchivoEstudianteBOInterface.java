/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sirelab.bo.interfacebo.cargue;

import com.sirelab.utilidades.ReporteCargueEstudiante;
import java.util.List;

/**
 *
 * @author ELECTRONICA
 */
public interface AdministrarCargueArchivoEstudianteBOInterface {

    public void almacenarNuevoEstudianteEnSistema(ReporteCargueEstudiante reporte);

    public ReporteCargueEstudiante cargarDatosArchivoEstudiante(String path);
}
