package sistema;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.*;

public class janelaLabs extends Thread implements ActionListener{

	JFrame frame = new JFrame();
	JPanel painel = new JPanel();
	ImageIcon iconePc = new ImageIcon(getClass().getResource("pc.png"));
	JButton[] b  = new JButton[21];
	int i,lab;	
	String maq = "Máquina Professor";
	Object[] options = {"Reportar outro problema","Visualizar problemas"};		
		
	public janelaLabs(int num){ 
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
            int x,op,pc;
            LinkedList<PC> pcs = new LinkedList<> ();             
            ResultSet rs = stm.executeQuery("select * from PC where lab="+num);   
            while(rs.next()){                                                          
                pcs.add(new PC(rs.getInt(2),rs.getInt(3),rs.getInt(4)));  
            }
            
            JLabel porta =new JLabel("Porta");
            porta.setVerticalAlignment(JLabel.TOP);
            porta.setHorizontalAlignment(JLabel.CENTER);
            porta.setFont(new Font("Taoma",0,25));
            porta.setForeground(Color.GRAY);
            
            frame.setTitle("Laboratório "+num);			
            painel.setLayout(new GridLayout(6,5,10,10));
            painel.add(porta);
            painel.add(new JLabel());
            painel.add(new JLabel());
            b[0] = new JButton(maq,iconePc);
            b[0].addActionListener(this);
            painel.add(b[0]);
            painel.add(new JLabel());
            for(i=1;i<=20;i++){
                if((i-3)%4==0)
                    painel.add(new JLabel());             
                maq = "Máquina "+(i);
                b[i] = new JButton(maq,iconePc);   
                b[i].addActionListener(this);
                painel.add(b[i]);                                    
            }
            for(x=0;x<pcs.size();x++){
                op=pcs.get(x).getTipoErro();
                pc=pcs.get(x).getNum();
                b[pc].setOpaque(true);
                if(op==1)
                    b[pc].setBackground(Color.YELLOW);
                if(op==2)
                    b[pc].setBackground(Color.ORANGE);
                if(op==3)
                    b[pc].setBackground(Color.RED);							
                b[pc].setOpaque(false);
            }
            frame.add(painel);			
            frame.setSize(new Dimension(1366,723));			
            frame.setResizable(false);			
            frame.setVisible(true);
            this.lab=num;
            con.close();        
            } catch (Exception a) {     
                a.printStackTrace();  
            }
        }

	@Override	      
        public void actionPerformed(ActionEvent e) {            
            int pc;			
            for(pc=0;pc<=20;pc++){				
                if(e.getSource()==b[pc]){					
                    if(!((b[pc].getBackground()==Color.RED)||(b[pc].getBackground()==Color.ORANGE)||(b[pc].getBackground()==Color.YELLOW))){										
                        new JErro((pc),b[pc],lab);					
                    }		
                    else{			
                        int op = JOptionPane.showOptionDialog(frame,"O que deseja fazer?",null,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null, options,options[0]);		
                        if(op==1){                
                            new Mostra_Erro(pc, lab,b[pc]);	
                        }			
                        if(op==0){								                                       
                            new JErro((pc),b[pc],lab);		
                        }
                    }	
                }	
            } 
        }
}
