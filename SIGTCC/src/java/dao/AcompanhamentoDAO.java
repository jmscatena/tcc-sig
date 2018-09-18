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
import model.Acompanhamento;
import model.Tcc;

/**
 *
 * @author jscatena
 */
public class AcompanhamentoDAO implements Serializable {

    public AcompanhamentoDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Acompanhamento acompanhamento) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tcc idTcc = acompanhamento.getIdTcc();
            if (idTcc != null) {
                idTcc = em.getReference(idTcc.getClass(), idTcc.getId());
                acompanhamento.setIdTcc(idTcc);
            }
            em.persist(acompanhamento);
            if (idTcc != null) {
                idTcc.getAcompanhamentoList().add(acompanhamento);
                idTcc = em.merge(idTcc);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Acompanhamento acompanhamento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Acompanhamento persistentAcompanhamento = em.find(Acompanhamento.class, acompanhamento.getId());
            Tcc idTccOld = persistentAcompanhamento.getIdTcc();
            Tcc idTccNew = acompanhamento.getIdTcc();
            if (idTccNew != null) {
                idTccNew = em.getReference(idTccNew.getClass(), idTccNew.getId());
                acompanhamento.setIdTcc(idTccNew);
            }
            acompanhamento = em.merge(acompanhamento);
            if (idTccOld != null && !idTccOld.equals(idTccNew)) {
                idTccOld.getAcompanhamentoList().remove(acompanhamento);
                idTccOld = em.merge(idTccOld);
            }
            if (idTccNew != null && !idTccNew.equals(idTccOld)) {
                idTccNew.getAcompanhamentoList().add(acompanhamento);
                idTccNew = em.merge(idTccNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = acompanhamento.getId();
                if (findAcompanhamento(id) == null) {
                    throw new NonexistentEntityException("The acompanhamento with id " + id + " no longer exists.");
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
            Acompanhamento acompanhamento;
            try {
                acompanhamento = em.getReference(Acompanhamento.class, id);
                acompanhamento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The acompanhamento with id " + id + " no longer exists.", enfe);
            }
            Tcc idTcc = acompanhamento.getIdTcc();
            if (idTcc != null) {
                idTcc.getAcompanhamentoList().remove(acompanhamento);
                idTcc = em.merge(idTcc);
            }
            em.remove(acompanhamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Acompanhamento> findAcompanhamentoEntities() {
        return findAcompanhamentoEntities(true, -1, -1);
    }

    public List<Acompanhamento> findAcompanhamentoEntities(int maxResults, int firstResult) {
        return findAcompanhamentoEntities(false, maxResults, firstResult);
    }

    private List<Acompanhamento> findAcompanhamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Acompanhamento.class));
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

    public Acompanhamento findAcompanhamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Acompanhamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getAcompanhamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Acompanhamento> rt = cq.from(Acompanhamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
