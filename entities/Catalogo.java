package entities;

public class Catalogo {
    private Profissional profissional;
    private Servico servico;
    private double valor;
    private int tempoMedio;
    private int status;

    public void setProfissional(Profissional profissional){ this.profissional = profissional; }
    public void setServico(Servico servico){ this.servico = servico; }
    public void setValor(double valor){ this.valor = valor; }
    public void setTempoMedio(int tempoMedio){ this.tempoMedio = tempoMedio; }
    public void setStatus(int status){ this.status = status; }

    public Profissional getProfissional(){ return profissional; }
    public Servico getServico(){ return servico; }
    public double getValor(){ return valor; }
    public int getTempoMedio(){ return tempoMedio; }
    public boolean isAtivo(){ return status == 1; }
    public int getStatus(){ return status; }

    public Catalogo(Profissional profissional, Servico servico, int status) {
        setProfissional(profissional);
        setServico(servico);
        setStatus(status);
        profissional.adicionarAoCatalogo(this);
    }

    public Catalogo(Profissional profissional, Servico servico, double valor, int tempoMedio, int status) {
        setProfissional(profissional);
        setServico(servico);
        setValor(valor);
        setTempoMedio(tempoMedio);
        setStatus(status);
        profissional.adicionarAoCatalogo(this);
    }

    public String toString(){
        return String.format("%s: %s\n%s: %s\n%s: %s\n",
            "Profissional", getProfissional().getNome(),
            "Serviço", getServico().getDescricao(),
            "Status", isAtivo() ? "Ativo" : "Inativo"
        );
    }
}
