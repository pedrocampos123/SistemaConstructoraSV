
package com.controller;

import com.controller.exceptions.IllegalOrphanException;
import com.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.entities.Ubicacion;
import com.entities.Empleado;
import java.util.ArrayList;
import java.util.List;
import com.entities.Detalleproyectomaquina;
import com.entities.Maquinaria;
import com.entities.Proyecto;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Nombre del controller: ProyectoJpaController
 * Fecha: 04/11/2020 
 * CopyRigth: Pedro Campos
 * Modificacion: 09/11/2020
 * Version: 1.1
 * @author pedro
 */
public class ProyectoJpaController implements Serializable {

    public ProyectoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    public ProyectoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("SistemaConstructoraSVPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proyecto proyecto) {
        if (proyecto.getEmpleadoList() == null) {
            proyecto.setEmpleadoList(new ArrayList<Empleado>());
        }
        if (proyecto.getDetalleproyectomaquinaList() == null) {
            proyecto.setDetalleproyectomaquinaList(new ArrayList<Detalleproyectomaquina>());
        }
        if (proyecto.getMaquinariaList() == null) {
            proyecto.setMaquinariaList(new ArrayList<Maquinaria>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ubicacion idUbicacion = proyecto.getIdUbicacion();
            if (idUbicacion != null) {
                idUbicacion = em.getReference(idUbicacion.getClass(), idUbicacion.getIdUbicacion());
                proyecto.setIdUbicacion(idUbicacion);
            }
            List<Empleado> attachedEmpleadoList = new ArrayList<Empleado>();
            for (Empleado empleadoListEmpleadoToAttach : proyecto.getEmpleadoList()) {
                empleadoListEmpleadoToAttach = em.getReference(empleadoListEmpleadoToAttach.getClass(), empleadoListEmpleadoToAttach.getIdEmpleado());
                attachedEmpleadoList.add(empleadoListEmpleadoToAttach);
            }
            proyecto.setEmpleadoList(attachedEmpleadoList);
            List<Detalleproyectomaquina> attachedDetalleproyectomaquinaList = new ArrayList<Detalleproyectomaquina>();
            for (Detalleproyectomaquina detalleproyectomaquinaListDetalleproyectomaquinaToAttach : proyecto.getDetalleproyectomaquinaList()) {
                detalleproyectomaquinaListDetalleproyectomaquinaToAttach = em.getReference(detalleproyectomaquinaListDetalleproyectomaquinaToAttach.getClass(), detalleproyectomaquinaListDetalleproyectomaquinaToAttach.getIdDetalle());
                attachedDetalleproyectomaquinaList.add(detalleproyectomaquinaListDetalleproyectomaquinaToAttach);
            }
            proyecto.setDetalleproyectomaquinaList(attachedDetalleproyectomaquinaList);
            List<Maquinaria> attachedMaquinariaList = new ArrayList<Maquinaria>();
            for (Maquinaria maquinariaListMaquinariaToAttach : proyecto.getMaquinariaList()) {
                maquinariaListMaquinariaToAttach = em.getReference(maquinariaListMaquinariaToAttach.getClass(), maquinariaListMaquinariaToAttach.getIdMaquinaria());
                attachedMaquinariaList.add(maquinariaListMaquinariaToAttach);
            }
            proyecto.setMaquinariaList(attachedMaquinariaList);
            em.persist(proyecto);
            if (idUbicacion != null) {
                idUbicacion.getProyectoList().add(proyecto);
                idUbicacion = em.merge(idUbicacion);
            }
            for (Empleado empleadoListEmpleado : proyecto.getEmpleadoList()) {
                Proyecto oldIdProyectoOfEmpleadoListEmpleado = empleadoListEmpleado.getIdProyecto();
                empleadoListEmpleado.setIdProyecto(proyecto);
                empleadoListEmpleado = em.merge(empleadoListEmpleado);
                if (oldIdProyectoOfEmpleadoListEmpleado != null) {
                    oldIdProyectoOfEmpleadoListEmpleado.getEmpleadoList().remove(empleadoListEmpleado);
                    oldIdProyectoOfEmpleadoListEmpleado = em.merge(oldIdProyectoOfEmpleadoListEmpleado);
                }
            }
            for (Detalleproyectomaquina detalleproyectomaquinaListDetalleproyectomaquina : proyecto.getDetalleproyectomaquinaList()) {
                Proyecto oldIdProyectoOfDetalleproyectomaquinaListDetalleproyectomaquina = detalleproyectomaquinaListDetalleproyectomaquina.getIdProyecto();
                detalleproyectomaquinaListDetalleproyectomaquina.setIdProyecto(proyecto);
                detalleproyectomaquinaListDetalleproyectomaquina = em.merge(detalleproyectomaquinaListDetalleproyectomaquina);
                if (oldIdProyectoOfDetalleproyectomaquinaListDetalleproyectomaquina != null) {
                    oldIdProyectoOfDetalleproyectomaquinaListDetalleproyectomaquina.getDetalleproyectomaquinaList().remove(detalleproyectomaquinaListDetalleproyectomaquina);
                    oldIdProyectoOfDetalleproyectomaquinaListDetalleproyectomaquina = em.merge(oldIdProyectoOfDetalleproyectomaquinaListDetalleproyectomaquina);
                }
            }
            for (Maquinaria maquinariaListMaquinaria : proyecto.getMaquinariaList()) {
                Proyecto oldIdProyectoOfMaquinariaListMaquinaria = maquinariaListMaquinaria.getIdProyecto();
                maquinariaListMaquinaria.setIdProyecto(proyecto);
                maquinariaListMaquinaria = em.merge(maquinariaListMaquinaria);
                if (oldIdProyectoOfMaquinariaListMaquinaria != null) {
                    oldIdProyectoOfMaquinariaListMaquinaria.getMaquinariaList().remove(maquinariaListMaquinaria);
                    oldIdProyectoOfMaquinariaListMaquinaria = em.merge(oldIdProyectoOfMaquinariaListMaquinaria);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proyecto proyecto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proyecto persistentProyecto = em.find(Proyecto.class, proyecto.getIdProyecto());
            Ubicacion idUbicacionOld = persistentProyecto.getIdUbicacion();
            Ubicacion idUbicacionNew = proyecto.getIdUbicacion();
            List<Empleado> empleadoListOld = persistentProyecto.getEmpleadoList();
            List<Empleado> empleadoListNew = proyecto.getEmpleadoList();
            List<Detalleproyectomaquina> detalleproyectomaquinaListOld = persistentProyecto.getDetalleproyectomaquinaList();
            List<Detalleproyectomaquina> detalleproyectomaquinaListNew = proyecto.getDetalleproyectomaquinaList();
            List<Maquinaria> maquinariaListOld = persistentProyecto.getMaquinariaList();
            List<Maquinaria> maquinariaListNew = proyecto.getMaquinariaList();
            List<String> illegalOrphanMessages = null;
            for (Empleado empleadoListOldEmpleado : empleadoListOld) {
                if (!empleadoListNew.contains(empleadoListOldEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Empleado " + empleadoListOldEmpleado + " since its idProyecto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idUbicacionNew != null) {
                idUbicacionNew = em.getReference(idUbicacionNew.getClass(), idUbicacionNew.getIdUbicacion());
                proyecto.setIdUbicacion(idUbicacionNew);
            }
            List<Empleado> attachedEmpleadoListNew = new ArrayList<Empleado>();
            for (Empleado empleadoListNewEmpleadoToAttach : empleadoListNew) {
                empleadoListNewEmpleadoToAttach = em.getReference(empleadoListNewEmpleadoToAttach.getClass(), empleadoListNewEmpleadoToAttach.getIdEmpleado());
                attachedEmpleadoListNew.add(empleadoListNewEmpleadoToAttach);
            }
            empleadoListNew = attachedEmpleadoListNew;
            proyecto.setEmpleadoList(empleadoListNew);
            List<Detalleproyectomaquina> attachedDetalleproyectomaquinaListNew = new ArrayList<Detalleproyectomaquina>();
            for (Detalleproyectomaquina detalleproyectomaquinaListNewDetalleproyectomaquinaToAttach : detalleproyectomaquinaListNew) {
                detalleproyectomaquinaListNewDetalleproyectomaquinaToAttach = em.getReference(detalleproyectomaquinaListNewDetalleproyectomaquinaToAttach.getClass(), detalleproyectomaquinaListNewDetalleproyectomaquinaToAttach.getIdDetalle());
                attachedDetalleproyectomaquinaListNew.add(detalleproyectomaquinaListNewDetalleproyectomaquinaToAttach);
            }
            detalleproyectomaquinaListNew = attachedDetalleproyectomaquinaListNew;
            proyecto.setDetalleproyectomaquinaList(detalleproyectomaquinaListNew);
            List<Maquinaria> attachedMaquinariaListNew = new ArrayList<Maquinaria>();
            for (Maquinaria maquinariaListNewMaquinariaToAttach : maquinariaListNew) {
                maquinariaListNewMaquinariaToAttach = em.getReference(maquinariaListNewMaquinariaToAttach.getClass(), maquinariaListNewMaquinariaToAttach.getIdMaquinaria());
                attachedMaquinariaListNew.add(maquinariaListNewMaquinariaToAttach);
            }
            maquinariaListNew = attachedMaquinariaListNew;
            proyecto.setMaquinariaList(maquinariaListNew);
            proyecto = em.merge(proyecto);
            if (idUbicacionOld != null && !idUbicacionOld.equals(idUbicacionNew)) {
                idUbicacionOld.getProyectoList().remove(proyecto);
                idUbicacionOld = em.merge(idUbicacionOld);
            }
            if (idUbicacionNew != null && !idUbicacionNew.equals(idUbicacionOld)) {
                idUbicacionNew.getProyectoList().add(proyecto);
                idUbicacionNew = em.merge(idUbicacionNew);
            }
            for (Empleado empleadoListNewEmpleado : empleadoListNew) {
                if (!empleadoListOld.contains(empleadoListNewEmpleado)) {
                    Proyecto oldIdProyectoOfEmpleadoListNewEmpleado = empleadoListNewEmpleado.getIdProyecto();
                    empleadoListNewEmpleado.setIdProyecto(proyecto);
                    empleadoListNewEmpleado = em.merge(empleadoListNewEmpleado);
                    if (oldIdProyectoOfEmpleadoListNewEmpleado != null && !oldIdProyectoOfEmpleadoListNewEmpleado.equals(proyecto)) {
                        oldIdProyectoOfEmpleadoListNewEmpleado.getEmpleadoList().remove(empleadoListNewEmpleado);
                        oldIdProyectoOfEmpleadoListNewEmpleado = em.merge(oldIdProyectoOfEmpleadoListNewEmpleado);
                    }
                }
            }
            for (Detalleproyectomaquina detalleproyectomaquinaListOldDetalleproyectomaquina : detalleproyectomaquinaListOld) {
                if (!detalleproyectomaquinaListNew.contains(detalleproyectomaquinaListOldDetalleproyectomaquina)) {
                    detalleproyectomaquinaListOldDetalleproyectomaquina.setIdProyecto(null);
                    detalleproyectomaquinaListOldDetalleproyectomaquina = em.merge(detalleproyectomaquinaListOldDetalleproyectomaquina);
                }
            }
            for (Detalleproyectomaquina detalleproyectomaquinaListNewDetalleproyectomaquina : detalleproyectomaquinaListNew) {
                if (!detalleproyectomaquinaListOld.contains(detalleproyectomaquinaListNewDetalleproyectomaquina)) {
                    Proyecto oldIdProyectoOfDetalleproyectomaquinaListNewDetalleproyectomaquina = detalleproyectomaquinaListNewDetalleproyectomaquina.getIdProyecto();
                    detalleproyectomaquinaListNewDetalleproyectomaquina.setIdProyecto(proyecto);
                    detalleproyectomaquinaListNewDetalleproyectomaquina = em.merge(detalleproyectomaquinaListNewDetalleproyectomaquina);
                    if (oldIdProyectoOfDetalleproyectomaquinaListNewDetalleproyectomaquina != null && !oldIdProyectoOfDetalleproyectomaquinaListNewDetalleproyectomaquina.equals(proyecto)) {
                        oldIdProyectoOfDetalleproyectomaquinaListNewDetalleproyectomaquina.getDetalleproyectomaquinaList().remove(detalleproyectomaquinaListNewDetalleproyectomaquina);
                        oldIdProyectoOfDetalleproyectomaquinaListNewDetalleproyectomaquina = em.merge(oldIdProyectoOfDetalleproyectomaquinaListNewDetalleproyectomaquina);
                    }
                }
            }
            for (Maquinaria maquinariaListOldMaquinaria : maquinariaListOld) {
                if (!maquinariaListNew.contains(maquinariaListOldMaquinaria)) {
                    maquinariaListOldMaquinaria.setIdProyecto(null);
                    maquinariaListOldMaquinaria = em.merge(maquinariaListOldMaquinaria);
                }
            }
            for (Maquinaria maquinariaListNewMaquinaria : maquinariaListNew) {
                if (!maquinariaListOld.contains(maquinariaListNewMaquinaria)) {
                    Proyecto oldIdProyectoOfMaquinariaListNewMaquinaria = maquinariaListNewMaquinaria.getIdProyecto();
                    maquinariaListNewMaquinaria.setIdProyecto(proyecto);
                    maquinariaListNewMaquinaria = em.merge(maquinariaListNewMaquinaria);
                    if (oldIdProyectoOfMaquinariaListNewMaquinaria != null && !oldIdProyectoOfMaquinariaListNewMaquinaria.equals(proyecto)) {
                        oldIdProyectoOfMaquinariaListNewMaquinaria.getMaquinariaList().remove(maquinariaListNewMaquinaria);
                        oldIdProyectoOfMaquinariaListNewMaquinaria = em.merge(oldIdProyectoOfMaquinariaListNewMaquinaria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = proyecto.getIdProyecto();
                if (findProyecto(id) == null) {
                    throw new NonexistentEntityException("The proyecto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proyecto proyecto;
            try {
                proyecto = em.getReference(Proyecto.class, id);
                proyecto.getIdProyecto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proyecto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Empleado> empleadoListOrphanCheck = proyecto.getEmpleadoList();
            for (Empleado empleadoListOrphanCheckEmpleado : empleadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proyecto (" + proyecto + ") cannot be destroyed since the Empleado " + empleadoListOrphanCheckEmpleado + " in its empleadoList field has a non-nullable idProyecto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Ubicacion idUbicacion = proyecto.getIdUbicacion();
            if (idUbicacion != null) {
                idUbicacion.getProyectoList().remove(proyecto);
                idUbicacion = em.merge(idUbicacion);
            }
            List<Detalleproyectomaquina> detalleproyectomaquinaList = proyecto.getDetalleproyectomaquinaList();
            for (Detalleproyectomaquina detalleproyectomaquinaListDetalleproyectomaquina : detalleproyectomaquinaList) {
                detalleproyectomaquinaListDetalleproyectomaquina.setIdProyecto(null);
                detalleproyectomaquinaListDetalleproyectomaquina = em.merge(detalleproyectomaquinaListDetalleproyectomaquina);
            }
            List<Maquinaria> maquinariaList = proyecto.getMaquinariaList();
            for (Maquinaria maquinariaListMaquinaria : maquinariaList) {
                maquinariaListMaquinaria.setIdProyecto(null);
                maquinariaListMaquinaria = em.merge(maquinariaListMaquinaria);
            }
            em.remove(proyecto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proyecto> findProyectoEntities() {
        return findProyectoEntities(true, -1, -1);
    }

    public List<Proyecto> findProyectoEntities(int maxResults, int firstResult) {
        return findProyectoEntities(false, maxResults, firstResult);
    }

    private List<Proyecto> findProyectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proyecto.class));
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

    public Proyecto findProyecto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proyecto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProyectoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proyecto> rt = cq.from(Proyecto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Proyecto> getAllProyects(){
        List<Proyecto> resultado = null;
        try {
            EntityManager em = getEntityManager();
            Query query = em.createQuery("SELECT p FROM Proyecto p");
            
            resultado = query.getResultList();
            
        } catch (Exception e) {
            String ee ="";
        }
        return resultado;
    }
    
    public Proyecto getProyectoUbicacion(int idUbicacion){
        List<Proyecto> resultado = null;
        Proyecto proyecto = new Proyecto();
        Ubicacion ubi = new Ubicacion();
        try {
            EntityManager em = getEntityManager();
            Query query = em.createQuery("SELECT p FROM Proyecto p where p.idUbicacion.idUbicacion = :idUbicacion");
            query.setParameter("idUbicacion", idUbicacion);
            
            resultado = query.getResultList();
            
            if(!resultado.isEmpty()){
                for (Proyecto obj : resultado) {
                    proyecto.setIdProyecto(obj.getIdProyecto());
                    proyecto.setNombreProyecto(obj.getNombreProyecto());
                    proyecto.setFechaInicio(obj.getFechaInicio());
                    proyecto.setTiempoEstimado(obj.getTiempoEstimado());
                    proyecto.setPrecioTotal(obj.getPrecioTotal());
                    
                    ubi.setIdUbicacion(obj.getIdUbicacion().getIdUbicacion());
                    ubi.setNombre(obj.getIdUbicacion().getNombre());
                    ubi.setLatitud(obj.getIdUbicacion().getLatitud());
                    ubi.setLongitud(obj.getIdUbicacion().getLongitud());
                    
                    proyecto.setIdUbicacion(ubi);
                }
            }
            
        } catch (Exception e) {
            return new Proyecto();
        }
        return proyecto;
    }
    
    public List<Proyecto> getProyecto(int idProyecto){
        List<Proyecto> resultado = null;
        try {
            EntityManager em = getEntityManager();
            Query query = em.createQuery("SELECT p FROM Proyecto p where p.idProyecto = :idProyecto");
            query.setParameter("idProyecto", idProyecto);
            
            resultado = query.getResultList();
            
        } catch (Exception e) {
            
        }
        return resultado;
    }
    
}
