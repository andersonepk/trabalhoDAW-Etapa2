package br.edu.ifsul.controle;

import br.edu.ifsul.dao.PessoaFisicaDAO;
import br.edu.ifsul.dao.LivrosDAO;
import br.edu.ifsul.dao.VendaDAO;
import br.edu.ifsul.modelo.PessoaFisica;
import br.edu.ifsul.modelo.Livros;
import br.edu.ifsul.modelo.Venda;
import br.edu.ifsul.modelo.VendaID;
import br.edu.ifsul.modelo.VendaItem;
import br.edu.ifsul.util.Util;
import br.edu.ifsul.util.UtilRelatorios;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Jorge Luis Boeira Bavaresco
 * @email jorge.bavaresco@passofundo.ifsul.edu.br
 */
@ManagedBean(name = "controleVenda")
@ViewScoped
public class ControleVenda implements Serializable {

    private VendaDAO<Venda> dao;
    private Venda objeto;
    private LivrosDAO<Livros> daoLivros;
    private PessoaFisicaDAO<PessoaFisica> daoPessoaFisica;
    private VendaItem item;
    private Boolean novoItem;

    public ControleVenda() {
        dao = new VendaDAO<>();
        daoLivros = new LivrosDAO<>();
        daoPessoaFisica = new PessoaFisicaDAO<>();

    }

    public void imprimeVenda(VendaID id) {
        try {
            objeto = dao.getObjectById(id);
            List<Venda> listaVenda = new ArrayList<>();
            listaVenda.add(objeto);
            HashMap parametros = new HashMap();
// deve ser informada a lista para o subrelatório
            parametros.put("listaItens", objeto.getItens());
            UtilRelatorios.imprimeRelatorio("relatorioVendaJavaBeans", parametros, listaVenda);
        } catch (Exception e) {
            Util.mensagemErro("Erro ao recuperar objeto");
        }
// Deve-se criar uma lista com um único objeto entrada

    }

    public String listar() {
        return "/privado/venda/listar?faces-redirect=true";
    }

    public void novo() {
        objeto = new Venda();
        objeto.setId(new VendaID());
        objeto.setData(Calendar.getInstance());
    }

    public void salvar() {
        try {
            if (objeto.getId() == null) {
                dao.persist(objeto);
            } else {
                dao.merge(objeto);
            }
            Util.mensagemInformacao("Objeto persistido com sucesso");
        } catch (Exception e) {
            Util.mensagemErro("Erro ao persistir: " + e.getMessage());
        }
    }

    public void editar(VendaID id) {
        try {
            objeto = dao.getObjectById(id);
        } catch (Exception e) {
            Util.mensagemErro("Erro ao recuperar objeto");
        }
    }

    public void remover(VendaID id) {
        try {
            objeto = dao.getObjectById(id);
            dao.remover(objeto);
            Util.mensagemInformacao("Objeto removido com sucesso!");
        } catch (Exception e) {
            Util.mensagemErro("Erro a remover objeto: " + e.getMessage());
        }
    }

    public void novoItem() {
        item = new VendaItem();
        novoItem = true;
    }

    public void alterarItem(int index) {
        item = objeto.getItens().get(index);
        novoItem = false;
    }

    public void salvarItem() {
        if (novoItem) {
            objeto.adicionarItem(item);
        } else {
            atualizaValorTotal();
        }
        Util.mensagemInformacao("Operação realizada com sucesso");
    }

    public void atualizaValorUnitarioItem() {
        if (item != null) {
            if (item.getLivros() != null) {
                item.setValorUnitario(item.getLivros().getValorUnitario());
            }
        }
    }

    private void atualizaValorTotal() {
        objeto.setValorTotal(0.00);
        Double total = 0.0;
        for (VendaItem vi : objeto.getItens()) {
            total += vi.getValorTotal();
        }
        objeto.setValorTotal(total);
    }

    public void calculaValorTotalItem() {
        if (item.getValorUnitario() != null && item.getQuantidade() != null) {
            item.setValorTotal(item.getValorUnitario() * item.getQuantidade());
        }
    }

    public void removerItem(int index) {
        objeto.removerItem(index);
        Util.mensagemInformacao("Item removido com sucesso");
    }

    public VendaDAO getDao() {
        return dao;
    }

    public void setDao(VendaDAO dao) {
        this.dao = dao;
    }

    public Venda getObjeto() {
        return objeto;
    }

    public void setObjeto(Venda objeto) {
        this.objeto = objeto;
    }

    public VendaItem getItem() {
        return item;
    }

    public void setItem(VendaItem item) {
        this.item = item;
    }

    public Boolean getNovoItem() {
        return novoItem;
    }

    public void setNovoItem(Boolean novoItem) {
        this.novoItem = novoItem;
    }

    public LivrosDAO<Livros> getDaoLivros() {
        return daoLivros;
    }

    public void setDaoLivros(LivrosDAO<Livros> daoLivros) {
        this.daoLivros = daoLivros;
    }

    public PessoaFisicaDAO<PessoaFisica> getDaoPessoaFisica() {
        return daoPessoaFisica;
    }

    public void setDaoPessoaFisica(PessoaFisicaDAO<PessoaFisica> daoPessoaFisica) {
        this.daoPessoaFisica = daoPessoaFisica;
    }

}
