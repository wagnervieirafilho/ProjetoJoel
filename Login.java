/*
  * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistema;

import java.awt.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author Rodrigo
 */
public class Login extends JFrame  {    
    JLabel jLabel2 = new JLabel();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel3 = new JLabel(); 
    JPanel pane2 = new JPanel();
    JPanel pane3 = new JPanel();
    JPanel pane4 = new JPanel();
    JTextField jTextField1 = new JTextField();
    JPasswordField jPasswordField1 = new JPasswordField();
    JButton login = new JButton();  
    
    String s="",p="";
    // Usuario help = new Usuario();  
    int x;
    
    public String criptografaSenha (String senha) throws NoSuchAlgorithmException {  
        MessageDigest md = MessageDigest.getInstance("MD5");  
        BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));  
        String s = hash.toString(16);  
        if (s.length() %2 != 0)  
            s = "0" + s;  
        return s;  
    }
    public Login(final int id,final int lab,final int pc,final Mostra_Erro me,final JButton butao){
        this.setLayout(null);
        setSize(400,250);
        setLocation(400,200);
        
        final LinkedList<Usuario> users = new LinkedList<> (); 
        
        pane2.setLayout(new BoxLayout(pane2, BoxLayout.LINE_AXIS));        
        pane3.setLayout(new BoxLayout(pane3, BoxLayout.LINE_AXIS));     
        pane4.setLayout(new BoxLayout(pane4, BoxLayout.LINE_AXIS));  
        
        jLabel1.setText("Usuario ");
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); 
        jLabel1.setSize(50, this.getWidth());
                
        jLabel2.setText("Senha ");  
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); 
        
        jLabel3.setText("Autenticação");
        jLabel3.setFont(new java.awt.Font("Tahoma", 3, 14));
        
        login.setSize(70,50);
        login.setText("Login");
        login.addActionListener(new java.awt.event.ActionListener() {
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
            }   
            //</editor-fold> 
                ResultSet rs = stm.executeQuery("select * from Login");       
                boolean erro=true;
                int x,y,maiorTE=0;
                String l = jTextField1.getText();
                String s = new String (jPasswordField1.getPassword());
                while(rs.next()){
                    users.add(new Usuario(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4)));
                }
                for(x=0;x<users.size();x++){
                    if((l.equalsIgnoreCase(users.get(x).getNome()))&&(criptografaSenha(s).equals(users.get(x).getSenha()))){                           
                        int op=JOptionPane.showConfirmDialog(null, "O Problema foi totalmente resolvido?","Problema", JOptionPane.YES_NO_OPTION);
                        if(op==0){                            
                            stm.executeUpdate("DELETE FROM public.erro WHERE id = "+id);
                            if(me.selections.length>0){
                                me.model.removeElementAt(me.selections[0]); 
                                me.er.remove(me.selections[0]);
                            }
                            for(y=0;y<me.er.size();y++){
                                if((y==0)||(maiorTE<me.er.get(y).getTipo()))
                                    maiorTE=me.er.get(y).getTipo();                               
                            }
                            butao.setOpaque(true);
                            if(maiorTE==0)
                                butao.setBackground(null);
                            if(maiorTE==1)
                                butao.setBackground(Color.YELLOW);
                            if(maiorTE==2)
                                butao.setBackground(Color.ORANGE);
                            if(maiorTE==3)
                                butao.setBackground(Color.RED);	
                            butao.setOpaque(false);
                            stm.executeUpdate("UPDATE pc SET tipoerro = "+maiorTE+" WHERE lab = "+lab+"and num = "+pc);
                            if(me.list.getModel().getSize()==0)
                                me.dispose();
                        }
                        setVisible(false);                            
                        erro=false;                        
                     }
                }
                if(erro)
                    JOptionPane.showMessageDialog(null, "Login e senha invalidos","Erro de Senha",2);
                con.close();        
            } catch (Exception a) {     
                a.printStackTrace();  
            }
            }
        });
        
        
        jTextField1.setSize(10,12);
        
        pane2.add(Box.createHorizontalGlue());
        pane2.add(jLabel3);          
        pane2.add(Box.createHorizontalGlue());     
        pane2.setSize(400, 25);  
        
        pane3.add(jLabel1);
        pane3.add(Box.createRigidArea(new Dimension(10,0)));
        pane3.add(jTextField1);
        pane3.setSize(200, 25);
        pane3.setLocation(50,100);
        
        pane4.add(jLabel2);
        pane4.add(Box.createRigidArea(new Dimension(16,0)));
        pane4.add(jPasswordField1);
        pane4.setSize(200, 25);
        pane4.setLocation(50,125);
        
        login.setLocation(300,100);
             
        add(pane2);
        add(pane3); 
        add(pane4);
        add(login);
        this.setResizable(false);
        this.setVisible(true);
    }
    /**
     * 
     * @param tipo tipo da tela 1=Criar 2=Alterar
     */
    public Login(final int tipo) {
        this.setLayout(null);
        setSize(400,250);
        setLocation(400,200);
        
        final LinkedList<Usuario> users = new LinkedList<> (); 
        
        pane2.setLayout(new BoxLayout(pane2, BoxLayout.LINE_AXIS));        
        pane3.setLayout(new BoxLayout(pane3, BoxLayout.LINE_AXIS));     
        pane4.setLayout(new BoxLayout(pane4, BoxLayout.LINE_AXIS));  
        
        jLabel1.setText("Usuario ");
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); 
        jLabel1.setSize(50, this.getWidth());
                
        jLabel2.setText("Senha ");  
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); 
        
        jLabel3.setText("Autenticação ");
        jLabel3.setFont(new java.awt.Font("Tahoma", 3, 14));
        
        login.setSize(70,50);
        login.setText("Login");
        login.addActionListener(new java.awt.event.ActionListener() {
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
            }   
            //</editor-fold> 
                users.removeAll(users);
                ResultSet rs = stm.executeQuery("select * from Login");       
                boolean erro=true,erro2=true;
                int x,id;
                String e = null;
                String l = jTextField1.getText();
                String s = new String (jPasswordField1.getPassword());
                while(rs.next()){
                    users.add(new Usuario(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4)));
                }
                for(x=0;x<users.size();x++){
                    if((l.equalsIgnoreCase(users.get(x).getNome()))&&(criptografaSenha(s).equals(users.get(x).getSenha()))){                           
                        erro=false;
                        if(users.get(x).getPrivilegio()>=2){                            
                            switch (tipo){
                                case 1:
                                    new janelaCadastro(users).setVisible(true);
                                    break;                         
                                    
                                case 2:
                                    new JAlterarSenha(users,users.get(x).getPrivilegio());
                                break;
                                        
                                case 3:
                                    new JExcluir(users,users.get(x).getPrivilegio());
                                    break;
                                                                                
                            }
                         
                        
                            dispose();
                           erro2 = false;
                            
                        }
                        if(users.get(x).getPrivilegio()<=1){                            
                            switch (tipo){
                               case 2:
                                    new JAlterarSenha(users,users.get(x).getPrivilegio());
                                    erro2 = false;
                                break;                                        
                            }
                         
                        
                            dispose(); 
                            
                        }
                    }
                }                                  
                if(erro)
                    JOptionPane.showMessageDialog(null, "Login e senha invalidos","Erro de Senha",2);
                else
                    if(erro2)                            
                        JOptionPane.showMessageDialog(null, "Você não tem o privilégio necessário","Erro de Senha",2);  
                con.close();        
            } catch (Exception a) {     
                a.printStackTrace();  
            }
            }
        });
        
        
        jTextField1.setSize(10,12);
        
        pane2.add(Box.createHorizontalGlue());
        pane2.add(jLabel3);          
        pane2.add(Box.createHorizontalGlue());     
        pane2.setSize(400, 25);  
        
        pane3.add(jLabel1);
        pane3.add(Box.createRigidArea(new Dimension(10,0)));
        pane3.add(jTextField1);
        pane3.setSize(200, 25);
        pane3.setLocation(50,100);
        
        pane4.add(jLabel2);
        pane4.add(Box.createRigidArea(new Dimension(16,0)));
        pane4.add(jPasswordField1);
        pane4.setSize(200, 25);
        pane4.setLocation(50,125);
        
        login.setLocation(300,100);
             
        add(pane2);
        add(pane3); 
        add(pane4);
        add(login);
        
        this.setTitle("Login");
        this.setResizable(false);
    }
}
