package projetoricardo

import grails.gorm.services.Service

@Service(Empregado)
interface EmpregadoService {

    Empregado get(Serializable id)

    List<Empregado> list(Map args)

    Long count()

    Empregado delete(Serializable id)

    Empregado save(Empregado empregado)

}
