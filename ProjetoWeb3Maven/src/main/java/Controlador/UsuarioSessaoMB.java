/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Dao.UserJpaController;
import Entidade.User;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author german Controla o LogIn e LogOut do Usuario
 */
@ManagedBean(name = "usuarioSessaoMB")
@SessionScoped
public class UsuarioSessaoMB implements Serializable {

    UserJpaController DaoUser;

    private String login = null;
    private String senha = null;

    public UsuarioSessaoMB() {
    }

    public String ChecarUser() {
        String Saida = null, SaidaMsn;

        User usuario = DaoUser.getInstance().ChecarUser(getLogin(), getSenha());

        if (usuario == null) {
            SaidaMsn = "Usuário não encontrado. Tente novamente!";
            Saida = "index";
        } else {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            session.setAttribute("UserLogado", usuario);
            SaidaMsn = "Bem Vindo," + usuario.getLogin();
            Saida = "indexIn";
        }

        FacesContext.getCurrentInstance().addMessage("ResultadoMensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, SaidaMsn, "Projeto"));
        return Saida;
    }
    
    public String LogOutUser(){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
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
