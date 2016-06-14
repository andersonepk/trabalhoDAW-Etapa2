
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Genero;
import java.io.Serializable;

/**
 *
 * @author Jorge Luis Boeira Bavaresco
 * @email jorge.bavaresco@passofundo.ifsul.edu.br
 */
public class GeneroDAO<T> extends DAOGenerico<Genero>implements Serializable {

    public GeneroDAO(){
        super();
        super.setClassePersistente(Genero.class);
        super.setOrdem("descricao");// ordem padrão
    }

}
