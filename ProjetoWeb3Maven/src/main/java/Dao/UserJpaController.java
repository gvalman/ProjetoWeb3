/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Dao.exceptions.IllegalOrphanException;
import Dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidade.Comentario;
import Entidade.User;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.Part;

/**
 *
 * @author german
 */
public class UserJpaController implements Serializable {
    
    private static UserJpaController instance;
    
    protected EntityManager entityManager;
    
    public static UserJpaController getInstance() {
        if (instance == null) {
            instance = new UserJpaController();
        }
        return instance;
    }
    
    private UserJpaController() {
        entityManager = getEntityManager();
    }
    
    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pw3_ProjetoWeb3Maven_war_1.0PU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }
    
    public void NovoUsuario(String login, String email, String senha, int cep, Part foto) {
        User usuario = new User();
        usuario.setLogin(login);
        usuario.setEmail(email);
        usuario.setSenha(Criptografia.criptografar(senha));
        usuario.setCep(cep);
        
        try {
            usuario.setFoto(toByteArray(foto.getInputStream(), (int) foto.getSize()));
        } catch (IOException ex) {
            Logger.getLogger(UserJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        usuario.setFotoTipo(foto.getContentType());
        create(usuario);
    }
    
    public void create(User user) {
        if (user.getComentarioCollection() == null) {
            user.setComentarioCollection(new ArrayList<Comentario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Comentario> attachedComentarioCollection = new ArrayList<Comentario>();
            for (Comentario comentarioCollectionComentarioToAttach : user.getComentarioCollection()) {
                comentarioCollectionComentarioToAttach = em.getReference(comentarioCollectionComentarioToAttach.getClass(), comentarioCollectionComentarioToAttach.getIdcomentario());
                attachedComentarioCollection.add(comentarioCollectionComentarioToAttach);
            }
            user.setComentarioCollection(attachedComentarioCollection);
            em.persist(user);
            for (Comentario comentarioCollectionComentario : user.getComentarioCollection()) {
                User oldUserIduserOfComentarioCollectionComentario = comentarioCollectionComentario.getUserIduser();
                comentarioCollectionComentario.setUserIduser(user);
                comentarioCollectionComentario = em.merge(comentarioCollectionComentario);
                if (oldUserIduserOfComentarioCollectionComentario != null) {
                    oldUserIduserOfComentarioCollectionComentario.getComentarioCollection().remove(comentarioCollectionComentario);
                    oldUserIduserOfComentarioCollectionComentario = em.merge(oldUserIduserOfComentarioCollectionComentario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User persistentUser = em.find(User.class, user.getIduser());
            Collection<Comentario> comentarioCollectionOld = persistentUser.getComentarioCollection();
            Collection<Comentario> comentarioCollectionNew = user.getComentarioCollection();
            List<String> illegalOrphanMessages = null;
            for (Comentario comentarioCollectionOldComentario : comentarioCollectionOld) {
                if (!comentarioCollectionNew.contains(comentarioCollectionOldComentario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Comentario " + comentarioCollectionOldComentario + " since its userIduser field is not nullable.");
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
            user.setComentarioCollection(comentarioCollectionNew);
            user = em.merge(user);
            for (Comentario comentarioCollectionNewComentario : comentarioCollectionNew) {
                if (!comentarioCollectionOld.contains(comentarioCollectionNewComentario)) {
                    User oldUserIduserOfComentarioCollectionNewComentario = comentarioCollectionNewComentario.getUserIduser();
                    comentarioCollectionNewComentario.setUserIduser(user);
                    comentarioCollectionNewComentario = em.merge(comentarioCollectionNewComentario);
                    if (oldUserIduserOfComentarioCollectionNewComentario != null && !oldUserIduserOfComentarioCollectionNewComentario.equals(user)) {
                        oldUserIduserOfComentarioCollectionNewComentario.getComentarioCollection().remove(comentarioCollectionNewComentario);
                        oldUserIduserOfComentarioCollectionNewComentario = em.merge(oldUserIduserOfComentarioCollectionNewComentario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = user.getIduser();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
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
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getIduser();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Comentario> comentarioCollectionOrphanCheck = user.getComentarioCollection();
            for (Comentario comentarioCollectionOrphanCheckComentario : comentarioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Comentario " + comentarioCollectionOrphanCheckComentario + " in its comentarioCollection field has a non-nullable userIduser field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(user);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }
    
    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }
    
    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
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
    
    public User findUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }
    
    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    private byte[] toByteArray(InputStream is, int size) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            byte[] b = new byte[size];
            int n = 0;
            while ((n = is.read(b)) != -1) {
                output.write(b, 0, n);
            }
            return output.toByteArray();
        } finally {
            output.close();
        }
    }
    
}
