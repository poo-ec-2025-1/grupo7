package UFGCarona.view;

import javax.swing.*;
import java.awt.*;

public class CadastroView extends JDialog {
    private JTextField txtNome;
    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JTextField txtTelefone;
    private JComboBox<String> cbTipoUsuario;
    private JButton btnCadastrar;
    private JButton btnCancelar;
    private JTextField txtNumeroHabilitacao; // Novo campo
    private JTextField txtPlacaVeiculo;      // Novo campo

    public CadastroView(JFrame parent) {
        super(parent, "Cadastro de Usuário", true);
        setSize(450, 400);
        setLayout(new BorderLayout());
        
        JPanel panelCampos = new JPanel(new GridLayout(8, 2, 5, 5));
        
        // Campos básicos
        panelCampos.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        panelCampos.add(txtNome);
        
        panelCampos.add(new JLabel("E-mail @discente.ufg.br:"));
        txtEmail = new JTextField();
        panelCampos.add(txtEmail);
        
        panelCampos.add(new JLabel("Senha:"));
        txtSenha = new JPasswordField();
        panelCampos.add(txtSenha);
        
        panelCampos.add(new JLabel("Telefone:"));
        txtTelefone = new JTextField();
        panelCampos.add(txtTelefone);
        
        // Tipo de usuário
        panelCampos.add(new JLabel("Tipo de Usuário:"));
        cbTipoUsuario = new JComboBox<>(new String[]{"Passageiro", "Motorista"});
        cbTipoUsuario.addActionListener(e -> toggleCamposMotorista());
        panelCampos.add(cbTipoUsuario);
        
        // Campos específicos de motorista (inicialmente invisíveis)
        panelCampos.add(new JLabel("Nº Habilitação:"));
        txtNumeroHabilitacao = new JTextField();
        txtNumeroHabilitacao.setVisible(false);
        panelCampos.add(txtNumeroHabilitacao);
        
        panelCampos.add(new JLabel("Placa do Veículo:"));
        txtPlacaVeiculo = new JTextField();
        txtPlacaVeiculo.setVisible(false);
        panelCampos.add(txtPlacaVeiculo);
        
        // Botões
        JPanel panelBotoes = new JPanel();
        btnCadastrar = new JButton("Cadastrar");
        btnCancelar = new JButton("Cancelar");
        panelBotoes.add(btnCancelar);
        panelBotoes.add(btnCadastrar);
        
        add(panelCampos, BorderLayout.CENTER);
        add(panelBotoes, BorderLayout.SOUTH);
    }
    
    public void mostrarErroPlacaExistente(String placa) {
    JOptionPane.showMessageDialog(this,
        "A placa " + placa + " já está cadastrada para outro veículo",
        "Placa Duplicada",
        JOptionPane.ERROR_MESSAGE);
    txtPlacaVeiculo.setText("");
    txtPlacaVeiculo.requestFocus();
   }

    private void toggleCamposMotorista() {
        boolean isMotorista = cbTipoUsuario.getSelectedItem().equals("Motorista");
        txtNumeroHabilitacao.setVisible(isMotorista);
        txtPlacaVeiculo.setVisible(isMotorista);
        
        // Ajusta o tamanho da janela
        pack();
    }

    // Getters
    public String getNome() { return txtNome.getText(); }
    public String getEmail() { return txtEmail.getText(); }
    public String getSenha() { return new String(txtSenha.getPassword()); }
    public String getTelefone() { return txtTelefone.getText(); }
    public String getTipoUsuario() { return cbTipoUsuario.getSelectedItem().toString(); }
    public String getNumeroHabilitacao() { return txtNumeroHabilitacao.getText(); }
    public String getPlacaVeiculo() { return txtPlacaVeiculo.getText(); }
    public JButton getBtnCadastrar() { return btnCadastrar; }
    public JButton getBtnCancelar() { return btnCancelar; }
}