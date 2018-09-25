/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.AcompanhamentoDAO;
import dao.ProfessorDAO;
import java.util.List;
import model.Acompanhamento;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author jscatena
 */
@ManagedBean
@ViewScoped
public class BeanAcompanhamento {
    private AcompanhamentoDAO dao;
    private Acompanhamento acompanhamento;
    public BeanAcompanhamento() {
        dao = new AcompanhamentoDAO(javax.persistence.Persistence.createEntityManagerFactory("SIGTCCPU"));
        this.acompanhamento = new Acompanhamento();
    }

    public Acompanhamento getAcompanhamento() {
        return acompanhamento;
    }

    public void setAcompanhamento(Acompanhamento acompanhamento) {
        this.acompanhamento = acompanhamento;
    }
    
    
    public List<Acompanhamento> getAcompanhamentos()
    {
        return dao.findAcompanhamentoEntities();
    }
    
}
