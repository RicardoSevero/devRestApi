package projetoricardo

import com.fasterxml.jackson.annotation.JsonFormat

import java.time.LocalDate

class Empregado {

    Long id
    String nome
    @JsonFormat(pattern = "dd/MM/yyyy") // Configura o formato de data
    LocalDate dataNascimento
    Integer matricula

    static belongsTo = [departamento: Departamento]

    static constraints = {
        nome blank: false
        nome nullable: false
        dataNascimento blank: false
        dataNascimento nullable: false
        matricula blank: false
        matricula unique: true
        matricula nullable: false
        departamento blank: false
        departamento nullable: false
    }
}
