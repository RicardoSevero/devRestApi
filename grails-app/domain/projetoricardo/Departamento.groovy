package projetoricardo

class Departamento {

    Long id
    String nome

    static hasMany = [empregados:Empregado]

    static constraints = {
        id unique: true
        nome nullable: false
        nome blank: false
    }
}
