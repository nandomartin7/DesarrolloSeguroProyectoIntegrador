package com.example.MiniCore.Service;

import com.example.MiniCore.Models.Empleado;
import com.example.MiniCore.Repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {
    @Autowired
    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public List<Empleado> findAll(){
        return empleadoRepository.findAll();
    }

    public Empleado registrarEmpleado(Empleado empleado, String encodedPassword) throws Exception{
        //Validacion de que no existan empleados con el mismo idEmpleado
        Empleado existe = findById(empleado.getIdEmpleado());
        if (existe != null){
            throw new Exception("El empleado con cedula "+empleado.getIdEmpleado()+" ya esta registrado");
        }

        //Validacion del idCliente o cedula
        if (!empleado.getIdEmpleado().matches("^\\d{10}$")){
            throw new Exception("Formato de cedula inválido: "+empleado.getIdEmpleado());
        }

        //Validacion del nombre y apellido
        if (!empleado.getNombre().matches("[A-Za-záéíóúÁÉÍÓÚñÑ ]+")) {
            throw new Exception("El nombre contiene caracteres no permitidos: " + empleado.getNombre());
        }
        if (!empleado.getApellido().matches("[A-Za-záéíóúÁÉÍÓÚñÑ ]+")) {
            throw new Exception("El apellido contiene caracteres no permitidos: " + empleado.getApellido());
        }

        //Validacion en telefono
        if (!empleado.getTelefono().matches("^\\d{10}$")){
            throw new Exception("El telefono contiene caracteres que no son numéricos");
        }

        //Validacion formato del correo
        if (!empleado.getCorreo().matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w+$")){
            throw new Exception("Formato de correo electronico inválido: "+ empleado.getCorreo());
        }

        //Validar correo duplicado
        if (empleadoRepository.findByCorreo(empleado.getCorreo()) != null){
            throw new Exception("El correo ya esta registrado en otra cuenta: "+empleado.getCorreo());
        }

        empleado.setPassword(encodedPassword);
        return empleadoRepository.save(empleado);
    }


    public Empleado findById(String idEmpleado){
        return empleadoRepository.findByIdEmpleado(idEmpleado);
    }
}
