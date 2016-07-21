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
import javax.enterprise.context.RequestScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

/**
 *
 * @author german
 */
@ManagedBean(name = "comentarioMB")
@RequestScoped
public class ComentarioMB implements Serializable {

    BairroJpaController DaoBairro;
    ComentarioJpaController DaoComentario;

    private String titulo = null, descricao = null, NomeBairro = null;
    private Part foto;
    private int CodBairro;

    /**
     * Creates a new instance of ComentarioMB
     */
    public ComentarioMB() {
    }

    public String cadastrarComentario() {
        Bairro bairro = DaoBairro.getInstance().FindByCodigo(CodBairro);
        if (bairro == null) {
            bairro = new Bairro();
            bairro.setCodigo(CodBairro);
            bairro.setNome(NomeBairro);
            DaoBairro.getInstance().create(bairro);
            bairro = DaoBairro.getInstance().FindByCodigo(CodBairro);
        }

        return null;
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

}
