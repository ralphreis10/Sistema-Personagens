package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.awt.*;
import controller.PersonagemController;
import model.Personagem;
import java.util.ArrayList;

public class CadastroPersonagemVIEW extends JFrame {

    private JTextField txtNome, txtVida, txtIdade;
    private JComboBox<String> cbRaca;
    private JButton btnSalvar, btnEditar, btnVoltar;
    private JTable tabela;
    private DefaultTableModel modelo;
    private String personagemSelecionado = null; // guarda o personagem selecionado para edição

    public CadastroPersonagemVIEW() {
        setTitle("Adicionar/Editar Personagens");
        setSize(600,450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Labels e campos
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(20, 20, 100, 25);
        add(lblNome);
        txtNome = new JTextField();
        txtNome.setBounds(120, 20, 200, 25);
        add(txtNome);

        JLabel lblVida = new JLabel("Vida:");
        lblVida.setBounds(20, 60, 100, 25);
        add(lblVida);
        txtVida = new JTextField();
        txtVida.setBounds(120, 60, 100, 25);
        add(txtVida);

        JLabel lblIdade = new JLabel("Idade:");
        lblIdade.setBounds(20, 100, 100, 25);
        add(lblIdade);
        txtIdade = new JTextField();
        txtIdade.setBounds(120, 100, 100, 25);
        add(txtIdade);

        JLabel lblRaca = new JLabel("Raça:");
        lblRaca.setBounds(20, 140, 100, 25);
        add(lblRaca);
        cbRaca = new JComboBox<>(new String[]{"Anão", "Elfo", "Bruxo", "Humano"});
        cbRaca.setBounds(120, 140, 120, 25);
        add(cbRaca);

        // Botões
        btnSalvar = new JButton("Adicionar");
        btnSalvar.setBounds(20, 180, 120, 30);
        add(btnSalvar);

        btnEditar = new JButton("Editar Selecionado");
        btnEditar.setBounds(150, 180, 180, 30);
        add(btnEditar);

        btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(350, 180, 120, 30);
        add(btnVoltar);

        // Tabela
        tabela = new JTable() {
            public boolean isCellEditable(int row, int column) {
                return false; // tabela não editável diretamente
            }
        };
        tabela.setFont(new Font("Arial", Font.PLAIN, 16));
        tabela.setRowHeight(25);
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(20, 230, 540, 150);
        add(scroll);

        // Carregar dados iniciais
        carregarTabela();

        // Ações
        btnSalvar.addActionListener(e -> adicionarOuAtualizar());
        btnEditar.addActionListener(e -> selecionarParaEdicao());
        btnVoltar.addActionListener(e -> dispose());
    }

    private void carregarTabela() {
        String[] colunas = {"Nome", "Vida", "Raça", "Idade"};
        modelo = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        ArrayList<Personagem> lista = PersonagemController.listar();
        for (Personagem p : lista) {
            Object[] linha = {p.getNome(), p.getVida(), p.getRaca(), p.getIdade()};
            modelo.addRow(linha);
        }

        tabela.setModel(modelo);
    }

    private void adicionarOuAtualizar() {
        try {
            String nome = txtNome.getText();
            int vida = Integer.parseInt(txtVida.getText());
            int idade = Integer.parseInt(txtIdade.getText());
            String raca = cbRaca.getSelectedItem().toString();

            Personagem p = new Personagem(nome, vida, raca, idade);

            if (personagemSelecionado == null) {
                // Adicionar novo
                PersonagemController.adicionar(p);
            } else {
                // Atualizar existente
                PersonagemController.atualizar(p);
                personagemSelecionado = null;
                btnSalvar.setText("Adicionar");
            }

            limparCampos();
            carregarTabela();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos corretamente!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void selecionarParaEdicao() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            String nome = tabela.getValueAt(linha, 0).toString();
            int vida = Integer.parseInt(tabela.getValueAt(linha, 1).toString());
            String raca = tabela.getValueAt(linha, 2).toString();
            int idade = Integer.parseInt(tabela.getValueAt(linha, 3).toString());

            txtNome.setText(nome);
            txtVida.setText(String.valueOf(vida));
            txtIdade.setText(String.valueOf(idade));
            cbRaca.setSelectedItem(raca);

            personagemSelecionado = nome;
            btnSalvar.setText("Salvar Alterações");
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um personagem na tabela!", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtVida.setText("");
        txtIdade.setText("");
        cbRaca.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CadastroPersonagemVIEW().setVisible(true));
    }
}
