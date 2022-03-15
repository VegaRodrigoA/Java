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
        //Creamos una lista con los nombres de las tablas que deseamos migrar
        String[] lista = new String[]{"tabla1" ,"tabla2"};
        
        //Iniciamos un bucle que va a recorrer la lista con los nombres de las tablas
        for (String list :lista){
            try {
                //Nos conectamos a la base de datos MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conexion;
                conexion=DriverManager.getConnection ("jdbc:mysql://127.0.0.1:3306/Base",
                        "usuario","password");
                
                PreparedStatement stmt;
                
                //Seleccionamos todos los datos de cada tabla iterada
                stmt = conexion.prepareStatement("Select * from " + list + ";");
                
                ClassSQL nuevo;//Creamos una clase ClassSQL

                ResultSet rs;
                //Almacenamos los datos de la tabla en un ResultSet
                rs = stmt.executeQuery();

                //Obtenemos los metadatos
                ResultSetMetaData rsMd = rs.getMetaData();
                String sql = "";//Sentencia para crear las tablas
                String sql2 = "";//Sentencia con nombres de columna para agregar datos
                String sql3 = "";//Sentencia con valores a agregar
                String tipo = "";//Tipo de datos de cada columna
                int colCount;//Lo usaremos para iterar dentro de las columnas de cada tabla
                int cantidadColumnas = rsMd.getColumnCount();//Columnas de cada tabla
                
                //Bucle para obtener los nombres y tipo de datos de cada columna
                for (int i = 1; i <= cantidadColumnas; i++) {

                    if (i != 1){//Si no es la primera columna, debemos anteponer
                        //una coma antes del nombre de la columna
                        sql = sql + " , ";
                        sql2 = sql2 + " , ";
                        }
                    
                    //Tipo de datos que va a tener cada columna
                    if (rsMd.getColumnTypeName(i)=="INTEGER"){
                        tipo = "INTEGER";
                    }else if(rsMd.getColumnTypeName(i)=="DOUBLE"){
                        tipo = "NUMERIC";                                
                    } else{
                        tipo = "TEXT";
                    }
                    
                    //Agregamos los nombres de las columnas a las sentencias SQL
                    sql = sql + "'" + rsMd.getColumnName(i) + "' " + tipo ;
                    sql2 = sql2 + "'" + rsMd.getColumnName(i) + "' ";

                }//Salimos del bucle del renglon 56

                //Creamos la tabla iterada
                nuevo = new ClassSQL("Create table '" + list + "' (" + sql + ");");
                nuevo.Ejecutar();
                nuevo.close();

                //Dentro de cada tabla, iteramos los registros para cargar los datos
                while (rs.next()) {
                    //Reiniciamos los valores para que no arrastren los valores 
                    //de la iteración anterior
                    colCount = 1;
                    sql3 = "";

                    while (colCount <= cantidadColumnas){
                            if (colCount != 1){//Si no es la primera columna, 
                                //debemos anteponer una coma antes del nombre
                                sql3 = sql3 + " , ";
                            }
                            sql3 = sql3 + "'"+ rs.getString(colCount) + "'" ;
                            colCount = colCount +1;
                        }

                        //Agregamos los valores a la tabla correspondiente
                        nuevo = new ClassSQL("Insert into '" + list
                        + "' (" + sql2 + ") values (" + sql3 + ");");
                        nuevo.Ejecutar();
                        nuevo.close();
                        }
                
                //Cerramos la conección a la base de datos MySQL
                conexion.close();
                //Obtenemos los posibles errores de ejecución
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error de ejecución.");
                } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Class not found.");
                Logger.getLogger(MySQLASQLite.class.getName()).log(Level.SEVERE, null, ex);
            }

    }

    }
    
}
