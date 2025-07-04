package repository;

import banco.Conexao;
import entities.Cliente;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ClienteRepository extends Conexao {
    public ClienteRepository() throws Exception {}

    public boolean incluir(Cliente c) throws Exception {
        String sql = "INSERT INTO Cliente(nome, telefone, email, senha, status) VALUES (?,?,?,?,?)";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setString(1, c.getNome());
        ps.setString(2, c.getTelefone());
        ps.setString(3, c.getEmail());
        ps.setString(4, c.getSenha());
        ps.setInt(5, c.getStatus());
        return ps.executeUpdate() > 0;
    }

    public boolean alterar(Cliente c) throws Exception {
        String sql = "UPDATE Cliente SET nome=?, telefone=?, email=?, senha=?, status=? WHERE id=?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setString(1, c.getNome());
        ps.setString(2, c.getTelefone());
        ps.setString(3, c.getEmail());
        ps.setString(4, c.getSenha());
        ps.setInt(5, c.getStatus());
        ps.setInt(6, c.getId());
        return ps.executeUpdate() > 0;
    }

    public boolean excluir(int id) throws Exception {
        String sql = "DELETE FROM Cliente WHERE id=?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setInt(1, id);
        return ps.executeUpdate() > 0;
    }

    public Cliente buscarPorId(int id) throws Exception {
        String sql = "SELECT * FROM Cliente WHERE id=?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Cliente(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("telefone"),
                rs.getString("email"),
                rs.getString("senha"),
                rs.getInt("status")
            );
        }
        return null;
    }

    public ArrayList<Cliente> listarTodos() throws Exception {
        String sql = "SELECT * FROM Cliente";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ArrayList<Cliente> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new Cliente(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("telefone"),
                rs.getString("email"),
                rs.getString("senha"),
                rs.getInt("status")
            ));
        }
        return list;
    }
}
