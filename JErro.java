/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;

/**
 *
 * @author Rodrigo
 */
public class JErro implements ActionListener {

    JButton ok = new JButton("Ok");
    JButton cancelar = new JButton("Cancelar");
    JFrame frameErro = new JFrame();
    JPanel painelErro = new JPanel();
    JLabel lerro = new JLabel("Descreva o problema:");
    JLabel label = new JLabel(" Qual é o tipo de Erro?");
    JTextArea ta = new JTextArea();
    JButton butao = null;
    String erro;
    int pc, tipo, lab,maiorTE=0;
    Object[] options = { "Sem Algum Programa", "Problema ao iniciar", "Outros" };    

    /*public int nomePika(String a) {
        int tam = a.length();
        int numb;
            try {
                numb = Integer.parseInt(a.substring(tam - 2));
            } catch (Exception e) {
                numb = Integer.parseInt(a.substring(tam - 1));
            }
        return numb;
    }*/
    /**
     * @param pc numero do computador
     * @param b butao que fez o clique
     * @param lab laboratorio do butao
     * @param tipo tipo da janela
     * */
    public JErro(int pc, JButton b,int lab) {   
            painelErro.setLayout(null);

            frameErro.setLocation(400, 100);
            frameErro.setSize(400, 350);
            frameErro.setTitle("Problema maquina " + pc);
            if(pc==0)
                frameErro.setTitle("Problema maquina professor");
            
            lerro.setLocation(30, 0);
            lerro.setSize(150, 50);

            label.setFont(new Font("Gerogia", 0, 14));
            //label.setForeground(new Color(255,225,200));

            ta.setLocation(25, 35);
            ta.setSize(335, 200);


            ok.setSize(90, 30);
            ok.setLocation(180, 250);
            ok.addActionListener(this);

            cancelar.setSize(90, 30);
            cancelar.setLocation(272, 250);
            cancelar.addActionListener(this);

            painelErro.add(ta);
            painelErro.add(cancelar);
            painelErro.add(ok);
            painelErro.add(lerro);
            frameErro.add(painelErro);
            frameErro.setVisible(true);

            this.butao = b;
            this.lab=lab;
            this.pc=pc;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            //<editor-fold defaultstate="collapsed" desc="Conexão com o BD ">
        try {
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String usuario = "postgres";
            String senha = "admin0123";
            Connection con = null;
            Statement stm = null;
            try {
                Class.forName("org.postgresql.Driver");
                con = DriverManager.getConnection(url, usuario, senha);
                stm = con.createStatement();
            } catch (SQLException a) {
                a.printStackTrace();
                JOptionPane.showMessageDialog(null, "Problemas de conexão com o banco de dados");
            } catch (ClassNotFoundException a) {
                a.printStackTrace();
            }
            //</editor-fold> 
            int op=0,aux;    
            
            ResultSet rs = stm.executeQuery("select * from Erro where descri is not null and lab="+lab+"and num="+pc);
            while(rs.next()){
                aux=rs.getInt("tipoerro");
                if((rs.isFirst())||(aux>maiorTE))
                    maiorTE=aux;
            } 
            
            if (e.getSource() == cancelar) {                
                frameErro.dispose();
                if(maiorTE==0){
                   butao.setBackground(null);
                   butao.setOpaque(false);
                }
            }
            if (e.getSource() == ok) {                
                if (!(ta.getText().equals(""))){
                    Object n = JOptionPane.showInputDialog(frameErro,label,"Comfirmação de Erro",JOptionPane.PLAIN_MESSAGE,null,options,options[0]);	
                    if(n!=null){
                        if(n.equals("Sem Algum Programa"))                      
                            op=2;                    
                        if(n.equals("Problema ao iniciar"))
                            op=3;                    
                        if(n.equals("Outros"))
                            op=1;   
                    }                                               
                    if(maiorTE<=op){
                        stm.executeUpdate("UPDATE pc SET tipoerro = "+op+" WHERE lab = "+lab+"and num = "+pc);
                        if(op==1)                                            
                            butao.setBackground(Color.YELLOW);                                        
                        if(op==2)                                            
                            butao.setBackground(Color.ORANGE);                                        
                        if(op==3)                                            
                            butao.setBackground(Color.RED);
                    } 
                    stm.executeUpdate("INSERT INTO Erro (descri,tipoerro,num,lab) VALUES ('" + ta.getText()+ "',"+op+","+ pc + "," + lab + ") ;");
                }
                frameErro.dispose();                
        }
            con.close();
        } catch (Exception a) {
            a.printStackTrace();
        }
    }
}
