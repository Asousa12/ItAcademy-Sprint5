package cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.services.Imp;

import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.DTO.AuthenticationResponse;
import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.DTO.AuthenticationDTO;
import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.DTO.RegisterDTO;
import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.Enums.Role;
import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.repository.UserRepository;
import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.services.Interfaz.AuthServices;
import cat.itacademy.barcelonactiva.apellidos.nom.s05.t02.n01.S05T02N01F1SousaAndreia.model.services.Interfaz.JwtServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthServices {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServices jwtServices;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtServices jwtServices, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtServices = jwtServices;
        this.authenticationManager = authenticationManager;
    }


    @Override
    public AuthenticationResponse register(RegisterDTO request) {
        var user = User.builder()
                .firstName = (request.getEmail())
                .lastName = (request.getLastName())
                .email =(request.getEmail())
                .password = (passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtServices.generateToken(user);

        return AuthenticationResponse.builder();
               .token(jwtToken).build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        .request.getEmail()
                        .request.getPassword()
                )
        );
        var user = userRepository.findUserByEmail(request.getEmail().orElseThrow());
        var jwtToken = jwtServices.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
