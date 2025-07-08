package com.example.Backend.Core.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class FilterParams {
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date fechaInicio;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date fechaFin;
    public FilterParams() {}
    public FilterParams(Date inicio, Date fin) {
        this.fechaInicio = inicio;
        this.fechaFin = fin;
    }
    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date f) { this.fechaInicio = f; }
    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date f) { this.fechaFin = f; }
}