package school.sptech.crud_proj_v1.dto.Empresa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EmpresaRequestDto {

    @NotBlank(message = "A razão social é obrigatória.")
    @Size(max = 45)
    private String razaoSocial;

    @NotBlank(message = "O CNPJ é obrigatório.")
    @Pattern(regexp = "^\\d{2}\\.?\\d{3}\\.?\\d{3}/\\d{4}-?\\d{2}$", message = "CNPJ inválido. Use o formato XX.XXX.XXX/XXXX-XX")
    private String cnpj;

    @NotBlank(message = "O nome do responsável é obrigatório.")
    @Size(max = 45)
    private String responsavel;

    @NotNull(message = "O ID do endereço é obrigatório.")
    private Integer fkEndereco;

    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }
    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }
    public Integer getFkEndereco() { return fkEndereco; }
    public void setFkEndereco(Integer fkEndereco) { this.fkEndereco = fkEndereco; }
}
