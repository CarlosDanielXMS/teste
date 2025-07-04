package entities;
public abstract class Usuario {
    private int id;
    private String nome;
    private String telefone;
    private String email;
    private String senha;
    private int status;

    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setEmail(String email) { this.email = email; }
    public void setSenha(String senha) { this.senha = senha; }
    public void setStatus(int status) { this.status = status; }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getTelefone() { return telefone; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public int getStatus() { return status; }

    public Usuario(){
        setId(0);
        setNome(new String());
        setTelefone(new String());
        setEmail(new String());
        setSenha(new String());
        setStatus(0);
    }

    public Usuario(int id, String nome, String telefone, String email, String senha, int status){
        setId(id);
        setNome(nome);
        setTelefone(telefone);
        setEmail(email);
        setSenha(senha);
        setStatus(status);
    }

    public String toString(){
        return String.format("%s: %d\n%s: %s\n%s: %s\n%s: %s\n%s: %d\n",
            "Id", getId(),
            "Nome", getNome(),
            "Telefone", getTelefone(),
            "Email", getEmail(),
            "Status", getStatus()
        );
    }
}
