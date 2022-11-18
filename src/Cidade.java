public class Cidade {
    private String nome;
    private int codigo;
    
    public Cidade(int codigo, String nome) {
        this.nome = nome;
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "Código:" + codigo + "  Nome:" + nome;
    }

    
}
