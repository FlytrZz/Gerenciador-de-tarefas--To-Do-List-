package registro;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.google.gson.Gson;

import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

public class Tela {

    private JFrame frameTela;
    private JTable table;
    private DefaultTableModel modelo;
    private JTextField txtFiltro;
    private List <Tarefa> tarefas = new LinkedList<Tarefa>();
    Gson g = new Gson();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            public void run() {

                try {

                    Tela window = new Tela();
                    window.frameTela.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Tela() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        frameTela = new JFrame();
        frameTela.setTitle("Sistema de Tarefas - To Do List");
        frameTela.setBounds(100, 100, 850, 320);
        frameTela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameTela.getContentPane().setLayout(null);

        // ================= BOTÕES =================

        JButton btnCarregarDados = new JButton("Carregar Dados");
        btnCarregarDados.setBounds(10, 11, 153, 23);
        frameTela.getContentPane().add(btnCarregarDados);

        JButton btnAdicionarTarefa = new JButton("Adicionar Tarefa");
        btnAdicionarTarefa.setBounds(173, 11, 153, 23);
        frameTela.getContentPane().add(btnAdicionarTarefa);

        JButton btnEditarTarefa = new JButton("Editar Tarefa");
        btnEditarTarefa.setBounds(336, 11, 153, 23);
        frameTela.getContentPane().add(btnEditarTarefa);

        JButton btnDeletarTarefa = new JButton("Deletar Tarefa");
        btnDeletarTarefa.setBounds(499, 11, 153, 23);
        frameTela.getContentPane().add(btnDeletarTarefa);

        JButton btnAplicarAtualizacoes =
                new JButton("Aplicar Atualizações");
        btnAplicarAtualizacoes.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {

                    FileWriter escrita =
                            new FileWriter("tarefas.json");

                    String json =
                            g.toJson(tarefas);

                    escrita.write(json);

                    escrita.close();

                    JOptionPane.showMessageDialog(
                            null,
                            "Dados salvos!"
                    );

                } catch (Exception erro) {

                    erro.printStackTrace();

                    JOptionPane.showMessageDialog(
                            null,
                            "Erro ao salvar!"
                    );
                }
            }
        });

        btnAplicarAtualizacoes.setBounds(
                662,
                11,
                161,
                23
        );

        frameTela.getContentPane().add(
                btnAplicarAtualizacoes
        );

     // ================= TABELA =================

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 79, 813, 171);
        frameTela.getContentPane().add(scrollPane);

        table = new JTable();

        modelo = new DefaultTableModel();

        modelo.addColumn("Id");
        modelo.addColumn("Feita");
        modelo.addColumn("Data");
        modelo.addColumn("Prioridade");
        modelo.addColumn("Tarefa");
        modelo.addColumn("Detalhes");
        modelo.addColumn("Usuário");

        table.setModel(modelo);
        TableRowSorter<DefaultTableModel> sorter =
                new TableRowSorter<>(modelo);

        table.setRowSorter(sorter);

        scrollPane.setViewportView(table);
        
        // ================= FILTRO =================

        txtFiltro = new JTextField();
        txtFiltro.setBounds(10, 45, 642, 23);
        frameTela.getContentPane().add(txtFiltro);
        txtFiltro.setColumns(10);

        JButton btnAplicarFiltros =
                new JButton("Aplicar Filtros");
        btnAplicarFiltros.addActionListener(new ActionListener() {

        	    public void actionPerformed(ActionEvent e) {

        	        String texto =
        	                txtFiltro.getText();

        	        if (texto.trim().isEmpty()) {

        	            sorter.setRowFilter(null);

        	        } else {

        	        	sorter.setRowFilter(
        	        	        RowFilter.regexFilter(
        	        	                "(?i)" + texto
        	        	        )
        	        	);
        	        }
        	    }
        	});

        btnAplicarFiltros.setBounds(
                662,
                45,
                161,
                23
        );

        frameTela.getContentPane().add(
                btnAplicarFiltros
        );

        // ================= CARREGAR DADOS =================

        btnCarregarDados.addActionListener(
                new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                modelo.addRow(new Object[] {
                        1,
                        "Já",
                        "05-Jun-2026",
                        "Alta",
                        "Estudar Java",
                        "Swing e JTable",
                        "João"
                });

                modelo.addRow(new Object[] {
                        2,
                        "Ainda não",
                        "06-Jun-2026",
                        "Média",
                        "Treinar",
                        "Academia",
                        "João"
                });

                JOptionPane.showMessageDialog(
                        null,
                        "Dados carregados!"
                );
            }
        });

        // ================= ADICIONAR =================

        btnAdicionarTarefa.addActionListener(
                new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                String tarefa =
                        JOptionPane.showInputDialog(
                                "Digite a tarefa:"
                        );

                if (tarefa == null ||
                        tarefa.trim().isEmpty()) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Tarefa inválida!"
                    );

                    return;
                }

                int id = modelo.getRowCount() + 1;
                Tarefa t = new Tarefa(
                        id,
                        "não",
                        "05-Jun-2026",
                        "Média",
                        tarefa,
                        "Sem detalhes",
                        "João"
                );

                tarefas.add(t);

                modelo.addRow(new Object[] {

                        t.getId(),
                        t.getDone(),
                        t.getData(),
                        t.getPrioridade(),
                        t.getTarefa(),
                        t.getDetalhes(),
                        t.getUsuario()
                });

                JOptionPane.showMessageDialog(
                        null,
                        "Tarefa adicionada!"
                );
            }
        });

        // ================= EDITAR =================

        btnEditarTarefa.addActionListener(
                new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                int linha =
                        table.getSelectedRow();

                if (linha == -1) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Selecione uma linha!"
                    );

                    return;
                }

                String novaTarefa =
                        JOptionPane.showInputDialog(
                                "Editar tarefa:",
                                modelo.getValueAt(linha, 4)
                        );

                if (novaTarefa != null &&
                        !novaTarefa.trim().isEmpty()) {

                    modelo.setValueAt(
                            novaTarefa,
                            linha,
                            4
                    );

                    JOptionPane.showMessageDialog(
                            null,
                            "Tarefa atualizada!"
                    );
                }
            }
        });

        // ================= DELETAR =================

        btnDeletarTarefa.addActionListener(
                new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                int linha =
                        table.getSelectedRow();

                if (linha == -1) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Selecione uma linha!"
                    );

                    return;
                }

                int confirmar =
                        JOptionPane.showConfirmDialog(
                                null,
                                "Deseja excluir?",
                                "Confirmação",
                                JOptionPane.YES_NO_OPTION
                        );

                if (confirmar ==
                        JOptionPane.YES_OPTION) {

                    modelo.removeRow(linha);

                    JOptionPane.showMessageDialog(
                            null,
                            "Tarefa removida!"
                    );
                }
            }
        });
    }
}