package br.com.fiap.model;

import java.util.Date;

public class TransacaoConsumidor {

	private Long idTransacaoConsumidor;
	private Date dataTransicao;
	private int qtdTransacaoConsumidor;
	private double valorTotalTransacao;
	private ConsumidorProduto consumidorProduto;

	public TransacaoConsumidor(Long idTransacaoConsumidor, Date dataTransicao, int qtdTransacaoConsumidor,
            double valorTotalTransacao, ConsumidorProduto consumidorProduto) {
	this.idTransacaoConsumidor = idTransacaoConsumidor;
	this.dataTransicao = dataTransicao;
	this.qtdTransacaoConsumidor = qtdTransacaoConsumidor;
	this.valorTotalTransacao = valorTotalTransacao;
	this.consumidorProduto = consumidorProduto;
}






	public Long getIdTransacaoConsumidor() {
		return idTransacaoConsumidor;
	}

	public void setIdTransacaoConsumidor(Long idTransacaoConsumidor) {
		this.idTransacaoConsumidor = idTransacaoConsumidor;
	}

	public Date getDataTransicao() {
		return dataTransicao;
	}

	public void setDataTransicao(Date dataTransicao) {
		this.dataTransicao = dataTransicao;
	}

	public int getQtdTransacaoConsumidor() {
		return qtdTransacaoConsumidor;
	}

	public void setQtdTransacaoConsumidor(int qtdTransacaoConsumidor) {
		this.qtdTransacaoConsumidor = qtdTransacaoConsumidor;
	}

	public double getValorTotalTransacao() {
		return valorTotalTransacao;
	}

	public void setValorTotalTransacao(double valorTotalTransacao) {
		this.valorTotalTransacao = valorTotalTransacao;
	}

	public ConsumidorProduto getConsumidorProduto() {
		return consumidorProduto;
	}

	public void setConsumidorProduto(ConsumidorProduto consumidorProduto) {
		this.consumidorProduto = consumidorProduto;
	}

	public Long getIdConsumidorProduto() {
		return consumidorProduto.getIdConsumidorProduto();
	}

	@Override
	public String toString() {
		return "TransacaoConsumidor ["
				+ "idTransacaoConsumidor=" + idTransacaoConsumidor 
				+ ", dataTransicao=" + dataTransicao 
				+ ", qtdTransacaoConsumidor=" + qtdTransacaoConsumidor 
				+ ", valorTotalTransacao=" + valorTotalTransacao 
				+ ", consumidorProduto=" + consumidorProduto 
				+ "]";
	}

}
