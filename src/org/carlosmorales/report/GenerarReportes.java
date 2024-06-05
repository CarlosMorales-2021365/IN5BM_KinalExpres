/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carlosmorales.report;

import java.io.InputStream;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.carlosmorales.DB.Conexion;

/**
 *
 * @author Usuario
 */
public class GenerarReportes {
    public static void mostrarReportes(String nombreReporte, String titulo, Map parametros){
        InputStream reporte = GenerarReportes.class.getResourceAsStream(nombreReporte);
        try{
            JasperReport reportesClientes = (JasperReport)JRLoader.loadObject(reporte);
            JasperPrint reporteImpreso = JasperFillManager.fillReport(reportesClientes, parametros, Conexion.getInstance().getConexion());
            JasperViewer visor = new JasperViewer(reporteImpreso, false);
            visor.setTitle(titulo);
            visor.setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}


/*

Interface MAP

    FlashMap es uno de los objetos que implementa un conjunto de Key-value.

    Tiene un constructor sin parametros new HashMap() y su finalidad suele referirse para agrupar

    información de un bojeto.
 
    Tiene cierta similitud con la colección de objetos (ArrayList) pero con la diferencia que estos no tienen orden.
 
Hash hace referencia a una tecnica de organización de archivos, hashing (abierto-cerrado) la cual

se almacena registro en una dirección que es generada por una función, se aplica a la llavedel registro.
 
En Java el HasMap posee un espacio de memoria y cuando se guarda un objeto en dicho espacio, se determina su

dirección aplicando una función a la llave que le indiquemos. Cada objeto se identifica

mediante algún identificador apropiado.

*/