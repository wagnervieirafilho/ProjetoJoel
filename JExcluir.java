/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import javax.swing.*;


/**
 *
 * @author Rodrigo
 */
public class JExcluir extends JFrame {
    JList list;
    DefaultListModel model;
    int selections[];  
    JExcluir me=this;  
    
    public JExcluir(LinkedList<Usuario> users,final int privilegio){  
        int x;
        
        setLayout(new BorderLayout());
        model = new DefaultListModel();
        list = new JList(model);
        JScrollPane pane = new JScrollPane(list);
        JPanel pane2 = new JPanel();
        JButton remove = new JButton("Remover Usuario");
        for(x=0;x<users.size();x++){
            model.addElement(users.get(x).getNome());
        }
        
        pane2.setLayout(new BoxLayout(pane2, BoxLayout.LINE_AXIS));
        pane2.add(remove);
                
        add(pane, BorderLayout.CENTER);
        add(pane2, BorderLayout.EAST);
        
        remove.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            selections = list.getSelectedIndices();   
            if(selections.length!=0){
                int op=JOptionPane.showConfirmDialog(me, "<html>Você tem certeza que deseja excluir o usuario<br><center>"+list.getSelectedValue().toString()+"</center>","Excluir",JOptionPane.OK_CANCEL_OPTION);
                if(op==0){                    
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
                    stm.executeUpdate("DELETE FROM public.login WHERE  nome= '"+model.getElementAt(selections[0]).toString()+"'");
                    model.removeElementAt(selections[0]);
                    if(list.getModel().getSize()==0)
                          dispose();
                    con.close();      
                    } catch (Exception a) {   
                        a.printStackTrace();         
                    }
                }
            }
        }
        });
        
        this.setTitle("Janela de Usuario");
        setVisible(true);    
        setSize(400, 250);
    }
}
