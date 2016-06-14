
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.PessoaJuridicaDAO;
import br.edu.ifsul.modelo.PessoaJuridica;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "controlePessoaJuridica")
@SessionScoped
public class ControlePessoaJuridica implements Serializable {
    private PessoaJuridicaDAO<PessoaJuridica> dao;
    private PessoaJuridica objeto;
    
    public ControlePessoaJuridica(){
        dao = new PessoaJuridicaDAO<>();
    }
    
    public String listar(){
        return "/privado/pessoaJuridica/listar?faces-redirect=true";
    }
    
    public String novo(){
        objeto = new PessoaJuridica();
        return "formulario";
    }
    
    public String salvar(){
        boolean persistiu;
        if (objeto.getId() == null){
            persistiu = dao.persist(objeto);
        } else {
            persistiu = dao.merge(objeto);
        }        
        if(persistiu){
            Util.mensagemInformacao(dao.getMensagem());
            return "listar";
        } else {
            Util.mensagemErro(dao.getMensagem());
            return "formulario";
        }                        
    }
    
    public String cancelar(){
        return "listar";
    }
    
    public String editar(Integer id){
        try {
            objeto = dao.localizar(id);
            return "formulario";
        } catch (Exception e){
            Util.mensagemErro("Erro ao recuperar objeto: "+Util.getMensagemErro(e));
            return "listar";
        }
    }
    
    public void remover(Integer id){
        try {
            objeto = dao.localizar(id);
            if(dao.remover(objeto)){
                Util.mensagemInformacao(dao.getMensagem());
            } else {
                Util.mensagemErro(dao.getMensagem());
            }            
        } catch (Exception e){
            Util.mensagemErro("Erro ao recuperar objeto: "+Util.getMensagemErro(e));            
        }
    }    
        

    public PessoaJuridicaDAO getDao() {
        return dao;
    }

    public void setDao(PessoaJuridicaDAO dao) {
        this.dao = dao;
    }

    public PessoaJuridica getObjeto() {
        return objeto;
    }

    public void setObjeto(PessoaJuridica objeto) {
        this.objeto = objeto;
    }
}
