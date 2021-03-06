package Entidade;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByIduser", query = "SELECT u FROM User u WHERE u.iduser = :iduser"),
    @NamedQuery(name = "User.findByLogin", query = "SELECT u FROM User u WHERE u.login = :login"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByLoginBySenha", query = "SELECT u FROM User u WHERE u.login = :login AND u.senha = :senha"),
    @NamedQuery(name = "User.findAllExceptAdm", query = "SELECT u FROM User u WHERE u.iduser NOT IN (:iduser)"),
    @NamedQuery(name = "User.findByCep", query = "SELECT u FROM User u WHERE u.cep = :cep"),
    @NamedQuery(name = "User.findBySenha", query = "SELECT u FROM User u WHERE u.senha = :senha"),
    @NamedQuery(name = "User.findByFotoTipo", query = "SELECT u FROM User u WHERE u.fotoTipo = :fotoTipo"),
    @NamedQuery(name = "User.findByTipo", query = "SELECT u FROM User u WHERE u.tipo = :tipo")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iduser")
    private Integer iduser;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "login")
    private String login;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="E-mail inválido")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cep")
    private int cep;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "senha")
    private String senha;
    @Lob
    @Column(name = "foto")
    private byte[] foto;
    @Size(max = 45)
    @Column(name = "fotoTipo")
    private String fotoTipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tipo")
    private String tipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userIduser")
    private Collection<Comentario> comentarioCollection;

    public User() {
    }

    public User(Integer iduser) {
        this.iduser = iduser;
    }

    public User(Integer iduser, String login, String email, int cep, String senha, String tipo) {
        this.iduser = iduser;
        this.login = login;
        this.email = email;
        this.cep = cep;
        this.senha = senha;
        this.tipo = tipo;
    }

    public Integer getIduser() {
        return iduser;
    }

    public void setIduser(Integer iduser) {
        this.iduser = iduser;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
        hash += (iduser != null ? iduser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.iduser == null && other.iduser != null) || (this.iduser != null && !this.iduser.equals(other.iduser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidade.User[ iduser=" + iduser + " ]";
    }

    public String ConversorFoto() {
        return "data:" + fotoTipo + ";base64," + Base64.encode(foto);
    }

    public int ContComentario(String tipo) {
        int cont = 0;
        for (Comentario i : comentarioCollection) {
            if (tipo.equals(i.getTipo())) {
                cont += 1;
            }
        }
        return cont;
    }

    public List<Comentario> ListaComentarioByTipo(String tipo) {
        List<Comentario> lista = new ArrayList<>();
        for (Comentario i : comentarioCollection) {
            if (tipo.equals(i.getTipo())) {
                lista.add(i);
            }
        }
        return lista;
    }
}
