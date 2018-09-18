/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Acesso;
import model.Tcc;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Professores;

/**
 *
 * @author jscatena
 */
public class ProfessorDAO implements Serializable {

    public ProfessorDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Professores professores) {
        if (professores.getTccList() == null) {
            professores.setTccList(new ArrayList<Tcc>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Acesso idAcesso = professores.getIdAcesso();
            if (idAcesso != null) {
                idAcesso = em.getReference(idAcesso.getClass(), idAcesso.getId());
                professores.setIdAcesso(idAcesso);
            }
            List<Tcc> attachedTccList = new ArrayList<Tcc>();
            for (Tcc tccListTccToAttach : professores.getTccList()) {
                tccListTccToAttach = em.getReference(tccListTccToAttach.getClass(), tccListTccToAttach.getId());
                attachedTccList.add(tccListTccToAttach);
            }
            professores.setTccList(attachedTccList);
            em.persist(professores);
            if (idAcesso != null) {
                Professores oldProfessoresOfIdAcesso = idAcesso.getProfessores();
                if (oldProfessoresOfIdAcesso != null) {
                    oldProfessoresOfIdAcesso.setIdAcesso(null);
                    oldProfessoresOfIdAcesso = em.merge(oldProfessoresOfIdAcesso);
                }
                idAcesso.setProfessores(professores);
                idAcesso = em.merge(idAcesso);
            }
            for (Tcc tccListTcc : professores.getTccList()) {
                Professores oldIdProfessoresOfTccListTcc = tccListTcc.getIdProfessores();
                tccListTcc.setIdProfessores(professores);
                tccListTcc = em.merge(tccListTcc);
                if (oldIdProfessoresOfTccListTcc != null) {
                    oldIdProfessoresOfTccListTcc.getTccList().remove(tccListTcc);
                    oldIdProfessoresOfTccListTcc = em.merge(oldIdProfessoresOfTccListTcc);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Professores professores) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Professores persistentProfessores = em.find(Professores.class, professores.getId());
            Acesso idAcessoOld = persistentProfessores.getIdAcesso();
            Acesso idAcessoNew = professores.getIdAcesso();
            List<Tcc> tccListOld = persistentProfessores.getTccList();
            List<Tcc> tccListNew = professores.getTccList();
            if (idAcessoNew != null) {
                idAcessoNew = em.getReference(idAcessoNew.getClass(), idAcessoNew.getId());
                professores.setIdAcesso(idAcessoNew);
            }
            List<Tcc> attachedTccListNew = new ArrayList<Tcc>();
            for (Tcc tccListNewTccToAttach : tccListNew) {
                tccListNewTccToAttach = em.getReference(tccListNewTccToAttach.getClass(), tccListNewTccToAttach.getId());
                attachedTccListNew.add(tccListNewTccToAttach);
            }
            tccListNew = attachedTccListNew;
            professores.setTccList(tccListNew);
            professores = em.merge(professores);
            if (idAcessoOld != null && !idAcessoOld.equals(idAcessoNew)) {
                idAcessoOld.setProfessores(null);
                idAcessoOld = em.merge(idAcessoOld);
            }
            if (idAcessoNew != null && !idAcessoNew.equals(idAcessoOld)) {
                Professores oldProfessoresOfIdAcesso = idAcessoNew.getProfessores();
                if (oldProfessoresOfIdAcesso != null) {
                    oldProfessoresOfIdAcesso.setIdAcesso(null);
                    oldProfessoresOfIdAcesso = em.merge(oldProfessoresOfIdAcesso);
                }
                idAcessoNew.setProfessores(professores);
                idAcessoNew = em.merge(idAcessoNew);
            }
            for (Tcc tccListOldTcc : tccListOld) {
                if (!tccListNew.contains(tccListOldTcc)) {
                    tccListOldTcc.setIdProfessores(null);
                    tccListOldTcc = em.merge(tccListOldTcc);
                }
            }
            for (Tcc tccListNewTcc : tccListNew) {
                if (!tccListOld.contains(tccListNewTcc)) {
                    Professores oldIdProfessoresOfTccListNewTcc = tccListNewTcc.getIdProfessores();
                    tccListNewTcc.setIdProfessores(professores);
                    tccListNewTcc = em.merge(tccListNewTcc);
                    if (oldIdProfessoresOfTccListNewTcc != null && !oldIdProfessoresOfTccListNewTcc.equals(professores)) {
                        oldIdProfessoresOfTccListNewTcc.getTccList().remove(tccListNewTcc);
                        oldIdProfessoresOfTccListNewTcc = em.merge(oldIdProfessoresOfTccListNewTcc);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = professores.getId();
                if (findProfessores(id) == null) {
                    throw new NonexistentEntityException("The professores with id " + id + " no longer exists.");
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
            Professores professores;
            try {
                professores = em.getReference(Professores.class, id);
                professores.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The professores with id " + id + " no longer exists.", enfe);
            }
            Acesso idAcesso = professores.getIdAcesso();
            if (idAcesso != null) {
                idAcesso.setProfessores(null);
                idAcesso = em.merge(idAcesso);
            }
            List<Tcc> tccList = professores.getTccList();
            for (Tcc tccListTcc : tccList) {
                tccListTcc.setIdProfessores(null);
                tccListTcc = em.merge(tccListTcc);
            }
            em.remove(professores);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Professores> findProfessoresEntities() {
        return findProfessoresEntities(true, -1, -1);
    }

    public List<Professores> findProfessoresEntities(int maxResults, int firstResult) {
        return findProfessoresEntities(false, maxResults, firstResult);
    }

    private List<Professores> findProfessoresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Professores.class));
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

    public Professores findProfessores(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Professores.class, id);
        } finally {
            em.close();
        }
    }

    public int getProfessoresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Professores> rt = cq.from(Professores.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
