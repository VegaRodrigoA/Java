/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql.a.sqlite;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rodrigo
 */
//Esta es una clase que podemos usar para ejecutar en los proyectos que 
//involucren una base de datos SQLite
public class ClassSQL {
    
private String consSQL;
private String msjError;
private String bd2;
private Connection conexion = null;
private PreparedStatement stmt;
private Statement st;

//Ponemos la ruta a nuestra base de datos principal
public String nBaseDatosSC = "Base de datos";

//Ponemos la ruta a nuestra base de datos secundaria
public String nBD2 = "Base de datos secundaria";

//Creamos la clase principal
public ClassSQL(String nSQL ) {//nSQL es la sentencia a ejecutar
    consSQL = nSQL;
    msjError = "No se pudo ejecutar las sentencia SQL ";//Mensaje de error
    
    //Nos conectamos a nuestra base de datos
    try {
        Class.forName("org.sqlite.JDBC");
        conexion=DriverManager.getConnection ("jdbc:sqlite:"+nBaseDatosSC);

    } catch (SQLException ex) {
        Logger.getLogger(ClassSQL.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, ex);
    } catch (ClassNotFoundException ex) {
    Logger.getLogger(ClassSQL.class.getName()).log(Level.SEVERE, null, ex);
    JOptionPane.showMessageDialog(null, ex);
}

}

//Obtenemos los result set de una consulta        
public ResultSet getRS() {
    try {
        stmt = conexion.prepareStatement(consSQL);
        return stmt.executeQuery();
    } catch (SQLException ex) {
        Logger.getLogger(ClassSQL.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, msjError + consSQL);
        return null;
    }
    
}

//Ejecutamos una sentencia de actualización
public int Ejecutar() {
    try {
        stmt = conexion.prepareStatement(consSQL);
        return stmt.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(ClassSQL.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, msjError + consSQL);
        return 0;
    }
    
}

//Obtenemos los resultados de una consulta a 2 bases de datos
public ResultSet State(String bd){
     try {
        st = conexion.createStatement();
        st.execute("ATTACH DATABASE '" + bd + "'  AS d1");
        stmt = conexion.prepareStatement(consSQL);
        return stmt.executeQuery();
        } catch (SQLException ex) {
        Logger.getLogger(ClassSQL.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, msjError + consSQL);
        return null;
    }
    
}
        
//Cerramos la conección a la base de datos   
public Connection close(){
    try {
        conexion.close();
    } catch (SQLException ex) {
        Logger.getLogger(ClassSQL.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(null, ex);
        return null;
    }
    return null;
}

   
}