package school.sptech.crud_proj_v1.dto.Endereco;

import jakarta.validation.constraints.*;

public class EnderecoRequestDto {

    @NotBlank
    @Size(min = 9, max = 9, message = "O CEP deve ter 9 caracteres (ex: 00000-000)")
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "O CEP deve seguir o formato 00000-000")
    private String cep;

    @NotBlank
    @Size(min = 2, max = 2, message = "O Estado deve ter 2 caracteres (ex: SP)")
    private String estado;

    @NotBlank
    @Size(max = 45)
    private String cidade;

    @NotBlank
    @Size(max = 45)
    private String bairro;

    @NotBlank
    @Size(max = 45)
    private String logradouro;

    @NotNull(message = "O número é obrigatório")
    @Positive(message = "O número deve ser positivo")
    private Integer numero;

    @Size(max = 100)
    private String complemento;

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }
    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }
    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }
    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }
}