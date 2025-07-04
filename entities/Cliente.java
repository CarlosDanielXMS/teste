package entities;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario{
    private List<Agenda> agendamentos = new ArrayList<>();

    public void addAgendamento(Agenda agenda){ this.agendamentos.add(agenda); }
    public List<Agenda> getAgendamentos(){ return agendamentos; }

    public Cliente(int id, String nome, String telefone, String email, String senha, int status) {
        super(id, nome, telefone, email, senha, status);
    }
    
    public String toString(){
        return String.format("%s%s: %d\n",
            super.toString(),
            "Número de Agendamentos", getAgendamentos().size()
        );
    }
}