package Telas;

import javafx.animation.TranslateTransition;
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
import javafx.util.Duration;

public class PrincipalView {

    private StackPane root;
    private BorderPane layout;
    private VBox menuLateral;
    private boolean menuAberto = false;
    private Region overlay; // camada escura ao fundo

    public PrincipalView() {
        layout = new BorderPane();
        layout.setStyle("-fx-font-family: 'Segoe UI', 'Roboto', sans-serif;");

        // 🌙 Botão de tema
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

        // 🍔 Botão de menu hambúrguer
        Button btnMenu = new Button("☰");
        btnMenu.setStyle("-fx-background-color: transparent; -fx-font-size: 20px;");
        btnMenu.setOnAction(e -> alternarMenuLateral());

        // 🐾 Títulos
        Text tituloPrincipal = new Text("Seja bem-vindo ao Petshop!");
        tituloPrincipal.setFont(Font.font("Arial", 28));

        Text tituloServicos = new Text("Serviços do PetShop");
        tituloServicos.setFont(Font.font("Arial", 20));

        VBox titulosBox = new VBox(6, tituloPrincipal, tituloServicos);
        titulosBox.setAlignment(Pos.CENTER);

        // Container superior
        HBox topLeft = new HBox(10, btnMenu, btnTema);
        topLeft.setAlignment(Pos.CENTER_LEFT);

        BorderPane topContainer = new BorderPane();
        topContainer.setLeft(topLeft);
        topContainer.setCenter(titulosBox);
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

        Button[] botoes = {btnCadastrar, btnBanho, btnTosa, btnCabelo, btnUnhas, btnSpa, btnCompleto};

        int col = 0, row = 0;
        for (Button b : botoes) {
            grid.add(b, col, row);
            col++;
            if (col == 3) {
                col = 0;
                row++;
            }
        }

        layout.setCenter(grid);

        // ====== MENU LATERAL ======
        menuLateral = criarMenuLateral();
        menuLateral.setMouseTransparent(true);

        // ====== OVERLAY (fundo escurecido) ======
        overlay = new Region();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);");
        overlay.setVisible(false);
        overlay.setOnMouseClicked(e -> {
            if (menuAberto) alternarMenuLateral(); // fecha menu ao clicar fora
        });

        // Cria o StackPane para sobrepor menu e overlay
        root = new StackPane(layout, overlay, menuLateral);
        StackPane.setAlignment(menuLateral, Pos.TOP_LEFT);
    }

    private VBox criarMenuLateral() {
        VBox menu = new VBox(20);
        menu.setPadding(new Insets(30));
        menu.setAlignment(Pos.TOP_LEFT);
        menu.setPrefWidth(180);
        menu.setMaxWidth(180);
        menu.setStyle("-fx-background-color: #001F8E;");

        Button btnPerfil = criarBotaoMenu("👤 Perfil");
        Button btnConfig = criarBotaoMenu("⚙️ Configurações");
        Button btnPagamentos = criarBotaoMenu("💳 Pagamentos");
        Button btnSair = criarBotaoMenu("🚪 Sair");

        btnSair.setOnAction(e -> mostrarPopupSair());

        menu.getChildren().addAll(btnPerfil, btnConfig, btnPagamentos, btnSair);
        menu.setTranslateX(-180); // começa fora da tela

        return menu;
    }

    private Button criarBotaoMenu(String texto) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; "
                + "-fx-font-size: 16px; -fx-font-weight: bold; -fx-cursor: hand;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: rgba(255,255,255,0.2); "
                + "-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; "
                + "-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;"));
        return btn;
    }

    private void alternarMenuLateral() {
        TranslateTransition anim = new TranslateTransition(Duration.millis(300), menuLateral);
        BoxBlur blur = new BoxBlur(5, 5, 3);

        if (!menuAberto) {
            menuLateral.setMouseTransparent(false);
            anim.setToX(0);
            overlay.setVisible(true);
            layout.setEffect(blur);
        } else {
            anim.setToX(-180);
            anim.setOnFinished(e -> {
                menuLateral.setMouseTransparent(true);
                overlay.setVisible(false);
                layout.setEffect(null);
            });
        }

        menuAberto = !menuAberto;
        anim.play();
    }

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
            Main.trocarTela("login");
        });

        btnCancel.setOnAction(e -> {
            popup.close();
            layout.setEffect(null);
        });
    }

    private Button criarBotao(String texto) {
        Button botao = new Button(texto);
        botao.setPrefSize(220, 100);
        botao.setWrapText(true);

        String baseStyle = "-fx-background-color: #0a22ac; -fx-text-fill: white; -fx-font-size: 14px; "
                + "-fx-font-weight: bold; -fx-background-radius: 12; -fx-cursor: hand;";
        String hoverStyle = "-fx-background-color: #17238d; -fx-text-fill: white; -fx-font-size: 14px; "
                + "-fx-font-weight: bold; -fx-background-radius: 12; -fx-cursor: hand;";

        botao.setStyle(baseStyle);
        botao.setOnMouseEntered(e -> botao.setStyle(hoverStyle));
        botao.setOnMouseExited(e -> botao.setStyle(baseStyle));

        botao.setOnAction(e -> System.out.println("Botão clicado: " + texto));

        return botao;
    }

    public StackPane getView() {
        return root;
    }
}
