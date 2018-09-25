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
import model.Aluno;
import model.Curso;

/**
 *
 * @author jscatena
 */
public class CursoDAO implements Serializable {

    public CursoDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Curso curso) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Aluno alunos = curso.getAlunos();
            if (alunos != null) {
                alunos = em.getReference(alunos.getClass(), alunos.getId());
                curso.setAlunos(alunos);
            }
            em.persist(curso);
            if (alunos != null) {
                Curso oldIdCursoOfAlunos = alunos.getIdCurso();
                if (oldIdCursoOfAlunos != null) {
                    oldIdCursoOfAlunos.setAlunos(null);
                    oldIdCursoOfAlunos = em.merge(oldIdCursoOfAlunos);
                }
                alunos.setIdCurso(curso);
                alunos = em.merge(alunos);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Curso curso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Curso persistentCurso = em.find(Curso.class, curso.getId());
            Aluno alunosOld = persistentCurso.getAlunos();
            Aluno alunosNew = curso.getAlunos();
            if (alunosNew != null) {
                alunosNew = em.getReference(alunosNew.getClass(), alunosNew.getId());
                curso.setAlunos(alunosNew);
            }
            curso = em.merge(curso);
            if (alunosOld != null && !alunosOld.equals(alunosNew)) {
                alunosOld.setIdCurso(null);
                alunosOld = em.merge(alunosOld);
            }
            if (alunosNew != null && !alunosNew.equals(alunosOld)) {
                Curso oldIdCursoOfAlunos = alunosNew.getIdCurso();
                if (oldIdCursoOfAlunos != null) {
                    oldIdCursoOfAlunos.setAlunos(null);
                    oldIdCursoOfAlunos = em.merge(oldIdCursoOfAlunos);
                }
                alunosNew.setIdCurso(curso);
                alunosNew = em.merge(alunosNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = curso.getId();
                if (findCurso(id) == null) {
                    throw new NonexistentEntityException("The curso with id " + id + " no longer exists.");
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
            Curso curso;
            try {
                curso = em.getReference(Curso.class, id);
                curso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The curso with id " + id + " no longer exists.", enfe);
            }
            Aluno alunos = curso.getAlunos();
            if (alunos != null) {
                alunos.setIdCurso(null);
                alunos = em.merge(alunos);
            }
            em.remove(curso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Curso> findCursoEntities() {
        return findCursoEntities(true, -1, -1);
    }

    public List<Curso> findCursoEntities(int maxResults, int firstResult) {
        return findCursoEntities(false, maxResults, firstResult);
    }

    private List<Curso> findCursoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Curso.class));
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

    public Curso findCurso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Curso.class, id);
        } finally {
            em.close();
        }
    }

    public int getCursoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Curso> rt = cq.from(Curso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
