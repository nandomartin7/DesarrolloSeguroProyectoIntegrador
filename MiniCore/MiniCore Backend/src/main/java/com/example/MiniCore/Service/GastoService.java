package com.example.MiniCore.Service;

import com.example.MiniCore.Models.Departamento;
import com.example.MiniCore.Models.Empleado;
import com.example.MiniCore.Models.Gasto;
import com.example.MiniCore.Repository.GastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GastoService {
    @Autowired
    private final GastoRepository gastoRepository;
    private final DepartamentoService departamentoService;
    private final EmpleadoService empleadoService;

    public GastoService(GastoRepository gastoRepository, DepartamentoService departamentoService, EmpleadoService empleadoService) {
        this.gastoRepository = gastoRepository;
        this.departamentoService = departamentoService;
        this.empleadoService = empleadoService;
    }

    public List<Gasto> findAll(){
        return gastoRepository.findAll();
    }

    public Gasto registrarGasto(Gasto gasto){
        Empleado empleado = empleadoService.findById(gasto.getEmpleado().getIdEmpleado());
        gasto.setEmpleado(empleado);
        Departamento departamento = departamentoService.findById(gasto.getDepartamento().getIdDepartamento());
        gasto.setDepartamento(departamento);
        gasto.setFecha(new Date());
        gastoRepository.save(gasto);
        return gasto;
    }

    public Gasto findById(Long id){
        return gastoRepository.findByIdGasto(id);
    }

    public Gasto update(Long id, Gasto gasto){
        Gasto existeGasto = findById(id);
        if (existeGasto != null){
            existeGasto.setDescripcion(gasto.getDescripcion());
            existeGasto.setMonto(gasto.getMonto());
            gastoRepository.save(existeGasto);
        }
        return existeGasto;
    }

    public Boolean delete(Long id){
        Gasto existeGasto = findById(id);
        if (existeGasto != null){
            gastoRepository.deleteById(existeGasto.getIdGasto());
            return true;
        }
        return false;
    }

    public Map<String, List<Gasto>> filtrarPorRango(Date inicio, Date fin) {
        List<Gasto> gastosEnRango = findAll().stream()
                .filter(g -> !g.getFecha().before(inicio) && !g.getFecha().after(fin))
                .collect(Collectors.toList());

        Map<String, List<Gasto>> map = new HashMap<>();
        for (Gasto g : gastosEnRango) {
            String key = g.getDepartamento().getNombre();
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(g);
        }
        return map;
    }
}
