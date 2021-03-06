package Dao;

import Dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidade.User;
import Entidade.Bairro;
import Entidade.Comentario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.Part;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ComentarioJpaController implements Serializable {

    private static ComentarioJpaController instance;

    protected EntityManager entityManager;

    public static ComentarioJpaController getInstance() {
        if (instance == null) {
            instance = new ComentarioJpaController();
        }
        return instance;
    }

    public ComentarioJpaController() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pw3_ProjetoWeb3Maven_war_1.0PU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }

    public void cadastrarComentario(String titulo, String descricao, Part foto, String tipo, User usuario, Bairro bairro) {
        Comentario comentario = new Comentario();
        comentario.setTitulo(titulo);
        comentario.setDescricao(descricao);
        Date hoje = new Date();
        comentario.setDataInicio(hoje);
        comentario.setHoraInicio(hoje);
        comentario.setTipo(tipo);
        comentario.setBairroIdbairro(bairro);
        comentario.setUserIduser(usuario);

        try {
            comentario.setFoto(toByteArray(foto.getInputStream(), (int) foto.getSize()));
        } catch (IOException ex) {
            Logger.getLogger(ComentarioJpaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        comentario.setFotoTipo(foto.getContentType());

        create(comentario);
    }

    public List<Comentario> FindByTipoByCodBairro(String tipo, int codigo) {
        List<Comentario> comentarios = null;
        EntityManager em = null;
        em = getEntityManager();
        try {
            comentarios = (List<Comentario>) em.createNamedQuery("Comentario.findByTipoByBairro")
                    .setParameter("tipo", tipo)
                    .setParameter("CodBairro", codigo)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comentarios;
    }
    
    public List<Comentario> FindAll() {
        List<Comentario> comentarios = null;
        EntityManager em = null;
        em = getEntityManager();
        try {
            comentarios = (List<Comentario>) em.createNamedQuery("Comentario.findAll")
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comentarios;
    }
    
    public List<Comentario> FindAllByUser(int idUser) {
        List<Comentario> comentarios = null;
        EntityManager em = null;
        em = getEntityManager();
        try {
            comentarios = (List<Comentario>) em.createNamedQuery("Comentario.findAllByUser")
                    .setParameter("iduser", idUser)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comentarios;
    }
    
    public void finalizarComentario(Comentario comentario) throws NonexistentEntityException, Exception{
        Date hoje = new Date();
        comentario.setDataFim(hoje);
        comentario.setHoraFim(hoje);
        edit(comentario);
    }

    public void create(Comentario comentario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User userIduser = comentario.getUserIduser();
            if (userIduser != null) {
                userIduser = em.getReference(userIduser.getClass(), userIduser.getIduser());
                comentario.setUserIduser(userIduser);
            }
            Bairro bairroIdbairro = comentario.getBairroIdbairro();
            if (bairroIdbairro != null) {
                bairroIdbairro = em.getReference(bairroIdbairro.getClass(), bairroIdbairro.getIdbairro());
                comentario.setBairroIdbairro(bairroIdbairro);
            }
            em.persist(comentario);
            if (userIduser != null) {
                userIduser.getComentarioCollection().add(comentario);
                userIduser = em.merge(userIduser);
            }
            if (bairroIdbairro != null) {
                bairroIdbairro.getComentarioCollection().add(comentario);
                bairroIdbairro = em.merge(bairroIdbairro);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comentario comentario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comentario persistentComentario = em.find(Comentario.class, comentario.getIdcomentario());
            User userIduserOld = persistentComentario.getUserIduser();
            User userIduserNew = comentario.getUserIduser();
            Bairro bairroIdbairroOld = persistentComentario.getBairroIdbairro();
            Bairro bairroIdbairroNew = comentario.getBairroIdbairro();
            if (userIduserNew != null) {
                userIduserNew = em.getReference(userIduserNew.getClass(), userIduserNew.getIduser());
                comentario.setUserIduser(userIduserNew);
            }
            if (bairroIdbairroNew != null) {
                bairroIdbairroNew = em.getReference(bairroIdbairroNew.getClass(), bairroIdbairroNew.getIdbairro());
                comentario.setBairroIdbairro(bairroIdbairroNew);
            }
            comentario = em.merge(comentario);
            if (userIduserOld != null && !userIduserOld.equals(userIduserNew)) {
                userIduserOld.getComentarioCollection().remove(comentario);
                userIduserOld = em.merge(userIduserOld);
            }
            if (userIduserNew != null && !userIduserNew.equals(userIduserOld)) {
                userIduserNew.getComentarioCollection().add(comentario);
                userIduserNew = em.merge(userIduserNew);
            }
            if (bairroIdbairroOld != null && !bairroIdbairroOld.equals(bairroIdbairroNew)) {
                bairroIdbairroOld.getComentarioCollection().remove(comentario);
                bairroIdbairroOld = em.merge(bairroIdbairroOld);
            }
            if (bairroIdbairroNew != null && !bairroIdbairroNew.equals(bairroIdbairroOld)) {
                bairroIdbairroNew.getComentarioCollection().add(comentario);
                bairroIdbairroNew = em.merge(bairroIdbairroNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comentario.getIdcomentario();
                if (findComentario(id) == null) {
                    throw new NonexistentEntityException("The comentario with id " + id + " no longer exists.");
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
            Comentario comentario;
            try {
                comentario = em.getReference(Comentario.class, id);
                comentario.getIdcomentario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comentario with id " + id + " no longer exists.", enfe);
            }
            User userIduser = comentario.getUserIduser();
            if (userIduser != null) {
                userIduser.getComentarioCollection().remove(comentario);
                userIduser = em.merge(userIduser);
            }
            Bairro bairroIdbairro = comentario.getBairroIdbairro();
            if (bairroIdbairro != null) {
                bairroIdbairro.getComentarioCollection().remove(comentario);
                bairroIdbairro = em.merge(bairroIdbairro);
            }
            em.remove(comentario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comentario> findComentarioEntities() {
        return findComentarioEntities(true, -1, -1);
    }

    public List<Comentario> findComentarioEntities(int maxResults, int firstResult) {
        return findComentarioEntities(false, maxResults, firstResult);
    }

    private List<Comentario> findComentarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comentario.class));
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

    public Comentario findComentario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comentario.class, id);
        } finally {
            em.close();
        }
    }

    public int getComentarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comentario> rt = cq.from(Comentario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    /*Para converter a imagem de InpuStream para byte[]*/
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
