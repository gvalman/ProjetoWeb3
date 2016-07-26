package Controlador;

import Dao.ComentarioJpaController;
import Dao.UserJpaController;
import Entidade.Comentario;
import Entidade.User;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "usuarioViewMB")
@ViewScoped
public class UsuarioViewMB implements Serializable{

    UserJpaController DaoUser;
    ComentarioJpaController DaoComentario;

    private List<User> listaUser = null;
    private List<Comentario> listaComentario = null;

    /**
     * Creates a new instance of UsuarioViewMB
     */
    public UsuarioViewMB() {
        DaoUser = new UserJpaController();
        DaoComentario = new ComentarioJpaController();
    }

    public String ChamarListarUser() {
        ListarUser();
        return "ListaUsers";
    }

    /*Chama lista com todos os usuários exceto o administrador*/
    public List<User> ListarUser() {

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        User usuario = (User) session.getAttribute("UserLogado");

        setListaUser(DaoUser.ListaAllUser(usuario.getIduser()));
        return listaUser;
    }

    public void UpdateListaComentario(User usuario, String tipo) {
        setListaComentario(usuario.ListaComentarioByTipo(tipo));
    }

    /*Chama lista com todos os comentários*/
    public List<Comentario> ListarAllComentario() {
        setListaComentario(DaoComentario.FindAll());
        return listaComentario;
    }
    
    /*Chama lista com todos os comentários relacionados ao usuário*/
    public List<Comentario> ListarAllComentarioByUser() {
        
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        User usuario = (User) session.getAttribute("UserLogado");
        
        setListaComentario(DaoComentario.FindAllByUser(usuario.getIduser()));
        return listaComentario;
    }

    /**
     * @return the listaUser
     */
    public List<User> getListaUser() {
        return listaUser;
    }

    /**
     * @param listaUser the listaUser to set
     */
    public void setListaUser(List<User> listaUser) {
        this.listaUser = listaUser;
    }

    /**
     * @return the listaComentario
     */
    public List<Comentario> getListaComentario() {
        return listaComentario;
    }

    /**
     * @param listaComentario the listaComentario to set
     */
    public void setListaComentario(List<Comentario> listaComentario) {
        this.listaComentario = listaComentario;
    }
}
