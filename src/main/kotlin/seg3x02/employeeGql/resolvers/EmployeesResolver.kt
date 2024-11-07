package seg3x02.employeeGql.resolvers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeesRepository
import seg3x02.employeeGql.resolvers.types.CreateEmployeeInput
import java.util.*

@Controller
class EmployeesResolver @Autowired constructor(
    private val employeesRepository: EmployeesRepository
) {

    // Query to get all employees
    @QueryMapping
    fun employees(): List<Employee> {
        return employeesRepository.findAll()
    }

    // Mutation to add a new employee
    @MutationMapping
    fun addEmployee(@Argument createEmployeeInput: CreateEmployeeInput): Employee {
        val employee = Employee(
            id = UUID.randomUUID().toString(),
            name = createEmployeeInput.name ?: throw IllegalArgumentException("Name is required"),
            dateOfBirth = createEmployeeInput.dateOfBirth
                ?: throw IllegalArgumentException("Date of birth is required"),
            city = createEmployeeInput.city ?: throw IllegalArgumentException("City is required"),
            salary = createEmployeeInput.salary ?: throw IllegalArgumentException("Salary is required"),
            gender = createEmployeeInput.gender,
            email = createEmployeeInput.email
        )
        employeesRepository.save(employee)
        return employee

    }
}


