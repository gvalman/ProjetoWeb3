/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidade;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author german
 */
@Entity
@Table(name = "comentario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comentario.findAll", query = "SELECT c FROM Comentario c"),
    @NamedQuery(name = "Comentario.findByIdcomentario", query = "SELECT c FROM Comentario c WHERE c.idcomentario = :idcomentario"),
    @NamedQuery(name = "Comentario.findByTitulo", query = "SELECT c FROM Comentario c WHERE c.titulo = :titulo"),
    @NamedQuery(name = "Comentario.findByDescricao", query = "SELECT c FROM Comentario c WHERE c.descricao = :descricao"),
    @NamedQuery(name = "Comentario.findByDataInicio", query = "SELECT c FROM Comentario c WHERE c.dataInicio = :dataInicio"),
    @NamedQuery(name = "Comentario.findByHoraInicio", query = "SELECT c FROM Comentario c WHERE c.horaInicio = :horaInicio"),
    @NamedQuery(name = "Comentario.findByTipo", query = "SELECT c FROM Comentario c WHERE c.tipo = :tipo"),
    @NamedQuery(name = "Comentario.findByTipoByBairro", query = "SELECT c FROM Comentario c WHERE (c.tipo = :tipo AND c.bairroIdbairro.codigo = :CodBairro AND c.dataFim IS NULL)"),
    @NamedQuery(name = "Comentario.findAllByUser", query = "SELECT c FROM Comentario c WHERE c.userIduser.iduser = :iduser"),
    @NamedQuery(name = "Comentario.findByDataFim", query = "SELECT c FROM Comentario c WHERE c.dataFim = :dataFim"),
    @NamedQuery(name = "Comentario.findByHoraFim", query = "SELECT c FROM Comentario c WHERE c.horaFim = :horaFim"),
    @NamedQuery(name = "Comentario.findByFotoTipo", query = "SELECT c FROM Comentario c WHERE c.fotoTipo = :fotoTipo")})
public class Comentario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcomentario")
    private Integer idcomentario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "descricao")
    private String descricao;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dataInicio")
    @Temporal(TemporalType.DATE)
    private Date dataInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "horaInicio")
    @Temporal(TemporalType.TIME)
    private Date horaInicio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "dataFim")
    @Temporal(TemporalType.DATE)
    private Date dataFim;
    @Column(name = "horaFim")
    @Temporal(TemporalType.TIME)
    private Date horaFim;
    @Lob
    @Column(name = "foto")
    private byte[] foto;
    @Size(max = 45)
    @Column(name = "fotoTipo")
    private String fotoTipo;
    @JoinColumn(name = "user_iduser", referencedColumnName = "iduser")
    @ManyToOne(optional = false)
    private User userIduser;
    @JoinColumn(name = "bairro_idbairro", referencedColumnName = "idbairro")
    @ManyToOne(optional = false)
    private Bairro bairroIdbairro;
    

    public Comentario() {
    }

    public Comentario(Integer idcomentario) {
        this.idcomentario = idcomentario;
    }

    public Comentario(Integer idcomentario, String titulo, String descricao, Date dataInicio, Date horaInicio, String tipo) {
        this.idcomentario = idcomentario;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.horaInicio = horaInicio;
        this.tipo = tipo;
    }

    public Integer getIdcomentario() {
        return idcomentario;
    }

    public void setIdcomentario(Integer idcomentario) {
        this.idcomentario = idcomentario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Date getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(Date horaFim) {
        this.horaFim = horaFim;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoTipo() {
        return fotoTipo;
    }

    public void setFotoTipo(String fotoTipo) {
        this.fotoTipo = fotoTipo;
    }

    public User getUserIduser() {
        return userIduser;
    }

    public void setUserIduser(User userIduser) {
        this.userIduser = userIduser;
    }

    public Bairro getBairroIdbairro() {
        return bairroIdbairro;
    }

    public void setBairroIdbairro(Bairro bairroIdbairro) {
        this.bairroIdbairro = bairroIdbairro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcomentario != null ? idcomentario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comentario)) {
            return false;
        }
        Comentario other = (Comentario) object;
        if ((this.idcomentario == null && other.idcomentario != null) || (this.idcomentario != null && !this.idcomentario.equals(other.idcomentario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidade.Comentario[ idcomentario=" + idcomentario + " ]";
    }
    
    public String ConversorFoto() {
        return "data:" + fotoTipo +";base64,"+ Base64.encode(foto);
    }
}
