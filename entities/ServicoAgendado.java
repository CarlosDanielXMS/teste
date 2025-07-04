package entities;

public class ServicoAgendado {
    private Agenda agenda;
    private Servico servico;
    private Profissional profissional;
    private double valor;
    private int status;

    public void setAgenda(Agenda agenda){ this.agenda = agenda; }
    public void setServico(Servico servico){ this.servico = servico; }
    public void setProfissional(Profissional profissional){ this.profissional = profissional; }
    public void setValor(Double valor){ this.valor = valor; }
    public void setStatus(int status) {this.status = status; }

    public Agenda getAgenda(){ return agenda; }
    public Servico getServico(){ return servico; }
    public Profissional getProfissional(){ return profissional; }
    public Double getValor(){ return valor; }
    public int getStatus() { return status; }
    public boolean isAtivo() { return status == 1; }

    public ServicoAgendado(Agenda agenda, Servico servico, Profissional profissional, int status){
        setAgenda(agenda);
        setServico(servico);
        setProfissional(profissional);
        setValor(servico.getValor());
        setStatus(status);
    }

    public String toString(){
        return String.format("%s: %s\n%s: %s\n%s: %s\n%s: %.2f\n",
            "Agenda", agenda.getId(),
            "Serviço", getServico().getDescricao(),
            "Profissional", getProfissional().getNome(),
            "Valor", getValor()
        );
    }
}
