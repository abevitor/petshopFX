package Telas;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class CadastroView {

    private VBox layoutCadastro;

    public CadastroView(){
        layoutCadastro = new VBox(10);
        layoutCadastro.setAlignment(Pos.CENTER);

        Label lblNome = new Label("Nome:");
        TextField txtNome = new TextField();

        Label lblEmail = new Label("Email:");
        TextField txtEmail = new TextField();
        txtEmail.setPromptText("exemplo@dominio.com");

        Label lblSenha = new Label("Senha");
        PasswordField txtSenha = new PasswordField();

        Button btnSalvar = new Button("Salvar");
        Button btnVoltar = new Button("Voltar");

        btnSalvar.setOnAction(e -> {
            String email = txtEmail.getText();
            if(email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                System.out.println("Cadastro salvo!");
            } else {
                System.out.println("Email inválido!");
            }
        });

        btnVoltar.setOnAction(e -> Main.trocarTela("login"));

        layoutCadastro.getChildren().addAll(
            lblNome, txtNome,
            lblEmail, txtEmail,
            lblSenha, txtSenha,
            btnSalvar, btnVoltar
        );
    }

    public VBox getView(){
        return layoutCadastro;
    }
}
