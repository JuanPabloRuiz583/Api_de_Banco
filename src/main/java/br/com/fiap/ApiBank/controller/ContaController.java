package br.com.fiap.ApiBank.controller;
import br.com.fiap.ApiBank.Exceptions.ValidacaoException;
import br.com.fiap.ApiBank.model.Conta;
import br.com.fiap.ApiBank.repository.ContaRepository;
import br.com.fiap.ApiBank.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contas")
public class ContaController {
    private final ContaService contaService;


    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping
    public ResponseEntity<?> criarConta(@RequestBody Conta conta) {
        try {
            Conta novaConta = contaService.criarConta(conta);
            return new ResponseEntity<>(novaConta, HttpStatus.CREATED);
        } catch (ValidacaoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<Conta> listarContas() {
        return contaService.listarContas();
    }

    @GetMapping("/{id}")
    public Conta buscarPorId(@PathVariable long id) {
        return contaService.buscarPorId(id);
    }

    @GetMapping("/cpf/{cpf}")
    public Conta buscarPorCpf(@PathVariable String cpf) {
        return contaService.buscarPorCpf(cpf);
    }

    @DeleteMapping("/encerrar/{id}")
    public ResponseEntity<Void> encerrarConta(@PathVariable long id) {
        contaService.encerrarConta(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/deposito")
    public Conta depositar(@RequestParam long id, @RequestParam double valor) {
        return contaService.depositar(id, valor);
    }

    @PutMapping("/saque")
    public Conta sacar(@RequestParam long id, @RequestParam double valor) {
        return contaService.sacar(id, valor);
    }

    @PutMapping("/pix")
    public Conta realizarPix(@RequestParam long origem, @RequestParam long destino, @RequestParam double valor) {
        return contaService.realizarPix(origem, destino, valor);
    }

}
