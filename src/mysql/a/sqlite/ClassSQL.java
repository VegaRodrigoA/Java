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
public class ClassSQL {
    
private String consSQL;
private String msjError;
private String bd2;
private Connection conexion = null;
private PreparedStatement stmt;
private Statement st;

public String nBaseDatosSC = "/home/rodrigo/Escritorio/Pocholo.db";
//public String nBaseDatosSC = "//C://Users//Usuario//Desktop//gestion.db";

public String nBD2 = "P:\\gestionCerri.rv";

    public ClassSQL(String nSQL ) {
        consSQL = nSQL;
        msjError = "No se pudo ejecutar las sentencia SQL ";
        
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