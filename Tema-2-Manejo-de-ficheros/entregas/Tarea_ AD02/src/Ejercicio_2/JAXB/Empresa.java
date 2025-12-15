//package JAXB;
//
//import jakarta.xml.bind.annotation.XmlAccessType;
//import jakarta.xml.bind.annotation.XmlAccessorType;
//import jakarta.xml.bind.annotation.XmlElement;
//import jakarta.xml.bind.annotation.XmlRootElement;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlRootElement(name = "EMPRESA")
//public class Empresa {
//    @XmlElement(name = "TITULO")
//    private String titulo;
//
//    @XmlElement(name = "DEPARTAMENTO")
//    private List<Tarea_.Departamento> departamentos = new ArrayList<>();
//
//    public Empresa() {
//    }
//
//    public Empresa(String titulo) {
//        this.titulo = titulo;
//    }
//
//    public String getTitulo() {
//        return titulo;
//    }
//
//    public void setTitulo(String titulo) {
//        this.titulo = titulo;
//    }
//
//    public List<Departamento> getDepartamentos() {
//        return departamentos;
//    }
//
//    public void setDepartamentos(List<Departamento> departamentos) {
//        this.departamentos = departamentos;
//    }
//
//    public void addDepartamento(Departamento departamento) {
//        this.departamentos.add(departamento);
//    }
//
//    @Override
//    public String toString() {
//        return "Empresa{" + "titulo='" + titulo + '\'' +
//                ", departamentos=" + departamentos.size() + '}';
//    }
//}