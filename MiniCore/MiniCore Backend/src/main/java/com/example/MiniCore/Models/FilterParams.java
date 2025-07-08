package com.example.MiniCore.Models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class FilterParams {
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date fechaInicio;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date fechaFin;
    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date f) { this.fechaInicio = f; }
    public Date getFechaFin()    { return fechaFin;    }
    public void setFechaFin(Date f) { this.fechaFin = f; }
}