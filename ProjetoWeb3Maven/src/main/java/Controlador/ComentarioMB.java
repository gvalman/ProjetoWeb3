/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Dao.BairroJpaController;
import Dao.ComentarioJpaController;
import Entidade.Bairro;
import Entidade.Comentario;
import Entidade.User;
import javax.enterprise.context.RequestScoped;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.util.List;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author german
 */
@ManagedBean(name = "comentarioMB")
@RequestScoped
public class ComentarioMB implements Serializable {

    BairroJpaController DaoBairro;
    ComentarioJpaController DaoComentario;

    private String titulo = null, descricao = null, NomeBairro = null, tipo = null;
    private Part foto;
    private int CodBairro;
    private boolean ShowLista = false;

    /**
     * Creates a new instance of ComentarioMB
     */
    public ComentarioMB() {
        DaoBairro = new BairroJpaController();
        DaoComentario = new ComentarioJpaController();
    }

    public String cadastrarComentario() {
        Bairro bairro = DaoBairro.FindByCodigo(CodBairro);
        if (bairro == null) {
            bairro = new Bairro();
            bairro.setCodigo(CodBairro);
            bairro.setNome(NomeBairro);
            DaoBairro.create(bairro);
        }

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

        User usuario = (User) session.getAttribute("UserLogado");

        DaoComentario.cadastrarComentario(titulo, descricao, foto, tipo, usuario, bairro);

        FacesContext.getCurrentInstance().addMessage("ResultadoMensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Comentário Cadastrado com Sucesso", "Projeto"));

        return null;
    }

    public List<Comentario> ListaComentario() {
        //System.out.println(CodBairro + " " + tipo);

        List<Comentario> Lista = DaoComentario.FindByTipoByCodBairro(tipo, CodBairro);

        return Lista;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
     * @return the CodBairro
     */
    public int getCodBairro() {
        return CodBairro;
    }

    /**
     * @param CodBairro the CodBairro to set
     */
    public void setCodBairro(int CodBairro) {
        this.CodBairro = CodBairro;
    }

    /**
     * @return the NomeBairro
     */
    public String getNomeBairro() {
        return NomeBairro;
    }

    /**
     * @param NomeBairro the NomeBairro to set
     */
    public void setNomeBairro(String NomeBairro) {
        this.NomeBairro = NomeBairro;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the ShowLista
     */
    public boolean isShowLista() {
        return ShowLista;
    }

    /**
     * @param ShowLista the ShowLista to set
     */
    public void setShowLista(boolean ShowLista) {
        this.ShowLista = ShowLista;
    }
}
