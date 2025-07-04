package banco;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
     public Connection con;

    public Connection getConexao(){
        return con;
    }

    public Conexao() throws Exception{
        String url="jdbc:sqlserver://DANIEL\\SQLEXPRESS:1433;databaseName=ProjetoInter;encrypt=false;trustServerCertificate=true";
        String usuario="sa";
        String senha="dba";
        String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";

        Class.forName(driver);
        con = DriverManager.getConnection(url, usuario, senha);
    }
}
