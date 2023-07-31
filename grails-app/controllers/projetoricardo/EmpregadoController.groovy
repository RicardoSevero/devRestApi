package projetoricardo

import grails.converters.JSON
import grails.validation.ValidationException

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY
import projetoricardo.EmpregadoDTO
import groovy.json.JsonOutput
import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.DeserializationFeature

@ReadOnly
class EmpregadoController {

    EmpregadoService empregadoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)

        // Obter a lista de empregados do serviço
        List<Empregado> empregados = empregadoService.list(params)

        // Converter a lista de Empregado para EmpregadoDTO
        List<EmpregadoDTO> empregadosDTO = empregados.collect { empregado ->
                new EmpregadoDTO(empregado)
        }

        // Retornar a lista de EmpregadoDTO como JSON
        render empregadosDTO as JSON
    }

    def show(Long id) {
        def empregado = empregadoService.get(id)
        if (empregado) {
            def empregadoDTO = new EmpregadoDTO(empregado)
            render empregadoDTO as JSON
        } else {
            render status: NOT_FOUND
            return
        }
    }

    @Transactional
    def save() {
        def requestBody = request.JSON
        def empregado = new Empregado(
                nome: requestBody.nome,
                dataNascimento: parseDataNascimento(requestBody.dataNascimento),
                matricula: requestBody.matricula.toInteger(),
                departamento: Departamento.get(requestBody.departamento.toLong())
        )

        try {
            empregadoService.save(empregado)
            render status: 201, text: "Empregado cadastrado com sucesso!"
        } catch (ValidationException e) {
            respond empregado.errors, [status: BAD_REQUEST]
        }
    }

    @Transactional
    def update(Long id) {
        def requestBody = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readTree(request.getInputStream())
        def dataNascimento = requestBody.dataNascimento?.textValue()

        def empregado = empregadoService.get(id)

        if (!empregado) {
            render status: NOT_FOUND, text: "Empregado não encontrado!"
            return
        }

        if (dataNascimento) {
            empregado.dataNascimento = parseDataNascimento(dataNascimento)
        }

        if (requestBody.nome?.textValue()) {
            empregado.nome = requestBody.nome.textValue()
        }

        if (requestBody.matricula?.intValue()) {
            empregado.matricula = requestBody.matricula.intValue()
        }

        if (requestBody.departamento?.textValue()) {
            def departamento = Departamento.get(requestBody.departamento.textValue().toLong())
            if (!departamento) {
                render status: NOT_FOUND, text: "Departamento não encontrado!"
                return
            }
            empregado.departamento = departamento
        }

        if (empregado.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond empregado.errors
            return
        }

        try {
            empregadoService.save(empregado)
            render status: 201, text: "Empregado alterado com sucesso!"
        } catch (ValidationException e) {
            respond empregado.errors
            return
        }
    }

    private static LocalDate parseDataNascimento(String dataNascimento) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return LocalDate.parse(dataNascimento, dateFormatter)
    }

    @Transactional
    def delete(Long id) {
        if (id == null || empregadoService.delete(id) == null) {
            render status: NOT_FOUND, text: "Empregado não encontrado!"
            return
        }

        render status: 201, text: "Empregado deletado com sucesso!"
    }
}