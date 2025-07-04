package repository;

import banco.Conexao;
import entities.Agenda;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

public class AgendaRepository extends Conexao {
    public AgendaRepository() throws Exception {
        super();
    }

    public boolean incluir(Agenda a) throws Exception {
        String sql = "INSERT INTO Agenda(idCliente, dataHora, tempoTotal, valorTotal, status) VALUES (?,?,?,?,?)";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setInt(1, a.getCliente().getId());
        ps.setTimestamp(2, Timestamp.valueOf(a.getDataHora()));
        ps.setDouble(3, a.getTempoTotal());
        ps.setDouble(4, a.getValorTotal());
        ps.setInt(5, a.getStatus());
        return ps.executeUpdate() > 0;
    }

    public boolean alterar(Agenda a) throws Exception {
        String sql = "UPDATE Agenda SET idCliente=?, dataHora=?, status=? WHERE id=?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setInt(1, a.getCliente().getId());
        ps.setTimestamp(2, Timestamp.valueOf(a.getDataHora()));
        ps.setInt(3, a.getStatus());
        ps.setInt(4, a.getId());
        return ps.executeUpdate() > 0;
    }

    public boolean excluir(int id) throws Exception {
        String sql = "DELETE FROM Agenda WHERE id=?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setInt(1, id);
        return ps.executeUpdate() > 0;
    }

    public Agenda buscarPorId(int id) throws Exception {
        String sql = "SELECT * FROM Agenda WHERE id=?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            ClienteRepository cr = new ClienteRepository();
            return new Agenda(
                rs.getInt("id"),
                cr.buscarPorId(rs.getInt("idCliente")),
                rs.getTimestamp("dataHora").toLocalDateTime(),
                rs.getInt("status")
            );
        }
        return null;
    }

    public ArrayList<Agenda> listarTodos() throws Exception {
        String sql = "SELECT * FROM Agenda";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ArrayList<Agenda> list = new ArrayList<>();
        ClienteRepository cr = new ClienteRepository();
        while (rs.next()) {
            list.add(new Agenda(
                rs.getInt("id"),
                cr.buscarPorId(rs.getInt("idCliente")),
                rs.getTimestamp("dataHora").toLocalDateTime(),
                rs.getInt("status")
            ));
        }
        return list;
    }
}
