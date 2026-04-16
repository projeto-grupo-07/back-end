package school.sptech.crud_proj_v1.dto.Tela;

public class TelaDto {
    private String titulo;
    private String path;
    private String componentKey;
    private Integer ordem;

    public TelaDto() {}

    public TelaDto(String titulo, String path, String componentKey, Integer ordem) {
        this.titulo = titulo;
        this.path = path;
        this.componentKey = componentKey;
        this.ordem = ordem;
    }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public String getComponentKey() { return componentKey; }
    public void setComponentKey(String componentKey) { this.componentKey = componentKey; }

    public Integer getOrdem() { return ordem; }
    public void setOrdem(Integer ordem) { this.ordem = ordem; }
}