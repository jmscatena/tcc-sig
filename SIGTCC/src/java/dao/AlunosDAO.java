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
import model.Tcc;
import model.Acesso;
import model.Alunos;
import model.Curso;

/**
 *
 * @author jscatena
 */
public class AlunosDAO implements Serializable {

    public AlunosDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Alunos alunos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tcc tcc = alunos.getTcc();
            if (tcc != null) {
                tcc = em.getReference(tcc.getClass(), tcc.getId());
                alunos.setTcc(tcc);
            }
            Acesso idAcesso = alunos.getIdAcesso();
            if (idAcesso != null) {
                idAcesso = em.getReference(idAcesso.getClass(), idAcesso.getId());
                alunos.setIdAcesso(idAcesso);
            }
            Curso idCurso = alunos.getIdCurso();
            if (idCurso != null) {
                idCurso = em.getReference(idCurso.getClass(), idCurso.getId());
                alunos.setIdCurso(idCurso);
            }
            em.persist(alunos);
            if (tcc != null) {
                Alunos oldIdAlunosOfTcc = tcc.getIdAlunos();
                if (oldIdAlunosOfTcc != null) {
                    oldIdAlunosOfTcc.setTcc(null);
                    oldIdAlunosOfTcc = em.merge(oldIdAlunosOfTcc);
                }
                tcc.setIdAlunos(alunos);
                tcc = em.merge(tcc);
            }
            if (idAcesso != null) {
                Alunos oldAlunosOfIdAcesso = idAcesso.getAlunos();
                if (oldAlunosOfIdAcesso != null) {
                    oldAlunosOfIdAcesso.setIdAcesso(null);
                    oldAlunosOfIdAcesso = em.merge(oldAlunosOfIdAcesso);
                }
                idAcesso.setAlunos(alunos);
                idAcesso = em.merge(idAcesso);
            }
            if (idCurso != null) {
                Alunos oldAlunosOfIdCurso = idCurso.getAlunos();
                if (oldAlunosOfIdCurso != null) {
                    oldAlunosOfIdCurso.setIdCurso(null);
                    oldAlunosOfIdCurso = em.merge(oldAlunosOfIdCurso);
                }
                idCurso.setAlunos(alunos);
                idCurso = em.merge(idCurso);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Alunos alunos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alunos persistentAlunos = em.find(Alunos.class, alunos.getId());
            Tcc tccOld = persistentAlunos.getTcc();
            Tcc tccNew = alunos.getTcc();
            Acesso idAcessoOld = persistentAlunos.getIdAcesso();
            Acesso idAcessoNew = alunos.getIdAcesso();
            Curso idCursoOld = persistentAlunos.getIdCurso();
            Curso idCursoNew = alunos.getIdCurso();
            if (tccNew != null) {
                tccNew = em.getReference(tccNew.getClass(), tccNew.getId());
                alunos.setTcc(tccNew);
            }
            if (idAcessoNew != null) {
                idAcessoNew = em.getReference(idAcessoNew.getClass(), idAcessoNew.getId());
                alunos.setIdAcesso(idAcessoNew);
            }
            if (idCursoNew != null) {
                idCursoNew = em.getReference(idCursoNew.getClass(), idCursoNew.getId());
                alunos.setIdCurso(idCursoNew);
            }
            alunos = em.merge(alunos);
            if (tccOld != null && !tccOld.equals(tccNew)) {
                tccOld.setIdAlunos(null);
                tccOld = em.merge(tccOld);
            }
            if (tccNew != null && !tccNew.equals(tccOld)) {
                Alunos oldIdAlunosOfTcc = tccNew.getIdAlunos();
                if (oldIdAlunosOfTcc != null) {
                    oldIdAlunosOfTcc.setTcc(null);
                    oldIdAlunosOfTcc = em.merge(oldIdAlunosOfTcc);
                }
                tccNew.setIdAlunos(alunos);
                tccNew = em.merge(tccNew);
            }
            if (idAcessoOld != null && !idAcessoOld.equals(idAcessoNew)) {
                idAcessoOld.setAlunos(null);
                idAcessoOld = em.merge(idAcessoOld);
            }
            if (idAcessoNew != null && !idAcessoNew.equals(idAcessoOld)) {
                Alunos oldAlunosOfIdAcesso = idAcessoNew.getAlunos();
                if (oldAlunosOfIdAcesso != null) {
                    oldAlunosOfIdAcesso.setIdAcesso(null);
                    oldAlunosOfIdAcesso = em.merge(oldAlunosOfIdAcesso);
                }
                idAcessoNew.setAlunos(alunos);
                idAcessoNew = em.merge(idAcessoNew);
            }
            if (idCursoOld != null && !idCursoOld.equals(idCursoNew)) {
                idCursoOld.setAlunos(null);
                idCursoOld = em.merge(idCursoOld);
            }
            if (idCursoNew != null && !idCursoNew.equals(idCursoOld)) {
                Alunos oldAlunosOfIdCurso = idCursoNew.getAlunos();
                if (oldAlunosOfIdCurso != null) {
                    oldAlunosOfIdCurso.setIdCurso(null);
                    oldAlunosOfIdCurso = em.merge(oldAlunosOfIdCurso);
                }
                idCursoNew.setAlunos(alunos);
                idCursoNew = em.merge(idCursoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = alunos.getId();
                if (findAlunos(id) == null) {
                    throw new NonexistentEntityException("The alunos with id " + id + " no longer exists.");
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
            Alunos alunos;
            try {
                alunos = em.getReference(Alunos.class, id);
                alunos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The alunos with id " + id + " no longer exists.", enfe);
            }
            Tcc tcc = alunos.getTcc();
            if (tcc != null) {
                tcc.setIdAlunos(null);
                tcc = em.merge(tcc);
            }
            Acesso idAcesso = alunos.getIdAcesso();
            if (idAcesso != null) {
                idAcesso.setAlunos(null);
                idAcesso = em.merge(idAcesso);
            }
            Curso idCurso = alunos.getIdCurso();
            if (idCurso != null) {
                idCurso.setAlunos(null);
                idCurso = em.merge(idCurso);
            }
            em.remove(alunos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Alunos> findAlunosEntities() {
        return findAlunosEntities(true, -1, -1);
    }

    public List<Alunos> findAlunosEntities(int maxResults, int firstResult) {
        return findAlunosEntities(false, maxResults, firstResult);
    }

    private List<Alunos> findAlunosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Alunos.class));
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

    public Alunos findAlunos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Alunos.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlunosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Alunos> rt = cq.from(Alunos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
