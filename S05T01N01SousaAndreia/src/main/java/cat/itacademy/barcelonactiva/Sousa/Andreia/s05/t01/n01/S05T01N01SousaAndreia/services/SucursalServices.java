package cat.itacademy.barcelonactiva.Sousa.Andreia.s05.t01.n01.S05T01N01SousaAndreia.services;

import cat.itacademy.barcelonactiva.Sousa.Andreia.s05.t01.n01.S05T01N01SousaAndreia.dto.SucursalDTO;

import java.util.List;

public interface SucursalServices {
    public SucursalDTO save(SucursalDTO sucursalDTO);

    public SucursalDTO update(int id, SucursalDTO sucursalDTO);

    public String delete(int id);

    public SucursalDTO findById(int id);

    public List<SucursalDTO> findAll();

}

