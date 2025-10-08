package Telas;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class PrincipalView {

    private BorderPane layout;

    public PrincipalView(){
        layout = new BorderPane();

        Text tituloPrincipal = new Text("Seja bem vindo ao Petshop!");
        tituloPrincipal.setFont(Font.font("Arial", 28));
        BorderPane.setAlignment(tituloPrincipal, Pos.CENTER);
        
        Text tituloServicos = new Text("Serviços do PetShop");
        tituloServicos.setFont(Font.font("Arial", 24));
        BorderPane.setAlignment(tituloServicos, Pos.CENTER);

        

    }
    
}
