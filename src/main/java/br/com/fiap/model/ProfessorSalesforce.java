package br.com.fiap.model;

public class ProfessorSalesforce {
    private Long id;
    private String especializacaoProfessor;
    private int qtdAulas;
    private int qtdMaterias;
    private String descricaoMaterias;


    public ProfessorSalesforce(String especializacaoProfessor, int qtdAulas, int qtdMaterias, String descricaoMaterias){
        this.especializacaoProfessor = especializacaoProfessor;
        this.qtdAulas = qtdAulas;
        this.qtdMaterias = qtdMaterias;
        this.descricaoMaterias = descricaoMaterias;
    }

    public ProfessorSalesforce() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEspecializacaoProfessor() {
        return especializacaoProfessor;
    }

    public void setEspecializacaoProfessor(String especializacaoProfessor) {
        this.especializacaoProfessor = especializacaoProfessor;
    }

    public int getQtdAulas() {
        return qtdAulas;
    }

    public void setQtdAulas(int qtdAulas) {
        this.qtdAulas = qtdAulas;
    }

    public int getQtdMaterias() {
        return qtdMaterias;
    }

    public void setQtdMaterias(int qtdMaterias) {
        this.qtdMaterias = qtdMaterias;
    }

    public String getDescricaoMaterias() {
        return descricaoMaterias;
    }

    public void setDescricaoMaterias(String descricaoMaterias) {
        this.descricaoMaterias = descricaoMaterias;
    }

    @Override
    public String toString() {
        return "ProfessorSalesforce{" +
                "id=" + id +
                ", especializacaoProfessor='" + especializacaoProfessor + '\'' +
                ", qtdAulas=" + qtdAulas +
                ", qtdMaterias=" + qtdMaterias +  // Corrigido para int
                ", descricaoMaterias='" + descricaoMaterias + '\'' +  // Corrigido para descricaoMaterias
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProfessorSalesforce that = (ProfessorSalesforce) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
