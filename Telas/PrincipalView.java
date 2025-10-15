package Telas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PrincipalView {

    private BorderPane layout;

    public PrincipalView() {
        layout = new BorderPane();

         layout.setStyle("-fx-font-family: 'Segoe UI', 'Roboto', sans-serif;");

        // 🌙 Botão de tema (Lua / Sol)
        Button btnTema = new Button("\uD83C\uDF19"); // Lua
        btnTema.setStyle("-fx-background-color: transparent; -fx-font-size: 20px;");
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

        // 🐾 Títulos
        Text tituloPrincipal = new Text("Seja bem-vindo ao Petshop!");
        tituloPrincipal.setFont(Font.font("Arial", 28));

        Text tituloServicos = new Text("Serviços do PetShop");
        tituloServicos.setFont(Font.font("Arial", 20));

        VBox titulosBox = new VBox(6, tituloPrincipal, tituloServicos);
        titulosBox.setAlignment(Pos.CENTER);

        // Container superior com botão de tema + títulos
        BorderPane topContainer = new BorderPane();
        topContainer.setLeft(btnTema);
        topContainer.setCenter(titulosBox);
        BorderPane.setAlignment(btnTema, Pos.TOP_LEFT);
        BorderPane.setAlignment(titulosBox, Pos.CENTER);
        topContainer.setPadding(new Insets(10));

        layout.setTop(topContainer);

        // ====== GRID DE BOTÕES ======
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(30);
        grid.setVgap(30);
        grid.setPadding(new Insets(20));

        Button btnCadastrar = criarBotao("🐶 Cadastrar Bichinho");
        Button btnBanho = criarBotao("🛁 Dar Banho");
        Button btnTosa = criarBotao("✂️ Tosa");
        Button btnCabelo = criarBotao("🎨 Cabelo Maluco");
        Button btnUnhas = criarBotao("💅 Cortar Unhas");
        Button btnSpa = criarBotao("🌸 Spa");
        Button btnCompleto = criarBotao("💎 Serviço Completo");
        Button btnPerfil = criarBotao("👤 Perfil");
        Button btnSair = criarBotao("🚪 Sair");

        Button[] botoes = { btnCadastrar, btnBanho, btnTosa, btnCabelo, btnUnhas, btnSpa, btnCompleto, btnPerfil, btnSair };

        int col = 0, row = 0;
        for (Button b : botoes) {
            grid.add(b, col, row);
            col++;
            if (col == 3) {
                col = 0;
                row++;
            }
        }

        // Botão de sair com pop-up de confirmação
        btnSair.setOnAction(e -> mostrarPopupSair());

        layout.setCenter(grid);
    }

    // ===== POPUP DE CONFIRMAÇÃO =====
    private void mostrarPopupSair() {
        BoxBlur blur = new BoxBlur(5, 5, 3);
        layout.setEffect(blur);

        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initStyle(StageStyle.TRANSPARENT);

        VBox popBox = new VBox(20);
        popBox.setAlignment(Pos.CENTER);
        popBox.setPadding(new Insets(25));
        popBox.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(15), Insets.EMPTY)));
        popBox.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(15), BorderWidths.DEFAULT)));

        Text mensagem = new Text("Tem certeza que deseja sair?");
        mensagem.setFont(Font.font("Arial", 18));

        HBox botoes = new HBox(20);
        botoes.setAlignment(Pos.CENTER);

        Button btnSim = new Button("Sim");
        Button btnCancel = new Button("Cancelar");

        btnSim.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        btnCancel.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-weight: bold;");

        botoes.getChildren().addAll(btnSim, btnCancel);
        popBox.getChildren().addAll(mensagem, botoes);

        StackPane root = new StackPane(popBox);
        root.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        Scene popUpScene = new Scene(root, 400, 200, Color.TRANSPARENT);

        popup.setScene(popUpScene);
        popup.centerOnScreen();
        popup.show();

        btnSim.setOnAction(e -> {
            popup.close();
            layout.setEffect(null);
            Main.trocarTela("login"); // volta para a tela de login
        });

        btnCancel.setOnAction(e -> {
            popup.close();
            layout.setEffect(null);
        });
    }

    // ===== CRIADOR DE BOTÕES =====
    private Button criarBotao(String texto) {
        Button botao = new Button(texto);
        botao.setPrefSize(220, 100);
        botao.setWrapText(true);

        String baseStyle = "-fx-background-color: #0a22acff; -fx-text-fill: white; -fx-font-size: 14px; "
                + "-fx-font-weight: bold; -fx-background-radius: 12; -fx-cursor: hand;";
        String hoverStyle = "-fx-background-color: #17238dff; -fx-text-fill: white; -fx-font-size: 14px; "
                + "-fx-font-weight: bold; -fx-background-radius: 12; -fx-cursor: hand;";

        botao.setStyle(baseStyle);
        botao.setOnMouseEntered(e -> botao.setStyle(hoverStyle));
        botao.setOnMouseExited(e -> botao.setStyle(baseStyle));

        // Ação genérica
        botao.setOnAction(e -> System.out.println("Botão clicado: " + texto));

        return botao;
    }

    public BorderPane getView() {
        return layout;
    }
}


