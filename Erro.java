/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

/**
 *
 * @author Rodrigo
 */
public class Erro {
    private int id;     
    private int lab;
    private int pc;
    private String desci;
    private int tipo;    

    public Erro(int id, int lab, int pc, String desci, int tipo) {
        this.id = id;
        this.lab = lab;
        this.pc = pc;
        this.desci = desci;
        this.tipo = tipo;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public String getDesci() {
        return desci;
    }

    public void setDesci(String desci) {
        this.desci = desci;
    }

    public int getLab() {
        return lab;
    }

    public void setLab(int lab) {
        this.lab = lab;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

        
    
}
