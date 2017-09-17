package com.musingscafe.commons

import java.util.Optional

import com.musingscafe.commons.`object`.Obj

/**
  * Created by ayadav on 9/16/17.
  */
class ObjSpecs extends CommonsSpecs {
  describe("Object nullability") {
    it ("should toOptional or SupplierElse") {
      Given("an object")
      val nullableString: String = null

      When("asked for")
      val response = Obj.getOrElse(nullableString, "1")

      Then("1 is returned")
      response should be ("1")
    }

    it ("should return an Optional") {
      Given("an object")
      val nullableString: String = null

      When("asked for")
      val response = Obj.toOptional(nullableString)

      Then("Optional is returned")
      response.isInstanceOf[Optional[String]] should be (true)
    }
  }
}
