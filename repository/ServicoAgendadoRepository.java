package repository;

import banco.Conexao;
import entities.ServicoAgendado;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ServicoAgendadoRepository extends Conexao {
    public ServicoAgendadoRepository() throws Exception {
    }

    public boolean incluir(ServicoAgendado sa) throws Exception {
        String sql = "INSERT INTO Servico_Agendado(idAgenda, idServico, idProfissional, valor, status) VALUES (?,?,?,?,?)";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setInt(1, sa.getAgenda().getId());
        ps.setInt(2, sa.getServico().getId());
        ps.setInt(3, sa.getProfissional().getId());
        ps.setDouble(4, sa.getValor());
        ps.setInt(5, sa.getStatus());
        return ps.executeUpdate() > 0;
    }

    public boolean alterar(ServicoAgendado sa) throws Exception {
        String sql = "UPDATE Servico_Agendado SET status = ? WHERE idAgenda = ? AND idServico = ? AND idProfissional = ?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setInt(1, sa.getStatus());
        ps.setInt(2, sa.getAgenda().getId());
        ps.setInt(3, sa.getServico().getId());
        ps.setInt(4, sa.getProfissional().getId());
        return ps.executeUpdate() > 0;
    }

    public boolean excluir(int id) throws Exception {
        String sql = "DELETE FROM Servico_Agendado WHERE id=?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setInt(1, id);
        return ps.executeUpdate() > 0;
    }

    public ArrayList<ServicoAgendado> listarPorAgenda(int agendaId) throws Exception {
        String sql = "SELECT * FROM Servico_Agendado WHERE idAgenda=?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setInt(1, agendaId);
        ResultSet rs = ps.executeQuery();
        ArrayList<ServicoAgendado> list = new ArrayList<>();
        AgendaRepository ar = new AgendaRepository();
        ServicoRepository sr = new ServicoRepository();
        ProfissionalRepository pr = new ProfissionalRepository();
        while (rs.next()) {
            list.add(new ServicoAgendado(
                    ar.buscarPorId(rs.getInt("idAgenda")),
                    sr.buscarPorId(rs.getInt("idServico")),
                    pr.buscarPorId(rs.getInt("idProfissional")),
                    rs.getInt("status")));
        }
        return list;
    }

    public ArrayList<ServicoAgendado> listarTodos() throws Exception {
        String sql = "SELECT * FROM Servico_Agendado";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ArrayList<ServicoAgendado> list = new ArrayList<>();
        AgendaRepository ar = new AgendaRepository();
        ServicoRepository sr = new ServicoRepository();
        ProfissionalRepository pr = new ProfissionalRepository();
        while (rs.next()) {
            list.add(new ServicoAgendado(
                    ar.buscarPorId(rs.getInt("idAgenda")),
                    sr.buscarPorId(rs.getInt("idServico")),
                    pr.buscarPorId(rs.getInt("idProfissional")),
                    rs.getInt("status")));
        }
        return list;
    }
}
