
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.GeneroDAO;
import br.edu.ifsul.modelo.Genero;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "controleGenero")
@SessionScoped
public class ControleGenero implements Serializable {
    private GeneroDAO<Genero> dao;
    private Genero objeto;
    
    public ControleGenero(){
        dao = new GeneroDAO<>();
    }
    
    public String listar(){
        return "/privado/genero/listar?faces-redirect=true";
    }
    
    public String novo(){
        objeto = new Genero();
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
        

    public GeneroDAO getDao() {
        return dao;
    }

    public void setDao(GeneroDAO dao) {
        this.dao = dao;
    }

    public Genero getObjeto() {
        return objeto;
    }

    public void setObjeto(Genero objeto) {
        this.objeto = objeto;
    }
}
