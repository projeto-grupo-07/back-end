package school.sptech.crud_proj_v1.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.crud_proj_v1.dto.Categoria.CategoriaRequestDto;
import school.sptech.crud_proj_v1.dto.Categoria.CategoriaResponseDto;
import school.sptech.crud_proj_v1.entity.Categoria;
import school.sptech.crud_proj_v1.mapper.CategoriaMapper;
import school.sptech.crud_proj_v1.repository.CategoriaRepository;
import school.sptech.crud_proj_v1.repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;
    private final ProdutoRepository produtoRepository;

    public CategoriaService(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper, ProdutoRepository produtoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
        this.produtoRepository = produtoRepository;
    }

    public CategoriaResponseDto cadastrar(CategoriaRequestDto categoriaRequestDto) {

        Categoria categoriaaSerCriada = categoriaMapper.toEntity(categoriaRequestDto);
        categoriaRepository.save(categoriaaSerCriada);
        return categoriaMapper.toResponseDto(categoriaaSerCriada);
    }

    public List<CategoriaResponseDto> listarTodos() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream().map(categoriaMapper::toResponseDto)
                .toList();
    }

    public CategoriaResponseDto listarPorId(Integer id) {
        Optional<Categoria> opCategoria = categoriaRepository.findById(id);
        return opCategoria.map(categoriaMapper::toResponseDto).orElse(null);
    }

    public List<CategoriaResponseDto> listarPorNome(String descricao) {
        List<Categoria> categorias = categoriaRepository.findByDescricaoContainingIgnoreCase(descricao);
        return categorias.stream().map(categoriaMapper::toResponseDto)
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

    public CategoriaResponseDto atualizarTotal(Integer id, CategoriaRequestDto categoriaRequestDto) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada");
        }
        Categoria categoriaParaAtualizar = categoriaMapper.toEntity(categoriaRequestDto);

        categoriaParaAtualizar.setId(id);

        Categoria categoriaSalva = categoriaRepository.save(categoriaParaAtualizar);

        return categoriaMapper.toResponseDto(categoriaSalva);
    }

    public CategoriaResponseDto atualizarParcial(Integer id, CategoriaRequestDto categoriaRequestDto) {
        Categoria categoriaExistente = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));

        if (categoriaRequestDto.getDescricao() != null) {
            categoriaExistente.setDescricao(categoriaRequestDto.getDescricao());
        }

        Categoria categoriaSalva = categoriaRepository.save(categoriaExistente);

        return categoriaMapper.toResponseDto(categoriaSalva);
    }

}
