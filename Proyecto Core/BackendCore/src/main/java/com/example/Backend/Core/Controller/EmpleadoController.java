package com.example.Backend.Core.Controller;

import com.example.Backend.Core.Models.Cliente;
import com.example.Backend.Core.Models.Empleado;
import com.example.Backend.Core.Service.EmpleadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empleado")
public class EmpleadoController {

    private final EmpleadoService empleadoService;
    private final PasswordEncoder passwordEncoder;

    public EmpleadoController(EmpleadoService empleadoService, PasswordEncoder passwordEncoder) {
        this.empleadoService = empleadoService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/info")
    public String index() {
        return "Conectado a la tabla Empleado";
    }

    @PreAuthorize("hasAnyRole('cliente','admin','empleado')")
    @GetMapping()
    public List<Empleado> getAllEmpleados() {
        return empleadoService.findAll();
    }

    @PreAuthorize("hasAnyRole('cliente','admin','empleado')")
    @GetMapping("/{idEmpleado}")
    public ResponseEntity<Empleado> getEmpleadoById(@PathVariable String idEmpleado){
        Empleado empleado = empleadoService.findByIdEmpleado(idEmpleado);
        return empleado != null ? ResponseEntity.ok(empleado) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Empleado> registrarEmpleado(@RequestBody Empleado empleado) throws Exception {
        String encodedPassword = passwordEncoder.encode(empleado.getPassword());
        return new ResponseEntity<>(empleadoService.registrarEmpleado(empleado, encodedPassword), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('cliente','admin','empleado')")
    @PutMapping("/{idEmpleado}")
    public ResponseEntity<Empleado> updateEmpleado(@PathVariable String idEmpleado, @RequestBody Empleado empleado) throws Exception{
        Empleado actualizado = empleadoService.updateEmpleado(idEmpleado, empleado);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyRole('cliente','admin','empleado')")
    @DeleteMapping("/{idEmpleado}")
    public ResponseEntity<Void> deleteEmpleado (@PathVariable String idEmpleado) throws Exception{
        return empleadoService.deleteEmpleado(idEmpleado) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Empleado empleado){
        Empleado existe = empleadoService.findByIdEmpleado(empleado.getIdEmpleado());
        if (existe != null && passwordEncoder.matches(empleado.getPassword(), existe.getPassword())){
            return ResponseEntity.ok("Inicio de sesion exitoso");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales Invalidas");
    }
}
