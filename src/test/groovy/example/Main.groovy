package example

import com.github.choonchernlim.modularizer.core.ModularizerService
import groovy.json.JsonOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.stereotype.Service

/**
 * Run this main class to see how it works.
 */
@Service
class Main {
    private final ModularizerService modularizerService

    @Autowired
    Main(final ModularizerService modularizerService) {
        this.modularizerService = modularizerService
    }

    void run() {
        // In real world, this entity typically represents an ORM entity that is queried from DB.
        final PersonEntity personEntity = new PersonEntity(
                lastName: 'Bond',
                firstName: 'James',
                email: 'james.bond@gmail.com'
        )

        // =====================================================================================================
        // Requesting just "summary" module...
        // =====================================================================================================
        final PersonResult personResult1 = modularizerService.create(
                personEntity,
                [PersonModuleEnum.SUMMARY] as Set,
                PersonResult
        )

        /*
        Notes:
        - "details" module is `null` because that module wasn't requested.

        {
            "id": "james.bond@gmail.com",
            "fingerprint": "E0DFEE726D7BDBE7E5CFE916A1102744BFD95248A33C7518E84607B9FBBB388F",
            "summary": {
                "fingerprint": "4B7AB5A2BE7D95C32AD469879A9C2AAF5CFF9D971F78AEBC347489503B583651",
                "name": "Bond, James"
            },
            "detail": null
        }
        */
        displayAsJson(personResult1)

        // =====================================================================================================
        // Requesting both "summary" and "detail" modules...
        // =====================================================================================================
        final PersonResult personResult2 = modularizerService.create(
                personEntity,
                [PersonModuleEnum.SUMMARY, PersonModuleEnum.DETAIL] as Set,
                PersonResult
        )

        /*
        Notes:
        - The root fingerprint changes because it contains "details" payload now.
        - Both `personResult1` and `personResult2` has the same "summary" fingerprint value because the content is the same.
        
        {
            "id": "james.bond@gmail.com",
            "fingerprint": "6A6181CD869BEBE546F70954CAFA4C47B7EC35924B4BA16AF8ABFFFA37D4D286",
            "summary": {
                "fingerprint": "4B7AB5A2BE7D95C32AD469879A9C2AAF5CFF9D971F78AEBC347489503B583651",
                "name": "Bond, James"
            },
            "detail": {
                "firstName": "James",
                "lastName": "Bond",
                "email": "james.bond@gmail.com",
                "fingerprint": "547A0919025E911854256A271A05F3D8DA89D18DE8113AF38D6B8EA63D6C1BA3"
            }
        }
        */
        displayAsJson(personResult2)
    }

    static void displayAsJson(final Object object) {
        println JsonOutput.prettyPrint(JsonOutput.toJson(object))
    }

    static void main(String[] args) {
        new AnnotationConfigApplicationContext(SpringConfig).getBean(Main).run()
    }
}
