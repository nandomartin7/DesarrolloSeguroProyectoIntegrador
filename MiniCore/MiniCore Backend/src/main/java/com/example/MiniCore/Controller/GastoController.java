package com.example.MiniCore.Controller;

import com.example.MiniCore.Models.*;
import com.example.MiniCore.Service.CryptoService;
import com.example.MiniCore.Service.GastoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/gasto")
public class GastoController {
    @Autowired
    private final GastoService gastoService;
    private final CryptoService crypto;
    private final ObjectMapper mapper = new ObjectMapper();

    public GastoController(GastoService gastoService, CryptoService crypto) {
        this.gastoService = gastoService;
        this.crypto = crypto;
    }

    @GetMapping("/info")
    public String index(){
        return "Conectado a la tabla de Gastos";
    }

    @PreAuthorize("hasAnyRole('admin','empleado')")
    @GetMapping()
    public List<Gasto> getAllGasto(){
        return gastoService.findAll();
    }

    @PreAuthorize("hasAnyRole('admin','empleado')")
    @PostMapping()
    public ResponseEntity<String> registrarGasto(@RequestBody Gasto gasto){
        gastoService.registrarGasto(gasto);
        return ResponseEntity.ok("Registro de gasto exitoso");
    }

    @PreAuthorize("hasAnyRole('admin','empleado')")
    @PutMapping("/{idGasto}")
    public ResponseEntity<Gasto> actualizarGasto(@PathVariable Long id, @RequestBody Gasto gasto){
        Gasto actualizado = gastoService.update(id,gasto);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyRole('admin','empleado')")
    @DeleteMapping("/{idGasto}")
    public ResponseEntity<Void> eliminarGasto (@PathVariable Long id){
        return gastoService.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyRole('admin','empleado')")
    @GetMapping("/filtrar")
    public Map<String, List<Gasto>> ListarDepartamentosConGastos (
            @RequestParam("fechaInicio") @DateTimeFormat(pattern = "yyyy-MM-dd")Date fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd")Date fechaFin){

        List<Gasto> gastosEnRango = gastoService.findAll().stream()
                .filter(gasto -> gasto.getFecha().compareTo(fechaInicio)>=0 && gasto.getFecha().compareTo(fechaFin)<=0)
                .collect(Collectors.toList());

        Map<String, List<Gasto>> departamentosGastosMap = new HashMap<>();

        for (Gasto gasto : gastosEnRango){
            Departamento departamento = gasto.getDepartamento();

            if (departamento != null){
                //Crear una clave única para cada departamento
                String departamentoKey = departamento.getNombre();

                //Si ya existe una entrada para este departamento, añade el gasto a la lista
                departamentosGastosMap.putIfAbsent(departamentoKey, new ArrayList<>());
                departamentosGastosMap.get(departamentoKey).add(gasto);
            }
        }
        return departamentosGastosMap;
    }

    @PreAuthorize("hasAnyRole('admin','empleado')")
    @PostMapping("/secure/filtrar")
    public ResponseEntity<EncryptedResponse> filtrarCifrado(@RequestBody EncryptedRequest req) throws Exception {
        // 1️⃣ Desciframos el JSON con CryptoService
        String jsonParams = crypto.decrypt(req.getCiphertext());

        // 2️⃣ Lo convertimos a FilterParams
        FilterParams params = mapper.readValue(jsonParams, FilterParams.class);

        // 3️⃣ Llamamos al service que acabamos de extraer
        Map<String, List<Gasto>> resultados =
                gastoService.filtrarPorRango(params.getFechaInicio(), params.getFechaFin());

        // 4️⃣ Serializamos la respuesta y volvemos a cifrarla
        String jsonResult = mapper.writeValueAsString(resultados);
        String ciphertext = crypto.encrypt(jsonResult);

        // 5️⃣ Devolvemos solo el ciphertext
        return ResponseEntity.ok(new EncryptedResponse(ciphertext));
    }
}
