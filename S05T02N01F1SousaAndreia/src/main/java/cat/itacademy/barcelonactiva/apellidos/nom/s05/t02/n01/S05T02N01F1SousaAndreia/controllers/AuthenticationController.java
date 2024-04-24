package cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.controllers;

import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.DTO.AuthenticationResponse;
import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.DTO.AuthenticationDTO;
import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.DTO.RegisterDTO;
import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.services.Interfaz.AuthServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private AuthServices authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterDTO request){
        return ResponseEntity.ok(authenticationService.register(request));


    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationDTO request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
