package projetoricardo

import grails.converters.JSON
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class BootStrap {

    def init = { servletContext ->
       createMockup()

        JSON.registerObjectMarshaller(LocalDate) { LocalDate date ->
            date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        }
    }

    void createMockup(){

        // Criar os departamentos
        def departamentoDev = new Departamento(nome:"Desenvolvimento").save()
        def departamentoCon = new Departamento(nome:"Consultoria").save()
        def departamentoSup = new Departamento(nome:"Suporte").save()

        // Criar os empregados e vinculá-los aos departamentos
        def empregado1 = new Empregado(nome: "João Peidão", dataNascimento: LocalDate.parse("10/10/2000", DateTimeFormatter.ofPattern("dd/MM/yyyy")), matricula: 1234, departamento: departamentoDev).save()
        def empregado2 = new Empregado(nome: "Ricardo", dataNascimento: LocalDate.parse("24/05/1996", DateTimeFormatter.ofPattern("dd/MM/yyyy")), matricula: 5678, departamento: departamentoCon).save()
        def empregado3 = new Empregado(nome: "Luan", dataNascimento: LocalDate.parse("10/10/2001", DateTimeFormatter.ofPattern("dd/MM/yyyy")), matricula: 9876, departamento: departamentoCon).save()
        def empregado4 = new Empregado(nome: "Guilherme", dataNascimento: LocalDate.parse("10/10/2002", DateTimeFormatter.ofPattern("dd/MM/yyyy")), matricula: 6543, departamento: departamentoSup).save()
        def empregado5 = new Empregado(nome: "Vinicius", dataNascimento: LocalDate.parse("10/10/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")), matricula: 3456, departamento: departamentoSup).save()
    }

    def destroy = {
    }
}
