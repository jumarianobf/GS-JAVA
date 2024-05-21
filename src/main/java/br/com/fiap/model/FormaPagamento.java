package br.com.fiap.model;

public enum FormaPagamento {
    CREDITO("T_FORMA_PAGAMENTO_CREDITO",
            "#id_compra_credito", "M", "valor_compra_credito", "dt_transicao_credito", "dt_vencimento_credito", "qt_parcelas_compra_credito"),
    CURSO("T_COMPRA_CURSO",
            "#id_compra_curso", null, "valor_compra", null, null, null),
    BOLETO("T_FORMA_PAGAMENTO_BOLETO",
            "#id_compra_boleto", "banco_boleto", "valor_compra_boleto", "dt_vencimento_boleto", "dt_transicao_boleto", null),
    DEBITO("T_FORMA_PAGAMENTO_DEBITO",
            "#id_compra_debito", "bandeira_cartao_debito", "valor_compra_debito", "dt_transicao_debito", null, null),
    PIX("T_FORMA_PAGAMENTO_PIX",
            "#id_compra_pix", "banco_pix", "valor_compra_pix", "dt_transicao_pix", "forma_pix", null);

    private final String tipo;
    private final String id;
    private final String bandeira;
    private final String valor;
    private final String dtTransicao;
    private final String dtVencimento;
    private final String qtParcelas;

    FormaPagamento(String tipo, String id, String bandeira, String valor, String dtTransicao, String dtVencimento, String qtParcelas) {
        this.tipo = tipo;
        this.id = id;
        this.bandeira = bandeira;
        this.valor = valor;
        this.dtTransicao = dtTransicao;
        this.dtVencimento = dtVencimento;
        this.qtParcelas = qtParcelas;
    }

    public String getTipo() {
        return tipo;
    }

    public String getId() {
        return id;
    }

    public String getBandeira() {
        return bandeira;
    }

    public String getValor() {
        return valor;
    }

    public String getDtTransicao() {
        return dtTransicao;
    }

    public String getDtVencimento() {
        return dtVencimento;
    }

    public String getQtParcelas() {
        return qtParcelas;
    }
}
