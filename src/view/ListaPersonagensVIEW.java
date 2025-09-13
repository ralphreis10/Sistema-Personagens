package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.awt.*;
import controller.PersonagemController;
import model.Personagem;
import java.util.ArrayList;

public class ListaPersonagensVIEW extends JFrame {

    private JTable tabela;
    private JButton btnVoltar;
    private JTextField txtFiltroNome;
    private JComboBox<String> cbRaca;
    private JButton btnFiltrar;

    public ListaPersonagensVIEW() {
        setTitle("Lista de Personagens");
        setSize(550,350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Label e campo filtro por nome
        JLabel lblFiltroNome = new JLabel("Filtrar por nome:");
        lblFiltroNome.setBounds(20, 20, 150, 25);
        lblFiltroNome.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lblFiltroNome);

        txtFiltroNome = new JTextField();
        txtFiltroNome.setBounds(170, 20, 150, 25);
        txtFiltroNome.setFont(new Font("Arial", Font.PLAIN, 16));
        add(txtFiltroNome);

        // Label e ComboBox filtro por raça
        JLabel lblFiltroRaca = new JLabel("Filtrar por raça:");
        lblFiltroRaca.setBounds(330, 20, 120, 25);
        lblFiltroRaca.setFont(new Font("Arial", Font.PLAIN, 16));
        add(lblFiltroRaca);

        cbRaca = new JComboBox<>(new String[]{"Todas", "Anão", "Elfo", "Bruxo", "Humano"});
        cbRaca.setBounds(450, 20, 90, 25);
        cbRaca.setFont(new Font("Arial", Font.PLAIN, 16));
        add(cbRaca);

        // Botão Filtrar
        btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBounds(220, 60, 100, 30);
        btnFiltrar.setFont(new Font("Arial", Font.PLAIN, 16));
        add(btnFiltrar);

        btnFiltrar.addActionListener(e -> {
            String filtroNome = txtFiltroNome.getText();
            String filtroRaca = cbRaca.getSelectedItem().toString();
            carregarTabela(filtroNome, filtroRaca);
        });

        // Tabela
        tabela = new JTable() {
            // Faz a tabela não editável
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela.setFont(new Font("Arial", Font.PLAIN, 16));
        tabela.setRowHeight(25);
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(20,100,500,180);
        add(scroll);

        // Botão Voltar
        btnVoltar = new JButton("Voltar");
        btnVoltar.setBounds(200,290,120,30);
        btnVoltar.setFont(new Font("Arial", Font.PLAIN, 16));
        add(btnVoltar);

        btnVoltar.addActionListener(e -> dispose());

        // Carregar tabela inicial
        carregarTabela("", "Todas");
    }

    private void carregarTabela(String filtroNome, String filtroRaca) {
        String[] colunas = {"Nome","Vida","Raça","Idade"};
        DefaultTableModel modelo = new DefaultTableModel(colunas,0) {
            public boolean isCellEditable(int row, int column) {
                return false; // tabela não editável
            }
        };

        ArrayList<Personagem> lista = PersonagemController.listar();
        for(Personagem p : lista) {
            boolean matchNome = filtroNome == null || filtroNome.isEmpty() || p.getNome().toLowerCase().contains(filtroNome.toLowerCase());
            boolean matchRaca = filtroRaca.equals("Todas") || p.getRaca().equalsIgnoreCase(filtroRaca);

            if(matchNome && matchRaca){
                Object[] linha = {p.getNome(), p.getVida(), p.getRaca(), p.getIdade()};
                modelo.addRow(linha);
            }
        }

        tabela.setModel(modelo);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ListaPersonagensVIEW().setVisible(true));
    }
}
