package sistema;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;

public class janelaPrincipal implements ActionListener{

	JMenuBar mb = new JMenuBar();
        JFrame frame = new JFrame("Controle de Manuteção");
	JPanel painel = new JPanel();
        //JLabel Inst = new JLabel();
        JMenuItem inst = new JMenuItem();
	ImageIcon iconeLab = new ImageIcon(getClass().getResource("lab.png")); 
	JButton[] labs = new JButton[6];
        JMenu configuracoes = new JMenu("Configurações");
        //JMenu ajuda = new JMenu("Ajuda");
        JMenuItem aba2 = new JMenuItem("Alterar Senha");
        JMenuItem aba = new JMenuItem("Novo Cadastro");
        JMenuItem ex = new JMenuItem("Excluir Cadastro");
	int i;
	
		public janelaPrincipal(){
			Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("cefet.png"));
			frame.setIconImage(image);
                        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.LINE_AXIS));
                        frame.setJMenuBar(mb);
                        
          /*              Inst.setForeground(new java.awt.Color(153, 0, 204));
                        Inst.setFont(new java.awt.Font("Georgia", 0, 14));
                        Inst.setText("Ah se vira aí Cara");*/
                        
			/*inst.setText("Instruções");
                        inst.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                JOptionPane.showMessageDialog(frame, Inst);
                            }
                        });*/
                        
                        painel.setLayout(new GridLayout(2,3,10,10));
			for(i=0;i<=5;i++){
				labs[i] = new JButton("Lab "+(i+1),iconeLab);
				labs[i].addActionListener(this);
				painel.add(labs[i]);
				
			}
                        
                        ex.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                new Login(3).setVisible(true);
                                
                            }
                        });
                         
                        aba2.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                new Login(2).setVisible(true);
                                
                            }
                        });
                        
                        aba.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                new Login(1).setVisible(true);
                            }
                        });

                        configuracoes.add(aba);
                        configuracoes.add(aba2);
                        configuracoes.add(ex);
			mb.add(configuracoes);
      //                  mb.add(ajuda);
        //                ajuda.add(inst);
			frame.add(Box.createHorizontalGlue());
			frame.add(painel);
			frame.add(Box.createHorizontalGlue());
			frame.setSize(new Dimension(1366,723));
			frame.setResizable(false);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
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
                        for(int x=1;x<=6;x++){
                            int cont=0;
                            ResultSet rs=stm.executeQuery("select * from erro where lab="+x);
                            while(rs.next())
                                cont++;                            
                            if(cont>=1)
                                labs[x-1].setBackground(Color.yellow);
                        }
                        con.close();        
                        } catch (Exception a) {     
                            a.printStackTrace();  
                        }
			
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==labs[0]){
				new janelaLabs(1);				
			}
			if(e.getSource()==labs[1]){
				new janelaLabs(2);
			}
			if(e.getSource()==labs[2]){
				new janelaLabs(3);
			}
			if(e.getSource()==labs[3]){
				new janelaLabs(4);
			}
			if(e.getSource()==labs[4]){
				new janelaLabs(5);
			}
			if(e.getSource()==labs[5]){
				new janelaLabs(6);
			}
		}
        

}
