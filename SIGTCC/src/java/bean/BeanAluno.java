/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.AlunosDAO;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Aluno;

/**
 *
 * @author jscatena
 */
@ManagedBean
@ViewScoped
public class BeanAluno {

    private AlunosDAO dao;
    private Aluno aluno;
    public BeanAluno() {
        dao = new AlunosDAO(javax.persistence.Persistence.createEntityManagerFactory("SIGTCCPU"));
        this.aluno = new Aluno();

    }

    public boolean insertAluno(){
        if(!aluno.getNome().isEmpty())
        {
            dao.create(aluno);
            return true;
        }else return false;
        
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
    


    public List<Aluno> getAlunos(){
        return dao.findAlunosEntities();
    }
    

    
}
