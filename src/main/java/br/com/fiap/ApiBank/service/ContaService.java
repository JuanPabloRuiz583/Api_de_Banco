package br.com.fiap.ApiBank.service;
import br.com.fiap.ApiBank.Exceptions.ValidacaoException;
import br.com.fiap.ApiBank.model.Conta;
import br.com.fiap.ApiBank.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

   @Service
    public class ContaService {

       @Autowired
       private ContaRepository contaRepository;

       public Conta criarConta(Conta conta) {
           validarConta(conta);
           return contaRepository.save(conta);
       }

       public List<Conta> listarContas() {
           return contaRepository.findAll();
       }

       public Conta buscarPorId(long id) {
           return contaRepository.findById(id)
                   .orElseThrow(() -> new NoSuchElementException("Conta não encontrada"));
       }

       public Conta buscarPorCpf(String cpf) {
           return contaRepository.findByCpfTitular(cpf)
                   .orElseThrow(() -> new NoSuchElementException("Conta não encontrada"));
       }

       public void encerrarConta(long id) {
           Conta conta = buscarPorId(id);
           contaRepository.deleteById(id);}

       public Conta depositar(long id, double valor) {
           Conta conta = buscarPorId(id);
           if (valor <= 0) throw new IllegalArgumentException("Valor inválido");
           conta.setSaldo(conta.getSaldo() + valor);
           return contaRepository.save(conta);
       }

       public Conta sacar(long id, double valor) {
           Conta conta = buscarPorId(id);
           if (valor <= 0 || valor > conta.getSaldo()) throw new IllegalArgumentException("Saldo insuficiente");
           conta.setSaldo(conta.getSaldo() - valor);
           return contaRepository.save(conta);
       }

       public Conta realizarPix(long origem, long destino, double valor) {
           Conta contaOrigem = buscarPorId(origem);
           Conta contaDestino = buscarPorId(destino);
           if (valor <= 0 || valor > contaOrigem.getSaldo()) throw new IllegalArgumentException("Saldo insuficiente");
           contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
           contaDestino.setSaldo(contaDestino.getSaldo() + valor);
           contaRepository.save(contaDestino);
           return contaRepository.save(contaOrigem);
       }

       private void validarConta(Conta conta) {
           if (conta.getNomeTitular() == null || conta.getNomeTitular().isEmpty()) {
               throw new ValidacaoException("Nome do titular é obrigatório");
           }
           if (conta.getCpfTitular() == null || conta.getCpfTitular().isEmpty()) {
               throw new ValidacaoException("CPF do titular é obrigatório");
           }
           if (conta.getDataAbertura() != null && conta.getDataAbertura().isAfter(LocalDate.now())) {
               throw new ValidacaoException("Data de abertura não pode ser no futuro");
           }
           if (conta.getSaldo() < 0) {
               throw new ValidacaoException("Saldo inicial não pode ser negativo");
           }
           if (conta.getTipo() == null || !(conta.getTipo().equals("corrente") || conta.getTipo().equals("poupanca") || conta.getTipo().equals("salario"))) {
               throw new ValidacaoException("Tipo deve ser um tipo válido (corrente, poupança ou salário)");
           }
       }
    }

