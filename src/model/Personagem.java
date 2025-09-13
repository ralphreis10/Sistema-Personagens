package model;

public class Personagem {
    private String nome;
    private int vida;
    private String raca;
    private int idade;

    // Construtor vazio necessário para o controller
    public Personagem() {
    }

    // Construtor completo
    public Personagem(String nome, int vida, String raca, int idade) {
        this.nome = nome;
        this.vida = vida;
        this.raca = raca;
        this.idade = idade;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getVida() { return vida; }
    public void setVida(int vida) { this.vida = vida; }

    public String getRaca() { return raca; }
    public void setRaca(String raca) { this.raca = raca; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }
}
