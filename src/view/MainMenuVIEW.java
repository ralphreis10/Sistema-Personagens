package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenuVIEW extends JFrame {

    private JButton btnCadastro, btnLista, btnSair;

    public MainMenuVIEW() {
        setTitle("Menu Principal");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout principal
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaçamento

        // Label de boas-vindas
        JLabel lbl = new JLabel("Bem-vindo!");
        lbl.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lbl, gbc);

        // Botão Cadastro/Editar
        btnCadastro = new JButton("Adicionar/Editar Personagens");
        btnCadastro.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(btnCadastro, gbc);

        // Botão Visualizar Lista
        btnLista = new JButton("Visualizar Lista");
        btnLista.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(btnLista, gbc);

        // Botão Sair
        btnSair = new JButton("Sair");
        btnSair.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(btnSair, gbc);

        // Ações dos botões
        btnCadastro.addActionListener(e -> new CadastroPersonagemVIEW().setVisible(true));
        btnLista.addActionListener(e -> new ListaPersonagensVIEW().setVisible(true));
        btnSair.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainMenuVIEW().setVisible(true);
        });
    }
}
