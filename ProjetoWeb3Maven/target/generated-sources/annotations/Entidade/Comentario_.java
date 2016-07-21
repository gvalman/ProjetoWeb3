package Entidade;

import Entidade.Bairro;
import Entidade.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-20T16:08:39")
@StaticMetamodel(Comentario.class)
public class Comentario_ { 

    public static volatile SingularAttribute<Comentario, Date> horaFim;
    public static volatile SingularAttribute<Comentario, String> tipo;
    public static volatile SingularAttribute<Comentario, User> userIduser;
    public static volatile SingularAttribute<Comentario, byte[]> foto;
    public static volatile SingularAttribute<Comentario, Date> dataFim;
    public static volatile SingularAttribute<Comentario, String> titulo;
    public static volatile SingularAttribute<Comentario, String> fotoTipo;
    public static volatile SingularAttribute<Comentario, Date> dataInicio;
    public static volatile SingularAttribute<Comentario, Bairro> bairroIdbairro;
    public static volatile SingularAttribute<Comentario, Integer> idcomentario;
    public static volatile SingularAttribute<Comentario, Date> horaInicio;
    public static volatile SingularAttribute<Comentario, String> descricao;

}