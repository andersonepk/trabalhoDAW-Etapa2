package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Pessoa;
import br.edu.ifsul.modelo.Venda;
import br.edu.ifsul.modelo.VendaID;
import java.io.Serializable;


/**
 *
 * @author Jorge Luis Boeira Bavaresco
 * @email jorge.bavaresco@passofundo.ifsul.edu.br
 */

public class VendaDAO<T> extends DAOGenerico<Venda> implements Serializable {
 
    public VendaDAO() {
        super();
        super.setClassePersistente(Venda.class);
    }
    
    
    public Venda getObjectById(VendaID id) throws Exception {
        VendaID idObj = new VendaID();
        idObj.setNumeroNota(id.getNumeroNota());
        idObj.setPessoa(super.getEm().find(Pessoa.class, id.getPessoa().getId()));
        Venda obj = super.getEm().find(Venda.class, idObj);
        return obj;
    }
    
    
}
