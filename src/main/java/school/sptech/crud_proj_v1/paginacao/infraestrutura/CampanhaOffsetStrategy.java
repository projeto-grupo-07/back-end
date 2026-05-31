package school.sptech.crud_proj_v1.paginacao.infraestrutura;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import school.sptech.crud_proj_v1.dto.Campanha.CampanhaResponseDto;
import school.sptech.crud_proj_v1.entity.Campanha;
import school.sptech.crud_proj_v1.mapper.CampanhaMapper;
import school.sptech.crud_proj_v1.paginacao.dominio.PaginaOffsetCampanha;
import school.sptech.crud_proj_v1.paginacao.dominio.PaginacaoStrategy;
import school.sptech.crud_proj_v1.repository.CampanhaRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("campanhaOffsetStrategy")
@RequiredArgsConstructor
public class CampanhaOffsetStrategy implements PaginacaoStrategy<PaginaOffsetCampanha> {

    private final CampanhaRepository campanhaRepository;
    private final CampanhaMapper campanhaMapper;

    @Override
    public PaginaOffsetCampanha paginar(Map<String, Object> parametros) {
        int pagina = (int) parametros.get("pagina");
        int tamanho = (int) parametros.get("tamanho");

        PageRequest pageRequest = PageRequest.of(pagina, tamanho, Sort.by("id").descending());

        long inicio = System.currentTimeMillis();
        Page<Campanha> paginaResultado = campanhaRepository.findAll(pageRequest);
        long tempo = System.currentTimeMillis() - inicio;

        List<CampanhaResponseDto> conteudo = paginaResultado.getContent().stream()
                .map(campanhaMapper::toDto)
                .collect(Collectors.toList());

        return new PaginaOffsetCampanha(
                conteudo,
                pagina,
                tamanho,
                paginaResultado.getTotalElements(),
                paginaResultado.getTotalPages(),
                tempo
        );
    }
}