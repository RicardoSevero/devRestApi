package projetoricardo

import java.time.format.DateTimeFormatter

class EmpregadoDTO {

    Long id
    String nome
    String dataNascimento
    String departamentoNome
    Integer matricula
    long departamentoId

    // Construtor para criar o DTO a partir da classe Empregado
    EmpregadoDTO(Empregado empregado) {
        this.id = empregado.id
        this.nome = empregado.nome
        this.dataNascimento = empregado.dataNascimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        this.matricula = empregado.matricula
        this.departamentoId = empregado.departamento.id
        this.departamentoNome = empregado.departamento.nome
    }

    static constraints = {
    }
}
