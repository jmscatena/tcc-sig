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
import model.Tipotcc;

/**
 *
 * @author jscatena
 */
public class TipotccDAO implements Serializable {

    public TipotccDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipotcc tipotcc) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tcc tcc = tipotcc.getTcc();
            if (tcc != null) {
                tcc = em.getReference(tcc.getClass(), tcc.getId());
                tipotcc.setTcc(tcc);
            }
            em.persist(tipotcc);
            if (tcc != null) {
                Tipotcc oldIdTipotccOfTcc = tcc.getIdTipotcc();
                if (oldIdTipotccOfTcc != null) {
                    oldIdTipotccOfTcc.setTcc(null);
                    oldIdTipotccOfTcc = em.merge(oldIdTipotccOfTcc);
                }
                tcc.setIdTipotcc(tipotcc);
                tcc = em.merge(tcc);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipotcc tipotcc) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipotcc persistentTipotcc = em.find(Tipotcc.class, tipotcc.getId());
            Tcc tccOld = persistentTipotcc.getTcc();
            Tcc tccNew = tipotcc.getTcc();
            if (tccNew != null) {
                tccNew = em.getReference(tccNew.getClass(), tccNew.getId());
                tipotcc.setTcc(tccNew);
            }
            tipotcc = em.merge(tipotcc);
            if (tccOld != null && !tccOld.equals(tccNew)) {
                tccOld.setIdTipotcc(null);
                tccOld = em.merge(tccOld);
            }
            if (tccNew != null && !tccNew.equals(tccOld)) {
                Tipotcc oldIdTipotccOfTcc = tccNew.getIdTipotcc();
                if (oldIdTipotccOfTcc != null) {
                    oldIdTipotccOfTcc.setTcc(null);
                    oldIdTipotccOfTcc = em.merge(oldIdTipotccOfTcc);
                }
                tccNew.setIdTipotcc(tipotcc);
                tccNew = em.merge(tccNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipotcc.getId();
                if (findTipotcc(id) == null) {
                    throw new NonexistentEntityException("The tipotcc with id " + id + " no longer exists.");
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
            Tipotcc tipotcc;
            try {
                tipotcc = em.getReference(Tipotcc.class, id);
                tipotcc.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipotcc with id " + id + " no longer exists.", enfe);
            }
            Tcc tcc = tipotcc.getTcc();
            if (tcc != null) {
                tcc.setIdTipotcc(null);
                tcc = em.merge(tcc);
            }
            em.remove(tipotcc);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipotcc> findTipotccEntities() {
        return findTipotccEntities(true, -1, -1);
    }

    public List<Tipotcc> findTipotccEntities(int maxResults, int firstResult) {
        return findTipotccEntities(false, maxResults, firstResult);
    }

    private List<Tipotcc> findTipotccEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipotcc.class));
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

    public Tipotcc findTipotcc(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipotcc.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipotccCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipotcc> rt = cq.from(Tipotcc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
