package com.github.watabee.devapp

import org.junit.Assert
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object ExampleUnitTest : Spek({

    describe("An example") {
        it("should be same value") {
            Assert.assertEquals(4, 2 + 2)
        }
    }
})
