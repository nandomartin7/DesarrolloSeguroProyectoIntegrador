package com.example.Backend.Core.Controller;

import com.example.Backend.Core.Models.Automovil;
import com.example.Backend.Core.Models.UsoSeguro;
import com.example.Backend.Core.Service.UsoSeguroService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/uso")
public class UsoSeguroController {

    private final UsoSeguroService usoSeguroService;

    public UsoSeguroController(UsoSeguroService usoSeguroService) {
        this.usoSeguroService = usoSeguroService;
    }

    @GetMapping("/info")
    public String index(){
        return "Conectado a la tabla Uso Seguro";
    }

    @PreAuthorize("hasAnyRole('cliente','admin','empleado')")
    @GetMapping("")
    public List<UsoSeguro> getAllUsosSeguro(){
        return usoSeguroService.findAll();
    }

    @PreAuthorize("hasAnyRole('cliente','admin','empleado')")
    @GetMapping("/{idUso}")
    public ResponseEntity<UsoSeguro> getUsoByIdUso(@PathVariable int idUso){
        UsoSeguro usoSeguro = usoSeguroService.findByIdUso(idUso);
        return usoSeguro != null ? ResponseEntity.ok(usoSeguro) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public  ResponseEntity<UsoSeguro> registarUsoSeguro(@RequestBody UsoSeguro usoSeguro) throws Exception {
        return new ResponseEntity<>(usoSeguroService.registrarUso(usoSeguro), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('cliente','admin','empleado')")
    @PutMapping("/{idUso}")
    public ResponseEntity<UsoSeguro> updateUsoSeguro(@PathVariable int idUso, @RequestBody UsoSeguro usoSeguro) throws Exception{
        UsoSeguro actualizado = usoSeguroService.updateUso(idUso, usoSeguro);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyRole('cliente','admin','empleado')")
    @DeleteMapping("/{idUso}")
    public ResponseEntity<Void> deleteUsoSeguro (@PathVariable int idUso) throws Exception{
        return usoSeguroService.deleteUso(idUso) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyRole('cliente','admin','empleado')")
    @GetMapping("/filtrar/fecha")
    public List<UsoSeguro> filtrarByFecha(@RequestParam("fechaInicio") @DateTimeFormat(pattern = "yyyy-MM-dd")  Date fechaInicio,
                                          @RequestParam("fechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin){
        return usoSeguroService.findByFechas(fechaInicio,fechaFin);
    }

    @PreAuthorize("hasAnyRole('cliente','admin','empleado')")
    @GetMapping("/filtrar/contrato")
    public List<UsoSeguro> filtrarByContrato(@RequestParam Long idContrato){
        return usoSeguroService.findByContrato(idContrato);
    }

    @PreAuthorize("hasAnyRole('cliente','admin','empleado')")
    @GetMapping("/filtrar/tipoUso")
    public Map<Integer, Map<String,List<UsoSeguro>>> filtrarByTipoUso (@RequestParam("fechaInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
                                                                       @RequestParam("fechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin){
        return usoSeguroService.findByTipoUsosFechas(fechaInicio,fechaFin);
    }
}
