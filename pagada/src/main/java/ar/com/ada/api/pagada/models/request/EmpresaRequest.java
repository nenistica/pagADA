package ar.com.ada.api.pagada.models.request;

import ar.com.ada.api.pagada.entities.Pais.TipoIdImpositivoEnum;

public class EmpresaRequest {

    public Integer paisId;
    public TipoIdImpositivoEnum tipoIdImpositivo;
    public String idImpositivo;
    public String nombre;

}