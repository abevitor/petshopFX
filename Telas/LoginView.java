package Telas;

import DAO.UsuarioDAO;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class LoginView {

    private BorderPane layout;

    // ✅ Campos como atributos
    private TextField txtEmail;
    private PasswordField txtSenha;
    private Label lblMensagem;

    public LoginView() {
        layout = new BorderPane();

        // ===== Botão Modo Claro/Escuro =====
        Button btnTema = new Button("\uD83C\uDF19"); // Lua
        btnTema.setOnAction(e -> {
            if (layout.getScene() != null) {
                if (btnTema.getText().equals("\uD83C\uDF19")) {
                    layout.getScene().getStylesheets().clear();
                    layout.getScene().getStylesheets().add("css/dark.css");
                    btnTema.setText("\u2600"); // Sol
                } else {
                    layout.getScene().getStylesheets().clear();
                    layout.getScene().getStylesheets().add("css/light.css");
                    btnTema.setText("\uD83C\uDF19"); // Lua
                }
            }
        });
        layout.setTop(btnTema);
        BorderPane.setAlignment(btnTema, Pos.TOP_LEFT);

        // ===== Campos centrais =====
        VBox centro = new VBox(10);
        centro.setAlignment(Pos.CENTER);

        Label lblEmail = new Label("Email:");
        txtEmail = new TextField();
        txtEmail.setMaxWidth(500);
        txtEmail.setStyle("-fx-border-color: gray; -fx-border-width: 1.5; -fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 5;");

        Label lblSenha = new Label("Senha:");
        txtSenha = new PasswordField();
        txtSenha.setMaxWidth(500);
        txtSenha.setStyle("-fx-border-color: gray; -fx-border-width: 1.5; -fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 5;");

        lblMensagem = new Label();
        lblMensagem.setStyle("-fx-text-fill: red;");

        Button btnLogin = new Button("Entrar");
        Button btnCadastro = new Button("Cadastrar");

        // ===== Ação do botão Login =====
        btnLogin.setOnAction(e -> {
            String email = txtEmail.getText().trim();
            String senha = txtSenha.getText().trim();

            if (email.isEmpty() || senha.isEmpty()) {
                lblMensagem.setText("Preencha todos os campos!");
                lblMensagem.setStyle("-fx-text-fill: red;");
                return;
            }

            boolean valido = UsuarioDAO.validarLogin(email, senha);
            if (valido) {
                lblMensagem.setText("Login bem-sucedido!");
                lblMensagem.setStyle("-fx-text-fill: green;");
                Main.trocarTela("principal");
            } else {
                lblMensagem.setText("Usuário inexistente ou senha incorreta.");
                lblMensagem.setStyle("-fx-text-fill: red;");
            }
        });

        // ===== Ação do botão Cadastro =====
        btnCadastro.setOnAction(e -> {
            limparCampos(); // ✅ Limpa ao ir para cadastro
            Main.trocarTela("cadastro");
        });

        centro.getChildren().addAll(
                lblEmail, txtEmail,
                lblSenha, txtSenha,
                btnLogin, btnCadastro,
                lblMensagem
        );

        layout.setCenter(centro);
    }

    // ✅ Método para limpar campos
    public void limparCampos() {
        txtEmail.clear();
        txtSenha.clear();
        lblMensagem.setText("");
    }

    public BorderPane getView() {
        return layout;
    }
}
