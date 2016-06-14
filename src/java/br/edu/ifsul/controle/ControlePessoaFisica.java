
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.LivrosDAO;
import br.edu.ifsul.dao.PessoaFisicaDAO;
import br.edu.ifsul.modelo.Livros;
import br.edu.ifsul.modelo.PessoaFisica;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "controlePessoaFisica")
@SessionScoped
public class ControlePessoaFisica implements Serializable {
    private PessoaFisicaDAO<PessoaFisica> dao;
    private PessoaFisica objeto;
    private Livros livros;
    private LivrosDAO<Livros> daoLivros;
    
    public ControlePessoaFisica(){
        dao = new PessoaFisicaDAO<>();
        daoLivros = new LivrosDAO<>();
    }
    
     public void adicionarReserva(){
        if (livros != null){
            if(!objeto.getReservam().contains(livros)){
                objeto.getReservam().add(livros);
                Util.mensagemInformacao("Desejo adicionado com sucesso");
            } else {
                Util.mensagemErro("Produto já existe na lista");
            } 
        }
    }
    
    public void removerReserva(int index){
        livros = objeto.getReservam().get(index);
        objeto.getReservam().remove(livros);
        Util.mensagemInformacao("Desejo removido com sucesso!");
    }
    
    public String listar(){
        return "/privado/pessoaFisica/listar?faces-redirect=true";
    }
    
    public String novo(){
        objeto = new PessoaFisica();
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
        

    public PessoaFisicaDAO getDao() {
        return dao;
    }

    public void setDao(PessoaFisicaDAO dao) {
        this.dao = dao;
    }

    public PessoaFisica getObjeto() {
        return objeto;
    }

    public void setObjeto(PessoaFisica objeto) {
        this.objeto = objeto;
    }

    public Livros getLivros() {
        return livros;
    }

    public void setLivros(Livros livros) {
        this.livros = livros;
    }

    public LivrosDAO<Livros> getDaoLivros() {
        return daoLivros;
    }

    public void setDaoLivros(LivrosDAO<Livros> daoLivros) {
        this.daoLivros = daoLivros;
    }
    
    
}
