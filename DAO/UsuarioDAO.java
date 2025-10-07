package DAO;

import java.sql.*;

import DataBase.Database;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UsuarioDAO {
    
    private static String gerarHash(String senha) {
        try {
            MessageDigest msg = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = msg.digest(senha.getBytes());
            StringBuilder sb = new StringBuilder();
            for(byte b : hashBytes){
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        }catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash da senha", e);
        }
    }

    public static boolean cadastrar(String nome, String email, String senha){
        String sql = "INSERT INTO usuarios (nome, email, senha) VALUES (?, ?, ?)";

        try(Connection conexao = Database.getConeConnection();
        PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setString(1, nome);
            stm.setString(2, email);
            stm.setString(3,gerarHash(senha));
            stm.executeUpdate();
            return true;

        } catch(SQLIntegrityConstraintViolationException e) {
            System.out.println("Email ja cadastrado!");
            return false;
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean validarLogin(String email, String senhaDigitada) {
        String sql = "SELECT senha FROM usuarios WHERE email = ?";

        try(Connection conexao = Database.getConeConnection();
            PreparedStatement smt = conexao.prepareStatement(sql)){

                smt.setString(1, email);
                ResultSet resultado = smt.executeQuery();

                if(resultado.next()){
                    String senhaBanco = resultado.getString("senha");
                    return senhaBanco.equals(gerarHash(senhaDigitada));
                } else {
                    return false;
                }
            }catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
    
    }
    
}
