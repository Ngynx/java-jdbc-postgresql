package AccesoDatos.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import AccesoDatos.dto.PersonaDTO;
import AccesoDatos.utils.ConnectionToPSQL;

public class PersonaDAO {
    private Connection conexion;

    public PersonaDAO() throws SQLException {
        // Obtenemos la conexión usando tu clase personalizada
        conexion = ConnectionToPSQL.getInstancia().getConexion();
    };

    public void insertarPersona(PersonaDTO persona) throws SQLException {
        String sql = "{call usp_crearpersona(?, ?, ?, ?)}";

        // try-with-resources cierra automáticamente el CallableStatement al terminar
        try (CallableStatement stmt = conexion.prepareCall(sql)) {

            stmt.setString(1, persona.getNombre());
            stmt.setDate(2, Date.valueOf(persona.getFechaNacimiento())); // Índice 2 corregido (asegúrate de que sea java.sql.Date)
            stmt.setBoolean(3, persona.isActivo());       // Índice 3
            stmt.setBigDecimal(4, java.math.BigDecimal.valueOf(persona.getSalario()));

            stmt.execute();
            System.out.println("¡Persona insertada correctamente!");
        } catch (SQLException e) {
            throw new SQLException("Error al insertar la persona: " + e.getMessage());
        }
    };;


    public void actualizarPersona(PersonaDTO persona) throws SQLException {
        String sql = "CALL public.usp_actualizarpersona(?, ?, CAST(? AS date), ?, CAST(? AS numeric))";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, persona.getId());
            stmt.setString(2, persona.getNombre());
            stmt.setDate(3, Date.valueOf(persona.getFechaNacimiento()));
            stmt.setBoolean(4, persona.isActivo());
            stmt.setBigDecimal(5, java.math.BigDecimal.valueOf(persona.getSalario()));

            stmt.execute();
            System.out.println("¡Persona actualizada correctamente!");
            
        } catch (SQLException e) {
            throw new SQLException("Error al actualizar la persona con ID " + persona.getId() + ": " + e.getMessage());
        }
    }

    public void eliminarPersona(int id) throws SQLException {
        String sql = "{call usp_eliminarpersona(?)}";

        try (CallableStatement stmt = conexion.prepareCall(sql)) {
            
            // Pasamos el ID como único parámetro
            stmt.setInt(1, id);

            stmt.execute();
            System.out.println("¡Persona eliminada correctamente!");
            
        } catch (SQLException e) {
            throw new SQLException("Error al eliminar la persona con ID " + id + ": " + e.getMessage());
        }
    }

    public PersonaDTO obtenerPersonaPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usp_ObtenerPersona(?)";
        PersonaDTO persona = null;

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new PersonaDTO(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDate("fecha_nacimiento") != null ? rs.getDate("fecha_nacimiento").toLocalDate() : null,
                        rs.getBoolean("activo"),
                        rs.getDouble("salario")
                    );
                }
            }
            return null;
        } catch (SQLException e) {
            throw new SQLException("Error al obtener la persona con ID " + id + ": " + e.getMessage());
        }
        // return persona;
    }

    public List<PersonaDTO> listarPersonas() throws SQLException {
        String sql = "SELECT * FROM usp_ListarPersonas()";
        List<PersonaDTO> personas = new ArrayList<>();

        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Instanciamos el DTO directamente con el constructor por cada fila encontrada
                PersonaDTO persona = new PersonaDTO(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getDate("fecha_nacimiento") != null ? rs.getDate("fecha_nacimiento").toLocalDate() : null,
                    rs.getBoolean("activo"),
                    rs.getDouble("salario")
                );
                
                personas.add(persona);
            }
        } catch (SQLException e) {
            throw new SQLException("Error al listar las personas: " + e.getMessage());
        }
        return personas;
    }
}
