package school.sptech.crud_proj_v1.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.crud_proj_v1.dto.Categoria.CategoriaPaiResponseDto;
import school.sptech.crud_proj_v1.entity.Categoria;
import school.sptech.crud_proj_v1.repository.CategoriaRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository repository;

    @InjectMocks
    private CategoriaService service;


    @Test
    @DisplayName("Deve retornar lista vazia")
    void deveRetornarListaPaiVazia(){
        List<Categoria> resultado = new ArrayList<>();

        Mockito.when(repository.findByCategoriaPaiIsNull()).thenReturn(resultado);

        List<CategoriaPaiResponseDto> res = service.listarTodosPais();

        Assertions.assertTrue(res.isEmpty());
    }
}