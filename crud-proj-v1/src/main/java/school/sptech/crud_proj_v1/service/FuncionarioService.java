package school.sptech.crud_proj_v1.service;

import org.springframework.stereotype.Service;
import school.sptech.crud_proj_v1.entity.Funcionario;
import school.sptech.crud_proj_v1.exception.EntidadeConflitoException;
import school.sptech.crud_proj_v1.exception.EntidadeNotFoundException;
import school.sptech.crud_proj_v1.repository.FuncionarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    private FuncionarioRepository repository;

    public FuncionarioService(FuncionarioRepository repository) {
        this.repository = repository;
    }

    public List<Funcionario> listar(){
        return repository.findAll();
    }

    public Funcionario cadastrar(Funcionario func){
        if (repository.existsByCpf(func.getCpf())){
            throw new EntidadeConflitoException("Conflito no cpf");
        } else {
            repository.save(func);
            return func;
        }
    }

    public Funcionario buscarPorId(Integer id){
        Optional<Funcionario> opt = repository.findById(id);
        if (opt.isEmpty()){
            throw new EntidadeNotFoundException("Não encontrado");
        } else {
            return opt.get();
        }
    }

    public Funcionario atualizarPorId(Integer id, Funcionario func){
        func.setId(id);
        if (repository.existsById(id)){
            return repository.save(func);
        }
        throw new EntidadeNotFoundException("funcionario nao encontrado/existente");
    }

    public void deletarPorId(Integer id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return;
        }
        throw new EntidadeNotFoundException("funcionario nao encontrado/existente");
    }


}
