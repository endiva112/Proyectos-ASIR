package com.enrique_gabriel.empresa_api.entity;

public class MovimientoDinero {
    private double monto;
    private String concepto;

    public MovimientoDinero(double monto, String concepto) {
        this.monto = monto;
        this.concepto = concepto;
    }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public String getConcepto() { return concepto; }
    public void setConcepto(String concepto) { this.concepto = concepto; }
}