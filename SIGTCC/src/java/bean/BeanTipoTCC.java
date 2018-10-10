/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.TipotccDAO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Tipotcc;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author jscatena
 */
@ManagedBean
@ViewScoped
public class BeanTipoTCC {

    private TipotccDAO dao;
    private Tipotcc tipotcc;
    public BeanTipoTCC() {
        dao = new TipotccDAO(javax.persistence.Persistence.createEntityManagerFactory("SIGTCCPU"));
        tipotcc = new Tipotcc();
    }

    public boolean insertTcc(){
        if(!tipotcc.getDescricao().isEmpty())
        {
            dao.create(tipotcc);
            return true;
        }else return false;
    }

    public boolean updateTipoTcc(){
        boolean success=false;
        try {
                dao.edit(tipotcc);
                success = true;

        } catch (Exception ex) {
            success = false;    
            Logger.getLogger(BeanProfessor.class.getName()).log(Level.SEVERE, null, ex);
          }
        return success;
    }

    public Tipotcc getTipotcc() {
        return tipotcc;
    }

    public void setTipotcc(Tipotcc tipotcc) {
        this.tipotcc = tipotcc;
    }
    
    public List<Tipotcc> getTiposTcc()
    {
        return dao.findTipotccEntities();
    }

    public Tipotcc findTiposTcc(int id)
    {
        return dao.findTipotcc(id);
    }

    
}
