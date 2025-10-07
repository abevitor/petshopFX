package Telas;

import DAO.UsuarioDAO;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class CadastroView {

    private BorderPane layoutCadastro;

    // ✅ Campos como atributos
    private TextField txtNome;
    private TextField txtEmail;
    private PasswordField txtSenha;
    private Label lblErroEmail;

    public CadastroView() {
        layoutCadastro = new BorderPane();

        // ===== Botão modo claro/escuro no topo =====
        Button btnTema = new Button("\uD83C\uDF19");
        btnTema.setOnAction(e -> {
            if (layoutCadastro.getScene() != null) {
                if (btnTema.getText().equals("\uD83C\uDF19")) {
                    layoutCadastro.getScene().getStylesheets().clear();
                    layoutCadastro.getScene().getStylesheets().add("css/dark.css");
                    btnTema.setText("\u2600");
                } else {
                    layoutCadastro.getScene().getStylesheets().clear();
                    layoutCadastro.getScene().getStylesheets().add("css/light.css");
                    btnTema.setText("\uD83C\uDF19");
                }
            }
        });
        layoutCadastro.setTop(btnTema);
        BorderPane.setAlignment(btnTema, Pos.TOP_LEFT);

        // ===== Campos centrais =====
        VBox centro = new VBox(10);
        centro.setAlignment(Pos.CENTER);

        Label lblNomeLabel = new Label("Nome:");
        txtNome = new TextField();
        txtNome.setMaxWidth(500);
        txtNome.setStyle("-fx-border-color: gray; -fx-border-width: 1.5; -fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 5;");

        Label lblEmailLabel = new Label("Email:");
        txtEmail = new TextField();
        txtEmail.setPromptText("exemplo@dominio.com");
        txtEmail.setMaxWidth(500);
        txtEmail.setStyle("-fx-border-color: gray; -fx-border-width: 1.5; -fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 5;");

        lblErroEmail = new Label();
        lblErroEmail.setStyle("-fx-text-fill: red;");

        Label lblSenhaLabel = new Label("Senha:");
        txtSenha = new PasswordField();
        txtSenha.setMaxWidth(500);
        txtSenha.setStyle("-fx-border-color: gray; -fx-border-width: 1.5; -fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 5;");

        Button btnSalvar = new Button("Salvar");
        Button btnVoltar = new Button("Voltar");

        btnSalvar.setOnAction(e -> {
            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim();
            String senha = txtSenha.getText().trim();

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                lblErroEmail.setText("Preencha todos os campos!");
                return;
            }

            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                lblErroEmail.setText("Email inválido!");
                return;
            }

            boolean sucesso = UsuarioDAO.cadastrar(nome, email, senha);
            if (sucesso) {
                lblErroEmail.setStyle("-fx-text-fill: green;");
                lblErroEmail.setText("Usuário cadastrado com sucesso!");
                limparCampos();
            } else {
                lblErroEmail.setStyle("-fx-text-fill: red;");
                lblErroEmail.setText("Erro ao cadastrar! Email já existe?");
            }
        });

        btnVoltar.setOnAction(e -> {
            limparCampos(); // ✅ Limpa ao voltar para login
            Main.trocarTela("login");
        });

        centro.getChildren().addAll(
                lblNomeLabel, txtNome,
                lblEmailLabel, txtEmail,
                lblErroEmail,
                lblSenhaLabel, txtSenha,
                btnSalvar, btnVoltar
        );

        layoutCadastro.setCenter(centro);
    }

    // ✅ Método para limpar campos
    public void limparCampos() {
        txtNome.clear();
        txtEmail.clear();
        txtSenha.clear();
        lblErroEmail.setText("");
    }

    public BorderPane getView() {
        return layoutCadastro;
    }
}
