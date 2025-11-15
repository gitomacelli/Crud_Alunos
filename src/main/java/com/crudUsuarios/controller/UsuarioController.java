package com.crudUsuarios.controller;

import com.crudUsuarios.model.Usuario;
import com.crudUsuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    public UsuarioController(UsuarioRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    private List<Usuario> listar() {
        return repository.findAll();
    }

    @PostMapping
    private Usuario criar(@RequestBody Usuario usuario){
        return repository.save(usuario);
    }

    @PutMapping("/{id}")
    private ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Usuario dados){
        return repository.findById(id)
                .map(usuario -> {
                    usuario.setNome(dados.getNome());
                    usuario.setEmail(dados.getEmail());
                    usuario.setIdade(dados.getIdade());

                    var userAtualizado = repository.save(usuario);
                    return ResponseEntity.ok(userAtualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deletar(@PathVariable Long id){
        if (!repository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
