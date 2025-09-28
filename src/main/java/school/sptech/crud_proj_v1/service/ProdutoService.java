package school.sptech.crud_proj_v1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.crud_proj_v1.dto.Produto.ProdutoListDTO;
import school.sptech.crud_proj_v1.dto.Produto.ProdutoRequestDTO;
import school.sptech.crud_proj_v1.entity.Categoria;
import school.sptech.crud_proj_v1.entity.Produto;
import school.sptech.crud_proj_v1.mapper.ProdutoMapper;
import school.sptech.crud_proj_v1.repository.CategoriaRepository;
import school.sptech.crud_proj_v1.repository.ProdutoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<ProdutoListDTO> listarTodos() {
        List<Produto> produtos = produtoRepository.findAll();

        return ProdutoMapper.toListDTO(produtos);
    }

    public ProdutoListDTO criar(ProdutoRequestDTO novoProdutoDTO) {
        Produto novoProduto = ProdutoMapper.toEntity(novoProdutoDTO);

        if (novoProdutoDTO.getCategoriaId() != null) {
            Categoria categoriaEncontrada = categoriaRepository
                    .findById(novoProdutoDTO.getCategoriaId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Categoria com o ID informado não foi encontrada.")
                    );

            novoProduto.setCategoria(categoriaEncontrada);
        }
        Produto produtoSalvo = produtoRepository.save(novoProduto);

        return ProdutoMapper.toDTO(produtoSalvo);
    }



}
