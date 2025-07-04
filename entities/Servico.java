package entities;
public class Servico {

    private int id;
    private String descricao;
    private double valor;
    private int tempoMedio;
    private int status;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public int getTempoMedio(){ return tempoMedio; }
    public void setTempoMedio(int tempoMedio){ this.tempoMedio = tempoMedio; }
    
    public Servico(int id, String descricao, double valor, int tempoMedio, int status) {
        setId(id);
        setDescricao(descricao);
        setValor(valor);
        setTempoMedio(tempoMedio);
        setStatus(status);
    }
    
    public String toString(){
        return String.format("%s: %d\n%s: %s\n%s: %d\n%s: R$%.2f\n",
            "Id", getId(),
            "Serviço", getDescricao(),
            "Tempo Médio", getTempoMedio(),
            "Valor", getValor()
        );
    }
}
