package entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Agenda {
    private int id;
    private Cliente cliente;
    private LocalDateTime dataHora;
    private double tempoTotal;
    private double valorTotal;
    private int status;
    private List<ServicoAgendado> servicos = new ArrayList<>();

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public double getTempoTotal() { return tempoTotal; }
    
    public double getValorTotal() { return valorTotal; }

    public boolean isAtivo() { return status == 1; }
    public void setStatus(int status) { this.status = status; }
    public int getStatus() { return status; }

    public List<ServicoAgendado> getServicos(){ return servicos; }
    public void addServico(ServicoAgendado servico){ this.servicos.add(servico); }

    public Agenda(int id, Cliente cliente, LocalDateTime dataHora, int status) {
        setId(id);
        setCliente(cliente);
        setDataHora(dataHora);
        setStatus(status);
        this.tempoTotal = 0;
        this.valorTotal = 0;
        cliente.addAgendamento(this);
    }

    public void agendarServico(Servico servico, Profissional profissional) {
        if (!profissional.podePrestarServico(servico)) {
            throw new IllegalArgumentException(
                "Profissional não presta o serviço: " + servico.getDescricao());
        }

        LocalDateTime start = dataHora;
        LocalDateTime end = dataHora.plusMinutes((long)(servico.getTempoMedio()));

        for (Agenda ag : profissional.getAgendas()) {
            LocalDateTime otherStart = ag.getDataHora();
            LocalDateTime otherEnd = otherStart.plusMinutes((long)(ag.getTempoTotal() * 60));
            boolean overlap = start.isBefore(otherEnd) && otherStart.isBefore(end);
            if (overlap) {
                throw new IllegalArgumentException(
                    "Conflito de horário para o profissional neste período: "
                    + start + " – " + end);
            }
        }

        ServicoAgendado sa = new ServicoAgendado(this, servico, profissional, 1);
        servicos.add(sa);

        tempoTotal += servico.getTempoMedio() / 60.0;
        valorTotal += servico.getValor();

        profissional.addAgenda(this);
    }
    
    public String toString(){
        return String.format("%s: %d\n%s: %s\n%s: %s\n%s: %.2f\n%s: %.2f\n%s: %d\n%s: %s\n",
            "Id", getId(),
            "Cliente", getCliente().getNome(),
            "Horario", getDataHora().toString(),
            "Tempo Total", getTempoTotal(),
            "Valor Total", getValorTotal(),
            "Número de Serviços Agendados", getServicos().size(),
            "Status", isAtivo() ? "Ativo" : "Inativo"
        );
    }
}
