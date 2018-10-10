
package bean;

import dao.ProfessorDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Professores;

/**
 *
 * @author Prof.Jean Miler Scatena
 * @see Bean Gerenciado para Cadastro de Professores
 * @version 0.2
 * @date 09-25-2018
 */
@ManagedBean
@ViewScoped
public class BeanProfessor implements Serializable{

    private ProfessorDAO dao;
    private Professores professor;
    public BeanProfessor() {
        dao = new ProfessorDAO(javax.persistence.Persistence.createEntityManagerFactory("SIGTCCPU"));
        this.professor = new Professores();
    }

    public boolean insertProfessor(){
        if(!professor.getNome().isEmpty())
        {
            dao.create(professor);
            return true;
        }else return false;
    }

    public boolean updateProfessor(){
        boolean success=false;
        try {
                dao.edit(professor);
                success = true;

        } catch (Exception ex) {
            success = false;    
            Logger.getLogger(BeanProfessor.class.getName()).log(Level.SEVERE, null, ex);
          }
        return success;
    }
        
    public Professores getProfessor() {
        return professor;
    }

    public void setProfessor(Professores professor) {
        this.professor = new Professores();
    }
    
    public List<Professores> getProfessores(){
        return dao.findProfessoresEntities();
    }
    
    public Professores findProfessor(int id){
        if(id>0)
            return dao.findProfessores(id);
        else return null;
    }
    
    
}
