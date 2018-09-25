/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.TccDAO;
import java.util.List;
import model.Tcc;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author jscatena
 */
@ManagedBean
@ViewScoped
public class BeanTCC {

    private TccDAO dao;
    private Tcc tcc;
    public BeanTCC() {
        dao = new TccDAO(javax.persistence.Persistence.createEntityManagerFactory("SIGTCCPU"));
        tcc = new Tcc();
    }

    public Tcc getTcc() {
        return tcc;
    }

    public void setTcc(Tcc tcc) {
        this.tcc = tcc;
    }
    
    
    
    public List<Tcc> getTccs()
    {
        return dao.findTccEntities();
    }
    
}
