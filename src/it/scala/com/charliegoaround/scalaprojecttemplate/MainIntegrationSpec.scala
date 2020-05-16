package com.charliegoaround.scalaprojecttemplate

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MainIntegrationSpec extends AnyFlatSpec with Matchers {

  it should "be fun" in {
    1 should equal(1)
  }
}
