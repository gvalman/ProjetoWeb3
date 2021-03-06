package Controlador;

import Dao.UserJpaController;
import Entidade.User;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * Controla o LogIn e LogOut do Usuario
 */
@ManagedBean(name = "usuarioSessaoMB")
@SessionScoped
public class UsuarioSessaoMB implements Serializable {

    UserJpaController DaoUser;

    private String login = null;
    private String senha = null;
    
    public UsuarioSessaoMB() {
        DaoUser = new UserJpaController();
    }

    public String ChecarUser() {
        String SaidaMsn;

        User usuario = DaoUser.ChecarUser(getLogin(), getSenha());
        
        FacesContext context = FacesContext.getCurrentInstance();
        /*Chamando o message-bundle pelo bean*/
        ResourceBundle bundle = context.getApplication().getResourceBundle(context,"mensagem");

        if (usuario == null) {
            SaidaMsn = bundle.getString("tentarUser");
        } else {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            session.setAttribute("UserLogado", usuario);
            SaidaMsn = bundle.getString("bemvindo") + usuario.getLogin();
        }

        FacesContext.getCurrentInstance().addMessage("ResultadoMensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, SaidaMsn, "Projeto"));
        return "index";
    }

    public String LogOutUser() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context,"mensagem");
        FacesContext.getCurrentInstance().addMessage("ResultadoMensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("sessaoOut"), "Projeto"));
        
        return "index";
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }
}
