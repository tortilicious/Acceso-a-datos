package Tarea_;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)   // Hace que solo use los atributos privados en lugar de los getters y setters
public class Departamento {
    @XmlAttribute(name = "id")
    private String id;

    @XmlElement(name = "LOC")
    private String localizacion;

    @XmlElement(name = "DNOMBRE")
    private String nombre;

    @XmlElementWrapper(name = "EMPLEADOS")
    @XmlElement(name = "EMPLEADO")
    private List<Empleado> empleados = new ArrayList<>();

    public Departamento() {
    }

    public Departamento(String id, String localizacion, String nombre) {
        this.id = id;
        this.localizacion = localizacion;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public void addEmpleado(Empleado empleado) {
        this.empleados.add(empleado);
    }

    @Override
    public String toString() {
        return "Departamento{" + "id='" + id + '\'' + ", nombre='" + nombre + '\'' +
                ", empleados=" + empleados.size() + '}';
    }
}
