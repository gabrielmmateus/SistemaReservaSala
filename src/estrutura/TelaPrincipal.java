package estrutura;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.toedter.calendar.JDateChooser;

public class TelaPrincipal extends JFrame {

    // ==========================
    // CORES DO SISTEMA
    // ==========================
    private static final Color COR_FUNDO = new Color(225, 237, 255);
    private static final Color COR_CARD = Color.WHITE;
    private static final Color COR_AZUL = new Color(37, 99, 235);
    private static final Color COR_AZUL_ESCURO = new Color(29, 78, 216);
    private static final Color COR_AZUL_CLARO = new Color(219, 234, 254);
    private static final Color COR_CINZA = new Color(100, 116, 139);
    private static final Color COR_CINZA_ESCURO = new Color(71, 85, 105);
    private static final Color COR_BORDA = new Color(191, 219, 254);
    private static final Color COR_TEXTO = new Color(15, 23, 42);

    // ==========================
    // FONTES
    // ==========================
    private static final Font FONTE_TITULO = new Font("Segoe UI", Font.BOLD, 28);
    private static final Font FONTE_SUBTITULO = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONTE_SECAO = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FONTE_LABEL = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONTE_CAMPO = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONTE_BOTAO = new Font("Segoe UI", Font.BOLD, 14);

    // ==========================
    // COMPONENTES DO FORMULÁRIO
    // ==========================
    private JComboBox<String> comboSala;
    private JDateChooser campoData;
    private JTextField campoInicio;
    private JTextField campoFim;
    private JTextField campoUsuario;

    // ==========================
    // COMPONENTES DA TABELA
    // ==========================
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    // ==========================
    // LÓGICA DO SISTEMA
    // ==========================
    private SistemaReservas sistema;

    public TelaPrincipal() {
        sistema = new SistemaReservas();

        configurarJanela();
        inicializarComponentes();
        montarInterface();
        registrarEventos();
    }

    // Configuração inicial da janela
    private void configurarJanela() {
        setTitle("Sistema de Reserva de Salas");
        setSize(950, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(900, 680));
        setLocationRelativeTo(null);
    }

    // Inicialização dos campos, tabela e componentes principais
    private void inicializarComponentes() {

        String[] salas = {"Sala 01", "Sala 02", "Sala 03", "Sala 04", "Sala 05"};
        comboSala = new JComboBox<>(salas);
        estilizarComboBox(comboSala);

        // Campo de data com calendário
        campoData = new JDateChooser();
        campoData.setDateFormatString("dd/MM/yyyy");
        campoData.setFont(FONTE_CAMPO);

        // Aumenta o campo para mostrar a data completa
        campoData.setPreferredSize(new Dimension(360, 42));
        campoData.setMinSelectableDate(inicioDoDia(new Date()));
        campoData.setDate(inicioDoDia(new Date()));

        // Ajusta o campo interno do calendário
        JComponent editorData = (JComponent) campoData.getDateEditor().getUiComponent();
        editorData.setPreferredSize(new Dimension(320, 42));
        editorData.setFont(FONTE_CAMPO);

        campoInicio = criarCampoTexto();
        campoFim = criarCampoTexto();
        campoUsuario = criarCampoTexto();

        modeloTabela = new DefaultTableModel(
                new String[]{"Sala", "Data", "Início", "Fim", "Usuário"}, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        estilizarTabela();
    }

    // Monta a tela principal
    private void montarInterface() {

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setBackground(COR_FUNDO);
        painelPrincipal.setBorder(new EmptyBorder(24, 24, 24, 24));
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setAlignmentX(Component.CENTER_ALIGNMENT);

        painelPrincipal.add(criarCabecalho());
        painelPrincipal.add(Box.createVerticalStrut(18));
        painelPrincipal.add(criarCardFormulario());
        painelPrincipal.add(Box.createVerticalStrut(18));
        painelPrincipal.add(criarCardTabela());

        setContentPane(painelPrincipal);
    }

    // Registra eventos da tabela
    private void registrarEventos() {
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                preencherFormularioComLinhaSelecionada();
            }
        });
    }

    // ==========================
    // CABEÇALHO
    // ==========================
    private JPanel criarCabecalho() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(COR_AZUL);
        painel.setBorder(new EmptyBorder(22, 24, 22, 24));
        painel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 115));
        painel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titulo = new JLabel("Sistema de Reserva de Salas");
        titulo.setFont(FONTE_TITULO);
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Controle de reservas, horários e disponibilidade de salas");
        subtitulo.setFont(FONTE_SUBTITULO);
        subtitulo.setForeground(new Color(219, 234, 254));

        JPanel textos = new JPanel();
        textos.setBackground(COR_AZUL);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));

        textos.add(titulo);
        textos.add(Box.createVerticalStrut(6));
        textos.add(subtitulo);

        painel.add(textos, BorderLayout.CENTER);

        return painel;
    }

    // ==========================
    // CARD DO FORMULÁRIO
    // ==========================
    private JPanel criarCardFormulario() {
        JPanel card = criarCard();
        card.setLayout(new BorderLayout(0, 18));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 420));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel tituloSecao = new JLabel("Nova Reserva");
        tituloSecao.setFont(FONTE_SECAO);
        tituloSecao.setForeground(COR_TEXTO);

        card.add(tituloSecao, BorderLayout.NORTH);

        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBackground(COR_CARD);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 12, 10, 12);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        formulario.add(criarLabel("Sala"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        formulario.add(comboSala, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        formulario.add(criarLabel("Data"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        formulario.add(campoData, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        formulario.add(criarLabel("Horário inicial"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        formulario.add(campoInicio, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        formulario.add(criarLabel("Horário final"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        formulario.add(campoFim, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        formulario.add(criarLabel("Usuário"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        formulario.add(campoUsuario, gbc);

        JButton btnReservar = criarBotaoPrimario("Reservar");
        JButton btnEditar = criarBotaoSecundario("Editar");
        JButton btnExcluir = criarBotaoExcluir("Excluir");
        JButton btnLimpar = criarBotaoSecundario("Limpar");

        btnReservar.addActionListener(e -> reservar());
        btnEditar.addActionListener(e -> editar());
        btnExcluir.addActionListener(e -> excluir());
        btnLimpar.addActionListener(e -> limparFormulario());

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        painelBotoes.setBackground(COR_CARD);

        painelBotoes.add(btnReservar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLimpar);

        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(COR_CARD);
        centro.add(formulario, BorderLayout.CENTER);
        centro.add(painelBotoes, BorderLayout.SOUTH);

        card.add(centro, BorderLayout.CENTER);

        return card;
    }

    // ==========================
    // CARD DA TABELA
    // ==========================
    private JPanel criarCardTabela() {
        JPanel card = criarCard();
        card.setLayout(new BorderLayout(0, 14));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 320));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel tituloSecao = new JLabel("Reservas Cadastradas");
        tituloSecao.setFont(FONTE_SECAO);
        tituloSecao.setForeground(COR_TEXTO);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(new LineBorder(COR_BORDA, 1, true));
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setPreferredSize(new Dimension(850, 180));

        JLabel dica = new JLabel("Selecione uma reserva na tabela para editar ou excluir.");
        dica.setFont(FONTE_SUBTITULO);
        dica.setForeground(COR_CINZA);

        card.add(tituloSecao, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);
        card.add(dica, BorderLayout.SOUTH);

        return card;
    }

    // ==========================
    // AÇÕES
    // ==========================
    private void reservar() {
        try {
            Reserva nova = criarReservaAPartirFormulario();

            if (sistema.adicionarReserva(nova)) {
                atualizarTabela();
                mostrarMensagem("Reserva criada com sucesso.");
                limparFormulario();
            } else {
                mostrarErro("Conflito detectado: sala ocupada ou usuário já reservado nesse horário.");
            }

        } catch (NumberFormatException e) {
            mostrarErro("Os horários devem ser números inteiros.");
        } catch (IllegalArgumentException e) {
            mostrarErro(e.getMessage());
        } catch (Exception e) {
            mostrarErro("Ocorreu um erro ao criar a reserva.");
        }
    }

    private void editar() {
        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada == -1) {
            mostrarErro("Selecione uma reserva na tabela para editar.");
            return;
        }

        int indiceModelo = tabela.convertRowIndexToModel(linhaSelecionada);

        try {
            Reserva atualizada = criarReservaAPartirFormulario();

            if (sistema.editarReserva(indiceModelo, atualizada)) {
                atualizarTabela();
                mostrarMensagem("Reserva editada com sucesso.");
                limparFormulario();
                tabela.clearSelection();
            } else {
                mostrarErro("Não foi possível editar: conflito de sala ou de usuário.");
            }

        } catch (NumberFormatException e) {
            mostrarErro("Os horários devem ser números inteiros.");
        } catch (IllegalArgumentException e) {
            mostrarErro(e.getMessage());
        } catch (Exception e) {
            mostrarErro("Ocorreu um erro ao editar a reserva.");
        }
    }

    private void excluir() {
        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada == -1) {
            mostrarErro("Selecione uma reserva na tabela para excluir.");
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente excluir a reserva selecionada?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmar != JOptionPane.YES_OPTION) {
            return;
        }

        int indiceModelo = tabela.convertRowIndexToModel(linhaSelecionada);

        if (sistema.removerReserva(indiceModelo)) {
            atualizarTabela();
            mostrarMensagem("Reserva excluída com sucesso.");
            limparFormulario();
            tabela.clearSelection();
        } else {
            mostrarErro("Não foi possível excluir a reserva.");
        }
    }

    // ==========================
    // VALIDAÇÕES
    // ==========================
    private Reserva criarReservaAPartirFormulario() {

        String sala = comboSala.getSelectedItem().toString();
        Date dataSelecionada = campoData.getDate();
        String inicioTexto = campoInicio.getText().trim();
        String fimTexto = campoFim.getText().trim();
        String usuario = campoUsuario.getText().trim();

        if (dataSelecionada == null || inicioTexto.isEmpty() || fimTexto.isEmpty() || usuario.isEmpty()) {
            throw new IllegalArgumentException("Preencha todos os campos antes de continuar.");
        }

        if (usuario.length() < 3) {
            throw new IllegalArgumentException("O nome do usuário deve ter pelo menos 3 caracteres.");
        }

        Date hoje = inicioDoDia(new Date());
        Date dataReserva = inicioDoDia(dataSelecionada);

        if (dataReserva.before(hoje)) {
            throw new IllegalArgumentException("A data da reserva não pode ser passada.");
        }

        int inicio = Integer.parseInt(inicioTexto);
        int fim = Integer.parseInt(fimTexto);

        if (inicio < 8 || fim > 22) {
            throw new IllegalArgumentException("Os horários permitidos vão de 08h até 22h.");
        }

        if (inicio >= fim) {
            throw new IllegalArgumentException("O horário inicial deve ser menor que o horário final.");
        }

        int duracao = fim - inicio;

        if (duracao < 1 || duracao > 4) {
            throw new IllegalArgumentException("A reserva deve ter duração entre 1 e 4 horas.");
        }

        String dataFormatada = formatarData(dataSelecionada);

        return new Reserva(sala, dataFormatada, inicio, fim, usuario);
    }

    // ==========================
    // TABELA
    // ==========================
    private void atualizarTabela() {
        modeloTabela.setRowCount(0);

        for (Reserva r : sistema.getReservas()) {
            modeloTabela.addRow(r.toTableRow());
        }
    }

    private void preencherFormularioComLinhaSelecionada() {
        int linha = tabela.getSelectedRow();

        if (linha == -1) {
            return;
        }

        comboSala.setSelectedItem(tabela.getValueAt(linha, 0));

        String dataTexto = tabela.getValueAt(linha, 1).toString();
        campoData.setDate(converterTextoParaData(dataTexto));

        campoInicio.setText(tabela.getValueAt(linha, 2).toString());
        campoFim.setText(tabela.getValueAt(linha, 3).toString());
        campoUsuario.setText(tabela.getValueAt(linha, 4).toString());
    }

    // ==========================
    // MÉTODOS DE ESTILO
    // ==========================
    private JPanel criarCard() {
        JPanel card = new JPanel();
        card.setBackground(COR_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COR_BORDA, 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        card.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 360));

        return card;
    }

    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(FONTE_LABEL);
        label.setForeground(COR_TEXTO);
        return label;
    }

    private JTextField criarCampoTexto() {
        JTextField campo = new JTextField();
        campo.setFont(FONTE_CAMPO);
        campo.setPreferredSize(new Dimension(360, 42));
        campo.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COR_BORDA, 1, true),
                new EmptyBorder(8, 10, 8, 10)
        ));
        return campo;
    }

    private void estilizarComboBox(JComboBox<String> combo) {
        combo.setFont(FONTE_CAMPO);
        combo.setPreferredSize(new Dimension(360, 42));
        combo.setBackground(Color.WHITE);
        combo.setBorder(new LineBorder(COR_BORDA, 1, true));
    }

    private JButton criarBotaoPrimario(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(FONTE_BOTAO);
        botao.setForeground(Color.WHITE);
        botao.setBackground(COR_AZUL);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setPreferredSize(new Dimension(125, 42));
        botao.setBorder(new LineBorder(COR_AZUL, 1, true));
        aplicarHover(botao, COR_AZUL, COR_AZUL_ESCURO);
        return botao;
    }

    private JButton criarBotaoSecundario(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(FONTE_BOTAO);
        botao.setForeground(Color.WHITE);
        botao.setBackground(COR_CINZA);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setPreferredSize(new Dimension(125, 42));
        botao.setBorder(new LineBorder(COR_CINZA, 1, true));
        aplicarHover(botao, COR_CINZA, COR_CINZA_ESCURO);
        return botao;
    }

    private JButton criarBotaoExcluir(String texto) {
        Color vermelho = new Color(220, 38, 38);
        Color vermelhoEscuro = new Color(185, 28, 28);

        JButton botao = new JButton(texto);
        botao.setFont(FONTE_BOTAO);
        botao.setForeground(Color.WHITE);
        botao.setBackground(vermelho);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setPreferredSize(new Dimension(125, 42));
        botao.setBorder(new LineBorder(vermelho, 1, true));
        aplicarHover(botao, vermelho, vermelhoEscuro);
        return botao;
    }

    private void aplicarHover(JButton botao, Color normal, Color hover) {
        botao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botao.setBackground(hover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botao.setBackground(normal);
            }
        });
    }

    private void estilizarTabela() {
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabela.setRowHeight(32);
        tabela.setFillsViewportHeight(true);
        tabela.setShowGrid(true);
        tabela.setGridColor(new Color(226, 232, 240));
        tabela.setSelectionBackground(COR_AZUL_CLARO);
        tabela.setSelectionForeground(COR_TEXTO);
        tabela.setBackground(Color.WHITE);
        tabela.setForeground(COR_TEXTO);
        tabela.setAutoCreateRowSorter(true);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabela.getTableHeader().setBackground(COR_AZUL);
        tabela.getTableHeader().setForeground(Color.WHITE);
        tabela.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < tabela.getColumnCount(); i++) {
            tabela.getColumnModel().getColumn(i).setCellRenderer(centralizado);
        }
    }

    // ==========================
    // DATAS
    // ==========================
    private String formatarData(Date data) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(data);
    }

    private Date converterTextoParaData(String dataTexto) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.parse(dataTexto);
        } catch (Exception e) {
            return inicioDoDia(new Date());
        }
    }

    private Date inicioDoDia(Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    // ==========================
    // LIMPEZA E MENSAGENS
    // ==========================
    private void limparFormulario() {
        comboSala.setSelectedIndex(0);
        campoData.setDate(inicioDoDia(new Date()));
        campoInicio.setText("");
        campoFim.setText("");
        campoUsuario.setText("");
        tabela.clearSelection();
        campoInicio.requestFocus();
    }

    private void mostrarMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }

    private void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Atenção", JOptionPane.WARNING_MESSAGE);
    }
}