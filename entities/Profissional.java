package entities;

import java.util.ArrayList;
import java.util.List;

public class Profissional extends Usuario{
    private double salarioFixo;
    private double comissao;
    private List<Catalogo> catalogo = new ArrayList<>();
    private List<Agenda> agendas = new ArrayList<>();

    public double getSalarioFixo() { return salarioFixo; }
    public void setSalarioFixo(double salarioFixo) { this.salarioFixo = salarioFixo; }

    public double getComissao() { return comissao; }
    public void setComissao(double comissao) { this.comissao = comissao; }

    public List<Catalogo> getCatalogo(){ return catalogo; }

    public List<Agenda> getAgendas(){ return agendas; }

    public boolean adicionarAoCatalogo(Catalogo entry) {
        if (catalogo.contains(entry)) {
            return false;
        }

        catalogo.add(entry);
        return true;
    }

    public boolean addAgenda(Agenda agenda) {
        if (agendas.contains(agenda)) {
            return false;
        }

        agendas.add(agenda);
        return true;
    }
    
    public Profissional(int id, String nome, String telefone,
                        String email, String senha, int status,
                        double salarioFixo, double comissao){
        super(id, nome, telefone, email, senha, status);
        setSalarioFixo(salarioFixo);
        setComissao(comissao);
    }

    public boolean podePrestarServico(Servico servico) {
        return catalogo.stream().anyMatch(c -> c.getServico().equals(servico) && c.isAtivo());
    }

    public String toString(){
        return String.format("%s%s: %.2f\n%s: %.2f\n%s: %d\n%s: %d\n",
            super.toString(),
            "Salario Fixo", getSalarioFixo(),
            "Comissão", getComissao(),
            "Catálogo", getCatalogo().size(),
            "Agendas", getAgendas().size()
        );
    }
}
