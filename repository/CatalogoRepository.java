package repository;

import banco.Conexao;
import entities.Catalogo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CatalogoRepository extends Conexao {
    public CatalogoRepository() throws Exception {
    }

    public boolean incluir(Catalogo c) throws Exception {
        String sql = "INSERT INTO Catalogo(idProfissional, idServico, status) VALUES (?,?,?)";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setInt(1, c.getProfissional().getId());
        ps.setInt(2, c.getServico().getId());
        ps.setInt(3, c.getStatus());
        return ps.executeUpdate() > 0;
    }

    public boolean alterar(Catalogo c) throws Exception {
        String sql = "UPDATE Catalogo SET status=? WHERE idProfissional=? AND idServico=?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setInt(1, c.getStatus());
        ps.setInt(2, c.getProfissional().getId());
        ps.setInt(3, c.getServico().getId());
        return ps.executeUpdate() > 0;
    }

    public boolean excluir(int idProf, int idServ) throws Exception {
        String sql = "DELETE FROM Catalogo WHERE idProfissional=? AND idServico=?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setInt(1, idProf);
        ps.setInt(2, idServ);
        return ps.executeUpdate() > 0;
    }

    public Catalogo buscarPorId(int idProf, int idServ) throws Exception {
        String sql = "SELECT * FROM Catalogo WHERE idProfissional=? AND idServico=?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setInt(1, idProf);
        ps.setInt(2, idServ);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            ProfissionalRepository pr = new ProfissionalRepository();
            ServicoRepository sr = new ServicoRepository();
            return new Catalogo(
                    pr.buscarPorId(idProf),
                    sr.buscarPorId(idServ),
                    rs.getInt("status"));
        }
        return null;
    }

    public List<Catalogo> buscarPorProfissional(int idProfissional) throws Exception {
        String sql = "SELECT * FROM Catalogo WHERE idProfissional = ?";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ps.setInt(1, idProfissional);
        ResultSet rs = ps.executeQuery();

        List<Catalogo> lista = new ArrayList<>();
        ProfissionalRepository pr = new ProfissionalRepository();
        ServicoRepository sr = new ServicoRepository();
        while (rs.next()) {
            int idServ = rs.getInt("idServico");
            lista.add(new Catalogo(
                    pr.buscarPorId(idProfissional),
                    sr.buscarPorId(idServ),
                    rs.getInt("status")));
        }
        return lista;
    }

    public ArrayList<Catalogo> listarTodos() throws Exception {
        String sql = "SELECT * FROM Catalogo";
        PreparedStatement ps = getConexao().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ArrayList<Catalogo> list = new ArrayList<>();
        ProfissionalRepository pr = new ProfissionalRepository();
        ServicoRepository sr = new ServicoRepository();
        while (rs.next()) {
            list.add(new Catalogo(
                    pr.buscarPorId(rs.getInt("idProfissional")),
                    sr.buscarPorId(rs.getInt("idServico")),
                    rs.getInt("status")));
        }
        return list;
    }
}
