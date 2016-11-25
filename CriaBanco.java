/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Rodrigo
 */
public class CriaBanco {
    public CriaBanco(){
        //<editor-fold defaultstate="collapsed" desc="Conexão com o BD ">
        try {  
            String url = "jdbc:postgresql://localhost:5432/postgres";  
            String usuario = "postgres";  
            String senha = "admin0123"; 
            Connection con=null;  
            Statement stm=null; 
            try{  
                Class.forName("org.postgresql.Driver"); 
                con = DriverManager.getConnection(url, usuario, senha);    
                stm = con.createStatement();     
            }  
            catch(SQLException a){    
                  a.printStackTrace();  
                  JOptionPane.showMessageDialog(null, "Problemas de conexão com o banco de dados");  
            }   
            catch(ClassNotFoundException a){  
                a.printStackTrace();  
            }   
            //</editor-fold> 
        stm.execute("create table Login (Id serial PRIMARY KEY,Nome varchar(50),Senha text,Privilegio int);");
        stm.execute("create table PC (Id serial PRIMARY KEY,Lab int,Num int,tipoerro int);");
        stm.execute("create table Erro (Id serial PRIMARY KEY,Lab int,Num int,descri text,tipoerro int);");
        stm.execute("INSERT INTO Login (Nome,Senha,Privilegio) VALUES ('Rodrigo',MD5('oie'),3), ('Wagner',MD5('gay'),3), ('Lirojo',MD5('Liro)'),3)");
        int x,y;
        for(x=1;x<=6;x++){
            for(y=0;y<=20;y++){
                stm.execute("INSERT INTO PC (Lab,Num) VALUES ("+x+","+y+")");
            }
        }
        con.close();        
        } catch (Exception a) {     
            a.printStackTrace();  
        }  
    }
    
}
