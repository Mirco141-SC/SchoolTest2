import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Test

class SchoolTestTest {
    /**** Constructor ****/

    @Test
    fun constructor_studentsValueLowerThan0_IllegalArgumentException(){
        assertThrows<IllegalArgumentException> { SchoolTest(-5) }
    }

    @Test
    fun constructor_studentsValueEqualTo0_IllegalArgumentException(){
        assertThrows<IllegalArgumentException> { SchoolTest(0) }
    }

    @Test
    fun constructor_minMarkValueHigherThanMaxMarkValue_IllegalArgumentException(){
        assertThrows<IllegalArgumentException> { SchoolTest(24, 10.0, 1.0) }
    }

    @Test
    fun constructor_minMarkValueNotDeclared_minMarkValueSetToDefault(){
        val test = SchoolTest(24, maxMarkValue = 10.0)

        assertEquals(1.0, test.minMark)
    }

    @Test
    fun constructor_maxMarkValueNotDeclared_maxMarkValueSetToDefault(){
        val test = SchoolTest(24, 2.0)

        assertEquals(10.0, test.maxMark)
    }

    @Test
    fun constructor_allValuesMeetRequirements_objectCreated() {
        assertDoesNotThrow { SchoolTest(24, 2.0, 10.0) }
    }

    /**** Getters ****/

    @Test
    fun getStudents_valueReturned() {
        val test = SchoolTest(24, 2.0, 10.0)

        assertEquals(24, test.students)
    }

    @Test
    fun getMinMark_valueReturned() {
        val test = SchoolTest(24, 2.0, 10.0)

        assertEquals(2.0, test.minMark)
    }

    @Test
    fun getMaxMark_valueReturned() {
        val test = SchoolTest(24, 2.0, 10.0)

        assertEquals(10.0, test.maxMark)
    }

    @Test
    fun getAverage_valueReturned() {
        val test = SchoolTest(24, 2.0, 10.0)

        test.addMark(1, 7.5)
        test.addMark(2, 8.0)
        test.addMark(3,9.0)

        val expected = (7.5 + 8.0 + 9.0)/3

        assertEquals(expected, test.average, 0.05)
    }

    @Test
    fun getMaxMarkOccurrence_valueReturned() {
        val test = SchoolTest(24, 2.0, 10.0)

        test.addMark(1, 2.0)
        test.addMark(2,8.0)
        test.addMark(3, 10.0)

        assertEquals(1, test.maxMarkOccurrence)
    }

    @Test
    fun getMinMarkOccurrence_valueReturned() {
        val test = SchoolTest(24, 2.0, 10.0)

        test.addMark(1, 2.0)
        test.addMark(2, 8.0)
        test.addMark(3, 10.0)

        assertEquals(1, test.minMarkOccurrence)
    }

    @Test
    fun getAbsentStudents_valueReturned() {
        val test = SchoolTest(24, 2.0, 10.0)

        test.addMark(16, 9.0)

        assertEquals(23, test.absentStudents)
    }

    /**** Setters ****/

    @Test
    fun setMinMark_valueHigherThanMaxMark_IllegalArgumentException() {
        val test = SchoolTest(24, 2.0, 10.0)

        assertThrows<IllegalArgumentException> { test.minMark = 15.0 }
    }

    @Test
    fun setMinMark_invalidMarksPresent_IllegalArgumentException() {
        val test = SchoolTest(24, 1.0, 10.0)

        test.addMark(1, 1.0)
        test.addMark(2, 1.5)
        test.addMark(3, 9.0)
        test.addMark(4, 7.0)

        assertThrows<IllegalArgumentException> { test.minMark = 2.0 }
    }

    @Test
    fun setMinMark_valueLowerThanMaxMark_valueSet() {
        val test = SchoolTest(24, 2.0, 10.0)

        test.minMark = 1.0

        assertEquals(1.0, test.minMark)
    }

    @Test
    fun setMaxMark_valueLowerThanMinMark_IllegalArgumentException() {
        val test = SchoolTest(24, 2.0, 10.0)

        assertThrows<IllegalArgumentException> { test.maxMark = 1.0 }
    }

    @Test
    fun setMaxMark_invalidMarksPresent_IllegalArgumentException() {
        val test = SchoolTest(24, 1.0, 10.0)

        test.addMark(value = 1.0)
        test.addMark(value = 7.5)
        test.addMark(value = 10.0)
        test.addMark(value = 9.0)

        assertThrows<IllegalArgumentException> { test.maxMark = 9.0 }
    }

    @Test
    fun setMaxMark_valueHigherThanMinMark_valueSet() {
        val test = SchoolTest(24, 2.0, 10.0)

        test.maxMark = 9.0

        assertEquals(9.0, test.maxMark)
    }

    /**** Functions ****/

    @Test
    fun addMark_valueLowerThanRange_IllegalArgumentException() {
        val test = SchoolTest(24, 2.0, 10.0)

        assertThrows<IllegalArgumentException> { test.addMark(value = 1.0) }
    }

    @Test
    fun addMark_valueHigherThanRange_IllegalArgumentException() {
        val test = SchoolTest(24, 2.0, 10.0)

        assertThrows<IllegalArgumentException> { test.addMark(value = 11.0) }
    }

    @Test
    fun addMark_identifierInputLowerThanRange_IllegalArgumentException() {
        val test = SchoolTest(24, 2.0, 10.0)

        assertThrows<IllegalArgumentException> { test.addMark(0, 9.0)}
    }

    @Test
    fun addMark_identifierInputHigherThanRange_IllegalArgumentException() {
        val test = SchoolTest(24, 2.0, 10.0)

        assertThrows<IllegalArgumentException> { test.addMark(26, 9.0)}
    }

    @Test
    fun addMark_valueRequiresEditingStudents_studentsEdited() {
        val test = SchoolTest(1, 2.0, 10.0)

        test.addMark(value = 9.0)

        assertEquals(2, test.students)
    }

    @Test
    fun addMark_identifierNotPassed_valueGetsAddedAtTheEnd() {
        val test = SchoolTest(1, 2.0, 10.0)

        test.addMark(value = 7.5)

        assertEquals(7.5, test.getMark(2))
    }

    @Test
    fun addMark_identifierPassed_markAddedAtCorrectPosition() {
        val test = SchoolTest(24, 2.0, 10.0)

        test.addMark(5, 7.5)

        assertEquals(7.5, test.getMark(5))
    }

    @Test
    fun editMark_identifierDoesNotExists_IllegalArgumentException() {
        val test = SchoolTest(1, 2.0, 10.0)

        assertThrows<IllegalArgumentException> { test.editMark(3, 9.0) }
    }

    @Test
    fun editMark_identifierLowerThan0_IllegalArgumentException() {
        val test = SchoolTest(1, 2.0, 10.0)

        assertThrows<IllegalArgumentException> { test.editMark(-2, 9.0) }
    }

    @Test
    fun editMark_valueLowerThanRange_IllegalArgumentException() {
        val test = SchoolTest(1, 2.0, 10.0)

        assertThrows<IllegalArgumentException> { test.editMark(1, 1.0) }
    }

    @Test
    fun editMark_valueHigherThanRange_IllegalArgumentException() {
        val test = SchoolTest(1, 2.0, 10.0)

        assertThrows<IllegalArgumentException> { test.editMark(1, 11.0) }
    }

    @Test
    fun editMark_allParametersMeetRequirements_markEdited() {
        val test = SchoolTest(1, 2.0, 10.0)

        test.editMark(1, 9.0)

        assertEquals(9.0, test.getMark(1))
    }

    @Test
    fun getMark_identifierInputLowerThanRange_IllegalArgumentException() {
        val test = SchoolTest(24, 2.0, 10.0)

        assertThrows<IllegalArgumentException> {test.getMark(0)}
    }

    @Test
    fun getMark_identifierInputHigherThanRange_IllegalArgumentException() {
        val test = SchoolTest(24, 2.0, 10.0)

        assertThrows<IllegalArgumentException> {test.getMark(25)}
    }

    @Test
    fun getMark_markIsNull_nullReturned() {
        val test = SchoolTest(24, 2.0, 10.0)

        assertEquals(null, test.getMark(10))
    }

    @Test
    fun getMark_markExists_markReturned() {
        val test = SchoolTest(24, 2.0, 10.0)

        test.addMark(10, 7.5)

        assertEquals(7.5, test.getMark(10))
    }

    @Test
    fun occurrence_valueReturned() {
        val test = SchoolTest(24, 2.0, 10.0)

        test.addMark(1, 2.0)
        test.addMark(7, 9.0)
        test.addMark(12, 10.0)
        test.addMark(20, 9.0)

        assertEquals(2, test.occurrence(9.0))
    }
}