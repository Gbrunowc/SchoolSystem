
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;

public class Professor implements ValidaCPF {

    private String Nome;
    private long CPF;
    private int SIAPE;

    public Professor(String Nome, long CPF, int SIAPE) {
        this.Nome = Nome;
        this.CPF = CPF;
        this.SIAPE = SIAPE;
    }

    public Professor() {

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

    public int getSIAPE() {
        return SIAPE;
    }

    public void setSIAPE(int SIAPE) {
        this.SIAPE = SIAPE;
    }

    public void cadastraProfessor(Professor prof) throws Exception {

        try {
            try ( Connection cn = connector.getConnector()) {
                String sq1 = "insert into mydb.professor(pro_nome,pro_cpf,pro_siape) values(?,?,?)";

                PreparedStatement ps = cn.prepareStatement(sq1);

                ps.setString(1, prof.getNome());
                ps.setLong(2, prof.getCPF());
                ps.setInt(3, prof.getSIAPE());
                ps.execute();
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }

    }

    public void excluirProfessor(int siape) throws SQLException, Exception {
        Connection cn = connector.getConnector();
        String sq1 = "delete from mydb.professor where professor.pro_siape = " + siape;

        PreparedStatement ps = cn.prepareStatement(sq1);
        ps.execute();
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

    public Professor consultaProfessor(int siape) throws SQLException, Exception {
        Connection cn = connector.getConnector();
        String sq1 = "select * from mydb.professor where professor.pro_siape= " + siape;
        PreparedStatement ps = cn.prepareStatement(sq1);
        ResultSet rs = ps.executeQuery(sq1);
        Professor prof = new Professor();
        if (rs.next()) {

            prof.setNome(rs.getString("pro_nome"));
            prof.setCPF(rs.getLong("pro_cpf"));
            prof.setSIAPE(rs.getInt("pro_siape"));

        }
        cn.close();

        return prof;
    }

    public void alterarProfessor(Professor prof) throws SQLException, Exception {
        try ( Connection cn = connector.getConnector()) {
            String sq1 = "update mydb.professor set pro_nome = ?, pro_cpf = ? where pro_siape = ?";
            PreparedStatement ps = cn.prepareStatement(sq1);
            ps.setString(1, prof.getNome());
            ps.setLong(2, prof.getCPF());
            ps.setInt(3, prof.getSIAPE());

            ps.execute();

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }
}
