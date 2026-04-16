package school.sptech.crud_proj_v1.dto.Empresa;

import school.sptech.crud_proj_v1.dto.Endereco.EnderecoResponseDto;

public class EmpresaResponseDto {
    private Integer idEmpresa;
    private String razaoSocial;
    private String cnpj;
    private String responsavel;
    private EnderecoResponseDto endereco;


    public Integer getIdEmpresa() { return idEmpresa; }
    public void setIdEmpresa(Integer idEmpresa) { this.idEmpresa = idEmpresa; }
    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }
    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }
    public EnderecoResponseDto getEndereco() { return endereco; }
    public void setEndereco(EnderecoResponseDto endereco) { this.endereco = endereco; }
}