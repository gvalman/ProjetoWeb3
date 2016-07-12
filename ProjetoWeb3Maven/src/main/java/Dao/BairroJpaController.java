/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Dao.exceptions.IllegalOrphanException;
import Dao.exceptions.NonexistentEntityException;
import Entidade.Bairro;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidade.Comentario;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author german
 */
public class BairroJpaController implements Serializable {

    public BairroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Bairro bairro) {
        if (bairro.getComentarioCollection() == null) {
            bairro.setComentarioCollection(new ArrayList<Comentario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Comentario> attachedComentarioCollection = new ArrayList<Comentario>();
            for (Comentario comentarioCollectionComentarioToAttach : bairro.getComentarioCollection()) {
                comentarioCollectionComentarioToAttach = em.getReference(comentarioCollectionComentarioToAttach.getClass(), comentarioCollectionComentarioToAttach.getIdcomentario());
                attachedComentarioCollection.add(comentarioCollectionComentarioToAttach);
            }
            bairro.setComentarioCollection(attachedComentarioCollection);
            em.persist(bairro);
            for (Comentario comentarioCollectionComentario : bairro.getComentarioCollection()) {
                Bairro oldBairroIdbairroOfComentarioCollectionComentario = comentarioCollectionComentario.getBairroIdbairro();
                comentarioCollectionComentario.setBairroIdbairro(bairro);
                comentarioCollectionComentario = em.merge(comentarioCollectionComentario);
                if (oldBairroIdbairroOfComentarioCollectionComentario != null) {
                    oldBairroIdbairroOfComentarioCollectionComentario.getComentarioCollection().remove(comentarioCollectionComentario);
                    oldBairroIdbairroOfComentarioCollectionComentario = em.merge(oldBairroIdbairroOfComentarioCollectionComentario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Bairro bairro) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Bairro persistentBairro = em.find(Bairro.class, bairro.getIdbairro());
            Collection<Comentario> comentarioCollectionOld = persistentBairro.getComentarioCollection();
            Collection<Comentario> comentarioCollectionNew = bairro.getComentarioCollection();
            List<String> illegalOrphanMessages = null;
            for (Comentario comentarioCollectionOldComentario : comentarioCollectionOld) {
                if (!comentarioCollectionNew.contains(comentarioCollectionOldComentario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Comentario " + comentarioCollectionOldComentario + " since its bairroIdbairro field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Comentario> attachedComentarioCollectionNew = new ArrayList<Comentario>();
            for (Comentario comentarioCollectionNewComentarioToAttach : comentarioCollectionNew) {
                comentarioCollectionNewComentarioToAttach = em.getReference(comentarioCollectionNewComentarioToAttach.getClass(), comentarioCollectionNewComentarioToAttach.getIdcomentario());
                attachedComentarioCollectionNew.add(comentarioCollectionNewComentarioToAttach);
            }
            comentarioCollectionNew = attachedComentarioCollectionNew;
            bairro.setComentarioCollection(comentarioCollectionNew);
            bairro = em.merge(bairro);
            for (Comentario comentarioCollectionNewComentario : comentarioCollectionNew) {
                if (!comentarioCollectionOld.contains(comentarioCollectionNewComentario)) {
                    Bairro oldBairroIdbairroOfComentarioCollectionNewComentario = comentarioCollectionNewComentario.getBairroIdbairro();
                    comentarioCollectionNewComentario.setBairroIdbairro(bairro);
                    comentarioCollectionNewComentario = em.merge(comentarioCollectionNewComentario);
                    if (oldBairroIdbairroOfComentarioCollectionNewComentario != null && !oldBairroIdbairroOfComentarioCollectionNewComentario.equals(bairro)) {
                        oldBairroIdbairroOfComentarioCollectionNewComentario.getComentarioCollection().remove(comentarioCollectionNewComentario);
                        oldBairroIdbairroOfComentarioCollectionNewComentario = em.merge(oldBairroIdbairroOfComentarioCollectionNewComentario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bairro.getIdbairro();
                if (findBairro(id) == null) {
                    throw new NonexistentEntityException("The bairro with id " + id + " no longer exists.");
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
            Bairro bairro;
            try {
                bairro = em.getReference(Bairro.class, id);
                bairro.getIdbairro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bairro with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Comentario> comentarioCollectionOrphanCheck = bairro.getComentarioCollection();
            for (Comentario comentarioCollectionOrphanCheckComentario : comentarioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Bairro (" + bairro + ") cannot be destroyed since the Comentario " + comentarioCollectionOrphanCheckComentario + " in its comentarioCollection field has a non-nullable bairroIdbairro field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(bairro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Bairro> findBairroEntities() {
        return findBairroEntities(true, -1, -1);
    }

    public List<Bairro> findBairroEntities(int maxResults, int firstResult) {
        return findBairroEntities(false, maxResults, firstResult);
    }

    private List<Bairro> findBairroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Bairro.class));
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

    public Bairro findBairro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Bairro.class, id);
        } finally {
            em.close();
        }
    }

    public int getBairroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Bairro> rt = cq.from(Bairro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
