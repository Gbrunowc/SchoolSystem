
import java.sql.Connection;
import java.sql.*;
import java.util.InputMismatchException;

public class Aluno {

    private String Nome;
    private long CPF;
    private int RA;
    private java.sql.Date Nascimento;

    public Aluno(String Nome, long CPF, int RA, java.sql.Date nascimento) {
        this.Nome = Nome;
        this.CPF = CPF;
        this.RA = RA;
        this.Nascimento = nascimento;
    }

    public Aluno(String Nome, long CPF, int RA) {
        this.Nome = Nome;
        this.CPF = CPF;
        this.RA = RA;
    }

    public Aluno() {
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public long getCPF() {
        return CPF;
    }

    public void setCPF(long CPF) {
        this.CPF = CPF;
    }

    public int getRA() {
        return RA;
    }

    public void setRA(int RA) {
        this.RA = RA;
    }

    public java.sql.Date getNascimento() {
        return Nascimento;
    }

    public void setNascimento(java.sql.Date Nascimento) {
        this.Nascimento = Nascimento;
    }

    public boolean ValidaCPF(String CPF) {

        if (CPF.equals("00000000000")
                || CPF.equals("11111111111")
                || CPF.equals("22222222222") || CPF.equals("33333333333")
                || CPF.equals("44444444444") || CPF.equals("55555555555")
                || CPF.equals("66666666666") || CPF.equals("77777777777")
                || CPF.equals("88888888888") || CPF.equals("99999999999")
                || (CPF.length() != 11)) {
            return (false);
        }

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig10 = '0';
            } else {
                dig10 = (char) (r + 48);
            }
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig11 = '0';
            } else {
                dig11 = (char) (r + 48);
            }

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))) {
                return (true);
            } else {
                return (false);
            }
        } catch (InputMismatchException erro) {
            return (false);
        }

    }

    public int buscaRA() throws Exception {
        Connection cn = connector.getConnector();
        String sq1 = "select alu_ra from mydb.aluno where aluno.alu_cpf = " + getCPF();

        PreparedStatement ps = cn.prepareStatement(sq1);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {

            int ra = rs.getInt("alu_ra");
            return ra;
        }

        return 0;
    }

    void incluirAluno(Aluno aluno) throws ClassNotFoundException, Exception {

        try {
            try ( Connection cn = connector.getConnector()) {
                String sq1 = "insert into mydb.aluno(alu_nome,alu_cpf,alu_ra,alu_data) values(?,?,?,?)";

                PreparedStatement ps = cn.prepareStatement(sq1);

                ps.setString(1, aluno.getNome());
                ps.setLong(2, aluno.getCPF());
                ps.setInt(3, aluno.getRA());
                ps.setDate(4, aluno.getNascimento());
                ps.execute();
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }

    }

    public void excluirAluno(int ra) throws SQLException, Exception {
        Connection cn = connector.getConnector();
        String sq1 = "delete from mydb.aluno where aluno.alu_ra = " + ra;

        PreparedStatement ps = cn.prepareStatement(sq1);
        ps.execute();
    }

    public void alterarAluno(Aluno aluno) throws SQLException, Exception {
        try ( Connection cn = connector.getConnector()) {
            String sq1 = "update mydb.aluno set alu_nome = ?, alu_cpf = ?, alu_data = ? where alu_ra = ?";
            PreparedStatement ps = cn.prepareStatement(sq1);
            ps.setString(1, aluno.getNome());
            ps.setLong(2, aluno.getCPF());
            ps.setInt(4, aluno.getRA());
            ps.setDate(3, aluno.getNascimento());
            ps.execute();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    public Aluno consultarAluno(int ra) throws Exception {
        Connection cn = connector.getConnector();
        String sq1 = "select * from mydb.aluno where aluno.alu_ra = " + ra;
        PreparedStatement ps = cn.prepareStatement(sq1);
        ResultSet rs = ps.executeQuery(sq1);
        Aluno aluno = new Aluno();
        if (rs.next()) {

            aluno.setNome(rs.getString("alu_nome"));
            aluno.setCPF(rs.getLong("alu_cpf"));
            aluno.setRA(rs.getInt("alu_ra"));
            aluno.setNascimento(rs.getDate("alu_data"));

        }
        cn.close();
        return aluno;
    }
}
