package ar.com.ada.api.pagada.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pago_id")
    private Integer pagoId;
    @Column(name = "fecha_pago")
    private Date fechaPago;
    @Column(name = "importe_pagado")
    private BigDecimal importePagado;
    private String moneda;
    @Column(name = "medio_pago")
    private MedioPagoEnum medioPago;
    @Column(name = "info_medio_pago")
    private String infoMedioPago;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "servicio_id", referencedColumnName = "servicio_id")
    private Servicio servicio;

    public enum MedioPagoEnum {
        TARJETA, TRANSFERENCIA, DEPOSITO, ADADIGITAL
    }

    public Integer getPagoId() {
        return pagoId;
    }

    public void setPagoId(Integer pagoId) {
        this.pagoId = pagoId;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public BigDecimal getImportePagado() {
        return importePagado;
    }

    public void setImportePagado(BigDecimal importePagado) {
        this.importePagado = importePagado;
    }

    public MedioPagoEnum getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(MedioPagoEnum medioPago) {
        this.medioPago = medioPago;
    }

    public String getInfoMedioPago() {
        return infoMedioPago;
    }

    public void setInfoMedioPago(String infoMedioPago) {
        this.infoMedioPago = infoMedioPago;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String ofuscarInfoMedioPago() {
        int longitud = this.getInfoMedioPago().length();
        String infoOfuscada = this.getInfoMedioPago().substring(longitud - 3);
        return "*" + infoOfuscada;
    }
/* Otra forma(mas procedural)
    public String ofuscarInfoMedioPago(){
        String newInfo = new String();
        for (int i = (this.getInfoMedioPago().length()-1); i > (this.getInfoMedioPago().length()-4); i--) {
            newInfo = this.getInfoMedioPago().charAt(i) + newInfo;
        }
        return "*" + newInfo;
    }
    */
}