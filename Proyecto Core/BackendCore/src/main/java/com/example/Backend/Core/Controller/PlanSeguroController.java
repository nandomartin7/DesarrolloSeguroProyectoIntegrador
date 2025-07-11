package com.example.Backend.Core.Controller;

import com.example.Backend.Core.Models.PlanSeguro;
import com.example.Backend.Core.Service.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plan")
public class PlanSeguroController {

    private final PlanService planService;

    public PlanSeguroController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping("/info")
    public String index(){
        return "Conectado a la tabla Plan Seguro";
    }

    @PreAuthorize("hasAnyRole('cliente','admin','empleado')")
    @GetMapping()
    public List<PlanSeguro> getAllPlan(){
        return planService.findAll();
    }

    @PreAuthorize("hasAnyRole('cliente','admin','empleado')")
    @GetMapping("/{idPlan}")
    public ResponseEntity<PlanSeguro> getPlanByIdPlan(@PathVariable int idPlan){
        PlanSeguro planSeguro = planService.findByIdPlan(idPlan);
        return planSeguro != null ? ResponseEntity.ok(planSeguro) : ResponseEntity.notFound().build();
    }

    /*@PostMapping
    public ResponseEntity<PlanSeguro> registrarPlan(@RequestBody PlanSeguro planSeguro) throws Exception{
        return new ResponseEntity<>(planService.registrarPlan(planSeguro), HttpStatus.CREATED);
    }*/

    @PostMapping
    public ResponseEntity<PlanSeguro> registrarPlan(@RequestBody PlanSeguro planSeguro) throws Exception{
        PlanSeguro nuevoPlan = planSeguro;

        return new ResponseEntity<>(planService.registrarPlan(nuevoPlan.getNombre(),
                nuevoPlan.isPerdidasParciales(), nuevoPlan.getValorPerdidasParciales(),
                nuevoPlan.isPerdidasTotales(), nuevoPlan.getValorPerdidasTotales(),
                nuevoPlan.isAuxilioMecanico(), nuevoPlan.isMantenimientoVehicular(), nuevoPlan.getValorPlan()), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('cliente','admin','empleado')")
    @PutMapping("/{idPlan}")
    public ResponseEntity<PlanSeguro> updatePlan(@PathVariable int idPlan, @RequestBody PlanSeguro planSeguro) throws Exception{
        PlanSeguro actualizado = planService.updatePlan(idPlan,planSeguro);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyRole('cliente','admin','empleado')")
    @DeleteMapping("/{idPlan}")
    public ResponseEntity<Void> deletePlan(@PathVariable int idPlan) throws Exception{
        return planService.deletePlan(idPlan) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
