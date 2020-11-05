
package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.Tipomaquinaria;
import com.entities.Proyecto;
import com.entities.Detalleproyectomaquina;
import com.entities.Maquinaria;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Nombre del controller: MaquinariaJpaController
 * Fecha: 04/11/2020 
 * CopyRigth: Pedro Campos
 * Modificacion: 04/11/2020
 * Version: 1.0
 * @author pedro
 */
public class MaquinariaJpaController implements Serializable {

    public MaquinariaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public MaquinariaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("SistemaConstructoraSVPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Maquinaria maquinaria) {
        if (maquinaria.getDetalleproyectomaquinaList() == null) {
            maquinaria.setDetalleproyectomaquinaList(new ArrayList<Detalleproyectomaquina>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipomaquinaria idTipo = maquinaria.getIdTipo();
            if (idTipo != null) {
                idTipo = em.getReference(idTipo.getClass(), idTipo.getIdTipo());
                maquinaria.setIdTipo(idTipo);
            }
            Proyecto idProyecto = maquinaria.getIdProyecto();
            if (idProyecto != null) {
                idProyecto = em.getReference(idProyecto.getClass(), idProyecto.getIdProyecto());
                maquinaria.setIdProyecto(idProyecto);
            }
            List<Detalleproyectomaquina> attachedDetalleproyectomaquinaList = new ArrayList<Detalleproyectomaquina>();
            for (Detalleproyectomaquina detalleproyectomaquinaListDetalleproyectomaquinaToAttach : maquinaria.getDetalleproyectomaquinaList()) {
                detalleproyectomaquinaListDetalleproyectomaquinaToAttach = em.getReference(detalleproyectomaquinaListDetalleproyectomaquinaToAttach.getClass(), detalleproyectomaquinaListDetalleproyectomaquinaToAttach.getIdDetalle());
                attachedDetalleproyectomaquinaList.add(detalleproyectomaquinaListDetalleproyectomaquinaToAttach);
            }
            maquinaria.setDetalleproyectomaquinaList(attachedDetalleproyectomaquinaList);
            em.persist(maquinaria);
            if (idTipo != null) {
                idTipo.getMaquinariaList().add(maquinaria);
                idTipo = em.merge(idTipo);
            }
            if (idProyecto != null) {
                idProyecto.getMaquinariaList().add(maquinaria);
                idProyecto = em.merge(idProyecto);
            }
            for (Detalleproyectomaquina detalleproyectomaquinaListDetalleproyectomaquina : maquinaria.getDetalleproyectomaquinaList()) {
                Maquinaria oldIdMaquinariaOfDetalleproyectomaquinaListDetalleproyectomaquina = detalleproyectomaquinaListDetalleproyectomaquina.getIdMaquinaria();
                detalleproyectomaquinaListDetalleproyectomaquina.setIdMaquinaria(maquinaria);
                detalleproyectomaquinaListDetalleproyectomaquina = em.merge(detalleproyectomaquinaListDetalleproyectomaquina);
                if (oldIdMaquinariaOfDetalleproyectomaquinaListDetalleproyectomaquina != null) {
                    oldIdMaquinariaOfDetalleproyectomaquinaListDetalleproyectomaquina.getDetalleproyectomaquinaList().remove(detalleproyectomaquinaListDetalleproyectomaquina);
                    oldIdMaquinariaOfDetalleproyectomaquinaListDetalleproyectomaquina = em.merge(oldIdMaquinariaOfDetalleproyectomaquinaListDetalleproyectomaquina);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Maquinaria maquinaria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Maquinaria persistentMaquinaria = em.find(Maquinaria.class, maquinaria.getIdMaquinaria());
            Tipomaquinaria idTipoOld = persistentMaquinaria.getIdTipo();
            Tipomaquinaria idTipoNew = maquinaria.getIdTipo();
            Proyecto idProyectoOld = persistentMaquinaria.getIdProyecto();
            Proyecto idProyectoNew = maquinaria.getIdProyecto();
            List<Detalleproyectomaquina> detalleproyectomaquinaListOld = persistentMaquinaria.getDetalleproyectomaquinaList();
            List<Detalleproyectomaquina> detalleproyectomaquinaListNew = maquinaria.getDetalleproyectomaquinaList();
            if (idTipoNew != null) {
                idTipoNew = em.getReference(idTipoNew.getClass(), idTipoNew.getIdTipo());
                maquinaria.setIdTipo(idTipoNew);
            }
            if (idProyectoNew != null) {
                idProyectoNew = em.getReference(idProyectoNew.getClass(), idProyectoNew.getIdProyecto());
                maquinaria.setIdProyecto(idProyectoNew);
            }
            List<Detalleproyectomaquina> attachedDetalleproyectomaquinaListNew = new ArrayList<Detalleproyectomaquina>();
            for (Detalleproyectomaquina detalleproyectomaquinaListNewDetalleproyectomaquinaToAttach : detalleproyectomaquinaListNew) {
                detalleproyectomaquinaListNewDetalleproyectomaquinaToAttach = em.getReference(detalleproyectomaquinaListNewDetalleproyectomaquinaToAttach.getClass(), detalleproyectomaquinaListNewDetalleproyectomaquinaToAttach.getIdDetalle());
                attachedDetalleproyectomaquinaListNew.add(detalleproyectomaquinaListNewDetalleproyectomaquinaToAttach);
            }
            detalleproyectomaquinaListNew = attachedDetalleproyectomaquinaListNew;
            maquinaria.setDetalleproyectomaquinaList(detalleproyectomaquinaListNew);
            maquinaria = em.merge(maquinaria);
            if (idTipoOld != null && !idTipoOld.equals(idTipoNew)) {
                idTipoOld.getMaquinariaList().remove(maquinaria);
                idTipoOld = em.merge(idTipoOld);
            }
            if (idTipoNew != null && !idTipoNew.equals(idTipoOld)) {
                idTipoNew.getMaquinariaList().add(maquinaria);
                idTipoNew = em.merge(idTipoNew);
            }
            if (idProyectoOld != null && !idProyectoOld.equals(idProyectoNew)) {
                idProyectoOld.getMaquinariaList().remove(maquinaria);
                idProyectoOld = em.merge(idProyectoOld);
            }
            if (idProyectoNew != null && !idProyectoNew.equals(idProyectoOld)) {
                idProyectoNew.getMaquinariaList().add(maquinaria);
                idProyectoNew = em.merge(idProyectoNew);
            }
            for (Detalleproyectomaquina detalleproyectomaquinaListOldDetalleproyectomaquina : detalleproyectomaquinaListOld) {
                if (!detalleproyectomaquinaListNew.contains(detalleproyectomaquinaListOldDetalleproyectomaquina)) {
                    detalleproyectomaquinaListOldDetalleproyectomaquina.setIdMaquinaria(null);
                    detalleproyectomaquinaListOldDetalleproyectomaquina = em.merge(detalleproyectomaquinaListOldDetalleproyectomaquina);
                }
            }
            for (Detalleproyectomaquina detalleproyectomaquinaListNewDetalleproyectomaquina : detalleproyectomaquinaListNew) {
                if (!detalleproyectomaquinaListOld.contains(detalleproyectomaquinaListNewDetalleproyectomaquina)) {
                    Maquinaria oldIdMaquinariaOfDetalleproyectomaquinaListNewDetalleproyectomaquina = detalleproyectomaquinaListNewDetalleproyectomaquina.getIdMaquinaria();
                    detalleproyectomaquinaListNewDetalleproyectomaquina.setIdMaquinaria(maquinaria);
                    detalleproyectomaquinaListNewDetalleproyectomaquina = em.merge(detalleproyectomaquinaListNewDetalleproyectomaquina);
                    if (oldIdMaquinariaOfDetalleproyectomaquinaListNewDetalleproyectomaquina != null && !oldIdMaquinariaOfDetalleproyectomaquinaListNewDetalleproyectomaquina.equals(maquinaria)) {
                        oldIdMaquinariaOfDetalleproyectomaquinaListNewDetalleproyectomaquina.getDetalleproyectomaquinaList().remove(detalleproyectomaquinaListNewDetalleproyectomaquina);
                        oldIdMaquinariaOfDetalleproyectomaquinaListNewDetalleproyectomaquina = em.merge(oldIdMaquinariaOfDetalleproyectomaquinaListNewDetalleproyectomaquina);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = maquinaria.getIdMaquinaria();
                if (findMaquinaria(id) == null) {
                    throw new NonexistentEntityException("The maquinaria with id " + id + " no longer exists.");
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
            Maquinaria maquinaria;
            try {
                maquinaria = em.getReference(Maquinaria.class, id);
                maquinaria.getIdMaquinaria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The maquinaria with id " + id + " no longer exists.", enfe);
            }
            Tipomaquinaria idTipo = maquinaria.getIdTipo();
            if (idTipo != null) {
                idTipo.getMaquinariaList().remove(maquinaria);
                idTipo = em.merge(idTipo);
            }
            Proyecto idProyecto = maquinaria.getIdProyecto();
            if (idProyecto != null) {
                idProyecto.getMaquinariaList().remove(maquinaria);
                idProyecto = em.merge(idProyecto);
            }
            List<Detalleproyectomaquina> detalleproyectomaquinaList = maquinaria.getDetalleproyectomaquinaList();
            for (Detalleproyectomaquina detalleproyectomaquinaListDetalleproyectomaquina : detalleproyectomaquinaList) {
                detalleproyectomaquinaListDetalleproyectomaquina.setIdMaquinaria(null);
                detalleproyectomaquinaListDetalleproyectomaquina = em.merge(detalleproyectomaquinaListDetalleproyectomaquina);
            }
            em.remove(maquinaria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Maquinaria> findMaquinariaEntities() {
        return findMaquinariaEntities(true, -1, -1);
    }

    public List<Maquinaria> findMaquinariaEntities(int maxResults, int firstResult) {
        return findMaquinariaEntities(false, maxResults, firstResult);
    }

    private List<Maquinaria> findMaquinariaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Maquinaria.class));
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

    public Maquinaria findMaquinaria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Maquinaria.class, id);
        } finally {
            em.close();
        }
    }

    public int getMaquinariaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Maquinaria> rt = cq.from(Maquinaria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
