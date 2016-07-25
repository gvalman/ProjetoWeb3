/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Dao.UserJpaController;
import Entidade.Comentario;
import Entidade.User;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;

/**
 *
 * @author german
 */
@ManagedBean(name = "usuarioViewMB")
@ViewScoped
public class UsuarioViewMB {

    UserJpaController DaoUser;

    private List<User> listaUser = null;
    private List<Comentario> listaComentario = null;

    /**
     * Creates a new instance of UsuarioViewMB
     */
    public UsuarioViewMB() {
        DaoUser = new UserJpaController();
    }

    public String ChamarListarUser() {
        ListarUser();
        return "ListaUsers";
    }

    public List<User> ListarUser() {

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        User usuario = (User) session.getAttribute("UserLogado");

        setListaUser(DaoUser.ListaAllUser(usuario.getIduser()));
        return listaUser;
    }

    public void UpdateListaComentario(User usuario, String tipo) {
        setListaComentario(usuario.ListaComentarioByTipo(tipo));
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
