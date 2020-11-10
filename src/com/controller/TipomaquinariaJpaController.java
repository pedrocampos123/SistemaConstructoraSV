
package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.Maquinaria;
import com.entities.Tipomaquinaria;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Nombre del controller: TipomaquinariaJpaController
 * Fecha: 04/11/2020 
 * CopyRigth: Pedro Campos
 * Modificacion: 04/11/2020
 * Version: 1.0
 * @author pedro
 */
public class TipomaquinariaJpaController implements Serializable {

    public TipomaquinariaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public TipomaquinariaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("SistemaConstructoraSVPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipomaquinaria tipomaquinaria) {
        if (tipomaquinaria.getMaquinariaList() == null) {
            tipomaquinaria.setMaquinariaList(new ArrayList<Maquinaria>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Maquinaria> attachedMaquinariaList = new ArrayList<Maquinaria>();
            for (Maquinaria maquinariaListMaquinariaToAttach : tipomaquinaria.getMaquinariaList()) {
                maquinariaListMaquinariaToAttach = em.getReference(maquinariaListMaquinariaToAttach.getClass(), maquinariaListMaquinariaToAttach.getIdMaquinaria());
                attachedMaquinariaList.add(maquinariaListMaquinariaToAttach);
            }
            tipomaquinaria.setMaquinariaList(attachedMaquinariaList);
            em.persist(tipomaquinaria);
            for (Maquinaria maquinariaListMaquinaria : tipomaquinaria.getMaquinariaList()) {
                Tipomaquinaria oldIdTipoOfMaquinariaListMaquinaria = maquinariaListMaquinaria.getIdTipo();
                maquinariaListMaquinaria.setIdTipo(tipomaquinaria);
                maquinariaListMaquinaria = em.merge(maquinariaListMaquinaria);
                if (oldIdTipoOfMaquinariaListMaquinaria != null) {
                    oldIdTipoOfMaquinariaListMaquinaria.getMaquinariaList().remove(maquinariaListMaquinaria);
                    oldIdTipoOfMaquinariaListMaquinaria = em.merge(oldIdTipoOfMaquinariaListMaquinaria);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipomaquinaria tipomaquinaria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipomaquinaria persistentTipomaquinaria = em.find(Tipomaquinaria.class, tipomaquinaria.getIdTipo());
            List<Maquinaria> maquinariaListOld = persistentTipomaquinaria.getMaquinariaList();
            List<Maquinaria> maquinariaListNew = tipomaquinaria.getMaquinariaList();
            List<Maquinaria> attachedMaquinariaListNew = new ArrayList<Maquinaria>();
            for (Maquinaria maquinariaListNewMaquinariaToAttach : maquinariaListNew) {
                maquinariaListNewMaquinariaToAttach = em.getReference(maquinariaListNewMaquinariaToAttach.getClass(), maquinariaListNewMaquinariaToAttach.getIdMaquinaria());
                attachedMaquinariaListNew.add(maquinariaListNewMaquinariaToAttach);
            }
            maquinariaListNew = attachedMaquinariaListNew;
            tipomaquinaria.setMaquinariaList(maquinariaListNew);
            tipomaquinaria = em.merge(tipomaquinaria);
            for (Maquinaria maquinariaListOldMaquinaria : maquinariaListOld) {
                if (!maquinariaListNew.contains(maquinariaListOldMaquinaria)) {
                    maquinariaListOldMaquinaria.setIdTipo(null);
                    maquinariaListOldMaquinaria = em.merge(maquinariaListOldMaquinaria);
                }
            }
            for (Maquinaria maquinariaListNewMaquinaria : maquinariaListNew) {
                if (!maquinariaListOld.contains(maquinariaListNewMaquinaria)) {
                    Tipomaquinaria oldIdTipoOfMaquinariaListNewMaquinaria = maquinariaListNewMaquinaria.getIdTipo();
                    maquinariaListNewMaquinaria.setIdTipo(tipomaquinaria);
                    maquinariaListNewMaquinaria = em.merge(maquinariaListNewMaquinaria);
                    if (oldIdTipoOfMaquinariaListNewMaquinaria != null && !oldIdTipoOfMaquinariaListNewMaquinaria.equals(tipomaquinaria)) {
                        oldIdTipoOfMaquinariaListNewMaquinaria.getMaquinariaList().remove(maquinariaListNewMaquinaria);
                        oldIdTipoOfMaquinariaListNewMaquinaria = em.merge(oldIdTipoOfMaquinariaListNewMaquinaria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipomaquinaria.getIdTipo();
                if (findTipomaquinaria(id) == null) {
                    throw new NonexistentEntityException("The tipomaquinaria with id " + id + " no longer exists.");
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
            Tipomaquinaria tipomaquinaria;
            try {
                tipomaquinaria = em.getReference(Tipomaquinaria.class, id);
                tipomaquinaria.getIdTipo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipomaquinaria with id " + id + " no longer exists.", enfe);
            }
            List<Maquinaria> maquinariaList = tipomaquinaria.getMaquinariaList();
            for (Maquinaria maquinariaListMaquinaria : maquinariaList) {
                maquinariaListMaquinaria.setIdTipo(null);
                maquinariaListMaquinaria = em.merge(maquinariaListMaquinaria);
            }
            em.remove(tipomaquinaria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipomaquinaria> findTipomaquinariaEntities() {
        return findTipomaquinariaEntities(true, -1, -1);
    }

    public List<Tipomaquinaria> findTipomaquinariaEntities(int maxResults, int firstResult) {
        return findTipomaquinariaEntities(false, maxResults, firstResult);
    }

    private List<Tipomaquinaria> findTipomaquinariaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipomaquinaria.class));
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

    public Tipomaquinaria findTipomaquinaria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipomaquinaria.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipomaquinariaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipomaquinaria> rt = cq.from(Tipomaquinaria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Tipomaquinaria> getTipoMaquinariaSeleccionada(int tipo) {
        List<Tipomaquinaria> tipos = null;
        try {
            EntityManager em = getEntityManager();
            Query query = em.createQuery("SELECT t FROM Tipomaquinaria t WHERE t.idTipo = :tipo");
            query.setParameter("tipo", tipo);
            tipos = query.getResultList();
        } catch (Exception e) {
        }
        return tipos;
    }
    
}
