package Entidade;

import Entidade.Comentario;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-20T16:08:39")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, String> senha;
    public static volatile SingularAttribute<User, Integer> iduser;
    public static volatile SingularAttribute<User, byte[]> foto;
    public static volatile SingularAttribute<User, String> fotoTipo;
    public static volatile SingularAttribute<User, String> login;
    public static volatile SingularAttribute<User, String> email;
    public static volatile CollectionAttribute<User, Comentario> comentarioCollection;
    public static volatile SingularAttribute<User, Integer> cep;

}