package repository;

import banco.Conexao;
import entities.Servico;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ServicoRepository extends Conexao {
    public ServicoRepository() throws Exception {}

    public boolean incluir(Servico s) throws Exception {
        String sql = "INSERT INTO Servico(descricao, valor, tempoMedio, status) VALUES (?,?,?,?)";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setString(1, s.getDescricao());
        ps.setDouble(2, s.getValor());
        ps.setInt(3, s.getTempoMedio());
        ps.setInt(4, s.getStatus());
        return ps.executeUpdate() > 0;
    }

    public boolean alterar(Servico s) throws Exception {
        String sql = "UPDATE Servico SET descricao=?, valor=?, tempoMedio=?, status=? WHERE id=?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setString(1, s.getDescricao());
        ps.setDouble(2, s.getValor());
        ps.setInt(3, s.getTempoMedio());
        ps.setInt(4, s.getStatus());
        ps.setInt(5, s.getId());
        return ps.executeUpdate() > 0;
    }

    public boolean excluir(int id) throws Exception {
        String sql = "DELETE FROM Servico WHERE id=?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setInt(1, id);
        return ps.executeUpdate() > 0;
    }

    public Servico buscarPorId(int id) throws Exception {
        String sql = "SELECT * FROM Servico WHERE id=?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Servico(
                rs.getInt("id"),
                rs.getString("descricao"),
                rs.getDouble("valor"),
                rs.getInt("tempoMedio"),
                rs.getInt("status")
            );
        }
        return null;
    }

    public ArrayList<Servico> listarTodos() throws Exception {
        String sql = "SELECT * FROM Servico";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ArrayList<Servico> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new Servico(
                rs.getInt("id"),
                rs.getString("descricao"),
                rs.getDouble("valor"),
                rs.getInt("tempoMedio"),
                rs.getInt("status")
            ));
        }
        return list;
    }
}
