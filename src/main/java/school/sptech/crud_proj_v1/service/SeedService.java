package school.sptech.crud_proj_v1.service;

import net.datafaker.Faker;
import org.springframework.stereotype.Service;
import school.sptech.crud_proj_v1.entity.*;
import school.sptech.crud_proj_v1.entity.abstrato.Produto;
import school.sptech.crud_proj_v1.enumeration.FormaDePagamento;
import school.sptech.crud_proj_v1.repository.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class SeedService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;
    // ATENÇÃO: Mudei para VendaProdutoRepository para bater com a sua Entidade
    private final VendaProdutoRepository vendaProdutoRepository;
    private final FuncionarioRepository funcionarioRepository;

    public SeedService(VendaRepository vendaRepository, ProdutoRepository produtoRepository,
                       VendaProdutoRepository vendaProdutoRepository, FuncionarioRepository funcionarioRepository) {
        this.vendaRepository = vendaRepository;
        this.produtoRepository = produtoRepository;
        this.vendaProdutoRepository = vendaProdutoRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    public void gerarMassaDeDados(int quantidadeVendas) {
        Faker faker = new Faker();
        Random random = new Random();

        List<Produto> produtos = produtoRepository.findAll();
        List<Funcionario> funcionarios = funcionarioRepository.findAll();

        if (produtos.isEmpty() || funcionarios.isEmpty()) {
            System.out.println("⚠️ Produtos ou Funcionários não encontrados. Abortando Seeding.");
            return;
        }

        List<Venda> vendasLote = new ArrayList<>();
        List<VendaProduto> itensLote = new ArrayList<>();

        // Pega os valores reais do seu Enum (DEBITO, CREDITO, PIX)
        FormaDePagamento[] pagamentos = FormaDePagamento.values();

        for (int i = 0; i < quantidadeVendas; i++) {
            Venda venda = new Venda();

            // 1. DATA E HORA
            java.util.Date dataSorteada = faker.date().past(180, TimeUnit.DAYS);
            venda.setDataHora(dataSorteada.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

            // 2. FORMA DE PAGAMENTO (Usando o Enum corretamente)
            venda.setFormaDePagamento(pagamentos[random.nextInt(pagamentos.length)]);

            // 3. FUNCIONÁRIO (Bate com a variável 'funcionario' da sua entidade)
            Funcionario vendedorSorteado = funcionarios.get(random.nextInt(funcionarios.size()));
            venda.setFuncionario(vendedorSorteado);

            // 4. PRODUTO E VALORES
            Produto produtoSorteado = produtos.get(random.nextInt(produtos.size()));
            int quantidadeComprada = random.nextInt(3) + 1;
            double valorTotalVenda = produtoSorteado.getValorUnitario() * quantidadeComprada;

            // Bate com a variável 'totalVenda'
            venda.setTotalVenda(valorTotalVenda);
            venda.setPercentualComissaoAplicado(0.05);
            venda.setValorComissao(valorTotalVenda * 0.05);

            vendasLote.add(venda);

            // ==========================================
            // ITENS DA VENDA (VendaProduto)
            // ==========================================
            VendaProduto item = new VendaProduto();

            // Bate com as chaves estrangeiras padronizadas do JPA
            item.setVenda(venda);
            item.setProduto(produtoSorteado);

            // Os campos abaixo dependem de como você os nomeou na entidade 'VendaProduto'
            // Se algum desses der erro de 'cannot find symbol', basta olhar na classe VendaProduto
            // e trocar para o nome exato da variável (ex: setQuantidade, setTotal, etc.)
            item.setQuantidadeVendaProduto(quantidadeComprada);
            item.setPrecoUnitarioNaVenda(produtoSorteado.getValorUnitario());
            item.setDesconto(0.0);
            item.setValorTotalVendaProduto(valorTotalVenda);

            itensLote.add(item);
        }

        vendaRepository.saveAll(vendasLote);
        vendaProdutoRepository.saveAll(itensLote);

        System.out.println("✅ Massa de dados gerada: " + quantidadeVendas + " vendas criadas com sucesso!");
    }
}