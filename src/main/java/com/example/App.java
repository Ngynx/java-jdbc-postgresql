package com.example;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import AccesoDatos.dao.PersonaDAO;
import AccesoDatos.dto.PersonaDTO;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        // System.out.println( "Hello World!" );
        try {
            // Instanciamos el DAO (Esto abrirá la conexión usando tu Singleton ConnectionToPSQL)
            PersonaDAO personaDAO = new PersonaDAO();

            // 1. PROBAR INSERCIÓN
            System.out.println("\n--- 1. Insertando nueva persona ---");
            // Creamos el DTO (sin ID porque la base de datos lo genera con SERIAL)
            PersonaDTO nuevaPersona = new PersonaDTO(
                0, // ID temporal (lo asignará la BD)
                "Sofía Martínez",
                LocalDate.of(1995, 8, 20),
                true,
                3200.50
            );

            personaDAO.insertarPersona(nuevaPersona);

            // 2. PROBAR LISTADO GENERAL
            System.out.println("\n--- 2. Listando todas las personas ---");
            List<PersonaDTO> personas = personaDAO.listarPersonas();
            
            for (PersonaDTO p : personas) {
                System.out.println("ID: " + p.getId() + 
                                   " | Nombre: " + p.getNombre() + 
                                   " | F. Nacimiento: " + p.getFechaNacimiento() + 
                                   " | Activo: " + p.isActivo() + 
                                   " | Salario: " + p.getSalario());
            }

            // Validamos que exista al menos un registro para probar las siguientes operaciones
            if (!personas.isEmpty()) {
                // Tomamos el ID del primer registro listado para las pruebas
                int idPrueba = personas.get(0).getId();

                // 3. PROBAR OBTENER POR ID
                System.out.println("\n--- 3. Obteniendo persona por ID (" + idPrueba + ") ---");
                PersonaDTO personaEncontrada = personaDAO.obtenerPersonaPorId(idPrueba);
                
                if (personaEncontrada != null) {
                    System.out.println("¡Encontrada! -> Nombre: " + personaEncontrada.getNombre() + 
                                       ", Salario: " + personaEncontrada.getSalario());

                    // 4. PROBAR ACTUALIZACIÓN
                    System.out.println("\n--- 4. Actualizando la persona con ID " + idPrueba + " ---");
                    personaEncontrada.setNombre("Sofía Martínez Actualizada");
                    personaEncontrada.setSalario(3800.00);
                    
                    personaDAO.actualizarPersona(personaEncontrada);
                }

                // 5. PROBAR ELIMINACIÓN
                System.out.println("\n--- 5. Eliminando la persona con ID " + idPrueba + " ---");
                personaDAO.eliminarPersona(idPrueba);

                // Verificamos el listado final
                System.out.println("\n--- 6. Listado final después de eliminar ---");
                List<PersonaDTO> personasFinales = personaDAO.listarPersonas();
                System.out.println("Total de personas restantes en la base de datos: " + personasFinales.size());
            } else {
                System.out.println("No hay registros en la base de datos para probar las operaciones de ID, Actualizar y Eliminar.");
            }

        } catch (SQLException e) {
            System.err.println("¡Ocurrió un error SQL durante las pruebas!: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
