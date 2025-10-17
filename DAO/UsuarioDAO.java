package DAO;

import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import DataBase.Database;

public class UsuarioDAO {

    // ======== MÉTODO PARA GERAR HASH DA SENHA ========
    private static String gerarHash(String senha) {
        try {
            MessageDigest msg = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = msg.digest(senha.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash da senha", e);
        }
    }

    // ======== CADASTRAR NOVO USUÁRIO ========
    public static boolean cadastrar(String nome, String email, String senha) {
        String sql = "INSERT INTO usuarios (nome, email, senha) VALUES (?, ?, ?)";

        try (Connection conexao = Database.getConeConnection();
             PreparedStatement stm = conexao.prepareStatement(sql)) {

            stm.setString(1, nome);
            stm.setString(2, email);
            stm.setString(3, gerarHash(senha));
            stm.executeUpdate();
            return true;

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Email já cadastrado!");
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ======== VALIDAR LOGIN ========
    public static boolean validarLogin(String email, String senhaDigitada) {
        String sql = "SELECT senha FROM usuarios WHERE email = ?";

        try (Connection conexao = Database.getConeConnection();
             PreparedStatement smt = conexao.prepareStatement(sql)) {

            smt.setString(1, email);
            ResultSet resultado = smt.executeQuery();

            if (resultado.next()) {
                String senhaBanco = resultado.getString("senha");
                return senhaBanco.equals(gerarHash(senhaDigitada));
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ======== ALTERAR SENHA ========
    public static boolean alterarSenha(String email, String novaSenha) {
        String sql = "UPDATE usuarios SET senha = ? WHERE email = ?";

        try (Connection conexao = Database.getConeConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, gerarHash(novaSenha));
            stmt.setString(2, email);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ======== ATUALIZAR FOTO DE PERFIL ========
    public static void atualizarFoto(String email, byte[] imagemBytes) {
        String sql = "UPDATE usuarios SET foto = ? WHERE email = ?";

        try (Connection conexao = Database.getConeConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setBytes(1, imagemBytes);
            stmt.setString(2, email);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ======== CARREGAR FOTO DO USUÁRIO ========
    public static byte[] carregarFoto(String email) {
        String sql = "SELECT foto FROM usuarios WHERE email = ?";

        try (Connection conexao = Database.getConeConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getBytes("foto");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ======== OBTER NOME DO USUÁRIO (OU OUTRAS INFORMAÇÕES) ========
    public static String obterNomePorEmail(String email) {
        String sql = "SELECT nome FROM usuarios WHERE email = ?";

        try (Connection conexao = Database.getConeConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("nome");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

