package controller;

import java.sql.*;
import java.util.ArrayList;
import model.Personagem;
import dao.Conexao;

public class PersonagemController {

    // Listar personagens
    public static ArrayList<Personagem> listar() {
        ArrayList<Personagem> lista = new ArrayList<>();
        String sql = "SELECT p.nome, p.vida, r.nome AS raca, p.idade " +
                     "FROM Personagem p LEFT JOIN Raca r ON p.raca_id = r.id";

        try (Connection con = Conexao.conectar();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Personagem p = new Personagem(
                    rs.getString("nome"),
                    rs.getInt("vida"),
                    rs.getString("raca"),
                    rs.getInt("idade")
                );
                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Adicionar personagem
    public static void adicionar(Personagem p) {
        int racaId = getRacaIdPorNome(p.getRaca());
        if (racaId == 0) {
            System.out.println("Raça inválida: " + p.getRaca());
            return;
        }

        String sql = "INSERT INTO Personagem (nome, vida, raca_id, idade) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, p.getNome());
            stmt.setInt(2, p.getVida());
            stmt.setInt(3, racaId);
            stmt.setInt(4, p.getIdade());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Atualizar personagem
    public static void atualizar(Personagem p) {
        int racaId = getRacaIdPorNome(p.getRaca());
        if (racaId == 0) {
            System.out.println("Raça inválida: " + p.getRaca());
            return;
        }

        String sql = "UPDATE Personagem SET vida = ?, raca_id = ?, idade = ? WHERE nome = ?";

        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, p.getVida());
            stmt.setInt(2, racaId);
            stmt.setInt(3, p.getIdade());
            stmt.setString(4, p.getNome());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Remover personagem pelo nome
    public static void remover(String nome) {
        String sql = "DELETE FROM Personagem WHERE nome = ?";

        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método que retorna o ID da raça pelo nome
    private static int getRacaIdPorNome(String nome) {
        String sql = "SELECT id FROM Raca WHERE nome = ?";
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Não achou
    }
}
