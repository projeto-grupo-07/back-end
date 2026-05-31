package school.sptech.crud_proj_v1.paginacao.infraestrutura;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import school.sptech.crud_proj_v1.dto.Funcionario.FuncionarioResponseDto;
import school.sptech.crud_proj_v1.entity.Funcionario;
import school.sptech.crud_proj_v1.mapper.FuncionarioMapper;
import school.sptech.crud_proj_v1.paginacao.dominio.PaginaOffsetFuncionario;
import school.sptech.crud_proj_v1.paginacao.dominio.PaginacaoStrategy;
import school.sptech.crud_proj_v1.repository.FuncionarioRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("funcionarioOffsetStrategy")
@RequiredArgsConstructor
public class FuncionarioOffsetStrategy implements PaginacaoStrategy<PaginaOffsetFuncionario> {

    private final FuncionarioRepository funcionarioRepository;

    @Override
    public PaginaOffsetFuncionario paginar(Map<String, Object> parametros) {
        int pagina = (int) parametros.get("pagina");
        int tamanho = (int) parametros.get("tamanho");

        PageRequest pageRequest = PageRequest.of(pagina, tamanho, Sort.by("id").descending());

        long inicio = System.currentTimeMillis();
        Page<Funcionario> paginaResultado = funcionarioRepository.findAll(pageRequest);
        long tempo = System.currentTimeMillis() - inicio;

        // Utilizando o método estático do seu Mapper com Stream
        List<FuncionarioResponseDto> conteudo = paginaResultado.getContent().stream()
                .map(FuncionarioMapper::of)
                .collect(Collectors.toList());

        return new PaginaOffsetFuncionario(
                conteudo,
                pagina,
                tamanho,
                paginaResultado.getTotalElements(),
                paginaResultado.getTotalPages(),
                tempo
        );
    }
}