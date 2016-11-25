/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;


public class Usuario {    
    private int id;
    private String Nome;
    private String senha;
    private int privilegio;

    public Usuario(int id, String Nome, String senha, int privilegio) {
        this.id = id;
        this.Nome = Nome;
        this.senha = senha;
        this.privilegio = privilegio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getPrivilegio() {
        return privilegio;
    }

    public void setPrivilegio(int privilegio) {
        this.privilegio = privilegio;
    }
    
   
}
