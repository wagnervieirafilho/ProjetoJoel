/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

/**
 *
 * @author Rodrigo
 */
public class PC {
    private int id;
    private int lab;
    private int num;
    private String erro;
    private int tipoErro;

    public PC(int lab, int num,int tipoErro) {
        this.lab = lab;
        this.num = num;
        this.tipoErro = tipoErro;
    }

    public int getTipoErro() {
        return tipoErro;
    }

    public void setTipoErro(int tipoErro) {
        this.tipoErro = tipoErro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLab() {
        return lab;
    }

    public void setLab(int lab) {
        this.lab = lab;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }
}
