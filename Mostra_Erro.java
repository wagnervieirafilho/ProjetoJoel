package sistema;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.*;


public class Mostra_Erro extends JFrame {

  JList list;
  DefaultListModel model;
  int cont = 1;    
  int selections[];  
  Mostra_Erro me=this;  
  final LinkedList<Erro> er = new LinkedList<> ();  

  public Mostra_Erro(int pc,int lab,final JButton butao) {
      
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
    setLayout(new BorderLayout());
    model = new DefaultListModel();
    list = new JList(model);
    JScrollPane pane = new JScrollPane(list);
    JButton mostra = new JButton("Mostrar Erro");
    JButton remove = new JButton("Remover Erro");  
    
    ResultSet rs = stm.executeQuery("select * from Erro where descri is not null and lab="+lab+"and num="+pc);
    while (rs.next()){ 
        model.addElement("Erro "+cont);
        cont++;
        er.add(new Erro(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getInt(5)));
    }

    mostra.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
          int indice[] = list.getSelectedIndices();
          if(indice.length>0){
             JOptionPane.showMessageDialog(null,er.get(indice[0]).getDesci());             
          }
      }
    });
    
    remove.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
          selections = list.getSelectedIndices();   
          if(selections.length!=0)
              new Login(er.get(selections[0]).getId(),er.get(selections[0]).getLab(),er.get(selections[0]).getPc(),me,butao);
          
              
      }
    });

    add(pane, BorderLayout.NORTH);
    add(mostra, BorderLayout.WEST);
    add(remove, BorderLayout.EAST);
    this.setTitle("Janela de Erros");
    setVisible(true);    
    setSize(260, 250);
    
    con.close();      
    } catch (Exception a) {   
        a.printStackTrace();         
    }  
  }
}
