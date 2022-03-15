/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package mysql.a.sqlite;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author rodrigo
 */
public class MySQLASQLite {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic heretry {
        String[] lista = new String[]{"Compras" , 
            "CondImp", "Diario","limImp","Meses", "planCtas", "Proveedores", "Ventas"};
        //String list = "Clientes";
        lista = new String[]{"Diario"}; 
        for (String list :lista){
        try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conexion;
        conexion=DriverManager.getConnection ("jdbc:mysql://127.0.0.1:3306/Pocholo","sammy","password");
        PreparedStatement stmt;
        stmt = conexion.prepareStatement("Select * from " + list + ";");
        ClassSQL nuevo;
        
        ResultSet rs;
        
        rs = stmt.executeQuery();
        
        DefaultTableModel modelo = new DefaultTableModel();
        //this.jTable1.setModel(modelo);
        
        ResultSetMetaData rsMd = rs.getMetaData();
        String sql = "";
        String sql2 = "";
        String sql3 = "";
        String tipo = "";
        int colCount;
 int cantidadColumnas = rsMd.getColumnCount();
 
 for (int i = 1; i <= cantidadColumnas; i++) {
            
     if (i != 1){
                sql = sql + " , ";
                sql2 = sql2 + " , ";    
                }
    if (rsMd.getColumnTypeName(i)=="INTEGER"){
        tipo = "INTEGER";
    }else if(rsMd.getColumnTypeName(i)=="DOUBLE"){
        tipo = "NUMERIC";                                
    } else{
        tipo = "TEXT";
    }
                                    
    sql = sql + "'" + rsMd.getColumnName(i) + "' " + tipo ;
    sql2 = sql2 + "'" + rsMd.getColumnName(i) + "' ";
    
 }
 
 nuevo = new ClassSQL("Create table '" + list + "' (" + sql + ");");
 nuevo.Ejecutar();
 nuevo.close();
  
 while (rs.next()) {
  colCount = 1;
                    sql3 = "";
                    
                    while (colCount <= cantidadColumnas){
                            if (colCount != 1){
                                sql3 = sql3 + " , ";
                            }
                            sql3 = sql3 + "'"+ rs.getString(colCount) + "'" ;
                            
                            //System.out.println(sql2);
                            colCount = colCount +1;
                        }
                    
                     
                        nuevo = new ClassSQL("insert into '" + list
                        //stmt = conexion.prepareStatement("insert into '" + "Diario" 
                          + "' (" + sql2 + ") values (" + sql3 + ");");
                        nuevo.Ejecutar();
                        nuevo.close();
 }
     conexion.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error de ejecución.");
           //Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Class not found.");
            Logger.getLogger(MySQLASQLite.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    }
    
}
