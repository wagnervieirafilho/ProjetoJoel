/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import javax.swing.*;

/**
 *
 * @author Rodrigo
 */
public class janelaCadastro extends JFrame {
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();    
    JLabel jLabel1 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JLabel jLabel5 = new JLabel();
    JPanel pane = new JPanel();
    /*JPanel pane3 = new JPanel();
    JPanel pane4 = new JPanel();
    JPanel pane5 = new JPanel();
    JPanel pane6 = new JPanel();*/
    JTextField jTextField1 = new JTextField();
    JPasswordField jPasswordField1 = new JPasswordField();
    JPasswordField jPasswordField2 = new JPasswordField();
    JButton confirma = new JButton();  
    JComboBox jComboBox1 = new JComboBox();

    public janelaCadastro(final LinkedList<Usuario> users) {
        this.setLayout(null);
        pane.setLayout(new GridLayout(4,2));
        
        setSize(400,225);
        setLocation(400,200); 
        
        jLabel1.setText("Novo Cadastro ");
        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 14));
        
        jLabel2.setText("Usuario ");
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); 
        jLabel2.setSize(50, this.getWidth());
                
        jLabel3.setText("Senha ");  
        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14));        
        
        jLabel4.setText("Confirmação ");  
        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); 
        
        jLabel5.setText("Privilégio ");  
        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); 
        
        pane.setSize(220,100);
        pane.setLocation(30,50);
        
        confirma.setSize(90,50);
        confirma.setText("Confirmar");        
        confirma.setLocation(285,75);
        
        confirma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {  
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
                JOptionPane.showMessageDialog(null, "Problemas de conexão com o banco de dados"); 
            }   
            //</editor-fold>   
                String c = new String (jPasswordField2.getPassword());
                String l = jTextField1.getText();
                String s = new String (jPasswordField1.getPassword());
                int x,p = jComboBox1.getSelectedIndex();
                boolean erro=false;
                if(!(s.equals("")&&(l.equals("")))){
                    if(c.equals(s)){
                        for(x=0;x<users.size();x++){
                            if(users.get(x).getNome().equalsIgnoreCase(l)){
                                erro=true;
                                JOptionPane.showMessageDialog(null, "Usuario já cadastrado","Erro",2);
                            }
                        }
                        if(!erro){
                            stm.executeUpdate("INSERT INTO Login (Nome,Senha,privilegio) VALUES ('"+l+"',MD5('"+s+"'),"+p+")");
                            JOptionPane.showMessageDialog(null, "Cadastro Criado com Sucesso");
                            setVisible(false); 
                        }
                        
                    }
                    else
                        JOptionPane.showMessageDialog(null, "As senhas não conferem");
                }
                else                        
                    JOptionPane.showMessageDialog(null, "Campo(s) em Branco");                  
            con.close();        
            } catch (Exception a) {     
                a.printStackTrace();  
            }
            }
        });
        
        jTextField1.setSize(10,12);
        
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"Estagiario", "Monitor", "Professor", "Coordenador" }));
        
        
        pane.add(jLabel2);
        pane.add(jTextField1);
        pane.add(jLabel3);
        pane.add(jPasswordField1);
        pane.add(jLabel4);
        pane.add(jPasswordField2);
        pane.add(jLabel5);
        pane.add(jComboBox1);
        add(pane);
        add(confirma);
        
        this.setTitle("Novo Cadastro");
        this.setResizable(false);
    }    
}
