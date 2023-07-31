package projetoricardo

import grails.gorm.services.Service

@Service(Departamento)
interface DepartamentoService {

    Departamento get(Serializable id)

    List<Departamento> list(Map args)

    Long count()

    Departamento delete(Serializable id)

    Departamento save(Departamento departamento)

}
