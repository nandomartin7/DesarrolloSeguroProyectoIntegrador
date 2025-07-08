package com.example.Backend.Core.Controller;

import com.example.Backend.Core.Models.Admin;
import com.example.Backend.Core.Models.Cliente;
import com.example.Backend.Core.Service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteService clienteService;
    private final PasswordEncoder passwordEncoder;

    public ClienteController(ClienteService clienteService, PasswordEncoder passwordEncoder) {
        this.clienteService = clienteService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/info")
    public  String index(){
        return "Conectado a la tabla Cliente";
    }

    @PreAuthorize("hasAnyRole('cliente','admin','empleado')")
    @GetMapping()
    public List<Cliente> getAllClientes(){
        return clienteService.findAll();
    }

    @PreAuthorize("hasAnyRole('cliente','admin','empleado')")
    @GetMapping("/{idCliente}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable String idCliente){
        Cliente cliente = clienteService.findByIdCliente(idCliente);
        return cliente != null ? ResponseEntity.ok(cliente) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Cliente> registarCliente(@RequestBody Cliente cliente) throws Exception {
        String encodedPassword = passwordEncoder.encode(cliente.getPassword());
        return new ResponseEntity<>(clienteService.registrarCliente(cliente, encodedPassword), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('cliente','admin','empleado')")
    @PutMapping("/{idCliente}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable String idCliente, @RequestBody Cliente cliente) throws Exception{
        Cliente actualizado = clienteService.updateCliente(idCliente, cliente);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyRole('cliente','admin','empleado')")
    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Void> deleteCliente (@PathVariable String idCliente) throws Exception{
        return clienteService.deleteCliente(idCliente) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Cliente cliente){
        Cliente existe = clienteService.findByIdCliente(cliente.getIdCliente());
        Cliente referencia = new Cliente();
        if (existe != null && passwordEncoder.matches(cliente.getPassword(), existe.getPassword())){
            referencia.setIdCliente(existe.getIdCliente());
            referencia.setNombre(existe.getNombre());
            referencia.setApellido(existe.getApellido());
            referencia.setCorreo(existe.getCorreo());
            referencia.setDireccion(existe.getDireccion());
            referencia.setTelefono(existe.getTelefono());

            return ResponseEntity.ok(referencia);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales Invalidas");
    }
}
