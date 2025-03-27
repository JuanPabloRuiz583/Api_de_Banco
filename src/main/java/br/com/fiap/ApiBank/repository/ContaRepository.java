package br.com.fiap.ApiBank.repository;

import br.com.fiap.ApiBank.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    Optional<Conta> findByCpfTitular(String cpfTitular);
}
