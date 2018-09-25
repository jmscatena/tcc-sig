/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Acesso;
import model.Aluno;
import model.Professor;

/**
 *
 * @author jscatena
 */
public class AcessoDAO implements Serializable {

    public AcessoDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Acesso acesso) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Aluno alunos = acesso.getAlunos();
            if (alunos != null) {
                alunos = em.getReference(alunos.getClass(), alunos.getId());
                acesso.setAlunos(alunos);
            }
            Professor professores = acesso.getProfessores();
            if (professores != null) {
                professores = em.getReference(professores.getClass(), professores.getId());
                acesso.setProfessores(professores);
            }
            em.persist(acesso);
            if (alunos != null) {
                Acesso oldIdAcessoOfAlunos = alunos.getIdAcesso();
                if (oldIdAcessoOfAlunos != null) {
                    oldIdAcessoOfAlunos.setAlunos(null);
                    oldIdAcessoOfAlunos = em.merge(oldIdAcessoOfAlunos);
                }
                alunos.setIdAcesso(acesso);
                alunos = em.merge(alunos);
            }
            if (professores != null) {
                Acesso oldIdAcessoOfProfessores = professores.getIdAcesso();
                if (oldIdAcessoOfProfessores != null) {
                    oldIdAcessoOfProfessores.setProfessores(null);
                    oldIdAcessoOfProfessores = em.merge(oldIdAcessoOfProfessores);
                }
                professores.setIdAcesso(acesso);
                professores = em.merge(professores);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Acesso acesso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Acesso persistentAcesso = em.find(Acesso.class, acesso.getId());
            Aluno alunosOld = persistentAcesso.getAlunos();
            Aluno alunosNew = acesso.getAlunos();
            Professor professoresOld = persistentAcesso.getProfessores();
            Professor professoresNew = acesso.getProfessores();
            if (alunosNew != null) {
                alunosNew = em.getReference(alunosNew.getClass(), alunosNew.getId());
                acesso.setAlunos(alunosNew);
            }
            if (professoresNew != null) {
                professoresNew = em.getReference(professoresNew.getClass(), professoresNew.getId());
                acesso.setProfessores(professoresNew);
            }
            acesso = em.merge(acesso);
            if (alunosOld != null && !alunosOld.equals(alunosNew)) {
                alunosOld.setIdAcesso(null);
                alunosOld = em.merge(alunosOld);
            }
            if (alunosNew != null && !alunosNew.equals(alunosOld)) {
                Acesso oldIdAcessoOfAlunos = alunosNew.getIdAcesso();
                if (oldIdAcessoOfAlunos != null) {
                    oldIdAcessoOfAlunos.setAlunos(null);
                    oldIdAcessoOfAlunos = em.merge(oldIdAcessoOfAlunos);
                }
                alunosNew.setIdAcesso(acesso);
                alunosNew = em.merge(alunosNew);
            }
            if (professoresOld != null && !professoresOld.equals(professoresNew)) {
                professoresOld.setIdAcesso(null);
                professoresOld = em.merge(professoresOld);
            }
            if (professoresNew != null && !professoresNew.equals(professoresOld)) {
                Acesso oldIdAcessoOfProfessores = professoresNew.getIdAcesso();
                if (oldIdAcessoOfProfessores != null) {
                    oldIdAcessoOfProfessores.setProfessores(null);
                    oldIdAcessoOfProfessores = em.merge(oldIdAcessoOfProfessores);
                }
                professoresNew.setIdAcesso(acesso);
                professoresNew = em.merge(professoresNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = acesso.getId();
                if (findAcesso(id) == null) {
                    throw new NonexistentEntityException("The acesso with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Acesso acesso;
            try {
                acesso = em.getReference(Acesso.class, id);
                acesso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The acesso with id " + id + " no longer exists.", enfe);
            }
            Aluno alunos = acesso.getAlunos();
            if (alunos != null) {
                alunos.setIdAcesso(null);
                alunos = em.merge(alunos);
            }
            Professor professores = acesso.getProfessores();
            if (professores != null) {
                professores.setIdAcesso(null);
                professores = em.merge(professores);
            }
            em.remove(acesso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Acesso> findAcessoEntities() {
        return findAcessoEntities(true, -1, -1);
    }

    public List<Acesso> findAcessoEntities(int maxResults, int firstResult) {
        return findAcessoEntities(false, maxResults, firstResult);
    }

    private List<Acesso> findAcessoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Acesso.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Acesso findAcesso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Acesso.class, id);
        } finally {
            em.close();
        }
    }

    public int getAcessoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Acesso> rt = cq.from(Acesso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
