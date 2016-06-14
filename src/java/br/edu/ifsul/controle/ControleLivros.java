package br.edu.ifsul.controle;




import br.edu.ifsul.dao.LivrosDAO;
import br.edu.ifsul.dao.GeneroDAO;
import br.edu.ifsul.modelo.Livros;
import br.edu.ifsul.modelo.Genero;
import br.edu.ifsul.util.Util;
import br.edu.ifsul.util.UtilRelatorios;
import java.io.Serializable;
import java.util.HashMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean(name = "controleLivros")
@ViewScoped
public class ControleLivros implements Serializable {
    private LivrosDAO<Livros> dao;
    private Livros objeto;
    private GeneroDAO<Genero> daoGenero;
    
    public ControleLivros(){
        dao = new LivrosDAO<>();
        daoGenero = new GeneroDAO<>();
    }
    
    public void imprimeProdutos(){
        HashMap parametros = new HashMap();
        UtilRelatorios.imprimeRelatorio("relatorioLivrosJavaBeans", parametros, dao.getListaTodos());
    }
    
    public String listar(){
        return "/privado/livros/listar?faces-redirect=true";
    }
    
    public void novo(){
        objeto = new Livros();        
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
        

    public LivrosDAO getDao() {
        return dao;
    }

    public void setDao(LivrosDAO dao) {
        this.dao = dao;
    }

    public Livros getObjeto() {
        return objeto;
    }

    public void setObjeto(Livros objeto) {
        this.objeto = objeto;
    }

    public GeneroDAO<Genero> getDaoGenero() {
        return daoGenero;
    }

    public void setDaoGenero(GeneroDAO<Genero> daoGenero) {
        this.daoGenero = daoGenero;
    }
}
