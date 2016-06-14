package br.edu.ifsul.controle;




import br.edu.ifsul.dao.PromocaoDAO;
import br.edu.ifsul.dao.LivrosDAO;
import br.edu.ifsul.modelo.Promocao;
import br.edu.ifsul.modelo.Livros;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean(name = "controlePromocao")
@ViewScoped
public class ControlePromocao implements Serializable {
    private PromocaoDAO<Promocao> dao;
    private Promocao objeto;
    private LivrosDAO<Livros> daoLivros;
    
    public ControlePromocao(){
        dao = new PromocaoDAO<>();
        daoLivros = new LivrosDAO<>();
    }
    
    public String listar(){
        return "/privado/promocao/listar?faces-redirect=true";
    }
    
    public void novo(){
        objeto = new Promocao();        
    }
    
    public void salvar(){
        boolean persistiu;
        if (objeto.getId() == null){
            persistiu = dao.persist(objeto);
        } else {
            persistiu = dao.merge(objeto);
        }        
        if(persistiu){
            Util.mensagemInformacao(dao.getMensagem());            
        } else {
            Util.mensagemErro(dao.getMensagem());            
        }                        
    }    
    
    public void editar(Integer id){
        try {
            objeto = dao.localizar(id);            
        } catch (Exception e){
            Util.mensagemErro("Erro ao recuperar objeto: "+Util.getMensagemErro(e));            
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
        

    public PromocaoDAO getDao() {
        return dao;
    }

    public void setDao(PromocaoDAO dao) {
        this.dao = dao;
    }

    public Promocao getObjeto() {
        return objeto;
    }

    public void setObjeto(Promocao objeto) {
        this.objeto = objeto;
    }

    public LivrosDAO<Livros> getDaoLivros() {
        return daoLivros;
    }

    public void setDaoLivros(LivrosDAO<Livros> daoLivros) {
        this.daoLivros = daoLivros;
    }
}
