package repository;

import banco.Conexao;
import entities.Profissional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProfissionalRepository extends Conexao {
    public ProfissionalRepository() throws Exception {}

    public boolean incluir(Profissional p) throws Exception {
        String sql = "INSERT INTO Profissional(nome, telefone, email, senha, salarioFixo, comissao, status) " +
                     "VALUES (?,?,?,?,?,?,?)";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setString(1, p.getNome());
        ps.setString(2, p.getTelefone());
        ps.setString(3, p.getEmail());
        ps.setString(4, p.getSenha());
        ps.setDouble(5, p.getSalarioFixo());
        ps.setDouble(6, p.getComissao());
        ps.setInt(7, p.getStatus());
        return ps.executeUpdate() > 0;
    }

    public boolean alterar(Profissional p) throws Exception {
        String sql = "UPDATE Profissional SET nome=?, telefone=?, email=?, senha=?, salarioFixo=?, comissao=?, status=? WHERE id=?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setString(1, p.getNome());
        ps.setString(2, p.getTelefone());
        ps.setString(3, p.getEmail());
        ps.setString(4, p.getSenha());
        ps.setDouble(5, p.getSalarioFixo());
        ps.setDouble(6, p.getComissao());
        ps.setInt(7, p.getStatus());
        ps.setInt(8, p.getId());
        return ps.executeUpdate() > 0;
    }

    public boolean excluir(int id) throws Exception {
        String sql = "DELETE FROM Profissional WHERE id=?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setInt(1, id);
        return ps.executeUpdate() > 0;
    }

    public Profissional buscarPorId(int id) throws Exception {
        String sql = "SELECT * FROM Profissional WHERE id=?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Profissional(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("telefone"),
                rs.getString("email"),
                rs.getString("senha"),
                rs.getInt("status"),
                rs.getDouble("salarioFixo"),
                rs.getDouble("comissao")
            );
        }
        return null;
    }

    public ArrayList<Profissional> listarTodos() throws Exception {
        String sql = "SELECT * FROM Profissional";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ArrayList<Profissional> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new Profissional(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("telefone"),
                rs.getString("email"),
                rs.getString("senha"),
                rs.getInt("status"),
                rs.getDouble("salarioFixo"),
                rs.getDouble("comissao")
            ));
        }
        return list;
    }
}
