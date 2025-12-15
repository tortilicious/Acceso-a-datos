//package JAXB;
//
//import jakarta.xml.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@XmlAccessorType(XmlAccessType.FIELD)
//public class Empleado {
//    @XmlAttribute(name = "numero")
//    private String numero;
//
//    @XmlElement(name = "APELLIDO")
//    private String apellido;
//
//    @XmlElement(name = "OFICIO")
//    private String oficio;
//
//    @XmlElement(name = "DIR")
//    private String dir;
//
//    @XmlElement(name = "FECHA_ALT")
//    private String fechaAlta;
//
//    @XmlElement(name = "SALARIO")
//    private String salario;
//
//    @XmlElement(name = "COMISION")
//    private String comision;
//
//    public Empleado() {
//    }
//
//    public Empleado(String numero, String apellido, String oficio, String dir,
//                    String fechaAlta, String salario, String comision) {
//        this.numero = numero;
//        this.apellido = apellido;
//        this.oficio = oficio;
//        this.dir = dir;
//        this.fechaAlta = fechaAlta;
//        this.salario = salario;
//        this.comision = comision;
//    }
//
//    public String getNumero() {
//        return numero;
//    }
//
//    public void setNumero(String numero) {
//        this.numero = numero;
//    }
//
//    public String getApellido() {
//        return apellido;
//    }
//
//    public void setApellido(String apellido) {
//        this.apellido = apellido;
//    }
//
//    public String getOficio() {
//        return oficio;
//    }
//
//    public void setOficio(String oficio) {
//        this.oficio = oficio;
//    }
//
//    public String getDir() {
//        return dir;
//    }
//
//    public void setDir(String dir) {
//        this.dir = dir;
//    }
//
//    public String getFechaAlta() {
//        return fechaAlta;
//    }
//
//    public void setFechaAlta(String fechaAlta) {
//        this.fechaAlta = fechaAlta;
//    }
//
//    public String getSalario() {
//        return salario;
//    }
//
//    public void setSalario(String salario) {
//        this.salario = salario;
//    }
//
//    public String getComision() {
//        return comision;
//    }
//
//    public void setComision(String comision) {
//        this.comision = comision;
//    }
//
//    @Override
//    public String toString() {
//        return "Empleado{" + "numero='" + numero + '\'' + ", apellido='" + apellido + '\'' + '}';
//    }
//}