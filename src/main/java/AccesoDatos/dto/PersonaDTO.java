package AccesoDatos.dto;

import java.time.LocalDate;

public class PersonaDTO {
    private int id;
    private String nombre;
    private LocalDate fechaNacimiento;
    private boolean activo; 
    private double salario;

    public PersonaDTO(int id, String nombre, LocalDate fechaNacimiento, boolean activo, double salario) {
        this.id = id;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.activo = activo;
        this.salario = salario;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public boolean isActivo() {
        return activo;
    }

    public double getSalario() {
        return salario;
    };

    @Override 
    public String toString() {
        return "PersonaDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", activo=" + activo +
                ", salario=" + salario +
                '}';
    };

    
}
