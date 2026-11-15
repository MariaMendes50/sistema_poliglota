import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Processador {
    public static void main(String[] args) {
        // Uso do try-with-resources para fechar Connection, Statement e ResultSet automaticamente
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sistema_poliglota", "root", "");
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM alunos WHERE matricula = 'Pendente' LIMIT 1")) {
            
            if (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome").toUpperCase();
                
                String matriculaFicticia = "MAT-" + (1000 + id);
                
                String sqlUpdate = "UPDATE alunos SET nome = '" + nome + "', matricula = '" + matriculaFicticia + "' WHERE id = " + id;
                stmt.executeUpdate(sqlUpdate);
                
                System.out.println("Java processou o aluno: " + nome + " | Matricula: " + matriculaFicticia);
            } else {
                System.out.println("Nenhum aluno pendente para processar.");
            }
            
        } catch (SQLException e) {
            System.out.println("Erro de Base de Dados: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro geral: " + e.getMessage());
        }
    }
}