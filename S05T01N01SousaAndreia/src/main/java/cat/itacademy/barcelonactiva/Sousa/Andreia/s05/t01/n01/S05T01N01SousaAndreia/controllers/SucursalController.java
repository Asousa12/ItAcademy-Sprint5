package cat.itacademy.barcelonactiva.Sousa.Andreia.s05.t01.n01.S05T01N01SousaAndreia.controllers;

import java.util.List;

import cat.itacademy.barcelonactiva.Sousa.Andreia.s05.t01.n01.S05T01N01SousaAndreia.dto.SucursalDTO;
import cat.itacademy.barcelonactiva.Sousa.Andreia.s05.t01.n01.S05T01N01SousaAndreia.services.SucursalServices;
import cat.itacademy.barcelonactiva.Sousa.Andreia.s05.t01.n01.S05T01N01SousaAndreia.services.SucursalServicesImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/sucursal")
public class SucursalController {

    private final SucursalServices sucursalServices;

    @Autowired
    public SucursalController(SucursalServices sucursalServices) {
        this.sucursalServices = sucursalServices;
    }

    @PostMapping("/add")
    public ResponseEntity<SucursalDTO> createSucursal(@RequestBody SucursalDTO sucursalDTO) {
        SucursalDTO savedSucursalDTO = sucursalServices.save(sucursalDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSucursalDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SucursalDTO> updateSucursal(@PathVariable Integer id, @RequestBody SucursalDTO sucursalDTO) {
        SucursalDTO updatedSucursalDTO = sucursalServices.update(id, sucursalDTO);
        if (updatedSucursalDTO != null) {
            return ResponseEntity.ok(updatedSucursalDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSucursal(@PathVariable Integer id) {
        String message = sucursalServices.delete(id);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<SucursalDTO> getOneSucursal(@PathVariable Integer id) {
        try {
            SucursalDTO sucursalDTO = sucursalServices.findById(id);
            return ResponseEntity.ok(sucursalDTO);
        } catch (EntityNotFoundException enfe) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<SucursalDTO>> getAll() {
        List<SucursalDTO> sucursales = sucursalServices.findAll();
        if (sucursales.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(sucursales);
        }
    }
}