
public class Nota {

    private int Nota[];

    public int[] getNota() {
        return Nota;
    }

    public void setNota(int[] Nota) {
        this.Nota = Nota;
    }

    public int[] consultarNota(Disciplina disciplina, Aluno aluno) {
        Nota nota = new Nota();

        return nota.getNota();
    }

    public void alterarNota() {

    }

    public void incluirNota(Disciplina disciplina) {

    }

}
