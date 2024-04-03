package cat.itacademy.barcelonactiva.Sousa.Andreia.s05.t01.n01.S05T01N01SousaAndreia.repository;

import cat.itacademy.barcelonactiva.Sousa.Andreia.s05.t01.n01.S05T01N01SousaAndreia.domain.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {

}