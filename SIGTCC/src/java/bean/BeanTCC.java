/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.TccDAO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Tcc;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Alunos;

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

    public boolean insertTcc(){
        if(!tcc.getProjeto().isEmpty())
        {
            dao.create(tcc);
            return true;
        }else return false;
    }

    public boolean updateTcc(){
        boolean success=false;
        try {
                dao.edit(tcc);
                success = true;

        } catch (Exception ex) {
            success = false;    
            Logger.getLogger(BeanProfessor.class.getName()).log(Level.SEVERE, null, ex);
          }
        return success;
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
    
    public Tcc findTcc(Alunos aluno){
        if(aluno != null)
            return dao.findTcc(aluno);
        else return null;
    }
    
    public List<Tcc> getAceites(){
        return dao.getAceites();
    }
    
}
