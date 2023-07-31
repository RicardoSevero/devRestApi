package projetoricardo

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class DepartamentoController {

    DepartamentoService departamentoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond departamentoService.list(params), model:[departamentoCount: departamentoService.count()]
    }

    def show(Long id) {
        respond departamentoService.get(id)
    }

    @Transactional
    def save(Departamento departamento) {
        if (departamento == null) {
            render status: NOT_FOUND
            return
        }
        if (departamento.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond departamento.errors
            return
        }

        try {
            departamentoService.save(departamento)
        } catch (ValidationException e) {
            respond departamento.errors
            return
        }

        render "REGISTRO ADICIONADO COM SUCESSO!"
        respond departamento, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Departamento departamento) {
        if (departamento == null) {
            render status: NOT_FOUND
            return
        }
        if (departamento.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond departamento.errors
            return
        }

        try {
            departamentoService.save(departamento)
        } catch (ValidationException e) {
            respond departamento.errors
            return
        }

        render "REGISTRO ALTERADO COM SUCESSO!"
        respond departamento, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || departamentoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render "REGISTRO DELETADO COM SUCESSO!"
    }
}
