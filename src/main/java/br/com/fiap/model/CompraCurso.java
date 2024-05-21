package br.com.fiap.model;

public class CompraCurso {

    private Long idCompraCurso;
    private UsuarioSalesforce usuarioSalesforce;
    private CursoSalesforce cursoSalesforce;
    private FormaPagamento formaPagamento;
    private double valorCompra;

    public CompraCurso(UsuarioSalesforce usuarioSalesforce, CursoSalesforce cursoSalesforce, FormaPagamento formaPagamento, double valorCompra){
        this.usuarioSalesforce = usuarioSalesforce;
        this.cursoSalesforce = cursoSalesforce;
        this.formaPagamento = formaPagamento;
        this.valorCompra = valorCompra;
    }

    public CompraCurso() {

    }

    public Long getIdCompraCurso() {
        return idCompraCurso;
    }

    public void setIdCompraCurso(Long idCompraCurso) {
        this.idCompraCurso = idCompraCurso;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public double getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(double valorCompra) {
        this.valorCompra = valorCompra;
    }

    public UsuarioSalesforce getUsuarioSalesforce() {
        return usuarioSalesforce;
    }

    public void setUsuarioSalesforce(UsuarioSalesforce usuarioSalesforce) {
        this.usuarioSalesforce = usuarioSalesforce;
    }

    public CursoSalesforce getCursoSalesforce() {
        return cursoSalesforce;
    }

    public void setCursoSalesforce(CursoSalesforce cursoSalesforce) {
        this.cursoSalesforce = cursoSalesforce;
    }

    @Override
    public String toString() {
        return "CompraCurso{" +
                "idCompraCurso=" + idCompraCurso +
                ", idCurso=" + cursoSalesforce +
                ", idUsuario=" + usuarioSalesforce +
                ", valorCompra=" + valorCompra +
                ", formaPagamento='" + formaPagamento + '\'' +
                '}';
    }
}
