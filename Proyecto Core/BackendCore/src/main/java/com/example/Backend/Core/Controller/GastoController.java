package com.example.Backend.Core.Controller;

import com.example.Backend.Core.Client.GastoSecureClient;
import com.example.Backend.Core.DTO.GastoDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gasto")
public class GastoController {


    private GastoSecureClient secureClient;

    @PreAuthorize("hasAnyRole('admin','empleado')")
    @GetMapping("/filtrar-kms")
    public ResponseEntity<Map<String, List<GastoDto>>> filtrarConKms(
            @RequestParam("fechaInicio") @DateTimeFormat(pattern="yyyy-MM-dd") Date inicio,
            @RequestParam("fechaFin")    @DateTimeFormat(pattern="yyyy-MM-dd") Date fin
    ) throws Exception {
        Map<String,List<GastoDto>> result = secureClient.filtrar(inicio, fin);
        return ResponseEntity.ok(result);
    }

}
