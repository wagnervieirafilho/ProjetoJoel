package sistema;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import javax.swing.*;

public class JAlterarSenha implements ActionListener{

	JFrame frame = new JFrame("Alterar senha");	
	JLabel usuario = new JLabel("Usuário");
	JLabel senhaAtual = new JLabel("Senha Atual");
	JLabel senhaNova = new JLabel("Nova Senha");
        JLabel ConfSenha = new JLabel("Digite Novamente");
        JLabel msg = new JLabel("Senha alterada com sucesso!");
        JLabel alterar = new JLabel("Alterar senha");
        JPanel pane = new JPanel();
	JButton cancelar = new JButton("Cancelar");
	JButton ok = new JButton("Ok");
	JTextField nomeUser = new JTextField();
	JPasswordField senhaAtual2 = new JPasswordField();
	JPasswordField senhaNova2 = new JPasswordField();
        JPasswordField senhaNova3 = new JPasswordField();
        LinkedList<Usuario> users = new LinkedList<> (); 
        int privilegio;
        
        public String criptografaSenha (String senha) throws NoSuchAlgorithmException {  
        MessageDigest md = MessageDigest.getInstance("MD5");  
        BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));  
        String s = hash.toString(16);  
        if (s.length() %2 != 0)  
            s = "0" + s;  
        return s;  
    }
	
	public JAlterarSenha(final LinkedList<Usuario> users,final int privilegio){
		
                this.privilegio=privilegio;
                
		frame.setLocation(200,100);
		frame.setSize(400,250);
		frame.setLayout(null);
                
                this.users=users;
		pane.setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));                    
                
		alterar.setFont(new java.awt.Font("Tahoma", 1, 15));
                alterar.setHorizontalAlignment(JLabel.CENTER);
		
		usuario.setSize(100,25);
		usuario.setLocation(50,50);
		
		senhaAtual.setSize(100,25);
		senhaAtual.setLocation(50,75);
		
		senhaNova.setSize(100,25);
		senhaNova.setLocation(50,100);
                
                ConfSenha.setSize(100,25);
                ConfSenha.setLocation(50,125);
		
		nomeUser.setSize(150,25);
		nomeUser.setLocation(160,50);
		
		senhaAtual2.setSize(150,25);
		senhaAtual2.setLocation(160,75);
		
		senhaNova2.setSize(150,25);
		senhaNova2.setLocation(160,100);
                
                senhaNova3.setSize(150,25);
                senhaNova3.setLocation(160,125);                
		
		alterar.setSize(150,30);
		alterar.setLocation(120,0);
		
		ok.setSize(90, 30);
		ok.setLocation(180,160);
		
		cancelar.setSize(90, 30);
		cancelar.setLocation(275,160);
		
		cancelar.addActionListener(this);
                ok.addActionListener(this);
		
                frame.add(ConfSenha);
		frame.add(senhaNova3);
                frame.add(usuario);
		frame.add(senhaAtual);
		frame.add(senhaNova);
		frame.add(nomeUser);
		frame.add(senhaAtual2);
		frame.add(senhaNova2);
		frame.add(alterar);
		frame.add(ok);
		frame.add(cancelar);
		
                msg.setFont(new Font("Taoma",1,15));
                msg.setForeground(new Color(0,0,0));
                
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {           
		if(e.getSource()== ok){
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
                    int x;
                    boolean erro=true;
                    String l,s = null;
                    try{
                        s=criptografaSenha(new String (senhaAtual2.getPassword()));
                    }
                    catch(Exception flap){}
                    l=nomeUser.getText();
                    for(x=0;x<users.size();x++){
                        if((l.equalsIgnoreCase(users.get(x).getNome()))&&(s.equals(users.get(x).getSenha()))){
                                
                                    if(senhaNova3.getText().equals(senhaNova2.getText())){
                                        try{
                                            String s2=criptografaSenha(new String (senhaNova2.getPassword()));
                                            stm.executeUpdate("UPDATE login SET senha='"+s2+"'where nome LIKE '"+l+"'and senha LIKE '"+s+"'");
                                            JOptionPane.showMessageDialog(frame,msg ,"Alteração senha",JOptionPane.INFORMATION_MESSAGE);
                                            erro=false;
                                            frame.dispose();
                                        }
                                        catch(NoSuchAlgorithmException | SQLException | HeadlessException oie){}                                          
                                
                                }
                        }                            
                    }
                    if(erro)
                        JOptionPane.showMessageDialog(frame, "O nome de usuário ou senha não correspondem","Erro senha",2);
                            con.close();        
                    } catch (Exception a) {     
                        a.printStackTrace();  
                    }
                    }                
		if(e.getSource() == cancelar){
			frame.dispose();
		}  
            
        }
		

}
               
