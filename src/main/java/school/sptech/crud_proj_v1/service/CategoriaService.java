package school.sptech.crud_proj_v1.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.crud_proj_v1.dto.Categoria.CategoriaPaiRequestDto;
import school.sptech.crud_proj_v1.dto.Categoria.CategoriaPaiResponseDto;
import school.sptech.crud_proj_v1.dto.Categoria.CategoriaRequestDto;
import school.sptech.crud_proj_v1.dto.Categoria.CategoriaResponseDto;
import school.sptech.crud_proj_v1.entity.Categoria;
import school.sptech.crud_proj_v1.exception.EntidadeConflitoException;
import school.sptech.crud_proj_v1.mapper.CategoriaMapper;
import school.sptech.crud_proj_v1.repository.CategoriaRepository;
import school.sptech.crud_proj_v1.repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;

import static school.sptech.crud_proj_v1.mapper.CategoriaMapper.*;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;

    public CategoriaService(CategoriaRepository categoriaRepository, ProdutoRepository produtoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.produtoRepository = produtoRepository;
    }

    public CategoriaPaiResponseDto cadastrarPai(CategoriaPaiRequestDto req) {
        if (categoriaRepository.existsByDescricao(req.getDescricao())){
            throw new EntidadeConflitoException("Categoria existente!");
        }
        Categoria entity = toEntity(req);
        categoriaRepository.save(entity);
        return toResponseDtoPai(entity);
    }

    public CategoriaResponseDto cadastrarFilho(CategoriaRequestDto req){

        if (categoriaRepository.existsByDescricao(req.getDescricao())){
            throw new EntidadeConflitoException("Categoria existente!");
        }

        Categoria pai = categoriaRepository.findById(req.getCategoriaPaiId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Categoria pai não encontrada"
                ));

        Categoria filho = CategoriaMapper.toEntityFilho(req, pai);

        pai.getSubcategorias().add(filho);

        categoriaRepository.save(filho);

        return CategoriaMapper.toResponseDto(filho);
    }


    public List<CategoriaPaiResponseDto> listarTodosPais() {
        return CategoriaMapper.toResponseDtoPai(
                categoriaRepository.findByCategoriaPaiIsNull()
        );
    }

    public List<CategoriaResponseDto> listarTodosFilhos(){
        return CategoriaMapper.toResponseDto(
                categoriaRepository.findByCategoriaPaiIsNotNull()
        );
    }

    public CategoriaResponseDto listarFilhoPorId(Integer id) {
        Optional<Categoria> opCategoria = categoriaRepository.findById(id);
        return opCategoria.map(CategoriaMapper::toResponseDto).orElse(null);
    }

    public CategoriaPaiResponseDto listarPaiPorId(Integer id) {
        Optional<Categoria> opCategoria = categoriaRepository.findById(id);
        return opCategoria.map(CategoriaMapper::toResponseDtoPai).orElse(null);
    }

    public List<CategoriaResponseDto> listarPorNomeFilho(String descricao) {
        List<Categoria> categorias = categoriaRepository.findByDescricaoContainingIgnoreCase(descricao);
        return categorias.stream().map(CategoriaMapper::toResponseDto)
                .toList();
    }

    public List<CategoriaPaiResponseDto> listarPorNomePai(String descricao){
        List<Categoria> categorias = categoriaRepository.findByDescricaoContainingIgnoreCase(descricao);
        return categorias.stream().map(CategoriaMapper::toResponseDtoPai)
                .toList();
    }

    public List<CategoriaResponseDto> listarFilhosPorPai(Integer paiId) {
        List<Categoria> filhos = categoriaRepository.findByCategoriaPaiId(paiId);

        return filhos.stream()
                .map(CategoriaMapper::toResponseDto)
                .toList();
    }

    public void deletarPorId(Integer id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada");
        }

        if (produtoRepository.existsByCategoriaId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Não é possível deletar a categoria, pois existem produtos associados a ela.");
        }

        categoriaRepository.deleteById(id);
    }

    public CategoriaResponseDto atualizarTotalFilho(Integer id, CategoriaRequestDto req) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada");
        }

        Categoria pai = categoriaRepository.findById(req.getCategoriaPaiId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Categoria pai não encontrada"
                ));

        Categoria update = CategoriaMapper.toEntityFilho(req, pai);

        update.setId(id);

        Categoria res = categoriaRepository.save(update);

        return CategoriaMapper.toResponseDto(res);
    }

    public CategoriaPaiResponseDto atualizarTotalPai(Integer id, CategoriaPaiRequestDto req) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada");
        }
        Categoria update = CategoriaMapper.toEntity(req);

        update.setId(id);

        Categoria res = categoriaRepository.save(update);

        return CategoriaMapper.toResponseDtoPai(res);
    }

//    public CategoriaResponseDto atualizarParcial(Integer id, CategoriaRequestDto categoriaRequestDto) {
//        Categoria categoriaExistente = categoriaRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));
//
//        if (categoriaRequestDto.getDescricao() != null) {
//            categoriaExistente.setDescricao(categoriaRequestDto.getDescricao());
//        }
//
//        Categoria categoriaSalva = categoriaRepository.save(categoriaExistente);
//
//        return categoriaMapper.toResponseDto(categoriaSalva);
//    }

}
