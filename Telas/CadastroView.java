package Telas;

import DAO.UsuarioDAO;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class CadastroView {

    private BorderPane layoutCadastro;

    private TextField txtNome;
    private TextField txtEmail;
    private PasswordField txtSenha;
    private Label lblErroEmail;

    public CadastroView() {
        layoutCadastro = new BorderPane();

        // ===== Fundo com imagem borrada =====

        StackPane root = new StackPane( layoutCadastro);

        // ===== Botão de modo escuro/claro =====
        Button btnTema = new Button("\uD83C\uDF19");
        btnTema.setStyle("-fx-background-color: transparent; -fx-font-size: 20px;");
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

        // ===== Conteúdo central =====
        VBox centro = new VBox(10);
        centro.setAlignment(Pos.CENTER);

        Label lblNomeLabel = new Label("Nome:");
        txtNome = new TextField();
        txtNome.setMaxWidth(500);

        Label lblEmailLabel = new Label("Email:");
        txtEmail = new TextField();
        txtEmail.setPromptText("exemplo@dominio.com");
        txtEmail.setMaxWidth(500);

        lblErroEmail = new Label();
        lblErroEmail.setStyle("-fx-text-fill: red;");

        Label lblSenhaLabel = new Label("Senha:");
        txtSenha = new PasswordField();
        txtSenha.setMaxWidth(500);

        Button btnSalvar = new Button("Salvar");
        Button btnVoltar = new Button("Voltar");

        btnSalvar.setOnAction(e -> {
            String nome = txtNome.getText().trim();
            String email = txtEmail.getText().trim();
            String senha = txtSenha.getText().trim();

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                lblErroEmail.setText("Preencha todos os campos!");
                lblErroEmail.setStyle("-fx-text-fill: red;");
                return;
            }

            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                lblErroEmail.setText("Email inválido!");
                lblErroEmail.setStyle("-fx-text-fill: red;");
                return;
            }

            boolean sucesso = UsuarioDAO.cadastrar(nome, email, senha);
            if (sucesso) {
                lblErroEmail.setText("Usuário cadastrado com sucesso!");
                lblErroEmail.setStyle("-fx-text-fill: limegreen;");
                limparCampos();
            } else {
                lblErroEmail.setText("Erro ao cadastrar! Email já existe?");
                lblErroEmail.setStyle("-fx-text-fill: red;");
            }
        });

        btnVoltar.setOnAction(e -> {
            limparCampos();
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

        
        layoutCadastro.setStyle("-fx-font-family: 'Segoe UI', 'Roboto', sans-serif;");

        
        layoutCadastro = new BorderPane(root);
    }

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

