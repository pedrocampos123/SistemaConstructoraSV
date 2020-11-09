
package com.controller;

import com.controller.exceptions.NonexistentEntityException;
import com.entities.Empleado;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.Proyecto;
import com.entities.Pagoempleado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Nombre del controller: EmpleadoJpaController
 * Fecha: 04/11/2020 
 * CopyRigth: Pedro Campos
 * Modificacion: 04/11/2020
 * Version: 1.0
 * @author pedro
 */
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public EmpleadoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("SistemaConstructoraSVPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleado empleado) {
        if (empleado.getPagoempleadoList() == null) {
            empleado.setPagoempleadoList(new ArrayList<Pagoempleado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proyecto idProyecto = empleado.getIdProyecto();
            if (idProyecto != null) {
                idProyecto = em.getReference(idProyecto.getClass(), idProyecto.getIdProyecto());
                empleado.setIdProyecto(idProyecto);
            }
            List<Pagoempleado> attachedPagoempleadoList = new ArrayList<Pagoempleado>();
            for (Pagoempleado pagoempleadoListPagoempleadoToAttach : empleado.getPagoempleadoList()) {
                pagoempleadoListPagoempleadoToAttach = em.getReference(pagoempleadoListPagoempleadoToAttach.getClass(), pagoempleadoListPagoempleadoToAttach.getIdPago());
                attachedPagoempleadoList.add(pagoempleadoListPagoempleadoToAttach);
            }
            empleado.setPagoempleadoList(attachedPagoempleadoList);
            em.persist(empleado);
            if (idProyecto != null) {
                idProyecto.getEmpleadoList().add(empleado);
                idProyecto = em.merge(idProyecto);
            }
            for (Pagoempleado pagoempleadoListPagoempleado : empleado.getPagoempleadoList()) {
                Empleado oldIdEmpleadoOfPagoempleadoListPagoempleado = pagoempleadoListPagoempleado.getIdEmpleado();
                pagoempleadoListPagoempleado.setIdEmpleado(empleado);
                pagoempleadoListPagoempleado = em.merge(pagoempleadoListPagoempleado);
                if (oldIdEmpleadoOfPagoempleadoListPagoempleado != null) {
                    oldIdEmpleadoOfPagoempleadoListPagoempleado.getPagoempleadoList().remove(pagoempleadoListPagoempleado);
                    oldIdEmpleadoOfPagoempleadoListPagoempleado = em.merge(oldIdEmpleadoOfPagoempleadoListPagoempleado);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleado empleado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado persistentEmpleado = em.find(Empleado.class, empleado.getIdEmpleado());
            Proyecto idProyectoOld = persistentEmpleado.getIdProyecto();
            Proyecto idProyectoNew = empleado.getIdProyecto();
            List<Pagoempleado> pagoempleadoListOld = persistentEmpleado.getPagoempleadoList();
            List<Pagoempleado> pagoempleadoListNew = empleado.getPagoempleadoList();
            if (idProyectoNew != null) {
                idProyectoNew = em.getReference(idProyectoNew.getClass(), idProyectoNew.getIdProyecto());
                empleado.setIdProyecto(idProyectoNew);
            }
            List<Pagoempleado> attachedPagoempleadoListNew = new ArrayList<Pagoempleado>();
            for (Pagoempleado pagoempleadoListNewPagoempleadoToAttach : pagoempleadoListNew) {
                pagoempleadoListNewPagoempleadoToAttach = em.getReference(pagoempleadoListNewPagoempleadoToAttach.getClass(), pagoempleadoListNewPagoempleadoToAttach.getIdPago());
                attachedPagoempleadoListNew.add(pagoempleadoListNewPagoempleadoToAttach);
            }
            pagoempleadoListNew = attachedPagoempleadoListNew;
            empleado.setPagoempleadoList(pagoempleadoListNew);
            empleado = em.merge(empleado);
            if (idProyectoOld != null && !idProyectoOld.equals(idProyectoNew)) {
                idProyectoOld.getEmpleadoList().remove(empleado);
                idProyectoOld = em.merge(idProyectoOld);
            }
            if (idProyectoNew != null && !idProyectoNew.equals(idProyectoOld)) {
                idProyectoNew.getEmpleadoList().add(empleado);
                idProyectoNew = em.merge(idProyectoNew);
            }
            for (Pagoempleado pagoempleadoListOldPagoempleado : pagoempleadoListOld) {
                if (!pagoempleadoListNew.contains(pagoempleadoListOldPagoempleado)) {
                    pagoempleadoListOldPagoempleado.setIdEmpleado(null);
                    pagoempleadoListOldPagoempleado = em.merge(pagoempleadoListOldPagoempleado);
                }
            }
            for (Pagoempleado pagoempleadoListNewPagoempleado : pagoempleadoListNew) {
                if (!pagoempleadoListOld.contains(pagoempleadoListNewPagoempleado)) {
                    Empleado oldIdEmpleadoOfPagoempleadoListNewPagoempleado = pagoempleadoListNewPagoempleado.getIdEmpleado();
                    pagoempleadoListNewPagoempleado.setIdEmpleado(empleado);
                    pagoempleadoListNewPagoempleado = em.merge(pagoempleadoListNewPagoempleado);
                    if (oldIdEmpleadoOfPagoempleadoListNewPagoempleado != null && !oldIdEmpleadoOfPagoempleadoListNewPagoempleado.equals(empleado)) {
                        oldIdEmpleadoOfPagoempleadoListNewPagoempleado.getPagoempleadoList().remove(pagoempleadoListNewPagoempleado);
                        oldIdEmpleadoOfPagoempleadoListNewPagoempleado = em.merge(oldIdEmpleadoOfPagoempleadoListNewPagoempleado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = empleado.getIdEmpleado();
                if (findEmpleado(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
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
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getIdEmpleado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            Proyecto idProyecto = empleado.getIdProyecto();
            if (idProyecto != null) {
                idProyecto.getEmpleadoList().remove(empleado);
                idProyecto = em.merge(idProyecto);
            }
            List<Pagoempleado> pagoempleadoList = empleado.getPagoempleadoList();
            for (Pagoempleado pagoempleadoListPagoempleado : pagoempleadoList) {
                pagoempleadoListPagoempleado.setIdEmpleado(null);
                pagoempleadoListPagoempleado = em.merge(pagoempleadoListPagoempleado);
            }
            em.remove(empleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
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

    public Empleado findEmpleado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Empleado> getEmpleado(int idEmpleado){
        List<Empleado> resultado = null;
        try {
            EntityManager em = getEntityManager();
            Query query = em.createQuery("SELECT p FROM Empleado p where p.idEmpleado = :idEmpleado");
            query.setParameter("idEmpleado", idEmpleado);
            
            resultado = query.getResultList();
            
        } catch (Exception e) {
            
        }
        return resultado;
    }
    
}
