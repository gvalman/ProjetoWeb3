package Controlador;

import java.io.Serializable;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean(name = "aplicacaoMB", eager = true)
@ApplicationScoped
public class AplicacaoMB implements Serializable {

    private String linguagem = "";
    private String pais = "";

    private Locale CurrentLocal;

    /**
     * Creates a new instance of AplicacaoMB
     */
    public AplicacaoMB() {
        CurrentLocal = FacesContext.getCurrentInstance().getViewRoot().getLocale();
    }

    @PostConstruct
    public void init() {

    }

    public String mudarIdioma() {
        this.mudarLocalidade(new Locale(linguagem, pais));
        return null;
    }

    private void mudarLocalidade(Locale locale) {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
        CurrentLocal = locale;
    }

    /**
     * Metodos get e set
     */
    public String getLinguagem() {
        return linguagem;
    }

    public void setLinguagem(String linguagem) {
        this.linguagem = linguagem;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * @return the CurrentLocal
     */
    public Locale getCurrentLocal() {
        return CurrentLocal;
    }

    /**
     * @param CurrentLocal the CurrentLocal to set
     */
    public void setCurrentLocal(Locale CurrentLocal) {
        this.CurrentLocal = CurrentLocal;
    }
}
