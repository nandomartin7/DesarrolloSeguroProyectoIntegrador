package com.example.MiniCore.Controller;

import com.example.MiniCore.Models.Empleado;
import com.example.MiniCore.Service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empleado")
public class EmpleadoController {
    @Autowired
    private final EmpleadoService empleadoService;
    private final PasswordEncoder passwordEncoder;


    public EmpleadoController(EmpleadoService empleadoService, PasswordEncoder passwordEncoder) {
        this.empleadoService = empleadoService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/info")
    public String index(){
        return "Conectado a la tabla de Empleados";
    }

    @PreAuthorize("hasAnyRole('admin','empleado')")
    @GetMapping()
    public List<Empleado> getAllEmpleado(){
        return empleadoService.findAll();
    }

    @PostMapping
    public ResponseEntity<Empleado> registrarEmpleado(@RequestBody Empleado empleado) throws Exception {
        String encodedPassword = passwordEncoder.encode(empleado.getPassword());
        return new ResponseEntity<>(empleadoService.registrarEmpleado(empleado, encodedPassword), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Empleado empleado){
        Empleado existe = empleadoService.findById(empleado.getIdEmpleado());
        if (existe != null && passwordEncoder.matches(empleado.getPassword(), existe.getPassword())){
            return ResponseEntity.ok("Inicio de sesion exitoso");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales Invalidas");
    }



}
