package sistema;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class JanelaAlterarSenha implements ActionListener{

	JFrame frame = new JFrame("Alterar senha");
	JLabel novoCadastro = new JLabel("Novo cadastro");
	JLabel usuario = new JLabel("Usuário");
	JLabel senhaAtual = new JLabel("Senha Atual");
	JLabel senhaNova = new JLabel("Nova Senha");
	JButton cancelar = new JButton("Cancelar");
	JButton ok = new JButton("Ok");
	JTextField nomeUser = new JTextField();
	JPasswordField senhaAtual2 = new JPasswordField();
	JPasswordField senhaNova2 = new JPasswordField();
	
	public JanelaAlterarSenha(){
		
		frame.setLocation(200,100);
		frame.setSize(400,250);
		frame.setLayout(null);
		
		novoCadastro.setFont(new java.awt.Font("Tahoma", 3, 14));
		
		usuario.setSize(100,25);
		usuario.setLocation(50,50);
		
		senhaAtual.setSize(100,25);
		senhaAtual.setLocation(50,75);
		
		senhaNova.setSize(100,25);
		senhaNova.setLocation(50,100);
		
		nomeUser.setSize(150,25);
		nomeUser.setLocation(160,50);
		
		senhaAtual2.setSize(150,25);
		senhaAtual2.setLocation(160,75);
		
		senhaNova2.setSize(150,25);
		senhaNova2.setLocation(160,100);
		
		novoCadastro.setSize(150,30);
		novoCadastro.setLocation(120,0);
		
		ok.setSize(90, 30);
		ok.setLocation(180,145);
		
		cancelar.setSize(90, 30);
		cancelar.setLocation(275,145);
		
		cancelar.addActionListener(this);
		
		frame.add(usuario);
		frame.add(senhaAtual);
		frame.add(senhaNova);
		frame.add(nomeUser);
		frame.add(senhaAtual2);
		frame.add(senhaNova2);
		frame.add(novoCadastro);
		frame.add(ok);
		frame.add(cancelar);
		
		frame.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == cancelar){
			frame.dispose();
		}
		
	}

}
