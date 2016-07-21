package Entidade;

import Entidade.Comentario;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-20T16:08:39")
@StaticMetamodel(Bairro.class)
public class Bairro_ { 

    public static volatile SingularAttribute<Bairro, Integer> codigo;
    public static volatile SingularAttribute<Bairro, String> nome;
    public static volatile SingularAttribute<Bairro, Integer> idbairro;
    public static volatile CollectionAttribute<Bairro, Comentario> comentarioCollection;

}