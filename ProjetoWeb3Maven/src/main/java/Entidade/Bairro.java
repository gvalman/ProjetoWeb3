/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidade;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author german
 */
@Entity
@Table(name = "bairro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bairro.findAll", query = "SELECT b FROM Bairro b"),
    @NamedQuery(name = "Bairro.findByIdbairro", query = "SELECT b FROM Bairro b WHERE b.idbairro = :idbairro"),
    @NamedQuery(name = "Bairro.findByCodigo", query = "SELECT b FROM Bairro b WHERE b.codigo = :codigo"),
    @NamedQuery(name = "Bairro.findByNome", query = "SELECT b FROM Bairro b WHERE b.nome = :nome")})
public class Bairro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idbairro")
    private Integer idbairro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codigo")
    private int codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nome")
    private String nome;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bairroIdbairro")
    private Collection<Comentario> comentarioCollection;

    public Bairro() {
    }

    public Bairro(Integer idbairro) {
        this.idbairro = idbairro;
    }

    public Bairro(Integer idbairro, int codigo, String nome) {
        this.idbairro = idbairro;
        this.codigo = codigo;
        this.nome = nome;
    }

    public Integer getIdbairro() {
        return idbairro;
    }

    public void setIdbairro(Integer idbairro) {
        this.idbairro = idbairro;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @XmlTransient
    public Collection<Comentario> getComentarioCollection() {
        return comentarioCollection;
    }

    public void setComentarioCollection(Collection<Comentario> comentarioCollection) {
        this.comentarioCollection = comentarioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idbairro != null ? idbairro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bairro)) {
            return false;
        }
        Bairro other = (Bairro) object;
        if ((this.idbairro == null && other.idbairro != null) || (this.idbairro != null && !this.idbairro.equals(other.idbairro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidade.Bairro[ idbairro=" + idbairro + " ]";
    }
    
}
