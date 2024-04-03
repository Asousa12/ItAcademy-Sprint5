package cat.itacademy.barcelonactiva.Sousa.Andreia.s05.t01.n01.S05T01N01SousaAndreia.services;

import cat.itacademy.barcelonactiva.Sousa.Andreia.s05.t01.n01.S05T01N01SousaAndreia.domain.Sucursal;
import cat.itacademy.barcelonactiva.Sousa.Andreia.s05.t01.n01.S05T01N01SousaAndreia.dto.SucursalDTO;
import cat.itacademy.barcelonactiva.Sousa.Andreia.s05.t01.n01.S05T01N01SousaAndreia.repository.SucursalRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SucursalServicesImp implements SucursalServices {

    private final SucursalRepository sucursalRepository;

    @Autowired
    public SucursalServicesImp(SucursalRepository sucursalRepository) {
        this.sucursalRepository = sucursalRepository;
    }

    @Override
    public SucursalDTO save(SucursalDTO sucursalDTO) {
        Sucursal sucursal = new Sucursal(sucursalDTO.getNomSucursal(), sucursalDTO.getPaisSucursal());
        Sucursal savedSucursal = sucursalRepository.save(sucursal);
        return convertToDTO(savedSucursal);
    }

    @Override
    public SucursalDTO update(int id, SucursalDTO sucursalDTO) {
        Optional<Sucursal> optionalSucursal = sucursalRepository.findById(id);
        if (optionalSucursal.isPresent()) {
            Sucursal sucursal = optionalSucursal.get();
            sucursal.setNomSucursal(sucursalDTO.getNomSucursal());
            sucursal.setPaisSucursal(sucursalDTO.getPaisSucursal());
            Sucursal updatedSucursal = sucursalRepository.save(sucursal);
            return convertToDTO(updatedSucursal);
        } else {
            return null;
        }
    }

    @Override
    public String delete(int id) {
        sucursalRepository.deleteById(id);
        return "Sucursal eliminada con éxito";
    }

    @Override
    public SucursalDTO findById(int id) {
        return sucursalRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public List<SucursalDTO> findAll() {
        return sucursalRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private SucursalDTO convertToDTO(Sucursal sucursal) {
        return new SucursalDTO(sucursal.getPk_SucursalID(), sucursal.getNomSucursal(), sucursal.getPaisSucursal());
    }
}
