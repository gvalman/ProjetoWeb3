/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Dao.UserJpaController;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.Part;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import Entidade.User;
import javax.servlet.http.HttpSession;
import Dao.exceptions.IllegalOrphanException;
import Dao.exceptions.NonexistentEntityException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author german
 */
@ManagedBean(name = "usuarioMB")
@RequestScoped
public class UsuarioMB implements Serializable {

    UserJpaController DaoUser;

    private String login = null;
    private String email = null;
    private String senha = null;
    private String confirmarSenha = null;
    private int cep = 0;
    private Part foto = null;
    private String fotoString = null;

    /**
     * Creates a new instance of UsuarioMB
     */
    public UsuarioMB() {
        DaoUser = new UserJpaController();
        ChamarLogado();
    }

    public String cadastrarUsuario() {
        try {
            DaoUser.NovoUsuario(login, email, senha, cep, foto);
            FacesContext.getCurrentInstance().addMessage("ResultadoMensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário Cadastrado com Sucesso", "Projeto"));
            LimparForm();
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage("ResultadoMensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Problemas na foto. Tente novamente!", "Projeto"));
            Logger.getLogger(UsuarioMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void EditarUsuario() {

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        User usuario = (User) session.getAttribute("UserLogado");

        usuario.setLogin(login);
        usuario.setEmail(email);
        usuario.setCep(cep);
        
        try {
            session.setAttribute("UserLogado", DaoUser.EditarUsuario(usuario, senha, foto));
            FacesContext.getCurrentInstance().addMessage("ResultadoMensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário Atualizado com Sucesso", "Projeto"));
        } catch (NonexistentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage("ResultadoMensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Problemas na atualização. Tente novamente!", "Projeto"));
            Logger.getLogger(UsuarioMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage("ResultadoMensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Problemas na atualização. Tente novamente!", "Projeto"));
            Logger.getLogger(UsuarioMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ChamarLogado() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        User usuario = (User) session.getAttribute("UserLogado");

        if (usuario != null) {
            login = usuario.getLogin();
            email = usuario.getEmail();
            cep = usuario.getCep();
            fotoString = usuario.ConversorFoto();
        }
    }

    public void LimparForm() {
        login = null;
        email = null;
        senha = null;
        confirmarSenha = null;
        cep = 0;
    }

    public void GetInfoImagem() {
        FacesContext.getCurrentInstance().addMessage("ResultadoMensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipo:" + foto.getContentType() + " Tamanho:" + foto.getSize() + " kb", "Projeto"));
    }

    public void InfoConfirmarSenha() {
        //System.out.println(senha + " " + confirmarSenha);
        if (!(senha.equals(confirmarSenha))) {
            FacesContext.getCurrentInstance().addMessage("ResultadoMensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Campo SENHA e CONFIRMAR SENHA não confere", "Projeto"));
        }
    }

    public void ExcluirUser(User usuario) {
        try {
            DaoUser.destroy(usuario.getIduser());
            FacesContext.getCurrentInstance().addMessage("ResultadoMensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário excluido com SUCESSO", "Projeto"));
        } catch (IllegalOrphanException ex) {
            FacesContext.getCurrentInstance().addMessage("ResultadoMensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário não pode ser excluido por possuir comentários registrados.", "Projeto"));
            Logger.getLogger(UsuarioSessaoMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            FacesContext.getCurrentInstance().addMessage("ResultadoMensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário não pode ser excluido tente novamente", "Projeto"));
            Logger.getLogger(UsuarioSessaoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
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
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
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

    /**
     * @return the cep
     */
    public int getCep() {
        return cep;
    }

    /**
     * @param cep the cep to set
     */
    public void setCep(int cep) {
        this.cep = cep;
    }

    /**
     * @return the foto
     */
    public Part getFoto() {
        return foto;
    }

    /**
     * @param foto the foto to set
     */
    public void setFoto(Part foto) {
        this.foto = foto;
    }

    /**
     * @return the confirmarSenha
     */
    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    /**
     * @param confirmarSenha the confirmarSenha to set
     */
    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }

    /**
     * @return the fotoString
     */
    public String getFotoString() {
        return fotoString;
    }

    /**
     * @param fotoString the fotoString to set
     */
    public void setFotoString(String fotoString) {
        this.fotoString = fotoString;
    }
}
