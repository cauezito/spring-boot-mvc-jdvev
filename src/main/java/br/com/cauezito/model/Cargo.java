package br.com.cauezito.model;

public enum Cargo {
	ESTAGIARIO("Estagiário"),
	JUNIOR("Júnior"),
	PLENO("Pleno"),
	SENIOR("Sênior"),
	TRAINEE("Trainee");

	private String nome;
	
	private Cargo(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return this.name();
	}
}
