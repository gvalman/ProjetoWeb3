package Controlador;

import java.io.Serializable;
import javax.ejb.SessionContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "ControladorMB")
@RequestScoped
public class ControladorMB implements Serializable {

    private String msm, pagina1;

    public ControladorMB() {
        msm = "TESTANDO";
    }

    /**
     * @return the msm
     */
    public String getMsm() {
        return msm;
    }

    /**
     * @param msm the msm to set
     */
    public void setMsm(String msm) {
        this.msm = msm;
    }

    /**
     * @return the pagina1
     */
    public String getPagina1() {
        return pagina1;
    }

    /**
     * @param pagina1 the pagina1 to set
     */
    public void setPagina1(String pagina1) {
        this.pagina1 = pagina1;
    }

}
