
package br.edu.ifsul.controle;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean(name = "controleAjax")
@ViewScoped
public class ControleAjax implements Serializable{
    
    private String entrada;
    private String saida;

    public ControleAjax(){
        
    }
    
    public void processar(){
        saida = "Você informou :"+entrada;
        entrada = "";
    }
    
    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public String getSaida() {
        return saida;
    }

    public void setSaida(String saida) {
        this.saida = saida;
    }

}
