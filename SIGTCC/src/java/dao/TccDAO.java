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
import model.Alunos;
import model.Professores;
import model.Tipotcc;
import model.Acompanhamento;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import model.Tcc;

/**
 *
 * @author jscatena
 */
public class TccDAO implements Serializable {

    public TccDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tcc tcc) {
        if (tcc.getAcompanhamentoList() == null) {
            tcc.setAcompanhamentoList(new ArrayList<Acompanhamento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alunos idAlunos = tcc.getIdAlunos();
            if (idAlunos != null) {
                idAlunos = em.getReference(idAlunos.getClass(), idAlunos.getId());
                tcc.setIdAlunos(idAlunos);
            }
            Professores idProfessores = tcc.getIdProfessores();
            if (idProfessores != null) {
                idProfessores = em.getReference(idProfessores.getClass(), idProfessores.getId());
                tcc.setIdProfessores(idProfessores);
            }
            Tipotcc idTipotcc = tcc.getIdTipotcc();
            if (idTipotcc != null) {
                idTipotcc = em.getReference(idTipotcc.getClass(), idTipotcc.getId());
                tcc.setIdTipotcc(idTipotcc);
            }
            List<Acompanhamento> attachedAcompanhamentoList = new ArrayList<Acompanhamento>();
            for (Acompanhamento acompanhamentoListAcompanhamentoToAttach : tcc.getAcompanhamentoList()) {
                acompanhamentoListAcompanhamentoToAttach = em.getReference(acompanhamentoListAcompanhamentoToAttach.getClass(), acompanhamentoListAcompanhamentoToAttach.getId());
                attachedAcompanhamentoList.add(acompanhamentoListAcompanhamentoToAttach);
            }
            tcc.setAcompanhamentoList(attachedAcompanhamentoList);
            em.persist(tcc);
            if (idAlunos != null) {
                Tcc oldTccOfIdAlunos = idAlunos.getTcc();
                if (oldTccOfIdAlunos != null) {
                    oldTccOfIdAlunos.setIdAlunos(null);
                    oldTccOfIdAlunos = em.merge(oldTccOfIdAlunos);
                }
                idAlunos.setTcc(tcc);
                idAlunos = em.merge(idAlunos);
            }
            if (idProfessores != null) {
                idProfessores.getTccList().add(tcc);
                idProfessores = em.merge(idProfessores);
            }
            if (idTipotcc != null) {
                Tcc oldTccOfIdTipotcc = idTipotcc.getTcc();
                if (oldTccOfIdTipotcc != null) {
                    oldTccOfIdTipotcc.setIdTipotcc(null);
                    oldTccOfIdTipotcc = em.merge(oldTccOfIdTipotcc);
                }
                idTipotcc.setTcc(tcc);
                idTipotcc = em.merge(idTipotcc);
            }
            for (Acompanhamento acompanhamentoListAcompanhamento : tcc.getAcompanhamentoList()) {
                Tcc oldIdTccOfAcompanhamentoListAcompanhamento = acompanhamentoListAcompanhamento.getIdTcc();
                acompanhamentoListAcompanhamento.setIdTcc(tcc);
                acompanhamentoListAcompanhamento = em.merge(acompanhamentoListAcompanhamento);
                if (oldIdTccOfAcompanhamentoListAcompanhamento != null) {
                    oldIdTccOfAcompanhamentoListAcompanhamento.getAcompanhamentoList().remove(acompanhamentoListAcompanhamento);
                    oldIdTccOfAcompanhamentoListAcompanhamento = em.merge(oldIdTccOfAcompanhamentoListAcompanhamento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tcc tcc) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tcc persistentTcc = em.find(Tcc.class, tcc.getId());
            Alunos idAlunosOld = persistentTcc.getIdAlunos();
            Alunos idAlunosNew = tcc.getIdAlunos();
            Professores idProfessoresOld = persistentTcc.getIdProfessores();
            Professores idProfessoresNew = tcc.getIdProfessores();
            Tipotcc idTipotccOld = persistentTcc.getIdTipotcc();
            Tipotcc idTipotccNew = tcc.getIdTipotcc();
            List<Acompanhamento> acompanhamentoListOld = persistentTcc.getAcompanhamentoList();
            List<Acompanhamento> acompanhamentoListNew = tcc.getAcompanhamentoList();
            if (idAlunosNew != null) {
                idAlunosNew = em.getReference(idAlunosNew.getClass(), idAlunosNew.getId());
                tcc.setIdAlunos(idAlunosNew);
            }
            if (idProfessoresNew != null) {
                idProfessoresNew = em.getReference(idProfessoresNew.getClass(), idProfessoresNew.getId());
                tcc.setIdProfessores(idProfessoresNew);
            }
            if (idTipotccNew != null) {
                idTipotccNew = em.getReference(idTipotccNew.getClass(), idTipotccNew.getId());
                tcc.setIdTipotcc(idTipotccNew);
            }
            List<Acompanhamento> attachedAcompanhamentoListNew = new ArrayList<Acompanhamento>();
            for (Acompanhamento acompanhamentoListNewAcompanhamentoToAttach : acompanhamentoListNew) {
                acompanhamentoListNewAcompanhamentoToAttach = em.getReference(acompanhamentoListNewAcompanhamentoToAttach.getClass(), acompanhamentoListNewAcompanhamentoToAttach.getId());
                attachedAcompanhamentoListNew.add(acompanhamentoListNewAcompanhamentoToAttach);
            }
            acompanhamentoListNew = attachedAcompanhamentoListNew;
            tcc.setAcompanhamentoList(acompanhamentoListNew);
            tcc = em.merge(tcc);
            if (idAlunosOld != null && !idAlunosOld.equals(idAlunosNew)) {
                idAlunosOld.setTcc(null);
                idAlunosOld = em.merge(idAlunosOld);
            }
            if (idAlunosNew != null && !idAlunosNew.equals(idAlunosOld)) {
                Tcc oldTccOfIdAlunos = idAlunosNew.getTcc();
                if (oldTccOfIdAlunos != null) {
                    oldTccOfIdAlunos.setIdAlunos(null);
                    oldTccOfIdAlunos = em.merge(oldTccOfIdAlunos);
                }
                idAlunosNew.setTcc(tcc);
                idAlunosNew = em.merge(idAlunosNew);
            }
            if (idProfessoresOld != null && !idProfessoresOld.equals(idProfessoresNew)) {
                idProfessoresOld.getTccList().remove(tcc);
                idProfessoresOld = em.merge(idProfessoresOld);
            }
            if (idProfessoresNew != null && !idProfessoresNew.equals(idProfessoresOld)) {
                idProfessoresNew.getTccList().add(tcc);
                idProfessoresNew = em.merge(idProfessoresNew);
            }
            if (idTipotccOld != null && !idTipotccOld.equals(idTipotccNew)) {
                idTipotccOld.setTcc(null);
                idTipotccOld = em.merge(idTipotccOld);
            }
            if (idTipotccNew != null && !idTipotccNew.equals(idTipotccOld)) {
                Tcc oldTccOfIdTipotcc = idTipotccNew.getTcc();
                if (oldTccOfIdTipotcc != null) {
                    oldTccOfIdTipotcc.setIdTipotcc(null);
                    oldTccOfIdTipotcc = em.merge(oldTccOfIdTipotcc);
                }
                idTipotccNew.setTcc(tcc);
                idTipotccNew = em.merge(idTipotccNew);
            }
            for (Acompanhamento acompanhamentoListOldAcompanhamento : acompanhamentoListOld) {
                if (!acompanhamentoListNew.contains(acompanhamentoListOldAcompanhamento)) {
                    acompanhamentoListOldAcompanhamento.setIdTcc(null);
                    acompanhamentoListOldAcompanhamento = em.merge(acompanhamentoListOldAcompanhamento);
                }
            }
            for (Acompanhamento acompanhamentoListNewAcompanhamento : acompanhamentoListNew) {
                if (!acompanhamentoListOld.contains(acompanhamentoListNewAcompanhamento)) {
                    Tcc oldIdTccOfAcompanhamentoListNewAcompanhamento = acompanhamentoListNewAcompanhamento.getIdTcc();
                    acompanhamentoListNewAcompanhamento.setIdTcc(tcc);
                    acompanhamentoListNewAcompanhamento = em.merge(acompanhamentoListNewAcompanhamento);
                    if (oldIdTccOfAcompanhamentoListNewAcompanhamento != null && !oldIdTccOfAcompanhamentoListNewAcompanhamento.equals(tcc)) {
                        oldIdTccOfAcompanhamentoListNewAcompanhamento.getAcompanhamentoList().remove(acompanhamentoListNewAcompanhamento);
                        oldIdTccOfAcompanhamentoListNewAcompanhamento = em.merge(oldIdTccOfAcompanhamentoListNewAcompanhamento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tcc.getId();
                if (findTcc(id) == null) {
                    throw new NonexistentEntityException("The tcc with id " + id + " no longer exists.");
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
            Tcc tcc;
            try {
                tcc = em.getReference(Tcc.class, id);
                tcc.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tcc with id " + id + " no longer exists.", enfe);
            }
            Alunos idAlunos = tcc.getIdAlunos();
            if (idAlunos != null) {
                idAlunos.setTcc(null);
                idAlunos = em.merge(idAlunos);
            }
            Professores idProfessores = tcc.getIdProfessores();
            if (idProfessores != null) {
                idProfessores.getTccList().remove(tcc);
                idProfessores = em.merge(idProfessores);
            }
            Tipotcc idTipotcc = tcc.getIdTipotcc();
            if (idTipotcc != null) {
                idTipotcc.setTcc(null);
                idTipotcc = em.merge(idTipotcc);
            }
            List<Acompanhamento> acompanhamentoList = tcc.getAcompanhamentoList();
            for (Acompanhamento acompanhamentoListAcompanhamento : acompanhamentoList) {
                acompanhamentoListAcompanhamento.setIdTcc(null);
                acompanhamentoListAcompanhamento = em.merge(acompanhamentoListAcompanhamento);
            }
            em.remove(tcc);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tcc> findTccEntities() {
        return findTccEntities(true, -1, -1);
    }

    public List<Tcc> findTccEntities(int maxResults, int firstResult) {
        return findTccEntities(false, maxResults, firstResult);
    }

    private List<Tcc> findTccEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tcc.class));
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

    public Tcc findTcc(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tcc.class, id);
        } finally {
            em.close();
        }
    }
    
    public Tcc findTcc(Alunos aluno){
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Tcc> query=em.createNamedQuery("Tcc.findByAluno", Tcc.class);
            em.setProperty("idaluno", aluno);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    
    public List<Tcc> getAceites(){
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Tcc> query=em.createNamedQuery("Tcc.findByAceite", Tcc.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public int getTccCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tcc> rt = cq.from(Tcc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
