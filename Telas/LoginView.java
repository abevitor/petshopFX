package Telas;

import DAO.UsuarioDAO;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class LoginView {

    private StackPane root;
    private BorderPane layout;
    private TextField txtEmail;
    private PasswordField txtSenha;
    private Label lblMensagem;

    public LoginView() {
        layout = new BorderPane();

        // ===== Fundo com imagem =====
        ImageView background = new ImageView(
                new Image(getClass().getResource("/images/cachorro.jfif").toExternalForm())
        );
        background.setPreserveRatio(false);
        background.setSmooth(true);
        background.setEffect(new GaussianBlur(15));

        // imagem ocupa 100% da tela
        background.fitWidthProperty().bind(layout.widthProperty());
        background.fitHeightProperty().bind(layout.heightProperty());

        // ===== Botão Modo Claro/Escuro =====
        Button btnTema = new Button("\uD83C\uDF19"); // 🌙
        btnTema.setStyle("""
                -fx-font-size: 20px;
                -fx-background-color: rgba(0, 0, 0, 0.4);
                -fx-text-fill: white;
                -fx-background-radius: 50%;
                -fx-padding: 5;
                -fx-cursor: hand;
                """);
        btnTema.setEffect(new DropShadow(8, Color.BLACK)); // sombra para visibilidade

        btnTema.setOnAction(e -> {
            if (layout.getScene() != null) {
                if (btnTema.getText().equals("\uD83C\uDF19")) { // 🌙
                    layout.getScene().getStylesheets().clear();
                    layout.getScene().getStylesheets().add("css/dark.css");
                    btnTema.setText("\u2600"); // ☀️
                    btnTema.setStyle("""
                        -fx-font-size: 20px;
                        -fx-background-color: rgba(255, 255, 255, 0.4);
                        -fx-text-fill: black;
                        -fx-background-radius: 50%;
                        -fx-padding: 5;
                        -fx-cursor: hand;
                        """);
                } else {
                    layout.getScene().getStylesheets().clear();
                    layout.getScene().getStylesheets().add("css/light.css");
                    btnTema.setText("\uD83C\uDF19"); // 🌙
                    btnTema.setStyle("""
                        -fx-font-size: 20px;
                        -fx-background-color: rgba(0, 0, 0, 0.4);
                        -fx-text-fill: white;
                        -fx-background-radius: 50%;
                        -fx-padding: 5;
                        -fx-cursor: hand;
                        """);
                }
            }
        });

        // ===== Campos de login =====
        VBox centro = new VBox(10);
        centro.setAlignment(Pos.CENTER);
        centro.setStyle("-fx-background-color: rgba(255, 255, 255, 0.15); -fx-background-radius: 12; -fx-padding: 30;");

        Label lblEmail = new Label("Email:");
        lblEmail.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        txtEmail = new TextField();
        txtEmail.setPromptText("exemplo@dominio.com");
        txtEmail.setMaxWidth(300);

        Label lblSenha = new Label("Senha:");
        lblSenha.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        txtSenha = new PasswordField();
        txtSenha.setPromptText("Digite sua senha");
        txtSenha.setMaxWidth(300);

        lblMensagem = new Label();
        lblMensagem.setStyle("-fx-text-fill: red;");

        Button btnLogin = new Button("Entrar");
        Button btnCadastro = new Button("Cadastrar");

        btnLogin.setStyle("-fx-font-size: 14px; -fx-background-radius: 8; -fx-padding: 6 15;");
        btnCadastro.setStyle("-fx-font-size: 14px; -fx-background-radius: 8; -fx-padding: 6 15;");

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
                lblMensagem.setStyle("-fx-text-fill: limegreen;");
                Main.trocarTela("principal");
            } else {
                lblMensagem.setText("Usuário inexistente ou senha incorreta.");
                lblMensagem.setStyle("-fx-text-fill: red;");
            }
        });

        // ===== Ação do botão Cadastro =====
        btnCadastro.setOnAction(e -> {
            limparCampos();
            Main.trocarTela("cadastro");
        });

        centro.getChildren().addAll(lblEmail, txtEmail, lblSenha, txtSenha, btnLogin, btnCadastro, lblMensagem);

        layout.setCenter(centro);
        layout.setStyle("-fx-font-family: 'Segoe UI', 'Roboto', sans-serif;");

        // ===== Layout final com StackPane =====
        StackPane.setAlignment(btnTema, Pos.TOP_LEFT);
        StackPane.setMargin(btnTema, new javafx.geometry.Insets(10, 0, 0, 10));

        root = new StackPane(background, layout, btnTema);
    }

    public void limparCampos() {
        txtEmail.clear();
        txtSenha.clear();
        lblMensagem.setText("");
    }

    public StackPane getView() {
        return root;
    }
}
