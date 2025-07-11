package com.example.Backend.Core.Controller;

import com.example.Backend.Core.Models.AnalisisContrato;
import com.example.Backend.Core.Models.Cliente;
import com.example.Backend.Core.Models.Contrato;
import com.example.Backend.Core.Service.AnalisisContratoService;
import com.example.Backend.Core.Service.ContratoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/contrato")
public class ContratoController {

    private final ContratoService contratoService;
    private final AnalisisContratoService analisisContratoService;

    public ContratoController(ContratoService contratoService, AnalisisContratoService analisisContratoService) {
        this.contratoService = contratoService;
        this.analisisContratoService = analisisContratoService;
    }

    @GetMapping("/info")
    public String index(){
        return "Conectado a la tabla Contrato";
    }

    @PreAuthorize("hasAnyRole('cliente','admin','empleado')")
    @GetMapping()
    public List<Contrato> getAllContratos(){
        return contratoService.findAll();
    }

    @PreAuthorize("hasAnyRole('cliente','admin','empleado')")
    @GetMapping("/{idContrato}")
    public ResponseEntity<Contrato> getByIdContrato(@PathVariable Long idContrato){
        Contrato contrato = contratoService.findByIdContrato(idContrato);
        return contrato != null ? ResponseEntity.ok(contrato) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Contrato> registarContrato(@RequestBody Contrato contrato) throws Exception{
        return new ResponseEntity<>(contratoService.registrarContrato(contrato), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('cliente','admin','empleado')")
    @PutMapping("/{idContrato}")
    public ResponseEntity<Contrato> updateContrato(@PathVariable Long idContrato, @RequestBody Contrato contrato) throws Exception{
        Contrato actualizado = contratoService.updateContrato(idContrato, contrato);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyRole('cliente','admin','empleado')")
    @DeleteMapping("/{idContrato}")
    public ResponseEntity<Void> deleteContrato(@PathVariable Long idContrato) throws Exception{
        return contratoService.deleteContrato(idContrato) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyRole('cliente','admin','empleado')")
    @GetMapping("/filtrar/plan")
    public List<Contrato> filtrarByPlan (@RequestParam int planSeguro){
        return contratoService.findByPlan(planSeguro);
    }

    @PreAuthorize("hasAnyRole('cliente','admin','empleado')")
    @GetMapping("/filtrar/fecha")
    public List<Contrato> filtrarByFecha (@RequestParam("fechaInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date FechaInicio,
                                          @RequestParam("fechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date FecahFin){
        return contratoService.findByFechas(FechaInicio,FecahFin);
    }

    @PreAuthorize("hasAnyRole('cliente','admin','empleado')")
    @GetMapping("/filtrar/cliente")
    public List<Contrato> filtrarByCliente (@RequestParam String idcliente){
        return  contratoService.findByCliente(idcliente);
    }

    @PreAuthorize("hasAnyRole('cliente','admin','empleado')")
    @GetMapping("/filtrar/automovil")
    public Contrato filtrarByAutomovil (@RequestParam String idAutomovil){
        return  contratoService.findByAutomovil(idAutomovil);
    }

    @PreAuthorize("hasAnyRole('cliente','admin','empleado')")
    @GetMapping("/analisis")
    public ResponseEntity<AnalisisContrato> realizarAnalisis(@RequestParam Long idContrato) {
        try {
            Contrato contrato = contratoService.findByIdContrato(idContrato);
            if (contrato == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            AnalisisContrato analisisContrato = analisisContratoService.realizarAnalisisContrato(contrato);
            return ResponseEntity.ok(analisisContrato);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PreAuthorize("hasAnyRole('cliente','admin','empleado')")
    @PutMapping("/analisis/ajuste")
    public ResponseEntity<Contrato> ajustarContrato( @RequestParam Long idContrato, @RequestParam String motivoAgregado, @RequestParam double penalidad) throws Exception{
        try {
            Contrato contratoActualizado = contratoService.ajusteContrato(idContrato, motivoAgregado, penalidad);
            return ResponseEntity.ok(contratoActualizado);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
