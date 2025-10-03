package Telas;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class CadastroView {

    private BorderPane layoutCadastro;

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

        Label lblNome = new Label("Nome:");
        TextField txtNome = new TextField();
        txtNome.setMaxWidth(500);
        txtNome.setStyle("-fx-border-color: gray; -fx-border-width: 1.5; -fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 5;");

        Label lblEmail = new Label("Email:");
        TextField txtEmail = new TextField();
        txtEmail.setPromptText("exemplo@dominio.com");
        txtEmail.setMaxWidth(500);
        txtEmail.setStyle("-fx-border-color: gray; -fx-border-width: 1.5; -fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 5;");

        Label lblErroEmail = new Label();
        lblErroEmail.setStyle("-fx-text-fill: red;");

        Label lblSenha = new Label("Senha:");
        PasswordField txtSenha = new PasswordField();
        txtSenha.setMaxWidth(500);
        txtSenha.setStyle("-fx-border-color: gray; -fx-border-width: 1.5; -fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 5;");

        Button btnSalvar = new Button("Salvar");
        Button btnVoltar = new Button("Voltar");

        btnSalvar.setOnAction(e -> {
            String email = txtEmail.getText();
            if (email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                lblErroEmail.setText(""); // limpa erro
                System.out.println("Cadastro salvo!");
            } else {
                lblErroEmail.setText("Email inválido!");
            }
        });

        btnVoltar.setOnAction(e -> Main.trocarTela("login"));

        // ===== Adiciona elementos ao VBox central =====
        centro.getChildren().addAll(
                lblNome, txtNome,
                lblEmail, txtEmail,
                lblErroEmail,
                lblSenha, txtSenha,
                btnSalvar, btnVoltar
        );

        layoutCadastro.setCenter(centro);
    }

    public BorderPane getView() {
        return layoutCadastro;
    }
}
